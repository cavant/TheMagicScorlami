package com.themagicsportslami.app.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.themagicsportslami.app.data.model.MultiSportCatalog

private val Context.sportslamiDataStore: DataStore<Preferences> by preferencesDataStore(name = "sportslami_prefs")

enum class SportThemeMode(val displayName: String, val subtitle: String) {
    DYNAMIC("Material You ✨", "Follows phone wallpaper & system colors"),
    AMOLED("Pure Black OLED 🖤", "True pitch black (#000000) for OLED battery saver"),
    SALAMI("Scorlami Special 🍖", "Vibrant crimson & gold mascot theme"),
    GRIDIRON("Emerald Turf 🏈", "Football gridiron field green & neon turf accents"),
    ICE_RINK("Midnight Navy 🏒", "Deep hockey oceanic navy & ice cyan accents"),
    HARDWOOD("Sunset Court 🏀", "Basketball twilight purple & court neon orange"),
    DARK("Stadium Dark 🏟️", "Classic dark charcoal for night games"),
    LIGHT("Daylight Clean ☀️", "Crisp daytime high-contrast readability"),
    SYSTEM("System Default", "Matches Android day/night setting")
}

data class SportslamiUserPreferences(
    val favoriteTeamIds: Set<String>,
    val favoriteSportIds: Set<String>,
    val favoriteConferenceIds: Set<String> = emptySet(),
    val widgetIncludeConferences: Boolean = false,
    val themeMode: SportThemeMode,
    val refreshIntervalSeconds: Int,
    val stadiumDataSaver: Boolean,
    val notificationsMasterEnabled: Boolean = true,
    val globalGameStart: Boolean = true,
    val globalScoreChange: Boolean = true,
    val globalLeadChangeOnly: Boolean = false,
    val globalHalftime: Boolean = true,
    val globalFinal: Boolean = true,
    val globalRedZone: Boolean = true,
    val globalPinLiveScoreboard: Boolean = true,
    val globalDetailLevel: NotificationDetailLevel = NotificationDetailLevel.STANDARD,
    val vibrationEnabled: Boolean = true,
    val soundEnabled: Boolean = true,
    val spoilerDelaySeconds: Int = 0,
    val teamNotificationConfigs: Map<String, TeamNotificationConfig> = emptyMap(),
    val oddsFormat: OddsDisplayFormat = OddsDisplayFormat.AMERICAN,
    val preferredSportsbook: String = "DraftKings",
    val cardDensity: CardDensityMode = CardDensityMode.COMFORTABLE,
    val defaultLaunchSport: String = "all"
)

class SportslamiPreferences(private val context: Context) {

    companion object {
        private val KEY_FAVORITE_TEAMS = stringSetPreferencesKey("favorite_teams")
        private val KEY_FAVORITE_SPORTS = stringSetPreferencesKey("favorite_sports")
        private val KEY_THEME = stringPreferencesKey("theme_mode")
        private val KEY_INTERVAL = intPreferencesKey("refresh_interval")
        private val KEY_SAVER = booleanPreferencesKey("data_saver")

        // Notification Keys
        private val KEY_NOTIF_MASTER = booleanPreferencesKey("notif_master")
        private val KEY_NOTIF_START = booleanPreferencesKey("notif_start")
        private val KEY_NOTIF_SCORE = booleanPreferencesKey("notif_score")
        private val KEY_NOTIF_LEAD_ONLY = booleanPreferencesKey("notif_lead_only")
        private val KEY_NOTIF_HALFTIME = booleanPreferencesKey("notif_halftime")
        private val KEY_NOTIF_FINAL = booleanPreferencesKey("notif_final")
        private val KEY_NOTIF_REDZONE = booleanPreferencesKey("notif_redzone")
        private val KEY_NOTIF_PIN_LIVE = booleanPreferencesKey("notif_pin_live")
        private val KEY_NOTIF_DETAIL = stringPreferencesKey("notif_detail")
        private val KEY_NOTIF_VIBRATE = booleanPreferencesKey("notif_vibrate")
        private val KEY_NOTIF_SOUND = booleanPreferencesKey("notif_sound")
        private val KEY_NOTIF_SPOILER_DELAY = intPreferencesKey("notif_spoiler_delay")
        private val KEY_TEAM_NOTIF_CONFIGS = stringPreferencesKey("team_notif_configs")

        // Extended Settings Keys
        private val KEY_ODDS_FORMAT = stringPreferencesKey("odds_format")
        private val KEY_SPORTSBOOK = stringPreferencesKey("sportsbook")
        private val KEY_CARD_DENSITY = stringPreferencesKey("card_density")
        private val KEY_DEFAULT_SPORT = stringPreferencesKey("default_sport")
        private val KEY_FAVORITE_CONFERENCES = stringSetPreferencesKey("favorite_conferences")
        private val KEY_WIDGET_INCLUDE_CONFERENCES = booleanPreferencesKey("widget_include_conferences")
    }

