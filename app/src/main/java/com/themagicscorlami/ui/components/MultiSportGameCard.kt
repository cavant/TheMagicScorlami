package com.themagicscorlami.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SportsFootball
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.themagicscorlami.data.model.*
import com.themagicscorlami.ui.theme.LiveGreen
import com.themagicscorlami.ui.theme.MagicGold
import com.themagicscorlami.ui.theme.SalamiCrimson

@Composable
fun MultiSportGameCard(
    game: MultiSportGame,
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit,
    onClick: () -> Unit,
    dataSaverEnabled: Boolean = false,
    modifier: Modifier = Modifier
) {
    val home = game.homeTeam
    val away = game.awayTeam

    val accessibilityDesc = buildString {
        append("${game.sport.displayName}. ")
        if (game.sport == Sport.MMA_UFC && game.combatFight != null) {
            val f = game.combatFight
            append("${f.fighterA.name} vs ${f.fighterB.name}. ")
            if (!f.weightClass.isNullOrBlank()) append("${f.weightClass}. ")
            if (!f.resultText.isNullOrBlank()) append("Result: ${f.resultText}. ")
        } else {
            append("${away.shortName} ${if (game.isScheduled) "" else "${away.score},"} ")
            append("${home.shortName} ${if (game.isScheduled) "" else "${home.score}."} ")
            append("${game.status.detail}. ")
            if (game.situation?.downDistanceText != null) append("${game.situation.downDistanceText}. ")
        }
        if (game.odds?.formattedSummary != null) append("Odds: ${game.odds.formattedSummary}. ")
        if (isFavorite) append("Favorited team matchup.")
    }

    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .semantics { contentDescription = accessibilityDesc }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            // Top Row: League Badge, Status, TV Broadcast, Favorite Star
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    // Sport badge
                    Surface(
                        shape = RoundedCornerShape(4.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant
                    ) {
                        Text(
                            text = "${game.sport.emoji} ${game.sport.shortName}",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }

                    // Live dot indicator
                    if (game.isLive) {
                        Box(
                            modifier = Modifier
                                .size(7.dp)
                                .clip(CircleShape)
                                .background(LiveGreen)
                        )
                    }

                    // Game status / clock
                    Text(
                        text = when (game.status.state) {
                            SportGameState.IN_PROGRESS -> game.status.shortDetail
                            SportGameState.FINAL -> "FINAL"
                            SportGameState.HALFTIME -> "HALFTIME"
                            SportGameState.SCHEDULED -> game.status.shortDetail.ifEmpty { "UPCOMING" }
                            else -> game.status.shortDetail
                        },
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (game.isLive) LiveGreen else MaterialTheme.colorScheme.onSurface
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // TV Network
                    if (game.broadcasts.isNotEmpty()) {
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant
                        ) {
                            Text(
                                text = game.broadcasts.first(),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
                            )
                        }
                    }

                    // Favorite Star (48dp touch target for accessibility)
                    IconButton(
                        onClick = onToggleFavorite,
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Default.Star else Icons.Outlined.StarBorder,
                            contentDescription = if (isFavorite) "Remove from favorites" else "Add to favorites",
                            tint = if (isFavorite) MagicGold else MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (game.sport == Sport.MMA_UFC && game.combatFight != null) {
                // MMA / UFC Fight Card Layout
                CombatFightCard(
                    fight = game.combatFight,
                    dataSaverEnabled = dataSaverEnabled
                )
            } else {
                // Away Team
                SportTeamRow(
                    competitor = away,
                    isWinner = game.isFinal && away.score > home.score,
                    isGameStarted = !game.isScheduled,
                    dataSaverEnabled = dataSaverEnabled
                )

                Spacer(modifier = Modifier.height(6.dp))

                // Home Team
                SportTeamRow(
                    competitor = home,
                    isWinner = game.isFinal && home.score > away.score,
                    isGameStarted = !game.isScheduled,
                    dataSaverEnabled = dataSaverEnabled
                )
            }

            // Sport-specific situation bar
            if (game.isLive && game.situation != null) {
                Spacer(modifier = Modifier.height(8.dp))
                HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
                Spacer(modifier = Modifier.height(6.dp))

                when (game.sport) {
                    Sport.NFL, Sport.CFB, Sport.CFB_FCS, Sport.CFB_D2, Sport.CFB_D3 -> {
                        // Football Down & Distance + Possession
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.SportsFootball,
                                    contentDescription = null,
                                    tint = if (game.situation.isRedZone) SalamiCrimson else MagicGold,
                                    modifier = Modifier.size(14.dp)
                                )
                                Text(
                                    text = game.situation.downDistanceText ?: "In Progress",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (game.situation.isRedZone) SalamiCrimson else MaterialTheme.colorScheme.primary
                                )
                                if (game.situation.isRedZone) {
                                    Text(
                                        text = "RED ZONE",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = SalamiCrimson
                                    )
                                }
                            }
                        }
                    }

                    Sport.MLB, Sport.COLLEGE_BASEBALL -> {
                        // Baseball Diamond + Outs + Count
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            BaseballDiamond(
                                onFirst = game.situation.onFirst,
                                onSecond = game.situation.onSecond,
                                onThird = game.situation.onThird,
                                outs = game.situation.outs,
                                balls = game.situation.balls,
                                strikes = game.situation.strikes
                            )

                            if (game.situation.batterName != null) {
                                Text(
                                    text = "Batting: ${game.situation.batterName}",
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }

                    Sport.NHL -> {
                        // Hockey Shots on Goal
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            if (game.situation.shotsAway != null && game.situation.shotsHome != null) {
                                Text(
                                    text = "Shots: ${away.abbreviation} ${game.situation.shotsAway} • ${home.abbreviation} ${game.situation.shotsHome}",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            if (game.situation.isPowerPlay) {
                                Text(
                                    text = "POWER PLAY",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = MagicGold
                                )
                            }
                        }
                    }

                    Sport.SOCCER_EPL, Sport.SOCCER_MLS, Sport.SOCCER_UCL, Sport.SOCCER_LALIGA -> {
                        // Soccer Match Minute
                        if (game.situation.minute != null) {
                            Text(
                                text = "⏱ Match Time: ${game.situation.minute}'${if (game.situation.stoppageMinute != null) "+${game.situation.stoppageMinute}'" else ""}",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = LiveGreen
                            )
                        }
                    }

                    else -> {}
                }
            }

            // Odds Badge (Point Spread / Over-Under)
            if (game.odds != null && game.odds.formattedSummary.isNotBlank()) {
                Spacer(modifier = Modifier.height(6.dp))
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                    border = BorderStroke(0.5.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(5.dp),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = "🎲",
                            fontSize = 11.sp
                        )
                        val providerPrefix = if (!game.odds.provider.isNullOrBlank()) "${game.odds.provider}: " else ""
                        Text(
                            text = "$providerPrefix${game.odds.formattedSummary}",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SportTeamRow(
    competitor: SportCompetitor,
    isWinner: Boolean,
    isGameStarted: Boolean,
    dataSaverEnabled: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(1f)
        ) {
            if (!dataSaverEnabled && competitor.logoUrl != null) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(competitor.logoUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                )
            } else {
                Surface(
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    modifier = Modifier.size(24.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = competitor.abbreviation.take(3),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            if (competitor.rank != null && competitor.rank in 1..25) {
                Surface(
                    shape = RoundedCornerShape(3.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant
                ) {
                    Text(
                        text = "${competitor.rank}",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                    )
                }
            }

            Text(
                text = competitor.shortName,
                fontSize = 15.sp,
                fontWeight = if (isWinner) FontWeight.Bold else FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            if (competitor.recordSummary != null) {
                Text(
                    text = "(${competitor.recordSummary})",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        if (isGameStarted) {
            Text(
                text = "${competitor.score}",
                fontSize = 19.sp,
                fontWeight = if (isWinner) FontWeight.ExtraBold else FontWeight.SemiBold,
                color = if (isWinner) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(start = 12.dp)
            )
        }
    }
}

@Composable
private fun CombatFightCard(
    fight: CombatFight,
    dataSaverEnabled: Boolean,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        if (!fight.weightClass.isNullOrBlank()) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.padding(bottom = 6.dp)
            ) {
                Text(
                    text = if (fight.isTitleFight) "🏆 ${fight.weightClass} Championship" else "🥊 ${fight.weightClass}",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (fight.isTitleFight) MagicGold else MaterialTheme.colorScheme.primary
                )
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Fighter A
            FighterColumn(
                fighter = fight.fighterA,
                alignEnd = false,
                dataSaverEnabled = dataSaverEnabled,
                modifier = Modifier.weight(1f)
            )

            // VS or Result in center
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(horizontal = 8.dp)
            ) {
                if (!fight.resultText.isNullOrBlank()) {
                    Surface(
                        shape = RoundedCornerShape(4.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant
                    ) {
                        Text(
                            text = fight.resultText,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                } else {
                    Text(
                        text = "VS",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                    )
                }
            }

            // Fighter B
            FighterColumn(
                fighter = fight.fighterB,
                alignEnd = true,
                dataSaverEnabled = dataSaverEnabled,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun FighterColumn(
    fighter: Fighter,
    alignEnd: Boolean,
    dataSaverEnabled: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = if (alignEnd) Arrangement.End else Arrangement.Start,
        modifier = modifier
    ) {
        if (!alignEnd && !dataSaverEnabled && fighter.headshotUrl != null) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(fighter.headshotUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(8.dp))
        }

        Column(
            horizontalAlignment = if (alignEnd) Alignment.End else Alignment.Start
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                if (fighter.isWinner) {
                    Text(
                        text = "👑 WIN",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = LiveGreen
                    )
                }
                Text(
                    text = fighter.shortName,
                    fontSize = 14.sp,
                    fontWeight = if (fighter.isWinner) FontWeight.Bold else FontWeight.Medium,
                    color = if (fighter.isWinner) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            if (!fighter.record.isNullOrBlank()) {
                Text(
                    text = fighter.record,
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        if (alignEnd && !dataSaverEnabled && fighter.headshotUrl != null) {
            Spacer(modifier = Modifier.width(8.dp))
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(fighter.headshotUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
            )
        }
    }
}
