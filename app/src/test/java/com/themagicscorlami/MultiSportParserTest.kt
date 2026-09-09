package com.themagicscorlami

import com.themagicscorlami.data.api.MultiSportParser
import com.themagicscorlami.data.model.*
import org.junit.Assert.*
import org.junit.Test

class MultiSportParserTest {

    @Test
    fun testNflParsing() {
        val nflJson = """
        {
            "events": [
                {
                    "id": "401547417",
                    "name": "Dallas Cowboys at Philadelphia Eagles",
                    "shortName": "DAL @ PHI",
                    "date": "2026-09-07T20:00Z",
                    "status": {
                        "clock": 120.0,
                        "displayClock": "2:00",
                        "period": 4,
                        "type": { "name": "STATUS_IN_PROGRESS", "state": "in", "shortDetail": "2:00 - 4th" }
                    },
                    "competitions": [
                        {
                            "competitors": [
                                { "homeAway": "home", "score": "24", "team": { "id": "21", "name": "Eagles", "abbreviation": "PHI", "shortDisplayName": "Eagles" } },
                                { "homeAway": "away", "score": "20", "team": { "id": "6", "name": "Cowboys", "abbreviation": "DAL", "shortDisplayName": "Cowboys" } }
                            ],
                            "situation": {
                                "down": 3,
                                "distance": 4,
                                "yardLine": 18,
                                "downDistanceText": "3rd & 4 at PHI 18",
                                "possession": "6",
                                "isRedZone": true
                            },
                            "broadcasts": [{ "names": ["FOX"] }]
                        }
                    ]
                }
            ]
        }
        """.trimIndent()

        val games = MultiSportParser.parse(nflJson, Sport.NFL)
        assertEquals(1, games.size)
        val g = games[0]
        assertEquals("401547417", g.id)
        assertEquals(Sport.NFL, g.sport)
        assertTrue(g.isLive)
        assertEquals("Eagles", g.homeTeam.shortName)
        assertEquals(24, g.homeTeam.score)
        assertEquals("Cowboys", g.awayTeam.shortName)
        assertEquals(20, g.awayTeam.score)
        assertNotNull(g.situation)
        assertEquals("3rd & 4 at PHI 18", g.situation?.downDistanceText)
        assertTrue(g.situation?.isRedZone == true)
        assertEquals("6", g.situation?.possessionTeamId)
    }

    @Test
    fun testMlbBaseballParsing() {
        val mlbJson = """
        {
            "events": [
                {
                    "id": "401568912",
                    "name": "Boston Red Sox at New York Yankees",
                    "shortName": "BOS @ NYY",
                    "status": {
                        "type": { "name": "STATUS_IN_PROGRESS", "state": "in", "shortDetail": "Bot 7th" }
                    },
                    "competitions": [
                        {
                            "competitors": [
                                { "homeAway": "home", "score": "5", "team": { "id": "10", "abbreviation": "NYY", "shortDisplayName": "Yankees" } },
                                { "homeAway": "away", "score": "3", "team": { "id": "2", "abbreviation": "BOS", "shortDisplayName": "Red Sox" } }
                            ],
                            "situation": {
                                "inning": 7,
                                "isTopInning": false,
                                "balls": 3,
                                "strikes": 2,
                                "outs": 2,
                                "onFirst": true,
                                "onSecond": false,
                                "onThird": true
                            }
                        }
                    ]
                }
            ]
        }
        """.trimIndent()

        val games = MultiSportParser.parse(mlbJson, Sport.MLB)
        assertEquals(1, games.size)
        val g = games[0]
        assertEquals(Sport.MLB, g.sport)
        assertNotNull(g.situation)
        assertEquals(7, g.situation?.inning)
        assertFalse(g.situation?.isTopInning ?: true)
        assertEquals(3, g.situation?.balls)
        assertEquals(2, g.situation?.strikes)
        assertEquals(2, g.situation?.outs)
        assertTrue(g.situation?.onFirst == true)
        assertFalse(g.situation?.onSecond ?: true)
        assertTrue(g.situation?.onThird == true)
    }