    val preferencesFlow: Flow<SportslamiUserPreferences> = context.sportslamiDataStore.data.map { prefs ->
        val rawTeams = prefs[KEY_FAVORITE_TEAMS] ?: emptySet()
        val teams = rawTeams.map { id ->
            if (id.contains('_')) id
            else MultiSportCatalog.ALL_TEAMS.find { it.rawId == id }?.id ?: id
        }.toSet()
        val sports = prefs[KEY_FAVORITE_SPORTS] ?: setOf("nfl", "cfb", "nba", "mlb")
        val conferences = prefs[KEY_FAVORITE_CONFERENCES] ?: emptySet()
        val widgetConfs = prefs[KEY_WIDGET_INCLUDE_CONFERENCES] ?: false
        val themeStr = prefs[KEY_THEME] ?: SportThemeMode.SYSTEM.name
        val theme = try { SportThemeMode.valueOf(themeStr) } catch (_: Exception) { SportThemeMode.SYSTEM }
        val interval = prefs[KEY_INTERVAL] ?: 20
        val saver = prefs[KEY_SAVER] ?: false

        val notifMaster = prefs[KEY_NOTIF_MASTER] ?: true
        val notifStart = prefs[KEY_NOTIF_START] ?: true
        val notifScore = prefs[KEY_NOTIF_SCORE] ?: true
        val notifLeadOnly = prefs[KEY_NOTIF_LEAD_ONLY] ?: false
        val notifHalftime = prefs[KEY_NOTIF_HALFTIME] ?: true
        val notifFinal = prefs[KEY_NOTIF_FINAL] ?: true
        val notifRedZone = prefs[KEY_NOTIF_REDZONE] ?: true
        val notifPinLive = prefs[KEY_NOTIF_PIN_LIVE] ?: true
        val notifDetail = try {
            NotificationDetailLevel.valueOf(prefs[KEY_NOTIF_DETAIL] ?: NotificationDetailLevel.STANDARD.name)
        } catch (_: Exception) { NotificationDetailLevel.STANDARD }
        val vibrate = prefs[KEY_NOTIF_VIBRATE] ?: true
        val sound = prefs[KEY_NOTIF_SOUND] ?: true
        val spoilerDelay = prefs[KEY_NOTIF_SPOILER_DELAY] ?: 0
        val teamConfigs = TeamNotificationConfigSerializer.deserializeMap(prefs[KEY_TEAM_NOTIF_CONFIGS])

        val oddsFmt = try {
            OddsDisplayFormat.valueOf(prefs[KEY_ODDS_FORMAT] ?: OddsDisplayFormat.AMERICAN.name)
        } catch (_: Exception) { OddsDisplayFormat.AMERICAN }
        val book = prefs[KEY_SPORTSBOOK] ?: "DraftKings"
        val density = try {
            CardDensityMode.valueOf(prefs[KEY_CARD_DENSITY] ?: CardDensityMode.COMFORTABLE.name)
        } catch (_: Exception) { CardDensityMode.COMFORTABLE }
        val defSport = prefs[KEY_DEFAULT_SPORT] ?: "all"

        SportslamiUserPreferences(
            favoriteTeamIds = teams,
            favoriteSportIds = sports,
            favoriteConferenceIds = conferences,
            widgetIncludeConferences = widgetConfs,
            themeMode = theme,
            refreshIntervalSeconds = interval,
            stadiumDataSaver = saver,
            notificationsMasterEnabled = notifMaster,
            globalGameStart = notifStart,
            globalScoreChange = notifScore,
            globalLeadChangeOnly = notifLeadOnly,
            globalHalftime = notifHalftime,
            globalFinal = notifFinal,
            globalRedZone = notifRedZone,
            globalPinLiveScoreboard = notifPinLive,
            globalDetailLevel = notifDetail,
            vibrationEnabled = vibrate,
            soundEnabled = sound,
            spoilerDelaySeconds = spoilerDelay,
            teamNotificationConfigs = teamConfigs,
            oddsFormat = oddsFmt,
            preferredSportsbook = book,
            cardDensity = density,
            defaultLaunchSport = defSport
        )
    }

    suspend fun toggleFavoriteTeam(teamId: String) {
        context.sportslamiDataStore.edit { prefs ->
            val current = prefs[KEY_FAVORITE_TEAMS]?.toMutableSet() ?: mutableSetOf()
            val rawId = if (teamId.contains('_')) teamId.substringAfter('_') else teamId
            if (current.contains(teamId) || current.contains(rawId)) {
                current.remove(teamId)
                current.remove(rawId)
            } else {
                current.add(teamId)
            }
            prefs[KEY_FAVORITE_TEAMS] = current
        }
    }

