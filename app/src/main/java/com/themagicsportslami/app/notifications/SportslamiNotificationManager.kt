package com.themagicsportslami.app.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.themagicsportslami.app.MainActivity
import com.themagicsportslami.app.R
import com.themagicsportslami.app.data.local.NotificationDetailLevel
import com.themagicsportslami.app.data.local.SportslamiUserPreferences
import com.themagicsportslami.app.data.local.TeamNotificationConfig
import com.themagicsportslami.app.data.model.MultiSportGame
import com.themagicsportslami.app.data.model.SportGameState
import java.util.concurrent.ConcurrentHashMap

data class GameStateSnapshot(
    val homeScore: Int,
    val awayScore: Int,
    val state: SportGameState,
    val period: Int,
    val isRedZone: Boolean
)

class SportslamiNotificationManager(private val context: Context) {

    companion object {
        const val CHANNEL_LIVE_ALERTS = "channel_live_alerts"
        const val CHANNEL_GAME_STATUS = "channel_game_status"
        const val CHANNEL_PINNED_LIVE = "channel_pinned_live"

        private const val PINNED_ID_BASE = 900000
    }

    private val notificationManager = NotificationManagerCompat.from(context)
    private val previousSnapshots = ConcurrentHashMap<String, GameStateSnapshot>()
    private val activePinnedGameIds = ConcurrentHashMap.newKeySet<String>()

    init {
        createNotificationChannels()
    }

    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val systemManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager ?: return

            // 1. Live Game Alerts (High priority, vibrates/chimes on score change)
            val liveChannel = NotificationChannel(
                CHANNEL_LIVE_ALERTS,
                "Live Game Alerts",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Real-time score changes, lead changes, and red zone alerts for favorite teams"
                enableVibration(true)
            }

            // 2. Game Status (Normal priority: Game start, halftime, final score)
            val statusChannel = NotificationChannel(
                CHANNEL_GAME_STATUS,
                "Game Starts & Final Scores",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Game kickoff reminders, halftime summaries, and final game results"
            }

            // 3. Pinned Live Scoreboard (Low priority: Silent ongoing status bar updates)
            val pinnedChannel = NotificationChannel(
                CHANNEL_PINNED_LIVE,
                "Pinned Live Scoreboard",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Persistent live game scores and clocks pinned to the notification drawer"
                setShowBadge(false)
                enableVibration(false)
                setSound(null, null)
            }

