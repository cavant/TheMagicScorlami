package com.themagicscorlami.data.repository

import com.themagicscorlami.data.api.MultiSportHttpClient
import com.themagicscorlami.data.api.MultiSportParser
import com.themagicscorlami.data.local.SportslamiPreferences
import com.themagicscorlami.data.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

data class MultiSportResult(
    val games: List<MultiSportGame>,
    val favoriteMatchups: List<MultiSportGame> = emptyList(),
    val isFromCache: Boolean = false,
    val lastUpdatedMillis: Long = System.currentTimeMillis()
)

class MultiSportRepository(
    private val httpClient: MultiSportHttpClient,
    private val preferences: SportslamiPreferences
) {
    private val memoryCache = mutableMapOf<String, MultiSportResult>()

    suspend fun getGames(sport: Sport, dateString: String? = null): MultiSportResult = withContext(Dispatchers.IO) {
        val userPrefs = preferences.preferencesFlow.first()
        val confTeamIds = MultiSportCatalog.getTeamIdsForFavoritedConferences(userPrefs.favoriteConferenceIds)
        val cacheKey = "${sport.id}_${dateString ?: "top_events"}"

        try {
            val result = if (sport == Sport.ALL && dateString == null) {
                // Top Events Mode: look back 7d, forward 14d for favorites, and get today's I-A & Pro action
                val favMatchups = fetchTopEventsFavorites(userPrefs.favoriteTeamIds, userPrefs.favoriteConferenceIds)
                val todayGames = fetchAllSports(null)
                val sortedToday = todayGames.sortedWith(
                    compareBy<MultiSportGame> { getSportPriority(it.sport) }
                        .thenByDescending { it.isLive }
                        .thenByDescending { it.isFavorite(userPrefs.favoriteTeamIds, confTeamIds) }
                        .thenBy { it.isFinal }
                )
                MultiSportResult(games = sortedToday, favoriteMatchups = favMatchups, isFromCache = false)
            } else {
                val games = when (sport) {
                    Sport.ALL -> fetchAllSports(dateString)
                    Sport.FAVORITES -> fetchFavorites(userPrefs.favoriteTeamIds, confTeamIds, dateString)
                    else -> fetchSingleSport(sport, dateString)
                }
                val sorted = sortGames(games, userPrefs.favoriteTeamIds, confTeamIds)
                MultiSportResult(games = sorted, isFromCache = false)
            }

            memoryCache[cacheKey] = result
            result
        } catch (e: Exception) {
            e.printStackTrace()
            memoryCache[cacheKey]?.copy(isFromCache = true)
                ?: MultiSportResult(games = emptyList(), isFromCache = true)
        }
    }

    private fun getSportPriority(sport: Sport): Int = when (sport) {
        Sport.CFB -> 1
        Sport.NFL -> 2
        Sport.CBB -> 3
        Sport.WCBB -> 4
        Sport.NBA -> 5
        Sport.WNBA -> 6
        Sport.MLB -> 7
        Sport.NHL -> 8
        Sport.SOCCER_EPL -> 9
        Sport.SOCCER_UCL -> 10
        Sport.SOCCER_LALIGA -> 11
        Sport.SOCCER_MLS -> 12
        Sport.COLLEGE_BASEBALL -> 13
        Sport.MMA_UFC -> 14
        Sport.F1 -> 15
        else -> 20
    }

    private fun getDateRangeParam(daysBack: Int, daysForward: Int): String {
        val queryFmt = SimpleDateFormat("yyyyMMdd", Locale.US)
        val calStart = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, -daysBack) }
        val calEnd = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, daysForward) }
        return "${queryFmt.format(calStart.time)}-${queryFmt.format(calEnd.time)}"
    }

    private suspend fun fetchTopEventsFavorites(
        favTeamIds: Set<String>,
        favConferenceIds: Set<String>
    ): List<MultiSportGame> = withContext(Dispatchers.IO) {
        if (favTeamIds.isEmpty() && favConferenceIds.isEmpty()) return@withContext emptyList()
        val confTeamIds = MultiSportCatalog.getTeamIdsForFavoritedConferences(favConferenceIds)

        val favSports = mutableSetOf<Sport>()
        for (tId in favTeamIds) {
            MultiSportCatalog.findById(tId)?.sport?.let { favSports.add(it) }
        }
        for (cId in favConferenceIds) {
            MultiSportCatalog.findConferenceById(cId)?.sport?.let {
                favSports.add(it)
                if (it == Sport.CFB) {
                    favSports.add(Sport.CBB)
                    favSports.add(Sport.WCBB)
                }
            }
        }
        if (favSports.isEmpty()) {
            favSports.addAll(listOf(Sport.CFB, Sport.NFL, Sport.NBA, Sport.CBB, Sport.MLB))
        }

        val rangeParam = getDateRangeParam(7, 14)
        val deferred = favSports.map { sp ->
            async {
                try {
                    val games = fetchSingleSport(sp, rangeParam)
                    games.filter { it.isFavorite(favTeamIds, confTeamIds) }
                } catch (_: Exception) {
                    emptyList()
                }
            }
        }
        val allFavGames = deferred.awaitAll().flatten().distinctBy { "${it.sport.id}_${it.id}" }

        // Order: Live first, then upcoming (closest date first), then completed (most recent first)
        allFavGames.sortedWith(
            compareByDescending<MultiSportGame> { it.isLive }
                .thenByDescending { it.isScheduled }
                .thenBy { it.dateIso }
        )
    }

    private val summaryCache = mutableMapOf<String, GameBoxScore>()

    suspend fun getGameSummary(sport: Sport, gameId: String): GameBoxScore = withContext(Dispatchers.IO) {
        summaryCache[gameId]?.let { return@withContext it }
        val path = sport.endpointPath ?: return@withContext GameBoxScore()
        val basePath = if (path.contains("?")) path.split("?", limit = 2)[0] else path
        val url = "https://site.web.api.espn.com/apis/site/v2/sports/$basePath/summary?event=$gameId"
        try {
            val json = httpClient.get(url)
            val box = MultiSportParser.parseBoxScore(json)
            summaryCache[gameId] = box
            box
        } catch (e: Exception) {
            e.printStackTrace()
            GameBoxScore()
        }
    }

    private suspend fun fetchAllSports(dateString: String? = null): List<MultiSportGame> = withContext(Dispatchers.IO) {
        // Exclude FCS, D-II, and D-III from ALL
        val activeSports = Sport.entries.filter {
            it.isLeague && it != Sport.CFB_FCS && it != Sport.CFB_D2 && it != Sport.CFB_D3
        }
        val deferred = activeSports.map { sp ->
            async {
                try {
                    fetchSingleSport(sp, dateString)
                } catch (_: Exception) {
                    emptyList()
                }
            }
        }
        deferred.awaitAll().flatten()
    }

    private suspend fun fetchFavorites(
        favTeams: Set<String>,
        confTeamIds: Set<String>,
        dateString: String? = null
    ): List<MultiSportGame> = withContext(Dispatchers.IO) {
        if (favTeams.isEmpty() && confTeamIds.isEmpty()) return@withContext emptyList()
        val allGames = fetchAllSports(dateString)
        allGames.filter { game ->
            game.isFavorite(favTeams, confTeamIds)
        }
    }

    private fun fetchSingleSport(sport: Sport, dateString: String? = null): List<MultiSportGame> {
        val path = sport.endpointPath ?: return emptyList()
        val dateParam = if (!dateString.isNullOrBlank()) "&dates=$dateString" else ""
        val (basePath, queryParams) = if (path.contains("?")) {
            val parts = path.split("?", limit = 2)
            parts[0] to "&" + parts[1]
        } else {
            path to ""
        }
        val url = "https://site.web.api.espn.com/apis/site/v2/sports/$basePath/scoreboard?limit=200$queryParams$dateParam"
        val json = httpClient.get(url)
        return MultiSportParser.parse(json, sport)
    }

    private fun sortGames(
        games: List<MultiSportGame>,
        favoriteTeamIds: Set<String>,
        confTeamIds: Set<String> = emptySet()
    ): List<MultiSportGame> {
        return games.sortedWith(
            compareByDescending<MultiSportGame> { it.isFavorite(favoriteTeamIds, confTeamIds) }
                .thenByDescending { it.isLive }
                .thenBy { it.isFinal }
        )
    }
}