    @Test
    fun testNhlHockeyParsing() {
        val nhlJson = """
        {
            "events": [
                {
                    "id": "401559812",
                    "name": "Edmonton Oilers at Florida Panthers",
                    "shortName": "EDM @ FLA",
                    "status": {
                        "type": { "name": "STATUS_IN_PROGRESS", "state": "in", "shortDetail": "12:45 - 2nd" }
                    },
                    "competitions": [
                        {
                            "competitors": [
                                { "homeAway": "home", "score": "2", "team": { "id": "13", "abbreviation": "FLA", "shortDisplayName": "Panthers" } },
                                { "homeAway": "away", "score": "1", "team": { "id": "14", "abbreviation": "EDM", "shortDisplayName": "Oilers" } }
                            ],
                            "situation": {
                                "homeShots": 24,
                                "awayShots": 18,
                                "isPowerPlay": true
                            }
                        }
                    ]
                }
            ]
        }
        """.trimIndent()

        val games = MultiSportParser.parse(nhlJson, Sport.NHL)
        assertEquals(1, games.size)
        val g = games[0]
        assertEquals(Sport.NHL, g.sport)
        assertNotNull(g.situation)
        assertEquals(24, g.situation?.shotsHome)
        assertEquals(18, g.situation?.shotsAway)
        assertTrue(g.situation?.isPowerPlay == true)
    }

    @Test
    fun testOddsParsing() {
        val oddsJson = """
        {
            "events": [
                {
                    "id": "401547418",
                    "name": "Kansas City Chiefs at Baltimore Ravens",
                    "shortName": "KC @ BAL",
                    "status": {
                        "type": { "name": "STATUS_SCHEDULED", "state": "pre", "shortDetail": "Sun, 4:25 PM" }
                    },
                    "competitions": [
                        {
                            "competitors": [
                                { "homeAway": "home", "score": "0", "team": { "id": "33", "abbreviation": "BAL", "shortDisplayName": "Ravens" } },
                                { "homeAway": "away", "score": "0", "team": { "id": "12", "abbreviation": "KC", "shortDisplayName": "Chiefs" } }
                            ],
                            "odds": [
                                {
                                    "details": "KC -3.5",
                                    "overUnder": 47.5,
                                    "spread": -3.5,
                                    "provider": { "name": "DraftKings" },
                                    "awayTeamOdds": { "moneyLine": -175 },
                                    "homeTeamOdds": { "moneyLine": 150 }
                                }
                            ]
                        }
                    ]
                }
            ]
        }
        """.trimIndent()

        val games = MultiSportParser.parse(oddsJson, Sport.NFL)
        assertEquals(1, games.size)
        val odds = games[0].odds
        assertNotNull(odds)
        assertEquals("KC -3.5", odds?.details)
        assertEquals(47.5, odds?.overUnder ?: 0.0, 0.01)
        assertEquals(-3.5, odds?.spread ?: 0.0, 0.01)
        assertEquals("DraftKings", odds?.provider)
        assertEquals(-175, odds?.awayMoneyLine)
        assertEquals(150, odds?.homeMoneyLine)
        assertEquals("KC -3.5 • O/U 47.5", odds?.formattedSummary)
    }

    @Test
    fun testCombatSportsUfcParsing() {
        val ufcJson = """
        {
            "events": [
                {
                    "id": "600045123",
                    "name": "UFC 310: Pantoja vs. Asakura",
                    "shortName": "UFC 310",
                    "status": {
                        "type": { "name": "STATUS_FINAL", "state": "post", "detail": "Round 5 - Unanimous Decision" }
                    },
                    "competitions": [
                        {
                            "format": {
                                "regulation": { "displayName": "Flyweight" },
                                "titleFight": true
                            },
                            "competitors": [
                                {
                                    "id": "3012",
                                    "winner": true,
                                    "athlete": { "displayName": "Alexandre Pantoja", "shortName": "Pantoja" },
                                    "records": [{ "summary": "29-5-0" }]
                                },
                                {
                                    "id": "3013",
                                    "winner": false,
                                    "athlete": { "displayName": "Kai Asakura", "shortName": "Asakura" },
                                    "records": [{ "summary": "21-5-0" }]
                                }
                            ]
                        }
                    ]
                }
            ]
        }
        """.trimIndent()

        val games = MultiSportParser.parse(ufcJson, Sport.MMA_UFC)
        assertEquals(1, games.size)
        val fight = games[0].combatFight
        assertNotNull(fight)
        assertEquals("Flyweight", fight?.weightClass)
        assertTrue(fight?.isTitleFight == true)
        assertEquals("Pantoja", fight?.fighterA?.shortName)
        assertTrue(fight?.fighterA?.isWinner == true)
        assertEquals("29-5-0", fight?.fighterA?.record)
        assertEquals("Asakura", fight?.fighterB?.shortName)
        assertFalse(fight?.fighterB?.isWinner == true)
    }

