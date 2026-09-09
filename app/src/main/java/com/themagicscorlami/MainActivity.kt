package com.themagicscorlami

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.glance.appwidget.updateAll
import androidx.lifecycle.lifecycleScope
import com.themagicscorlami.data.local.SportThemeMode
import com.themagicscorlami.data.local.SportslamiUserPreferences
import com.themagicscorlami.data.model.MultiSportGame
import com.themagicscorlami.data.model.Sport
import com.themagicscorlami.data.repository.MultiSportResult
import com.themagicscorlami.ui.screens.FavoritesManagerScreen
import com.themagicscorlami.ui.screens.MultiSportDetailSheet
import com.themagicscorlami.ui.screens.MultiSportHomeScreen
import com.themagicscorlami.ui.screens.SettingsScreen
import com.themagicscorlami.ui.theme.TheMagicSportslamiTheme
import com.themagicscorlami.widget.MultiSportWidget
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

enum class Screen {
    HOME,
    FAVORITES,
    SETTINGS
}

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val app = application as? TheMagicSportslamiApp ?: TheMagicSportslamiApp.instance
        val repository = app.repository
        val preferences = app.preferences

        setContent {
            val userPrefs by preferences.preferencesFlow.collectAsState(
                initial = SportslamiUserPreferences(
                    favoriteTeamIds = emptySet(),
                    favoriteSportIds = setOf("nfl", "cfb", "nba", "mlb"),
                    themeMode = SportThemeMode.SALAMI,
                    refreshIntervalSeconds = 20,
                    stadiumDataSaver = false
                )
            )

            var currentScreen by remember { mutableStateOf(Screen.HOME) }
            var selectedSport by remember { mutableStateOf(Sport.ALL) }
            var selectedDateString by remember { mutableStateOf<String?>(null) }
            var multiSportResult by remember { mutableStateOf(MultiSportResult(emptyList())) }
            var isRefreshing by remember { mutableStateOf(false) }
            var selectedGameForDetails by remember { mutableStateOf<MultiSportGame?>(null) }

            val permissionLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestPermission()
            ) { /* granted / denied */ }

            LaunchedEffect(Unit) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    if (ContextCompat.checkSelfPermission(this@MainActivity, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                        permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                    }
                }
            }

            val fetchScores: () -> Unit = {
                lifecycleScope.launch {
                    isRefreshing = true
                    multiSportResult = repository.getGames(selectedSport, selectedDateString)
                    isRefreshing = false
                    try {
                        MultiSportWidget().updateAll(this@MainActivity)
                    } catch (_: Exception) {}
                    try {
                        app.notificationManager.processGames(multiSportResult.games, userPrefs)
                    } catch (_: Exception) {}
                }
            }

            LaunchedEffect(selectedSport, selectedDateString) {
                fetchScores()
            }

            // Adaptive auto-refresh
            LaunchedEffect(userPrefs.refreshIntervalSeconds, selectedSport, selectedDateString) {
                val interval = userPrefs.refreshIntervalSeconds
                if (interval > 0) {
                    while (isActive) {
                        delay(interval * 1000L)
                        multiSportResult = repository.getGames(selectedSport, selectedDateString)
                        try {
                            MultiSportWidget().updateAll(this@MainActivity)
                        } catch (_: Exception) {}
                        try {
                            app.notificationManager.processGames(multiSportResult.games, userPrefs)
                        } catch (_: Exception) {}
                    }
                }
            }

            // Layered intuitive back handling
            BackHandler(enabled = selectedGameForDetails != null) {
                selectedGameForDetails = null
            }

            BackHandler(enabled = currentScreen != Screen.HOME) {
                currentScreen = Screen.HOME
            }

            BackHandler(enabled = currentScreen == Screen.HOME && selectedDateString != null) {
                selectedDateString = null
            }

            BackHandler(enabled = currentScreen == Screen.HOME && selectedDateString == null && selectedSport != Sport.ALL) {
                selectedSport = Sport.ALL
            }

            TheMagicSportslamiTheme(themeMode = userPrefs.themeMode) {
                Surface(modifier = Modifier.fillMaxSize()) {
                    when (currentScreen) {
                        Screen.HOME -> {
                            MultiSportHomeScreen(
                                result = multiSportResult,
                                selectedSport = selectedSport,
                                selectedDateString = selectedDateString,
                                favoriteTeamIds = userPrefs.favoriteTeamIds,
                                favoriteConferenceIds = userPrefs.favoriteConferenceIds,
                                isRefreshing = isRefreshing,
                                dataSaverEnabled = userPrefs.stadiumDataSaver,
                                onSelectSport = { selectedSport = it },
                                onSelectDate = { selectedDateString = it },
                                onRefresh = fetchScores,
                                onToggleFavoriteTeam = { teamId ->
                                    lifecycleScope.launch {
                                        preferences.toggleFavoriteTeam(teamId)
                                        if (selectedSport == Sport.FAVORITES) {
                                            fetchScores()
                                        }
                                        try {
                                            MultiSportWidget().updateAll(this@MainActivity)
                                        } catch (_: Exception) {}
                                    }
                                },
                                onSelectGame = { game -> selectedGameForDetails = game },
                                onNavigateToFavorites = { currentScreen = Screen.FAVORITES },
                                onNavigateToSettings = { currentScreen = Screen.SETTINGS }
                            )

                            selectedGameForDetails?.let { game ->
                                MultiSportDetailSheet(
                                    game = game,
                                    onDismiss = { selectedGameForDetails = null }
                                )
                            }
                        }

                        Screen.FAVORITES -> {
                            FavoritesManagerScreen(
                                userPreferences = userPrefs,
                                onToggleTeam = { teamId ->
                                    lifecycleScope.launch {
                                        preferences.toggleFavoriteTeam(teamId)
                                        try {
                                            MultiSportWidget().updateAll(this@MainActivity)
                                        } catch (_: Exception) {}
                                    }
                                },
                                onToggleSport = { sportId ->
                                    lifecycleScope.launch {
                                        preferences.toggleFavoriteSport(sportId)
                                        try {
                                            MultiSportWidget().updateAll(this@MainActivity)
                                        } catch (_: Exception) {}
                                    }
                                },
                                onToggleConference = { confId ->
                                    lifecycleScope.launch {
                                        preferences.toggleFavoriteConference(confId)
                                        try {
                                            MultiSportWidget().updateAll(this@MainActivity)
                                        } catch (_: Exception) {}
                                    }
                                },
                                onSaveTeamNotificationConfig = { config ->
                                    lifecycleScope.launch {
                                        preferences.saveTeamNotificationConfig(config)
                                    }
                                },
                                onBack = { currentScreen = Screen.HOME }
                            )
                        }

                        Screen.SETTINGS -> {
                            SettingsScreen(
                                userPreferences = userPrefs,
                                onSetThemeMode = { mode -> lifecycleScope.launch { preferences.setThemeMode(mode) } },
                                onSetRefreshInterval = { sec -> lifecycleScope.launch { preferences.setRefreshInterval(sec) } },
                                onSetStadiumDataSaver = { enabled -> lifecycleScope.launch { preferences.setStadiumDataSaver(enabled) } },
                                onSetWidgetIncludeConferences = { enabled ->
                                    lifecycleScope.launch {
                                        preferences.setWidgetIncludeConferences(enabled)
                                        try {
                                            MultiSportWidget().updateAll(this@MainActivity)
                                        } catch (_: Exception) {}
                                    }
                                },
                                onSetNotificationsMasterEnabled = { enabled -> lifecycleScope.launch { preferences.setNotificationsMasterEnabled(enabled) } },
                                onSetGlobalGameStart = { enabled -> lifecycleScope.launch { preferences.setGlobalGameStart(enabled) } },
                                onSetGlobalScoreChange = { enabled -> lifecycleScope.launch { preferences.setGlobalScoreChange(enabled) } },
                                onSetGlobalLeadChangeOnly = { enabled -> lifecycleScope.launch { preferences.setGlobalLeadChangeOnly(enabled) } },
                                onSetGlobalHalftime = { enabled -> lifecycleScope.launch { preferences.setGlobalHalftime(enabled) } },
                                onSetGlobalFinal = { enabled -> lifecycleScope.launch { preferences.setGlobalFinal(enabled) } },
                                onSetGlobalRedZone = { enabled -> lifecycleScope.launch { preferences.setGlobalRedZone(enabled) } },
                                onSetGlobalPinLiveScoreboard = { enabled -> lifecycleScope.launch { preferences.setGlobalPinLiveScoreboard(enabled) } },
                                onSetGlobalDetailLevel = { level -> lifecycleScope.launch { preferences.setGlobalDetailLevel(level) } },
                                onSetVibrationEnabled = { enabled -> lifecycleScope.launch { preferences.setVibrationEnabled(enabled) } },
                                onSetSoundEnabled = { enabled -> lifecycleScope.launch { preferences.setSoundEnabled(enabled) } },
                                onSetSpoilerDelaySeconds = { sec -> lifecycleScope.launch { preferences.setSpoilerDelaySeconds(sec) } },
                                onSetOddsFormat = { fmt -> lifecycleScope.launch { preferences.setOddsFormat(fmt) } },
                                onSetCardDensity = { density -> lifecycleScope.launch { preferences.setCardDensity(density) } },
                                onBack = { currentScreen = Screen.HOME }
                            )
                        }
                    }
                }
            }
        }
    }
}