            systemManager.createNotificationChannels(listOf(liveChannel, statusChannel, pinnedChannel))
        }
    }

    fun processGames(games: List<MultiSportGame>, userPrefs: SportslamiUserPreferences) {
        if (!userPrefs.notificationsMasterEnabled || userPrefs.favoriteTeamIds.isEmpty()) {
            clearAllPinned()
            return
        }

        if (!userPrefs.globalPinLiveScoreboard) {
            clearAllPinned()
        }

        val favoriteGames = games.filter { game ->
            game.isFavorite(userPrefs.favoriteTeamIds)
        }

        // Dismiss any pinned notifications for games that are no longer live or not favorites
        val currentLiveFavGameIds = favoriteGames.filter { it.isLive }.map { it.id }.toSet()
        for (pinnedId in activePinnedGameIds.toList()) {
            if (pinnedId !in currentLiveFavGameIds) {
                dismissPinnedScoreboard(pinnedId)
            }
        }

        if (favoriteGames.isEmpty()) {
            return
        }

        for (game in favoriteGames) {
            val isHomeFav = game.homeTeam.matchesFavorite(userPrefs.favoriteTeamIds)
            val favTeamId = if (isHomeFav) game.homeTeam.id else game.awayTeam.id
            val teamConfig = resolveConfig(favTeamId, userPrefs)

            val currentSnapshot = GameStateSnapshot(
                homeScore = game.homeTeam.score,
                awayScore = game.awayTeam.score,
                state = game.status.state,
                period = game.status.period,
                isRedZone = game.situation?.isRedZone == true
            )

            val previousSnapshot = previousSnapshots[game.id]

            if (previousSnapshot != null) {
                evaluateTriggers(game, previousSnapshot, currentSnapshot, teamConfig, isHomeFav)
            }

            previousSnapshots[game.id] = currentSnapshot

            // Manage Ongoing Pinned Scoreboard
            if (teamConfig.pinLiveScoreboard && userPrefs.globalPinLiveScoreboard && game.isLive) {
                updatePinnedLiveScoreboard(game, teamConfig)
            } else if (activePinnedGameIds.contains(game.id)) {
                dismissPinnedScoreboard(game.id)
            }
        }
    }

    private fun resolveConfig(teamId: String, userPrefs: SportslamiUserPreferences): TeamNotificationConfig {
        val custom = userPrefs.teamNotificationConfigs[teamId]
        if (custom != null && custom.customSettingsEnabled) {
            return custom
        }

        return TeamNotificationConfig(
            teamId = teamId,
            customSettingsEnabled = false,
            notifyGameStart = userPrefs.globalGameStart,
            notifyScoreChange = userPrefs.globalScoreChange,
            notifyLeadChangeOnly = userPrefs.globalLeadChangeOnly,
            notifyHalftime = userPrefs.globalHalftime,
            notifyFinal = userPrefs.globalFinal,
            notifyRedZone = userPrefs.globalRedZone,
            pinLiveScoreboard = userPrefs.globalPinLiveScoreboard,
            detailLevel = userPrefs.globalDetailLevel
        )
    }

    private fun evaluateTriggers(
        game: MultiSportGame,
        prev: GameStateSnapshot,
        curr: GameStateSnapshot,
        config: TeamNotificationConfig,
        isHomeFav: Boolean
    ) {
        // 1. Game Start
        if (config.notifyGameStart && prev.state == SportGameState.SCHEDULED && curr.state == SportGameState.IN_PROGRESS) {
            postStatusNotification(
                game = game,
                title = "🔔 ${game.name} Started!",
                message = formatGameMessage(game, config.detailLevel, "Game Started")
            )
        }

        // 2. Score Change & Lead Change
        if (config.notifyScoreChange && (prev.homeScore != curr.homeScore || prev.awayScore != curr.awayScore)) {
            val favScore = if (isHomeFav) curr.homeScore else curr.awayScore
            val oppScore = if (isHomeFav) curr.awayScore else curr.homeScore
            val prevFav = if (isHomeFav) prev.homeScore else prev.awayScore
            val prevOpp = if (isHomeFav) prev.awayScore else prev.homeScore

            val isLeadChange = (prevFav <= prevOpp && favScore > oppScore) ||
                               (prevFav >= prevOpp && favScore < oppScore) ||
                               (prevFav != prevOpp && favScore == oppScore)

            if (!config.notifyLeadChangeOnly || isLeadChange) {
                val scoringTeam = if (curr.homeScore > prev.homeScore) game.homeTeam else game.awayTeam
                val title = if (isLeadChange) "🚨 LEAD CHANGE: ${scoringTeam.shortName} scores!" else "🎯 SCORE: ${scoringTeam.shortName}"
                postLiveAlertNotification(
                    game = game,
                    title = title,
                    message = formatGameMessage(game, config.detailLevel)
                )
            }
        }

        // 3. Halftime / Period Break
        if (config.notifyHalftime && prev.state != SportGameState.HALFTIME && curr.state == SportGameState.HALFTIME) {
            postStatusNotification(
                game = game,
                title = "⏸️ Halftime: ${game.awayTeam.abbreviation} ${game.awayTeam.score}, ${game.homeTeam.abbreviation} ${game.homeTeam.score}",
                message = formatGameMessage(game, config.detailLevel, "Halftime Break")
            )
        }

        // 4. Final Score
        if (config.notifyFinal && prev.state != SportGameState.FINAL && curr.state == SportGameState.FINAL) {
            val winnerName = if (curr.homeScore > curr.awayScore) game.homeTeam.name else if (curr.awayScore > curr.homeScore) game.awayTeam.name else "Tie"
            val title = "🏁 FINAL: $winnerName wins!"
            postStatusNotification(
                game = game,
                title = title,
                message = formatGameMessage(game, config.detailLevel, "Final Result")
            )
        }

        // 5. Red Zone Alert (Football)
        if (config.notifyRedZone && !prev.isRedZone && curr.isRedZone) {
            val drivingTeam = if (game.situation?.possessionTeamId == game.homeTeam.rawId || game.situation?.possessionTeamId == game.homeTeam.id) game.homeTeam.name else game.awayTeam.name
            postLiveAlertNotification(
                game = game,
                title = "🚨 RED ZONE ALERT: $drivingTeam",
                message = "${game.situation?.downDistanceText ?: "Inside the 20"} • ${game.awayTeam.abbreviation} ${game.awayTeam.score} @ ${game.homeTeam.abbreviation} ${game.homeTeam.score}"
            )
        }
    }

    private fun formatGameMessage(game: MultiSportGame, detailLevel: NotificationDetailLevel, extraStatus: String? = null): String {
        val awayName = game.awayTeam.abbreviation.ifBlank { game.awayTeam.shortName.ifBlank { game.awayTeam.name } }
        val homeName = game.homeTeam.abbreviation.ifBlank { game.homeTeam.shortName.ifBlank { game.homeTeam.name } }
        val baseScore = "$awayName ${game.awayTeam.score} @ $homeName ${game.homeTeam.score}"
        val clock = extraStatus ?: game.status.shortDetail.ifBlank { game.status.detail.ifBlank { "Live" } }

        return when (detailLevel) {
            NotificationDetailLevel.MINIMAL -> {
                "$baseScore ($clock)"
            }
            NotificationDetailLevel.STANDARD -> {
                val situation = game.situation?.downDistanceText?.let { " • $it" } ?: ""
                "$baseScore ($clock)$situation"
            }
            NotificationDetailLevel.DETAILED -> {
                val situation = game.situation?.downDistanceText?.let { " • $it" } ?: ""
                val odds = game.odds?.details?.let { " • Odds: $it" } ?: ""
                val leader = game.leaders.firstOrNull()?.let { " • ${it.category}: ${it.athleteName} (${it.displayValue})" } ?: ""
                "$baseScore ($clock)$situation$odds$leader"
            }
        }
    }

    private fun postLiveAlertNotification(game: MultiSportGame, title: String, message: String) {
        val pendingIntent = createOpenAppPendingIntent(game.id)
        val notif = NotificationCompat.Builder(context, CHANNEL_LIVE_ALERTS)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(message)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        try {
            notificationManager.notify(game.id.hashCode(), notif)
        } catch (_: SecurityException) {}
    }

    private fun postStatusNotification(game: MultiSportGame, title: String, message: String) {
        val pendingIntent = createOpenAppPendingIntent(game.id)
        val notif = NotificationCompat.Builder(context, CHANNEL_GAME_STATUS)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(message)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        try {
            notificationManager.notify(game.id.hashCode() + 1000, notif)
        } catch (_: SecurityException) {}
    }

    private fun updatePinnedLiveScoreboard(game: MultiSportGame, config: TeamNotificationConfig) {
        val notificationId = PINNED_ID_BASE + Math.abs(game.id.hashCode())
        activePinnedGameIds.add(game.id)

        val pendingIntent = createOpenAppPendingIntent(game.id)
        val awayName = game.awayTeam.abbreviation.ifBlank { game.awayTeam.shortName.ifBlank { game.awayTeam.name } }
        val homeName = game.homeTeam.abbreviation.ifBlank { game.homeTeam.shortName.ifBlank { game.homeTeam.name } }
        val title = "🔴 LIVE: $awayName ${game.awayTeam.score} @ $homeName ${game.homeTeam.score}"
        val body = formatGameMessage(game, config.detailLevel)

        val notif = NotificationCompat.Builder(context, CHANNEL_PINNED_LIVE)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(body)
            .setStyle(NotificationCompat.BigTextStyle().bigText(body))
            .setOngoing(true)
            .setOnlyAlertOnce(true)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setContentIntent(pendingIntent)
            .addAction(0, "Open Matchup", pendingIntent)
            .build()

        try {
            notificationManager.notify(notificationId, notif)
        } catch (_: SecurityException) {}
    }

    fun dismissPinnedScoreboard(gameId: String) {
        val notificationId = PINNED_ID_BASE + Math.abs(gameId.hashCode())
        activePinnedGameIds.remove(gameId)
        try {
            notificationManager.cancel(notificationId)
        } catch (_: Exception) {}
    }

    fun clearAllPinned() {
        for (gameId in activePinnedGameIds) {
            val notificationId = PINNED_ID_BASE + Math.abs(gameId.hashCode())
            try {
                notificationManager.cancel(notificationId)
            } catch (_: Exception) {}
        }
        activePinnedGameIds.clear()
    }

    private fun createOpenAppPendingIntent(gameId: String): PendingIntent {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra("selected_game_id", gameId)
        }
        return PendingIntent.getActivity(
            context,
            gameId.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }
}