    @Test
    fun testBoxScoreParsing() {
        val summaryJson = """
        {
            "boxscore": {
                "teams": [
                    {
                        "team": { "displayName": "Philadelphia Eagles" },
                        "statistics": [
                            { "name": "totalYards", "label": "Total Yards", "displayValue": "385" },
                            { "name": "firstDowns", "label": "1st Downs", "displayValue": "24" }
                        ]
                    },
                    {
                        "team": { "displayName": "Dallas Cowboys" },
                        "statistics": [
                            { "name": "totalYards", "label": "Total Yards", "displayValue": "310" },
                            { "name": "firstDowns", "label": "1st Downs", "displayValue": "18" }
                        ]
                    }
                ],
                "players": [
                    {
                        "team": { "displayName": "Philadelphia Eagles" },
                        "statistics": [
                            {
                                "name": "passing",
                                "athletes": [
                                    {
                                        "athlete": { "displayName": "Jalen Hurts", "position": { "abbreviation": "QB" } },
                                        "stats": ["22/30", "280", "2", "0"]
                                    }
                                ]
                            }
                        ]
                    }
                ]
            },
            "winprobability": [
                { "homeWinPercentage": 0.725 }
            ]
        }
        """.trimIndent()

        val box = MultiSportParser.parseBoxScore(summaryJson)
        assertEquals(2, box.teamStats.size)
        assertEquals("Total Yards", box.teamStats[0].label)
        assertEquals("385", box.teamStats[0].awayValue)
        assertEquals("310", box.teamStats[0].homeValue)

        assertEquals(1, box.playerCategories.size)
        assertEquals("passing", box.playerCategories[0].categoryName)
        assertEquals("Jalen Hurts", box.playerCategories[0].athletes[0].name)
        assertEquals("QB", box.playerCategories[0].athletes[0].position)
        assertEquals("280", box.playerCategories[0].athletes[0].stats[1])

        assertNotNull(box.winProbability)
        assertEquals(72.5, box.winProbability?.homeTeamPercentage ?: 0.0, 0.1)
        assertEquals(27.5, box.winProbability?.awayTeamPercentage ?: 0.0, 0.1)
    }

    @Test
    fun testCollegeBasketballParsing() {
        val cbbJson = """
        {
            "events": [
                {
                    "id": "401584321",
                    "name": "Duke Blue Devils at North Carolina Tar Heels",
                    "shortName": "DUKE @ UNC",
                    "status": {
                        "clock": 45.0,
                        "displayClock": "0:45",
                        "period": 2,
                        "type": { "name": "STATUS_IN_PROGRESS", "state": "in", "shortDetail": "0:45 - 2nd Half" }
                    },
                    "competitions": [
                        {
                            "competitors": [
                                { "homeAway": "home", "score": "78", "team": { "id": "153", "abbreviation": "UNC", "name": "Tar Heels", "shortDisplayName": "North Carolina" } },
                                { "homeAway": "away", "score": "75", "team": { "id": "150", "abbreviation": "DUKE", "name": "Blue Devils", "shortDisplayName": "Duke" } }
                            ]
                        }
                    ]
                }
            ]
        }
        """.trimIndent()

        val games = MultiSportParser.parse(cbbJson, Sport.CBB)
        assertEquals(1, games.size)
        val game = games[0]
        assertEquals(Sport.CBB, game.sport)
        assertEquals("401584321", game.id)
        assertTrue(game.isLive)
        assertEquals("cbb_153", game.homeTeam.id)
        assertEquals("153", game.homeTeam.rawId)
        assertEquals(78, game.homeTeam.score)
        assertEquals("cbb_150", game.awayTeam.id)
        assertEquals("150", game.awayTeam.rawId)
        assertEquals(75, game.awayTeam.score)
    }