    suspend fun toggleFavoriteConference(conferenceId: String) {
        context.sportslamiDataStore.edit { prefs ->
            val current = prefs[KEY_FAVORITE_CONFERENCES]?.toMutableSet() ?: mutableSetOf()
            if (current.contains(conferenceId)) {
                current.remove(conferenceId)
            } else {
                current.add(conferenceId)
            }
            prefs[KEY_FAVORITE_CONFERENCES] = current
        }
    }

    suspend fun setWidgetIncludeConferences(enabled: Boolean) {
        context.sportslamiDataStore.edit { prefs ->
            prefs[KEY_WIDGET_INCLUDE_CONFERENCES] = enabled
        }
    }

    suspend fun toggleFavoriteSport(sportId: String) {
        context.sportslamiDataStore.edit { prefs ->
            val current = prefs[KEY_FAVORITE_SPORTS]?.toMutableSet() ?: mutableSetOf()
            if (current.contains(sportId)) current.remove(sportId) else current.add(sportId)
            prefs[KEY_FAVORITE_SPORTS] = current
        }
    }

    suspend fun setThemeMode(mode: SportThemeMode) {
        context.sportslamiDataStore.edit { it[KEY_THEME] = mode.name }
    }

    suspend fun setRefreshInterval(seconds: Int) {
        context.sportslamiDataStore.edit { it[KEY_INTERVAL] = seconds }
    }

    suspend fun setStadiumDataSaver(enabled: Boolean) {
        context.sportslamiDataStore.edit { it[KEY_SAVER] = enabled }
    }

    // Notification setters
    suspend fun setNotificationsMasterEnabled(enabled: Boolean) {
        context.sportslamiDataStore.edit { it[KEY_NOTIF_MASTER] = enabled }
    }

    suspend fun setGlobalGameStart(enabled: Boolean) {
        context.sportslamiDataStore.edit { it[KEY_NOTIF_START] = enabled }
    }

    suspend fun setGlobalScoreChange(enabled: Boolean) {
        context.sportslamiDataStore.edit { it[KEY_NOTIF_SCORE] = enabled }
    }

    suspend fun setGlobalLeadChangeOnly(enabled: Boolean) {
        context.sportslamiDataStore.edit { it[KEY_NOTIF_LEAD_ONLY] = enabled }
    }

    suspend fun setGlobalHalftime(enabled: Boolean) {
        context.sportslamiDataStore.edit { it[KEY_NOTIF_HALFTIME] = enabled }
    }

    suspend fun setGlobalFinal(enabled: Boolean) {
        context.sportslamiDataStore.edit { it[KEY_NOTIF_FINAL] = enabled }
    }

    suspend fun setGlobalRedZone(enabled: Boolean) {
        context.sportslamiDataStore.edit { it[KEY_NOTIF_REDZONE] = enabled }
    }

    suspend fun setGlobalPinLiveScoreboard(enabled: Boolean) {
        context.sportslamiDataStore.edit { it[KEY_NOTIF_PIN_LIVE] = enabled }
    }

    suspend fun setGlobalDetailLevel(level: NotificationDetailLevel) {
        context.sportslamiDataStore.edit { it[KEY_NOTIF_DETAIL] = level.name }
    }

    suspend fun setVibrationEnabled(enabled: Boolean) {
        context.sportslamiDataStore.edit { it[KEY_NOTIF_VIBRATE] = enabled }
    }

    suspend fun setSoundEnabled(enabled: Boolean) {
        context.sportslamiDataStore.edit { it[KEY_NOTIF_SOUND] = enabled }
    }

    suspend fun setSpoilerDelaySeconds(seconds: Int) {
        context.sportslamiDataStore.edit { it[KEY_NOTIF_SPOILER_DELAY] = seconds }
    }

    suspend fun saveTeamNotificationConfig(config: TeamNotificationConfig) {
        context.sportslamiDataStore.edit { prefs ->
            val currentMap = TeamNotificationConfigSerializer.deserializeMap(prefs[KEY_TEAM_NOTIF_CONFIGS]).toMutableMap()
            currentMap[config.teamId] = config
            prefs[KEY_TEAM_NOTIF_CONFIGS] = TeamNotificationConfigSerializer.serializeMap(currentMap)
        }
    }

    // Extended settings setters
    suspend fun setOddsFormat(format: OddsDisplayFormat) {
        context.sportslamiDataStore.edit { it[KEY_ODDS_FORMAT] = format.name }
    }

    suspend fun setPreferredSportsbook(book: String) {
        context.sportslamiDataStore.edit { it[KEY_SPORTSBOOK] = book }
    }

    suspend fun setCardDensity(mode: CardDensityMode) {
        context.sportslamiDataStore.edit { it[KEY_CARD_DENSITY] = mode.name }
    }

    suspend fun setDefaultLaunchSport(sportId: String) {
        context.sportslamiDataStore.edit { it[KEY_DEFAULT_SPORT] = sportId }
    }
}
