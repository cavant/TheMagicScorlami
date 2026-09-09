package com.themagicscorlami.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.themagicscorlami.BuildConfig
import com.themagicscorlami.R
import com.themagicscorlami.data.local.*
import com.themagicscorlami.ui.components.NotificationToggleRow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    userPreferences: SportslamiUserPreferences,
    onSetThemeMode: (SportThemeMode) -> Unit,
    onSetRefreshInterval: (Int) -> Unit,
    onSetStadiumDataSaver: (Boolean) -> Unit,
    onSetWidgetIncludeConferences: (Boolean) -> Unit = {},
    onSetNotificationsMasterEnabled: (Boolean) -> Unit,
    onSetGlobalGameStart: (Boolean) -> Unit,
    onSetGlobalScoreChange: (Boolean) -> Unit,
    onSetGlobalLeadChangeOnly: (Boolean) -> Unit,
    onSetGlobalHalftime: (Boolean) -> Unit,
    onSetGlobalFinal: (Boolean) -> Unit,
    onSetGlobalRedZone: (Boolean) -> Unit,
    onSetGlobalPinLiveScoreboard: (Boolean) -> Unit,
    onSetGlobalDetailLevel: (NotificationDetailLevel) -> Unit,
    onSetVibrationEnabled: (Boolean) -> Unit,
    onSetSoundEnabled: (Boolean) -> Unit,
    onSetSpoilerDelaySeconds: (Int) -> Unit,
    onSetOddsFormat: (OddsDisplayFormat) -> Unit,
    onSetCardDensity: (CardDensityMode) -> Unit,
    onBack: () -> Unit
) {
    BackHandler(onBack = onBack)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings & Preferences", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // ==================== NOTIFICATIONS & ALERTS ====================
            Text(
                text = "GAME NOTIFICATIONS & ALERTS",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))

            Surface(
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 1.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Favorite Team Alerts",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "Receive alerts for favorited teams when scores change or games start.",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Switch(
                            checked = userPreferences.notificationsMasterEnabled,
                            onCheckedChange = { onSetNotificationsMasterEnabled(it) }
                        )
                    }

                    if (userPreferences.notificationsMasterEnabled) {
                        if (userPreferences.favoriteTeamIds.isEmpty()) {
                            Surface(
                                color = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.45f),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 10.dp)
                            ) {
                                Row(
                                    modifier = Modifier.padding(10.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.Notifications,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.tertiary,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Text(
                                        text = "No favorite teams selected yet. Live score updates and pinned notifications will activate once you star teams.",
                                        fontSize = 11.sp,
                                        color = MaterialTheme.colorScheme.onTertiaryContainer
                                    )
                                }
                            }
                        }

                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 12.dp),
                            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
                        )

                        NotificationToggleRow(
                            title = "🔔 Game Start Reminders",
                            subtitle = "Notify when a favorite team kicks off or tips off",
                            checked = userPreferences.globalGameStart,
                            enabled = true,
                            onCheckedChange = { onSetGlobalGameStart(it) }
                        )

                        HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.15f))

                        NotificationToggleRow(
                            title = "🎯 Score Updates",
                            subtitle = "Real-time notifications on scoring plays",
                            checked = userPreferences.globalScoreChange,
                            enabled = true,
                            onCheckedChange = { onSetGlobalScoreChange(it) }
                        )

                        if (userPreferences.globalScoreChange) {
                            HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.15f))

                            NotificationToggleRow(
                                title = "⚖️ Lead Changes Only",
                                subtitle = "Only notify when the lead changes or the game is tied",
                                checked = userPreferences.globalLeadChangeOnly,
                                enabled = true,
                                onCheckedChange = { onSetGlobalLeadChangeOnly(it) }
                            )
                        }

                        HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.15f))

                        NotificationToggleRow(
                            title = "⏸️ Halftime Score Recap",
                            subtitle = "Mid-game intermission score summary",
                            checked = userPreferences.globalHalftime,
                            enabled = true,
                            onCheckedChange = { onSetGlobalHalftime(it) }
                        )

                        HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.15f))

                        NotificationToggleRow(
                            title = "🏁 Final Score Alerts",
                            subtitle = "Post-game summary with winner and final score",
                            checked = userPreferences.globalFinal,
                            enabled = true,
                            onCheckedChange = { onSetGlobalFinal(it) }
                        )

                        HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.15f))

                        NotificationToggleRow(
                            title = "🚨 Football Red Zone Alert",
                            subtitle = "Alert when driving inside the opponent's 20-yard line",
                            checked = userPreferences.globalRedZone,
                            enabled = true,
                            onCheckedChange = { onSetGlobalRedZone(it) }
                        )

                        HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.15f))

                        NotificationToggleRow(
                            title = "📌 Pinned Live Scoreboard",
                            subtitle = "Keep an ongoing, silent scoreboard in notification shade while live",
                            checked = userPreferences.globalPinLiveScoreboard,
                            enabled = true,
                            onCheckedChange = { onSetGlobalPinLiveScoreboard(it) }
                        )

                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 12.dp),
                            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
                        )

                        // Vibration & Sound
                        NotificationToggleRow(
                            title = "📳 Vibration",
                            subtitle = "Vibrate phone on high-priority score alerts",
                            checked = userPreferences.vibrationEnabled,
                            enabled = true,
                            onCheckedChange = { onSetVibrationEnabled(it) }
                        )

                        HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.15f))

                        NotificationToggleRow(
                            title = "🔊 Notification Sound",
                            subtitle = "Play alert tone on scoring plays",
                            checked = userPreferences.soundEnabled,
                            enabled = true,
                            onCheckedChange = { onSetSoundEnabled(it) }
                        )
                    }
                }
            }

            if (userPreferences.notificationsMasterEnabled) {
                Spacer(modifier = Modifier.height(20.dp))

                // Detail Level Selector
                Text(
                    text = "NOTIFICATION DETAIL LEVEL",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(8.dp))

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.surface,
                    tonalElevation = 1.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column {
                        NotificationDetailLevel.entries.forEachIndexed { index, level ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { onSetGlobalDetailLevel(level) }
                                    .padding(14.dp)
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = level.displayName,
                                        fontSize = 14.sp,
                                        fontWeight = if (userPreferences.globalDetailLevel == level) FontWeight.Bold else FontWeight.Medium,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Text(
                                        text = level.description,
                                        fontSize = 11.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                                RadioButton(
                                    selected = userPreferences.globalDetailLevel == level,
                                    onClick = { onSetGlobalDetailLevel(level) }
                                )
                            }
                            if (index < NotificationDetailLevel.entries.size - 1) {
                                HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.15f))
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Spoiler Shield / Stream Delay
                Text(
                    text = "SPOILER SHIELD / STREAM DELAY SYNC",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(8.dp))

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.surface,
                    tonalElevation = 1.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column {
                        listOf(
                            0 to "Real-time (Instant push)",
                            15 to "15 seconds delay (Short cable lag)",
                            30 to "30 seconds delay (Streaming apps: YouTube TV, Hulu)",
                            60 to "60 seconds delay (Heavy stream buffer)"
                        ).forEachIndexed { idx, (sec, label) ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { onSetSpoilerDelaySeconds(sec) }
                                    .padding(14.dp)
                            ) {
                                Text(
                                    text = label,
                                    fontSize = 13.sp,
                                    fontWeight = if (userPreferences.spoilerDelaySeconds == sec) FontWeight.Bold else FontWeight.Medium,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                RadioButton(
                                    selected = userPreferences.spoilerDelaySeconds == sec,
                                    onClick = { onSetSpoilerDelaySeconds(sec) }
                                )
                            }
                            if (idx < 3) {
                                HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.15f))
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ==================== DISPLAY & ODDS ====================
            Text(
                text = "BETTING ODDS FORMAT",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))

            Surface(
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 1.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    OddsDisplayFormat.entries.forEachIndexed { index, fmt ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onSetOddsFormat(fmt) }
                                .padding(14.dp)
                        ) {
                            Column {
                                Text(
                                    text = fmt.displayName,
                                    fontSize = 14.sp,
                                    fontWeight = if (userPreferences.oddsFormat == fmt) FontWeight.Bold else FontWeight.Medium,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = "e.g. ${fmt.example}",
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            RadioButton(
                                selected = userPreferences.oddsFormat == fmt,
                                onClick = { onSetOddsFormat(fmt) }
                            )
                        }
                        if (index < OddsDisplayFormat.entries.size - 1) {
                            HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.15f))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Card Density
            Text(
                text = "SCOREBOARD VIEW DENSITY",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))

            Surface(
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 1.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    CardDensityMode.entries.forEachIndexed { index, density ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onSetCardDensity(density) }
                                .padding(14.dp)
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = density.displayName,
                                    fontSize = 14.sp,
                                    fontWeight = if (userPreferences.cardDensity == density) FontWeight.Bold else FontWeight.Medium,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = density.description,
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            RadioButton(
                                selected = userPreferences.cardDensity == density,
                                onClick = { onSetCardDensity(density) }
                            )
                        }
                        if (index < CardDensityMode.entries.size - 1) {
                            HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.15f))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ==================== APP THEME ====================
            Text(
                text = "APP THEME",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))

            Surface(
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 1.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    SportThemeMode.entries.forEachIndexed { index, mode ->
                        ThemeOptionRow(
                            title = mode.displayName,
                            subtitle = mode.subtitle,
                            isSelected = userPreferences.themeMode == mode,
                            onClick = { onSetThemeMode(mode) }
                        )
                        if (index < SportThemeMode.entries.size - 1) {
                            HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.15f))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Refresh Rate
            Text(
                text = "LIVE REFRESH INTERVAL",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))

            Surface(
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 1.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    RefreshRateOptionRow(
                        label = "Every 10 seconds (Hyper Live)",
                        seconds = 10,
                        current = userPreferences.refreshIntervalSeconds,
                        onClick = { onSetRefreshInterval(10) }
                    )
                    HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
                    RefreshRateOptionRow(
                        label = "Every 20 seconds (Standard)",
                        seconds = 20,
                        current = userPreferences.refreshIntervalSeconds,
                        onClick = { onSetRefreshInterval(20) }
                    )
                    HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
                    RefreshRateOptionRow(
                        label = "Every 30 seconds (Balanced)",
                        seconds = 30,
                        current = userPreferences.refreshIntervalSeconds,
                        onClick = { onSetRefreshInterval(30) }
                    )
                    HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
                    RefreshRateOptionRow(
                        label = "Every 60 seconds (Battery Saver)",
                        seconds = 60,
                        current = userPreferences.refreshIntervalSeconds,
                        onClick = { onSetRefreshInterval(60) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Stadium Data Saver
            Text(
                text = "STADIUM DATA SAVER",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))

            Surface(
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 1.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onSetStadiumDataSaver(!userPreferences.stadiumDataSaver) }
                        .padding(16.dp)
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Save Bandwidth in Stadiums",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "Replaces remote team logos with instant text avatars.",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Switch(
                        checked = userPreferences.stadiumDataSaver,
                        onCheckedChange = { onSetStadiumDataSaver(it) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // ==================== HOME SCREEN WIDGET ====================
            Text(
                text = "HOME SCREEN WIDGET",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))

            Surface(
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 1.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onSetWidgetIncludeConferences(!userPreferences.widgetIncludeConferences) }
                        .padding(16.dp)
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Include Favorited Conferences in Widget",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "When enabled, games from all teams in your favorited conferences will also appear in the Glance home widget alongside your starred teams.",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Switch(
                        checked = userPreferences.widgetIncludeConferences,
                        onCheckedChange = { onSetWidgetIncludeConferences(it) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // ==================== ABOUT & SUPPORT ====================
            Text(
                text = "ABOUT & SUPPORT",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))

            Surface(
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 1.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    // App Identity Header
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.mipmap.ic_launcher),
                            contentDescription = "TheMagicScorlami App Icon",
                            modifier = Modifier
                                .size(48.dp)
                                .clip(RoundedCornerShape(12.dp))
                        )
                        Column {
                            Text(
                                text = "TheMagicScorlami",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "Version ${BuildConfig.VERSION_NAME} (Build ${BuildConfig.VERSION_CODE})",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Ultra-fast, customizable, ad-free live sports tracker & alert center. Follow games across 17 leagues with real-time score updates, live pinned notifications, and personalized team alerts.",
                        fontSize = 13.sp,
                        lineHeight = 18.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Buy Me a Coffee Support Card
                    val uriHandler = LocalUriHandler.current
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.45f),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text("☕", fontSize = 20.sp)
                                Text(
                                    text = "Support TheMagicScorlami",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = MaterialTheme.colorScheme.onTertiaryContainer
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "Built for sports fans with zero ads, zero trackers, and zero paywalls. If you enjoy the app, consider supporting ongoing development with a coffee!",
                                fontSize = 12.sp,
                                lineHeight = 16.sp,
                                color = MaterialTheme.colorScheme.onTertiaryContainer.copy(alpha = 0.85f)
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Button(
                                onClick = {
                                    uriHandler.openUri("https://buymeacoffee.com/themagicsalami")
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.tertiary,
                                    contentColor = MaterialTheme.colorScheme.onTertiary
                                ),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = "☕ Buy Me a Coffee",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.15f))
                    Spacer(modifier = Modifier.height(12.dp))

                    // App Highlights
                    Text(
                        text = "KEY FEATURES",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "• 17 Sports & Leagues: CFB (FBS, FCS, D-II, D-III), NFL, CBB, WCBB, MLB, NBA, WNBA, NHL, MLS, EPL, UCL, La Liga, UFC, F1\n" +
                               "• Custom Team Alerts: Kickoff, scoring plays, lead changes, halftime, final, red zone\n" +
                               "• Live Status Bar Scoreboard: Pin real-time live games to your notification shade\n" +
                               "• Anti-Spoiler Buffer: Delay notifications by up to 120s to sync with TV or stream\n" +
                               "• 8 Theme Modes: Dynamic colors, Morphe-inspired palettes, & AMOLED pitch black\n" +
                               "• Home Screen Widget: Real-time Glance scoreboard on your launcher\n" +
                               "• Conference Favoriting: Track entire conferences & divisions with a single star",
                        fontSize = 12.sp,
                        lineHeight = 18.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.15f))
                    Spacer(modifier = Modifier.height(12.dp))

                    // Legal, Compliance & Privacy Disclaimers
                    Text(
                        text = "LEGAL, COMPLIANCE & PRIVACY",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        ExpandableLegalSection(
                            title = "Nominative Fair Use & League Trademarks",
                            content = "All team names, conference names, league names, and logos displayed in this application are trademarks or registered trademarks of their respective holders (including but not limited to NCAA, NFL, NBA, MLB, NHL, MLS, and ESPN). Use of these marks does not imply any affiliation with, sponsorship by, or endorsement by the trademark holders. Any references to these marks are strictly nominative fair use for descriptive, informational, and identification purposes only under 15 U.S.C. § 1125(c)(3)(A)."
                        )

                        ExpandableLegalSection(
                            title = "Factual Public Sports Data Reporting",
                            content = "All scores, play-by-play statistics, rankings, and schedules displayed in this app are factual matters of public knowledge reported as pure real-time event facts. Factual sports scores and game statistics are not protected by copyright (NBA v. Motorola, Inc., 105 F.3d 841 (2d Cir. 1997))."
                        )

                        ExpandableLegalSection(
                            title = "Open Source Software Licenses",
                            content = "This application is built with open source software licensed under the Apache License 2.0 and MIT licenses:\n\n• Jetpack Compose & Glance (Apache 2.0) - Google LLC\n• AndroidX Libraries & Core KTX (Apache 2.0) - The Android Open Source Project\n• OkHttp (Apache 2.0) - Square, Inc.\n• Coil Image Loading (Apache 2.0) - Coil Contributors\n• Gson (Apache 2.0) - Google LLC\n• Kotlin Coroutines (Apache 2.0) - JetBrains s.r.o."
                        )

                        ExpandableLegalSection(
                            title = "Zero-Data Collection Privacy Policy",
                            content = "• Data Collection: Zero data is collected. No personally identifiable information (PII), email addresses, IP logs, or analytics are stored or transmitted.\n• Account Creation: No accounts, logins, or user profiles are required or supported.\n• Local Storage: All favorited teams, conferences, alert settings, and display preferences are stored solely on your physical device using Android Jetpack DataStore.\n• Network Access: Network calls are exclusively directed to publicly available sports scoreboard endpoints to fetch live scores and schedules.\n• Third-Party Tracking: Zero advertising SDKs, zero behavioral trackers, and zero telemetry frameworks are included in this application."
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun ThemeOptionRow(
    title: String,
    subtitle: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp)
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontSize = 15.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = subtitle,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        RadioButton(selected = isSelected, onClick = onClick)
    }
}

@Composable
private fun RefreshRateOptionRow(
    label: String,
    seconds: Int,
    current: Int,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp)
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = if (seconds == current) FontWeight.Bold else FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface
        )
        RadioButton(selected = seconds == current, onClick = onClick)
    }
}

@Composable
private fun ExpandableLegalSection(
    title: String,
    content: String
) {
    var expanded by remember { mutableStateOf(false) }

    Surface(
        shape = RoundedCornerShape(8.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded }
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = title,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = if (expanded) "Collapse" else "Expand",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(20.dp)
                )
            }
            if (expanded) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = content,
                    fontSize = 12.sp,
                    lineHeight = 17.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