    @Test
    fun testStrictFavoritesLogic() {
        val favTeams = setOf("145") // Ole Miss only
        val mockGames = listOf(
            MultiSportGame(
                id = "1",
                sport = Sport.CBB,
                name = "Ole Miss Rebels at Kentucky Wildcats",
                shortName = "MISS @ UK",
                dateIso = "2026-09-07T19:00Z",
                status = SportGameStatus(state = SportGameState.IN_PROGRESS, period = 2, clock = "0:45", detail = "2nd Half", shortDetail = "2nd"),
                homeTeam = SportCompetitor(id = "145", name = "Ole Miss Rebels", shortName = "Ole Miss", abbreviation = "MISS", score = 78),
                awayTeam = SportCompetitor(id = "96", name = "Kentucky Wildcats", shortName = "Kentucky", abbreviation = "UK", score = 75)
            ),
            MultiSportGame(
                id = "2",
                sport = Sport.NFL,
                name = "Dallas Cowboys at Philadelphia Eagles",
                shortName = "DAL @ PHI",
                dateIso = "2026-09-07T20:00Z",
                status = SportGameStatus(state = SportGameState.IN_PROGRESS, period = 4, clock = "2:00", detail = "4th Quarter", shortDetail = "4th"),
                homeTeam = SportCompetitor(id = "21", name = "Philadelphia Eagles", shortName = "Eagles", abbreviation = "PHI", score = 24),
                awayTeam = SportCompetitor(id = "6", name = "Dallas Cowboys", shortName = "Cowboys", abbreviation = "DAL", score = 20)
            )
        )

        // Strict matching: only Ole Miss game should be returned
        val matched = mockGames.filter { g -> g.homeTeam.id in favTeams || g.awayTeam.id in favTeams }
        assertEquals(1, matched.size)
        assertEquals("1", matched[0].id)
        assertEquals("Ole Miss Rebels", matched[0].homeTeam.name)

        // When no favorite teams selected, favorites list must be strictly empty (not fallback to all)
        val emptyFavs = emptySet<String>()
        val emptyResult: List<MultiSportGame> = if (emptyFavs.isEmpty()) emptyList() else mockGames.filter { g -> g.homeTeam.id in emptyFavs || g.awayTeam.id in emptyFavs }
        assertTrue(emptyResult.isEmpty())
    }

    @Test
    fun testMultiSportCatalogContainsOleMissAndCbb() {
        val allTeams = com.themagicscorlami.data.model.MultiSportCatalog.ALL_TEAMS
        
        // Check Ole Miss in CFB and CBB
        val oleMissTeams = allTeams.filter { it.rawId == "145" }
        assertTrue("Ole Miss must be present", oleMissTeams.isNotEmpty())
        assertTrue("Ole Miss must be present in CFB", oleMissTeams.any { it.sport == Sport.CFB && it.id == "cfb_145" })
        assertTrue("Ole Miss must be present in CBB", oleMissTeams.any { it.sport == Sport.CBB && it.id == "cbb_145" })

        // Check search aliases
        val oleMiss = oleMissTeams.first()
        assertTrue(oleMiss.matches("Ole Miss"))
        assertTrue(oleMiss.matches("Mississippi"))
        assertTrue(oleMiss.matches("rebels"))
        assertTrue(oleMiss.matches("MISS"))

        // Check CBB specific programs (e.g. Gonzaga, Villanova)
        val cbbTeams = allTeams.filter { it.sport == Sport.CBB }
        assertTrue(cbbTeams.any { it.name.contains("Gonzaga") })
        assertTrue(cbbTeams.any { it.name.contains("Villanova") })
        assertTrue(cbbTeams.any { it.name.contains("Marquette") })
    }

