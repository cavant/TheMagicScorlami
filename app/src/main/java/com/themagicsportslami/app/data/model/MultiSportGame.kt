package com.themagicsportslami.app.data.model

data class MultiSportGame(
    val id: String,
    val sport: Sport,
    val name: String,
    val shortName: String,
    val dateIso: String,
    val status: SportGameStatus,
    val homeTeam: SportCompetitor,
    val awayTeam: SportCompetitor,
    val situation: SportSituation? = null,
    val broadcasts: List<String> = emptyList(),
    val leaders: List<SportLeader> = emptyList(),
    val venueName: String? = null,
    val odds: GameOdds? = null,
    val combatFight: CombatFight? = null
) {
    val isLive: Boolean
        get() = status.state == SportGameState.IN_PROGRESS

    val isFinal: Boolean
        get() = status.state == SportGameState.FINAL

    val isScheduled: Boolean
        get() = status.state == SportGameState.SCHEDULED

    fun hasTeam(teamId: String): Boolean {
        return homeTeam.id == teamId || homeTeam.scopedId == teamId || homeTeam.rawId == teamId ||
               awayTeam.id == teamId || awayTeam.scopedId == teamId || awayTeam.rawId == teamId
    }

    fun isFavorite(favTeams: Set<String>, conferenceTeamIds: Set<String> = emptySet()): Boolean {
        return homeTeam.matchesFavorite(favTeams, conferenceTeamIds) || awayTeam.matchesFavorite(favTeams, conferenceTeamIds)
    }
}

enum class SportGameState {
    SCHEDULED,
    IN_PROGRESS,
    FINAL,
    HALFTIME,
    DELAYED,
    POSTPONED,
    UNKNOWN
}

data class SportGameStatus(
    val state: SportGameState,
    val period: Int,
    val clock: String,
    val detail: String,
    val shortDetail: String
)

data class SportCompetitor(
    val id: String,
    val rawId: String = if (id.contains('_')) id.substringAfter('_') else id,
    val name: String,
    val shortName: String,
    val abbreviation: String,
    val score: Int,
    val rank: Int? = null,
    val lineScores: List<Int> = emptyList(),
    val logoUrl: String? = null,
    val primaryColorHex: String? = null,
    val alternateColorHex: String? = null,
    val recordSummary: String? = null,
    val sport: Sport = Sport.NFL
) {
    val scopedId: String
        get() = if (id.startsWith("${sport.id}_")) id else "${sport.id}_$id"

    fun matchesFavorite(favTeams: Set<String>, conferenceTeamIds: Set<String> = emptySet()): Boolean {
        return id in favTeams || scopedId in favTeams || rawId in favTeams ||
                id in conferenceTeamIds || scopedId in conferenceTeamIds || rawId in conferenceTeamIds
    }
}

data class SportSituation(
    // Football specifics
    val down: Int? = null,
    val distance: Int? = null,
    val yardLine: Int? = null,
    val downDistanceText: String? = null,
    val possessionTeamId: String? = null,
    val isRedZone: Boolean = false,
    val lastPlayText: String? = null,

    // Baseball specifics
    val inning: Int? = null,
    val isTopInning: Boolean = true,
    val balls: Int = 0,
    val strikes: Int = 0,
    val outs: Int = 0,
    val onFirst: Boolean = false,
    val onSecond: Boolean = false,
    val onThird: Boolean = false,
    val batterName: String? = null,
    val pitcherName: String? = null,

    // Hockey specifics
    val shotsHome: Int? = null,
    val shotsAway: Int? = null,
    val isPowerPlay: Boolean = false,

    // Soccer specifics
    val minute: Int? = null,
    val stoppageMinute: Int? = null
)

data class SportLeader(
    val category: String, // e.g. Passing, Points, Goals, Pitching, etc.
    val athleteName: String,
    val displayValue: String,
    val teamId: String
)

data class GameOdds(
    val details: String? = null, // e.g. "KC -3.5" or "DET -123"
    val overUnder: Double? = null, // e.g. 44.5
    val spread: Double? = null, // e.g. -3.5
    val provider: String? = null, // e.g. "DraftKings", "ESPN BET"
    val awayMoneyLine: Int? = null,
    val homeMoneyLine: Int? = null
) {
    val formattedSummary: String
        get() {
            val parts = mutableListOf<String>()
            if (!details.isNullOrBlank()) parts.add(details)
            if (overUnder != null && overUnder > 0) parts.add("O/U $overUnder")
            return parts.joinToString(" • ")
        }
}

data class CombatFight(
    val fighterA: Fighter,
    val fighterB: Fighter,
    val weightClass: String? = null,
    val isTitleFight: Boolean = false,
    val resultText: String? = null
)

data class Fighter(
    val id: String,
    val name: String,
    val shortName: String,
    val record: String? = null, // e.g. "15-2-0"
    val headshotUrl: String? = null,
    val isWinner: Boolean = false
)

data class GameBoxScore(
    val teamStats: List<TeamStatComparison> = emptyList(),
    val playerCategories: List<PlayerStatCategory> = emptyList(),
    val winProbability: WinProbabilityData? = null
)

data class TeamStatComparison(
    val label: String, // e.g. "Total Yards", "1st Downs", "FG%"
    val homeValue: String,
    val awayValue: String
)

data class PlayerStatCategory(
    val teamName: String,
    val categoryName: String, // e.g. "passing", "rushing", "receiving", "scoring"
    val teamId: String? = null,
    val labels: List<String> = emptyList(),
    val descriptions: List<String> = emptyList(),
    val athletes: List<AthleteStatLine> = emptyList(),
    val totals: List<String> = emptyList()
)

data class AthleteStatLine(
    val name: String,
    val position: String? = null,
    val jersey: String? = null,
    val stats: List<String> = emptyList() // e.g. ["21/28", "275", "3 TD", "0 INT"]
)

data class WinProbabilityData(
    val homeTeamPercentage: Double,
    val awayTeamPercentage: Double
)
