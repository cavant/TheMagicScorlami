package com.themagicscorlami.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.themagicscorlami.data.local.NotificationDetailLevel
import com.themagicscorlami.data.local.SportslamiUserPreferences
import com.themagicscorlami.data.local.TeamNotificationConfig
import com.themagicscorlami.data.model.FavoriteTeamEntity
import com.themagicscorlami.data.model.Sport

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamNotificationSheet(
    team: FavoriteTeamEntity,
    userPreferences: SportslamiUserPreferences,
    onSaveConfig: (TeamNotificationConfig) -> Unit,
    onDismiss: () -> Unit
) {
    val existingConfig = userPreferences.teamNotificationConfigs[team.id] ?: TeamNotificationConfig(
        teamId = team.id,
        customSettingsEnabled = false,
        notifyGameStart = userPreferences.globalGameStart,
        notifyScoreChange = userPreferences.globalScoreChange,
        notifyLeadChangeOnly = userPreferences.globalLeadChangeOnly,
        notifyHalftime = userPreferences.globalHalftime,
        notifyFinal = userPreferences.globalFinal,
        notifyRedZone = userPreferences.globalRedZone,
        pinLiveScoreboard = userPreferences.globalPinLiveScoreboard,
        detailLevel = userPreferences.globalDetailLevel
    )

    var customEnabled by remember { mutableStateOf(existingConfig.customSettingsEnabled) }
    var gameStart by remember { mutableStateOf(existingConfig.notifyGameStart) }
    var scoreChange by remember { mutableStateOf(existingConfig.notifyScoreChange) }
    var leadOnly by remember { mutableStateOf(existingConfig.notifyLeadChangeOnly) }
    var halftime by remember { mutableStateOf(existingConfig.notifyHalftime) }
    var finalScore by remember { mutableStateOf(existingConfig.notifyFinal) }
    var redZone by remember { mutableStateOf(existingConfig.notifyRedZone) }
    var pinLive by remember { mutableStateOf(existingConfig.pinLiveScoreboard) }
    var detailLevel by remember { mutableStateOf(existingConfig.detailLevel) }

    fun commitChanges() {
        val updated = TeamNotificationConfig(
            teamId = team.id,
            customSettingsEnabled = customEnabled,
            notifyGameStart = gameStart,
            notifyScoreChange = scoreChange,
            notifyLeadChangeOnly = leadOnly,
            notifyHalftime = halftime,
            notifyFinal = finalScore,
            notifyRedZone = redZone,
            pinLiveScoreboard = pinLive,
            detailLevel = detailLevel
        )
        onSaveConfig(updated)
    }

    ModalBottomSheet(
        onDismissRequest = {
            commitChanges()
            onDismiss()
        },
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(horizontal = 20.dp)
                .padding(bottom = 24.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Header Row
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    if (!team.logoUrl.isNullOrEmpty()) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(team.logoUrl)
                                .crossfade(true)
                                .build(),
                            contentDescription = team.name,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                        )
                    }

                    Column {
                        Text(
                            text = team.name,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "  • Notification Alerts",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                IconButton(onClick = { commitChanges(); onDismiss() }) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Master Override Switch Card
            Surface(
                color = if (customEnabled) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
                        else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.padding(16.dp)
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Custom Alerts for this Team",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = if (customEnabled) "Override global settings with specific rules below"
                                   else "Currently using app global notification defaults",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Switch(
                        checked = customEnabled,
                        onCheckedChange = { customEnabled = it }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "TRIGGER ALERTS",
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
                Column(modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)) {
                    NotificationToggleRow(
                        title = "🔔 Game Start",
                        subtitle = "Alert when game kicks off or tips off",
                        checked = if (customEnabled) gameStart else userPreferences.globalGameStart,
                        enabled = customEnabled,
                        onCheckedChange = { gameStart = it }
                    )

                    HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.15f))

                    NotificationToggleRow(
                        title = "🎯 Score Changes",
                        subtitle = "Notify on touchdowns, goals, field goals, and runs",
                        checked = if (customEnabled) scoreChange else userPreferences.globalScoreChange,
                        enabled = customEnabled,
                        onCheckedChange = { scoreChange = it }
                    )

                    if (customEnabled && scoreChange) {
                        HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.15f))

                        NotificationToggleRow(
                            title = "⚖️ Lead Changes Only",
                            subtitle = "Filter out blowouts; only alert on lead changes or ties",
                            checked = leadOnly,
                            enabled = customEnabled,
                            onCheckedChange = { leadOnly = it }
                        )
                    }

                    HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.15f))

                    NotificationToggleRow(
                        title = "⏸️ Halftime / Intermission",
                        subtitle = "Score check-in at the break",
                        checked = if (customEnabled) halftime else userPreferences.globalHalftime,
                        enabled = customEnabled,
                        onCheckedChange = { halftime = it }
                    )

                    HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.15f))

                    NotificationToggleRow(
                        title = "🏁 Final Score",
                        subtitle = "End-of-game summary with final result",
                        checked = if (customEnabled) finalScore else userPreferences.globalFinal,
                        enabled = customEnabled,
                        onCheckedChange = { finalScore = it }
                    )

                    if (team.sport.isFootball) {
                        HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.15f))

                        NotificationToggleRow(
                            title = "🚨 Red Zone Alert",
                            subtitle = "Notify when driving inside the opponent's 20-yard line",
                            checked = if (customEnabled) redZone else userPreferences.globalRedZone,
                            enabled = customEnabled,
                            onCheckedChange = { redZone = it }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Live Pinned Scoreboard
            Text(
                text = "LIVE STATUS BAR PINNING",
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
                Column(modifier = Modifier.padding(14.dp)) {
                    NotificationToggleRow(
                        title = "📌 Pin Live Scoreboard",
                        subtitle = "Keep an ongoing, silent live scoreboard in notification drawer while playing",
                        checked = if (customEnabled) pinLive else userPreferences.globalPinLiveScoreboard,
                        enabled = customEnabled,
                        onCheckedChange = { pinLive = it }
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Detail Level
            Text(
                text = "NOTIFICATION DETAIL LEVEL",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(8.dp))

            val activeDetailLevel = if (customEnabled) detailLevel else userPreferences.globalDetailLevel

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                NotificationDetailLevel.entries.forEach { level ->
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = if (activeDetailLevel == level) MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                                else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                        border = if (activeDetailLevel == level) androidx.compose.foundation.BorderStroke(1.5.dp, MaterialTheme.colorScheme.primary) else null,
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            if (customEnabled) {
                                detailLevel = level
                            }
                        }
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(12.dp)
                        ) {
                            RadioButton(
                                selected = activeDetailLevel == level,
                                onClick = { if (customEnabled) detailLevel = level },
                                enabled = customEnabled
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = level.displayName,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = level.description,
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    commitChanges()
                    onDismiss()
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("Save Notification Settings", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun NotificationToggleRow(
    title: String,
    subtitle: String,
    checked: Boolean,
    enabled: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
    ) {
        Column(modifier = Modifier.weight(1f).padding(end = 12.dp)) {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                color = if (enabled) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
            Text(
                text = subtitle,
                fontSize = 11.sp,
                color = if (enabled) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
            )
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            enabled = enabled
        )
    }
}