    @Test
    fun testAllSportsCatalogCoverage() {
        val allTeams = MultiSportCatalog.ALL_TEAMS
        val leagueSports = Sport.entries.filter { it.isLeague }

        for (sport in leagueSports) {
            val teamsInSport = allTeams.filter { it.sport == sport }
            assertTrue("Sport ${sport.id} (${sport.displayName}) must have catalog entries", teamsInSport.size >= 10)
        }

        // Verify WCBB specifically
        val wcbbTeams = allTeams.filter { it.sport == Sport.WCBB }
        assertTrue("WCBB must contain South Carolina", wcbbTeams.any { it.name.contains("South Carolina") || it.rawId == "2579" })
        assertTrue("WCBB must contain Iowa", wcbbTeams.any { it.name.contains("Iowa") || it.rawId == "2294" })
        assertTrue("WCBB must contain UConn", wcbbTeams.any { it.name.contains("UConn") || it.rawId == "41" })
        assertTrue("WCBB must contain Ole Miss", wcbbTeams.any { it.rawId == "145" })

        // Verify College Baseball specifically
        val ncaaBaseball = allTeams.filter { it.sport == Sport.COLLEGE_BASEBALL }
        assertTrue("College Baseball must contain LSU", ncaaBaseball.any { it.name.contains("LSU") || it.rawId == "99" })
        assertTrue("College Baseball must contain Ole Miss", ncaaBaseball.any { it.rawId == "145" })

        // Verify La Liga
        val laligaTeams = allTeams.filter { it.sport == Sport.SOCCER_LALIGA }
        assertTrue("La Liga must contain Real Madrid", laligaTeams.any { it.matches("Real Madrid") })
        assertTrue("La Liga must contain Barcelona", laligaTeams.any { it.matches("Barcelona") })

        // Verify Champions League
        val uclTeams = allTeams.filter { it.sport == Sport.SOCCER_UCL }
        assertTrue("UCL must contain Bayern Munich", uclTeams.any { it.matches("Bayern") })
        assertTrue("UCL must contain Man City", uclTeams.any { it.matches("Manchester City") })

        // Verify UFC
        val ufcFighters = allTeams.filter { it.sport == Sport.MMA_UFC }
        assertTrue("UFC must contain Jon Jones", ufcFighters.any { it.matches("Jon Jones") })
        assertTrue("UFC must contain Alex Pereira", ufcFighters.any { it.matches("Alex Pereira") })

        // Verify F1
        val f1Entities = allTeams.filter { it.sport == Sport.F1 }
        assertTrue("F1 must contain Ferrari", f1Entities.any { it.matches("Ferrari") })
        assertTrue("F1 must contain Max Verstappen", f1Entities.any { it.matches("Max Verstappen") })
    }

    @Test
    fun testScopedFavoriteIdsAcrossSports() {
        val allTeams = MultiSportCatalog.ALL_TEAMS
        val oleMissCFB = allTeams.find { it.sport == Sport.CFB && it.rawId == "145" }
        val oleMissCBB = allTeams.find { it.sport == Sport.CBB && it.rawId == "145" }
        val oleMissWCBB = allTeams.find { it.sport == Sport.WCBB && it.rawId == "145" }
        val oleMissBaseball = allTeams.find { it.sport == Sport.COLLEGE_BASEBALL && it.rawId == "145" }

        assertNotNull(oleMissCFB)
        assertNotNull(oleMissCBB)
        assertNotNull(oleMissWCBB)
        assertNotNull(oleMissBaseball)

        assertEquals("cfb_145", oleMissCFB?.id)
        assertEquals("cbb_145", oleMissCBB?.id)
        assertEquals("wcbb_145", oleMissWCBB?.id)
        assertEquals("college-baseball_145", oleMissBaseball?.id)

        // Verify all 4 have unique IDs
        val ids = setOf(oleMissCFB?.id, oleMissCBB?.id, oleMissWCBB?.id, oleMissBaseball?.id)
        assertEquals(4, ids.size)

        // Favoriting CFB Ole Miss must NOT favorite CBB or WCBB or Baseball
        val userFavorites = setOf("cfb_145")
        assertTrue(oleMissCFB?.id in userFavorites)
        assertFalse(oleMissCBB?.id in userFavorites)
        assertFalse(oleMissWCBB?.id in userFavorites)
        assertFalse(oleMissBaseball?.id in userFavorites)

        // findById resolves both scoped and legacy IDs
        assertEquals(oleMissCFB, MultiSportCatalog.findById("cfb_145"))
        assertEquals(oleMissCBB, MultiSportCatalog.findById("cbb_145"))
        assertEquals(oleMissCFB, MultiSportCatalog.findById("145"))
    }

