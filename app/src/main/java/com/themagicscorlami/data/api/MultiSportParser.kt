package com.themagicscorlami.data.api

import com.themagicscorlami.data.model.*
import org.json.JSONObject

object MultiSportParser {

    fun parse(jsonString: String, sport: Sport): List<MultiSportGame> {
        val games = mutableListOf<MultiSportGame>()
        try {
            val root = JSONObject(jsonString)
            val events = root.optJSONArray("events") ?: return emptyList()

            for (i in 0 until events.length()) {
                val event = events.optJSONObject(i) ?: continue
                val game = parseEvent(event, sport)
                if (game != null) {
                    games.add(game)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return games
    }

    private fun parseEvent(event: JSONObject, sport: Sport): MultiSportGame? {
        val id = event.optString("id")
        if (id.isEmpty()) return null

        val name = event.optString("name", "Matchup")
        val shortName = event.optString("shortName", name)
        val dateIso = event.optString("date", "")

        // Status
        val statusObj = event.optJSONObject("status")
        val statusType = statusObj?.optJSONObject("type")
        val stateStr = statusType?.optString("state", "pre") ?: "pre"
        val stateName = statusType?.optString("name", "") ?: ""

        val gameState = when {
            stateStr == "post" || stateName.contains("FINAL", ignoreCase = true) -> SportGameState.FINAL
            stateName.contains("HALFTIME", ignoreCase = true) -> SportGameState.HALFTIME
            stateName.contains("DELAY", ignoreCase = true) -> SportGameState.DELAYED
            stateName.contains("POSTPONE", ignoreCase = true) -> SportGameState.POSTPONED
            stateStr == "in" || stateName.contains("PROGRESS", ignoreCase = true) -> SportGameState.IN_PROGRESS
            else -> SportGameState.SCHEDULED
        }

        val clock = statusObj?.optString("displayClock", "0:00") ?: "0:00"
        val period = statusObj?.optInt("period", 0) ?: 0
        val detail = statusType?.optString("detail", "") ?: ""
        val shortDetail = statusType?.optString("shortDetail", detail) ?: detail

        val gameStatus = SportGameStatus(
            state = gameState,
            period = period,
            clock = clock,
            detail = detail,
            shortDetail = shortDetail
        )

        // Competitions
        val competitions = event.optJSONArray("competitions") ?: return null
        val competition = competitions.optJSONObject(0) ?: return null

        // Venue
        val venueName = competition.optJSONObject("venue")?.optString("fullName")

        // Broadcasts
        val broadcastsList = mutableListOf<String>()
        val broadcasts = competition.optJSONArray("broadcasts")
        if (broadcasts != null) {
            for (b in 0 until broadcasts.length()) {
                val names = broadcasts.optJSONObject(b)?.optJSONArray("names")
                if (names != null) {
                    for (n in 0 until names.length()) {
                        broadcastsList.add(names.optString(n))
                    }
                }
            }
        }
        if (broadcastsList.isEmpty()) {
            val single = competition.optString("broadcast", "")
            if (single.isNotEmpty()) broadcastsList.add(single)
        }

        // Competitors
        val competitorsArray = competition.optJSONArray("competitors") ?: return null
        var homeComp: SportCompetitor? = null
        var awayComp: SportCompetitor? = null

        for (c in 0 until competitorsArray.length()) {
            val compObj = competitorsArray.optJSONObject(c) ?: continue
            val competitor = parseCompetitor(compObj, sport)
            val homeAway = compObj.optString("homeAway", "")
            if (homeAway == "home") {
                homeComp = competitor
            } else if (homeAway == "away") {
                awayComp = competitor
            } else {
                if (homeComp == null) homeComp = competitor else awayComp = competitor
            }
        }

        if (homeComp == null && awayComp != null) homeComp = awayComp
        if (awayComp == null && homeComp != null) awayComp = homeComp
        if (homeComp == null || awayComp == null) return null

        // Situation (Sport-specific)
        val situationObj = competition.optJSONObject("situation")
        val situation = if (situationObj != null && gameState == SportGameState.IN_PROGRESS) {
            parseSituation(situationObj, sport)
        } else null

        // Stat Leaders
        val leadersList = mutableListOf<SportLeader>()
        val leadersArray = competition.optJSONArray("leaders")
        if (leadersArray != null) {
            for (l in 0 until leadersArray.length()) {
                val catObj = leadersArray.optJSONObject(l) ?: continue
                val categoryName = catObj.optString("displayName", catObj.optString("name", "Leader"))
                val leaderSub = catObj.optJSONArray("leaders") ?: continue
                if (leaderSub.length() > 0) {
                    val leaderObj = leaderSub.optJSONObject(0) ?: continue
                    val displayValue = leaderObj.optString("displayValue", "")
                    val athleteName = leaderObj.optJSONObject("athlete")?.optString("fullName", "") ?: ""
                    val teamId = leaderObj.optJSONObject("team")?.optString("id", "") ?: ""
                    if (athleteName.isNotEmpty()) {
                        leadersList.add(
                            SportLeader(
                                category = categoryName,
                                athleteName = athleteName,
                                displayValue = displayValue,
                                teamId = teamId
                            )
                        )
                    }
                }
            }
        }

        // Odds & Spread
        var gameOdds: GameOdds? = null
        val oddsArray = competition.optJSONArray("odds")
        if (oddsArray != null && oddsArray.length() > 0) {
            val oddsObj = oddsArray.optJSONObject(0)
            if (oddsObj != null) {
                val details = oddsObj.optString("details").takeIf { it.isNotBlank() }
                val overUnder = oddsObj.optDouble("overUnder", -1.0).takeIf { it > 0 }
                val spread = if (oddsObj.has("spread")) oddsObj.optDouble("spread") else null
                val provider = oddsObj.optJSONObject("provider")?.optString("name")
                val awayML = oddsObj.optJSONObject("awayTeamOdds")?.optInt("moneyLine")
                val homeML = oddsObj.optJSONObject("homeTeamOdds")?.optInt("moneyLine")

                if (details != null || overUnder != null) {
                    gameOdds = GameOdds(
                        details = details,
                        overUnder = overUnder,
                        spread = spread,
                        provider = provider,
                        awayMoneyLine = awayML,
                        homeMoneyLine = homeML
                    )
                }
            }
        }

        // Combat Sports (UFC / MMA)
        var combatFight: CombatFight? = null
        if (sport == Sport.MMA_UFC) {
            val formatObj = competition.optJSONObject("format")
            val weightClass = formatObj?.optJSONObject("regulation")?.optString("displayName")
            val isTitle = formatObj?.optBoolean("titleFight", false) ?: false

            val compA = competitorsArray.optJSONObject(0)
            val compB = competitorsArray.optJSONObject(1)
            if (compA != null && compB != null) {
                val athA = compA.optJSONObject("athlete")
                val athB = compB.optJSONObject("athlete")
                val fA = Fighter(
                    id = compA.optString("id"),
                    name = athA?.optString("displayName", compA.optString("name", "Fighter")) ?: "Fighter",
                    shortName = athA?.optString("shortName", "Fighter") ?: "Fighter",
                    record = compA.optJSONArray("records")?.optJSONObject(0)?.optString("summary"),
                    headshotUrl = athA?.optJSONObject("headshot")?.optString("href"),
                    isWinner = compA.optBoolean("winner", false)
                )
                val fB = Fighter(
                    id = compB.optString("id"),
                    name = athB?.optString("displayName", compB.optString("name", "Fighter")) ?: "Fighter",
                    shortName = athB?.optString("shortName", "Fighter") ?: "Fighter",
                    record = compB.optJSONArray("records")?.optJSONObject(0)?.optString("summary"),
                    headshotUrl = athB?.optJSONObject("headshot")?.optString("href"),
                    isWinner = compB.optBoolean("winner", false)
                )
                combatFight = CombatFight(
                    fighterA = fA,
                    fighterB = fB,
                    weightClass = weightClass,
                    isTitleFight = isTitle,
                    resultText = detail.takeIf { it.isNotBlank() }
                )
            }
        }

        return MultiSportGame(
            id = id,
            sport = sport,
            name = name,
            shortName = shortName,
            dateIso = dateIso,
            status = gameStatus,
            homeTeam = homeComp,
            awayTeam = awayComp,
            situation = situation,
            broadcasts = broadcastsList.distinct(),
            leaders = leadersList,
            venueName = venueName,
            odds = gameOdds,
            combatFight = combatFight
        )
    }

    private fun parseCompetitor(compObj: JSONObject, sport: Sport): SportCompetitor {
        val teamObj = compObj.optJSONObject("team")
        val athObj = compObj.optJSONObject("athlete")
        val rawId = compObj.optString("id").ifEmpty { teamObj?.optString("id") ?: athObj?.optString("id") ?: "" }
        val scopedId = if (rawId.isNotEmpty() && !rawId.startsWith("${sport.id}_")) "${sport.id}_$rawId" else rawId
        val score = compObj.optString("score", "0").toIntOrNull() ?: 0
        val name = athObj?.optString("displayName") ?: teamObj?.optString("name", compObj.optString("name", "Competitor")) ?: "Competitor"
        val displayName = athObj?.optString("displayName") ?: teamObj?.optString("displayName", name) ?: name
        val shortName = athObj?.optString("shortName") ?: teamObj?.optString("shortDisplayName", name) ?: name
        val abbreviation = athObj?.optString("shortName")?.take(4)?.uppercase() ?: teamObj?.optString("abbreviation", name.take(4).uppercase()) ?: name.take(4).uppercase()
        val logoUrl = athObj?.optJSONObject("headshot")?.optString("href") ?: teamObj?.optString("logo")?.takeIf { it.isNotEmpty() }
        val colorHex = teamObj?.optString("color")?.takeIf { it.isNotEmpty() }
        val alternateColorHex = teamObj?.optString("alternateColor")?.takeIf { it.isNotEmpty() }

        val rankObj = compObj.optJSONObject("curatedRank")
        val currentRank = rankObj?.optInt("current", 99)?.takeIf { it in 1..25 }

        // Linescores
        val lineScoresList = mutableListOf<Int>()
        val lineScoresArray = compObj.optJSONArray("linescores")
        if (lineScoresArray != null) {
            for (ls in 0 until lineScoresArray.length()) {
                val lsObj = lineScoresArray.optJSONObject(ls) ?: continue
                val valInt = lsObj.optInt("value", lsObj.optString("displayValue", "0").toIntOrNull() ?: 0)
                lineScoresList.add(valInt)
            }
        }

        // Record
        var recordSummary: String? = null
        val recordsArray = compObj.optJSONArray("records")
        if (recordsArray != null) {
            for (r in 0 until recordsArray.length()) {
                val recObj = recordsArray.optJSONObject(r) ?: continue
                if (recObj.optString("name", "") == "overall" || recObj.optString("type", "") == "total") {
                    recordSummary = recObj.optString("summary").takeIf { it.isNotEmpty() }
                    break
                }
            }
        }

        val competitor = SportCompetitor(
            id = scopedId,
            rawId = rawId,
            name = displayName,
            shortName = shortName,
            abbreviation = abbreviation,
            score = score,
            rank = currentRank,
            lineScores = lineScoresList,
            logoUrl = logoUrl,
            primaryColorHex = colorHex?.let { if (it.startsWith("#")) it else "#$it" },
            alternateColorHex = alternateColorHex?.let { if (it.startsWith("#")) it else "#$it" },
            recordSummary = recordSummary,
            sport = sport
        )

        MultiSportCatalog.registerTeam(
            FavoriteTeamEntity(
                id = rawId,
                name = displayName,
                abbreviation = abbreviation,
                sport = sport,
                logoUrl = logoUrl,
                colorHex = colorHex?.let { if (it.startsWith("#")) it else "#$it" } ?: "#000000",
                aliases = listOf(displayName, shortName, abbreviation).filter { it.isNotBlank() }
            )
        )

        return competitor
    }

    private fun parseSituation(obj: JSONObject, sport: Sport): SportSituation {
        return when (sport) {
            Sport.NFL, Sport.CFB, Sport.CFB_FCS, Sport.CFB_D2, Sport.CFB_D3 -> {
                val down = obj.optInt("down", -1).takeIf { it > 0 }
                val distance = obj.optInt("distance", -1).takeIf { it >= 0 }
                val yardLine = obj.optInt("yardLine", -1).takeIf { it >= 0 }
                val downDist = obj.optString("downDistanceText", "").takeIf { it.isNotEmpty() }
                val possession = obj.optString("possession", "").takeIf { it.isNotEmpty() }
                val isRedZone = obj.optBoolean("isRedZone", false)
                val lastPlay = obj.optJSONObject("lastPlay")?.optString("text")

                SportSituation(
                    down = down,
                    distance = distance,
                    yardLine = yardLine,
                    downDistanceText = downDist,
                    possessionTeamId = possession,
                    isRedZone = isRedZone,
                    lastPlayText = lastPlay
                )
            }

            Sport.MLB, Sport.COLLEGE_BASEBALL -> {
                val inning = obj.optInt("inning", 1)
                val isTop = obj.optBoolean("isTopInning", true)
                val balls = obj.optInt("balls", 0)
                val strikes = obj.optInt("strikes", 0)
                val outs = obj.optInt("outs", 0)
                val onFirst = obj.optBoolean("onFirst", false)
                val onSecond = obj.optBoolean("onSecond", false)
                val onThird = obj.optBoolean("onThird", false)
                val batter = obj.optJSONObject("batter")?.optJSONObject("athlete")?.optString("fullName")
                val pitcher = obj.optJSONObject("pitcher")?.optJSONObject("athlete")?.optString("fullName")

                SportSituation(
                    inning = inning,
                    isTopInning = isTop,
                    balls = balls,
                    strikes = strikes,
                    outs = outs,
                    onFirst = onFirst,
                    onSecond = onSecond,
                    onThird = onThird,
                    batterName = batter,
                    pitcherName = pitcher
                )
            }

            Sport.NHL -> {
                val shotsHome = obj.optInt("homeShots", -1).takeIf { it >= 0 }
                val shotsAway = obj.optInt("awayShots", -1).takeIf { it >= 0 }
                val isPP = obj.optBoolean("isPowerPlay", false)

                SportSituation(
                    shotsHome = shotsHome,
                    shotsAway = shotsAway,
                    isPowerPlay = isPP
                )
            }

            Sport.SOCCER_EPL, Sport.SOCCER_MLS, Sport.SOCCER_UCL, Sport.SOCCER_LALIGA -> {
                val minute = obj.optInt("minute", -1).takeIf { it >= 0 }
                val stoppage = obj.optInt("stoppageTime", -1).takeIf { it >= 0 }

                SportSituation(
                    minute = minute,
                    stoppageMinute = stoppage
                )
            }

            else -> SportSituation()
        }
    }

    fun parseBoxScore(summaryJsonString: String): GameBoxScore {
        try {
            val root = JSONObject(summaryJsonString)
            val boxscore = root.optJSONObject("boxscore") ?: return GameBoxScore()

            // 1. Team stats comparison
            val teamStats = mutableListOf<TeamStatComparison>()
            val teamsArray = boxscore.optJSONArray("teams")
            if (teamsArray != null && teamsArray.length() >= 2) {
                val teamA = teamsArray.optJSONObject(0)
                val teamB = teamsArray.optJSONObject(1)
                val statsA = teamA?.optJSONArray("statistics")
                val statsB = teamB?.optJSONArray("statistics")

                if (statsA != null && statsB != null) {
                    val mapB = mutableMapOf<String, String>()
                    for (j in 0 until statsB.length()) {
                        val statObj = statsB.optJSONObject(j) ?: continue
                        val label = statObj.optString("label", statObj.optString("name"))
                        val valStr = statObj.optString("displayValue", "")
                        mapB[label] = valStr
                    }

                    for (i in 0 until statsA.length()) {
                        val statObj = statsA.optJSONObject(i) ?: continue
                        val label = statObj.optString("label", statObj.optString("name"))
                        val valA = statObj.optString("displayValue", "")
                        val valB = mapB[label] ?: "--"
                        if (label.isNotBlank() && (valA.isNotBlank() || valB.isNotBlank())) {
                            teamStats.add(TeamStatComparison(label = label, awayValue = valA, homeValue = valB))
                        }
                    }
                }
            }

            // 2. Player stats categories
            val playerCategories = mutableListOf<PlayerStatCategory>()
            val playersArray = boxscore.optJSONArray("players")
            if (playersArray != null) {
                for (p in 0 until playersArray.length()) {
                    val pTeamObj = playersArray.optJSONObject(p) ?: continue
                    val teamId = pTeamObj.optJSONObject("team")?.optString("id")
                    val teamName = pTeamObj.optJSONObject("team")?.optString("displayName", "Team") ?: "Team"
                    val statsArray = pTeamObj.optJSONArray("statistics") ?: continue

                    for (s in 0 until statsArray.length()) {
                        val statGroup = statsArray.optJSONObject(s) ?: continue
                        val rawName = statGroup.optString("name", "")
                        val textName = statGroup.optString("text", "")
                        val catName = if (textName.isNotBlank()) textName else if (rawName.isNotBlank()) rawName else "stats"

                        // Labels
                        val labelsList = mutableListOf<String>()
                        val labelsArr = statGroup.optJSONArray("labels")
                        if (labelsArr != null) {
                            for (l in 0 until labelsArr.length()) {
                                labelsList.add(labelsArr.optString(l, ""))
                            }
                        }

                        // Descriptions
                        val descList = mutableListOf<String>()
                        val descArr = statGroup.optJSONArray("descriptions")
                        if (descArr != null) {
                            for (d in 0 until descArr.length()) {
                                descList.add(descArr.optString(d, ""))
                            }
                        }

                        // Totals
                        val totalsList = mutableListOf<String>()
                        val totalsArr = statGroup.optJSONArray("totals")
                        if (totalsArr != null) {
                            for (t in 0 until totalsArr.length()) {
                                totalsList.add(totalsArr.optString(t, ""))
                            }
                        }

                        val athletesArray = statGroup.optJSONArray("athletes") ?: continue
                        val athleteList = mutableListOf<AthleteStatLine>()

                        for (a in 0 until athletesArray.length()) {
                            val athObj = athletesArray.optJSONObject(a) ?: continue
                            val athleteName = athObj.optJSONObject("athlete")?.optString("displayName", "") ?: ""
                            val pos = athObj.optJSONObject("athlete")?.optJSONObject("position")?.optString("abbreviation")
                            val jersey = athObj.optJSONObject("athlete")?.optString("jersey")
                            val statsArr = athObj.optJSONArray("stats")
                            val statsValues = mutableListOf<String>()
                            if (statsArr != null) {
                                for (st in 0 until statsArr.length()) {
                                    statsValues.add(statsArr.optString(st, ""))
                                }
                            }
                            if (athleteName.isNotBlank()) {
                                athleteList.add(
                                    AthleteStatLine(
                                        name = athleteName,
                                        position = pos,
                                        jersey = jersey,
                                        stats = statsValues
                                    )
                                )
                            }
                        }

                        if (athleteList.isNotEmpty()) {
                            playerCategories.add(
                                PlayerStatCategory(
                                    teamName = teamName,
                                    categoryName = catName,
                                    teamId = teamId,
                                    labels = labelsList,
                                    descriptions = descList,
                                    athletes = athleteList,
                                    totals = totalsList
                                )
                            )
                        }
                    }
                }
            }

            // 3. Win probability
            var winProb: WinProbabilityData? = null
            val winProbArray = root.optJSONArray("winprobability")
            if (winProbArray != null && winProbArray.length() > 0) {
                val latest = winProbArray.optJSONObject(winProbArray.length() - 1)
                if (latest != null && latest.has("homeWinPercentage")) {
                    val homePct = latest.optDouble("homeWinPercentage", 0.5) * 100.0
                    val awayPct = 100.0 - homePct
                    winProb = WinProbabilityData(homeTeamPercentage = homePct, awayTeamPercentage = awayPct)
                }
            }

            return GameBoxScore(teamStats = teamStats, playerCategories = playerCategories, winProbability = winProb)
        } catch (e: Exception) {
            e.printStackTrace()
            return GameBoxScore()
        }
    }
}