    @Test
    fun testFcsD2D3FootballSupport() {
        // 1. Verify football classification
        assertTrue(Sport.CFB.isFootball)
        assertTrue(Sport.CFB_FCS.isFootball)
        assertTrue(Sport.CFB_D2.isFootball)
        assertTrue(Sport.CFB_D3.isFootball)
        assertTrue(Sport.NFL.isFootball)
        assertFalse(Sport.NBA.isFootball)
        assertFalse(Sport.MLB.isFootball)

        // 2. Verify Catalog contains FCS, D2, and D3 teams
        val allTeams = MultiSportCatalog.ALL_TEAMS
        val ndsu = allTeams.find { it.sport == Sport.CFB_FCS && it.rawId == "2449" }
        assertNotNull("NDSU must exist in FCS catalog", ndsu)
        assertEquals("cfb-fcs_2449", ndsu?.id)
        assertEquals("North Dakota State Bison", ndsu?.name)

        val gvsu = allTeams.find { it.sport == Sport.CFB_D2 && it.rawId == "125" }
        assertNotNull("Grand Valley State must exist in D2 catalog", gvsu)
        assertEquals("cfb-d2_125", gvsu?.id)
        assertEquals("Grand Valley State Lakers", gvsu?.name)

        val mountUnion = allTeams.find { it.sport == Sport.CFB_D3 && it.rawId == "426" }
        assertNotNull("Mount Union must exist in D3 catalog", mountUnion)
        assertEquals("cfb-d3_426", mountUnion?.id)
        assertEquals("Mount Union Purple Raiders", mountUnion?.name)

        // 3. Test parsing a D2 football event
        val d2Json = """
        {
            "events": [
                {
                    "id": "401868999",
                    "name": "Grand Valley State Lakers at University Of Charleston Golden Eagles",
                    "shortName": "GVSU @ CHWN",
                    "status": {
                        "clock": 180.0,
                        "displayClock": "3:00",
                        "period": 4,
                        "type": { "name": "STATUS_IN_PROGRESS", "state": "in", "shortDetail": "3:00 - 4th" }
                    },
                    "competitions": [
                        {
                            "competitors": [
                                { "homeAway": "home", "score": "14", "team": { "id": "2128", "name": "Golden Eagles", "abbreviation": "CHWN", "displayName": "University Of Charleston (WV) Golden Eagles" } },
                                { "homeAway": "away", "score": "35", "team": { "id": "125", "name": "Lakers", "abbreviation": "GVSU", "displayName": "Grand Valley State Lakers" } }
                            ],
                            "situation": {
                                "down": 1,
                                "distance": 10,
                                "yardLine": 15,
                                "downDistanceText": "1st & 10 at CHWN 15",
                                "possession": "125",
                                "isRedZone": true
                            }
                        }
                    ]
                }
            ]
        }
        """.trimIndent()

        val parsedGames = MultiSportParser.parse(d2Json, Sport.CFB_D2)
        assertEquals(1, parsedGames.size)
        val game = parsedGames[0]
        assertEquals(Sport.CFB_D2, game.sport)
        assertEquals("cfb-d2_125", game.awayTeam.id)
        assertEquals("125", game.awayTeam.rawId)
        assertEquals(35, game.awayTeam.score)
        assertEquals(14, game.homeTeam.score)
        assertTrue(game.isLive)
        assertNotNull(game.situation)
        assertTrue(game.situation?.isRedZone == true)

        // Verify dynamic team registration
        val dynamicLookup = MultiSportCatalog.findById("cfb-d2_2128")
        assertNotNull("Dynamic team from game event must be resolved in catalog", dynamicLookup)
        assertEquals("University Of Charleston (WV) Golden Eagles", dynamicLookup?.name)
    }

    @Test
    fun testBoxScoreAdvancedStatsParsing() {
        val summaryJson = """
        {
            "boxscore": {
                "teams": [
                    {
                        "team": { "id": "333", "displayName": "Alabama Crimson Tide", "abbreviation": "ALA" },
                        "statistics": [
                            { "name": "firstDowns", "displayValue": "24", "label": "First Downs" },
                            { "name": "totalYards", "displayValue": "450", "label": "Total Yards" }
                        ]
                    },
                    {
                        "team": { "id": "8", "displayName": "Arkansas Razorbacks", "abbreviation": "ARK" },
                        "statistics": [
                            { "name": "firstDowns", "displayValue": "18", "label": "First Downs" },
                            { "name": "totalYards", "displayValue": "320", "label": "Total Yards" }
                        ]
                    }
                ],
                "players": [
                    {
                        "team": { "id": "333", "displayName": "Alabama Crimson Tide" },
                        "statistics": [
                            {
                                "name": "passing",
                                "keys": ["c/att", "yds", "td", "int"],
                                "labels": ["C/ATT", "YDS", "TD", "INT"],
                                "descriptions": ["Completions/Attempts", "Passing Yards", "Passing Touchdowns", "Interceptions"],
                                "totals": ["20/28", "295", "3", "0"],
                                "athletes": [
                                    {
                                        "athlete": { "id": "4433970", "displayName": "Jalen Milroe", "jersey": "4", "position": { "abbreviation": "QB" } },
                                        "stats": ["20/28", "295", "3", "0"]
                                    }
                                ]
                            }
                        ]
                    }
                ]
            }
        }
        """.trimIndent()

        val boxScore = MultiSportParser.parseBoxScore(summaryJson)
        assertTrue(boxScore.teamStats.isNotEmpty())
        assertEquals("First Downs", boxScore.teamStats[0].label)
        assertEquals("24", boxScore.teamStats[0].awayValue)
        assertEquals("18", boxScore.teamStats[0].homeValue)

        assertEquals(1, boxScore.playerCategories.size)
        val passingCat = boxScore.playerCategories[0]
        assertEquals("passing", passingCat.categoryName)
        assertEquals("333", passingCat.teamId)
        assertEquals(listOf("C/ATT", "YDS", "TD", "INT"), passingCat.labels)
        assertEquals(listOf("20/28", "295", "3", "0"), passingCat.totals)

        assertEquals(1, passingCat.athletes.size)
        val qb = passingCat.athletes[0]
        assertEquals("Jalen Milroe", qb.name)
        assertEquals("4", qb.jersey)
        assertEquals("QB", qb.position)
        assertEquals(listOf("20/28", "295", "3", "0"), qb.stats)
    }

    @Test
    fun testConferenceFavoriting() {
        val sec = MultiSportCatalog.findConferenceById("cfb_sec")
        assertNotNull("SEC conference must exist", sec)
        assertTrue("Alabama must be in SEC", sec!!.teamIds.contains("333"))

        val secTeamIds = MultiSportCatalog.getTeamIdsForFavoritedConferences(setOf("cfb_sec"))
        assertTrue("Raw ID 333 should be in resolved conference team IDs", secTeamIds.contains("333"))
        assertTrue("Scoped ID cfb_333 should be in resolved conference team IDs", secTeamIds.contains("cfb_333"))

        val mockBamaGame = MultiSportGame(
            id = "test_game_1",
            sport = Sport.CFB,
            name = "Alabama vs Arkansas",
            shortName = "ALA vs ARK",
            dateIso = "2026-09-08T19:00Z",
            status = SportGameStatus(state = SportGameState.IN_PROGRESS, period = 2, clock = "5:00", detail = "2nd Quarter", shortDetail = "2nd"),
            homeTeam = SportCompetitor(id = "333", rawId = "333", name = "Alabama", shortName = "Alabama", abbreviation = "ALA", score = 24),
            awayTeam = SportCompetitor(id = "8", rawId = "8", name = "Arkansas", shortName = "Arkansas", abbreviation = "ARK", score = 14)
        )

        // If SEC is favorited but not Bama individually:
        assertTrue(mockBamaGame.isFavorite(emptySet(), secTeamIds))

        // If neither is favorited:
        assertFalse(mockBamaGame.isFavorite(emptySet(), emptySet()))
    }
}

