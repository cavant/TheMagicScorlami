package com.themagicsportslami.app.ui.screens

import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.themagicsportslami.app.TheMagicSportslamiApp
import com.themagicsportslami.app.data.model.*
import com.themagicsportslami.app.data.repository.MultiSportRepository
import com.themagicsportslami.app.ui.theme.LiveGreen
import com.themagicsportslami.app.ui.theme.MagicGold
import com.themagicsportslami.app.ui.theme.SalamiCrimson

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MultiSportDetailSheet(
    game: MultiSportGame,
    onDismiss: () -> Unit,
    repository: MultiSportRepository = TheMagicSportslamiApp.instance.repository
) {
    val context = LocalContext.current
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    var boxScore by remember { mutableStateOf<GameBoxScore?>(null) }
    var isLoadingSummary by remember { mutableStateOf(false) }

    LaunchedEffect(game.id) {
        isLoadingSummary = true
        boxScore = repository.getGameSummary(game.sport, game.id)
        isLoadingSummary = false
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(horizontal = 16.dp)
                .padding(bottom = 24.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Header Row: Sport Badge & Status & Share/Close Buttons
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant
                    ) {
                        Text(
                            text = "${game.sport.emoji} ${game.sport.displayName}",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }

                    if (game.isLive) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(LiveGreen)
                        )
                    }

                    Text(
                        text = game.status.detail.ifEmpty { game.status.shortDetail },
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (game.isLive) LiveGreen else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = {
                            val shareSummary = buildString {
                                if (game.combatFight != null) {
                                    append("${game.combatFight.fighterA.name} vs ${game.combatFight.fighterB.name}")
                                    if (!game.combatFight.resultText.isNullOrEmpty()) {
                                        append(" - ${game.combatFight.resultText}")
                                    }
                                } else {
                                    append("${game.awayTeam.name} (${game.awayTeam.score}) vs ${game.homeTeam.name} (${game.homeTeam.score})")
                                }
                                val status = game.status.detail.ifEmpty { game.status.shortDetail }
                                if (status.isNotEmpty()) {
                                    append(" [$status]")
                                }
                                game.odds?.details?.let { odds ->
                                    if (odds.isNotEmpty()) {
                                        append(" | Odds: $odds")
                                    }
                                }
                                append("\nShared via TheMagicScorlami 🥓")
                            }
                            val sendIntent = Intent().apply {
                                action = Intent.ACTION_SEND
                                putExtra(Intent.EXTRA_TEXT, shareSummary)
                                type = "text/plain"
                            }
                            val shareIntent = Intent.createChooser(sendIntent, "Share Score")
                            context.startActivity(shareIntent)
                        }
                    ) {
                        Icon(imageVector = Icons.Default.Share, contentDescription = "Share game summary")
                    }

                    IconButton(onClick = onDismiss) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Teams / Fighters Scoreboard Header Banner
            DetailMatchupBanner(game = game)

            Spacer(modifier = Modifier.height(14.dp))

            // Navigation Tabs: Matchup & Odds | Team Stats | Box Score
            TabRow(
                selectedTabIndex = selectedTabIndex,
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                contentColor = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clip(RoundedCornerShape(8.dp))
            ) {
                Tab(
                    selected = selectedTabIndex == 0,
                    onClick = { selectedTabIndex = 0 },
                    text = { Text("Matchup & Odds", fontWeight = FontWeight.Bold, fontSize = 12.sp) }
                )
                Tab(
                    selected = selectedTabIndex == 1,
                    onClick = { selectedTabIndex = 1 },
                    text = { Text("Team Stats", fontWeight = FontWeight.Bold, fontSize = 12.sp) }
                )
                Tab(
                    selected = selectedTabIndex == 2,
                    onClick = { selectedTabIndex = 2 },
                    text = { Text("Box Score", fontWeight = FontWeight.Bold, fontSize = 12.sp) }
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            when (selectedTabIndex) {
                0 -> MatchupAndOddsTab(game = game, boxScore = boxScore)
                1 -> TeamStatsTab(game = game, boxScore = boxScore, isLoading = isLoadingSummary)
                2 -> BoxScoreTab(game = game, boxScore = boxScore, isLoading = isLoadingSummary)
            }
        }
    }
}

@Composable
private fun DetailMatchupBanner(game: MultiSportGame) {
    if (game.sport == Sport.MMA_UFC && game.combatFight != null) {
        val fight = game.combatFight
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(14.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (!fight.weightClass.isNullOrBlank()) {
                    Text(
                        text = if (fight.isTitleFight) "🏆 ${fight.weightClass} Championship" else "🥊 ${fight.weightClass}",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = if (fight.isTitleFight) MagicGold else MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    FighterBannerColumn(fighter = fight.fighterA, modifier = Modifier.weight(1f))
                    Text(
                        text = "VS",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Black,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                    FighterBannerColumn(fighter = fight.fighterB, modifier = Modifier.weight(1f))
                }

                if (!fight.resultText.isNullOrBlank()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Surface(
                        shape = RoundedCornerShape(4.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant
                    ) {
                        Text(
                            text = "Result: ${fight.resultText}",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }
                }
            }
        }
    } else {
        val away = game.awayTeam
        val home = game.homeTeam
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp)
            ) {
                TeamBannerColumn(competitor = away, isWinner = game.isFinal && away.score > home.score, isGameStarted = !game.isScheduled, modifier = Modifier.weight(1f))
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(horizontal = 12.dp)
                ) {
                    Text(
                        text = "VS",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Black,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                    )
                }
                TeamBannerColumn(competitor = home, isWinner = game.isFinal && home.score > away.score, isGameStarted = !game.isScheduled, modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
private fun TeamBannerColumn(
    competitor: SportCompetitor,
    isWinner: Boolean,
    isGameStarted: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        if (competitor.logoUrl != null) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(competitor.logoUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = competitor.name,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
            )
        } else {
            Surface(
                shape = CircleShape,
                color = MaterialTheme.colorScheme.surfaceVariant,
                modifier = Modifier.size(44.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = competitor.abbreviation.take(3),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            if (competitor.rank != null && competitor.rank in 1..25) {
                Surface(
                    shape = RoundedCornerShape(3.dp),
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                ) {
                    Text(
                        text = "#${competitor.rank}",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                    )
                }
            }

            Text(
                text = competitor.shortName,
                fontSize = 15.sp,
                fontWeight = if (isWinner) FontWeight.Bold else FontWeight.SemiBold,
                color = if (isWinner) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        if (competitor.recordSummary != null) {
            Text(
                text = competitor.recordSummary,
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        if (isGameStarted) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "${competitor.score}",
                fontSize = 26.sp,
                fontWeight = FontWeight.Black,
                color = if (isWinner) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
private fun FighterBannerColumn(
    fighter: Fighter,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        if (fighter.headshotUrl != null) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(fighter.headshotUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = fighter.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
            )
        } else {
            Surface(
                shape = CircleShape,
                color = MaterialTheme.colorScheme.surfaceVariant,
                modifier = Modifier.size(48.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = fighter.shortName.take(2).uppercase(),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            if (fighter.isWinner) {
                Text(
                    text = "👑",
                    fontSize = 12.sp
                )
            }
            Text(
                text = fighter.shortName,
                fontSize = 14.sp,
                fontWeight = if (fighter.isWinner) FontWeight.Bold else FontWeight.SemiBold,
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
}

@Composable
private fun MatchupAndOddsTab(game: MultiSportGame, boxScore: GameBoxScore?) {
    Column(modifier = Modifier.fillMaxWidth()) {
        // 1. Linescore Table (for team sports)
        if (game.sport != Sport.MMA_UFC) {
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Scoring by Period",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(bottom = 10.dp)
                    )
                    LinescoreTable(away = game.awayTeam, home = game.homeTeam, sport = game.sport)
                }
            }
            Spacer(modifier = Modifier.height(14.dp))
        }

        // 2. Betting Odds & Spread Section
        if (game.odds != null) {
            BettingOddsCard(odds = game.odds, awayTeam = game.awayTeam, homeTeam = game.homeTeam)
            Spacer(modifier = Modifier.height(14.dp))
        }

        // 3. Live Win Probability (if available)
        val winProb = boxScore?.winProbability
        if (winProb != null) {
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Win Probability",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "${game.awayTeam.abbreviation} ${"%.1f".format(winProb.awayTeamPercentage)}% • ${game.homeTeam.abbreviation} ${"%.1f".format(winProb.homeTeamPercentage)}%",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    val awayRatio = (winProb.awayTeamPercentage / 100.0).toFloat().coerceIn(0.05f, 0.95f)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(10.dp)
                            .clip(RoundedCornerShape(5.dp))
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(awayRatio)
                                .fillMaxHeight()
                                .background(MaterialTheme.colorScheme.secondary)
                        )
                        Box(
                            modifier = Modifier
                                .weight(1f - awayRatio)
                                .fillMaxHeight()
                                .background(MaterialTheme.colorScheme.primary)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(14.dp))
        }

        // 4. Quick Game Leaders
        if (game.leaders.isNotEmpty()) {
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "Game Leaders",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    game.leaders.forEachIndexed { index, leader ->
                        if (index > 0) {
                            HorizontalDivider(
                                modifier = Modifier.padding(vertical = 8.dp),
                                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
                            )
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column {
                                Text(
                                    text = leader.category,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Text(
                                    text = leader.athleteName,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                            Text(
                                text = leader.displayValue,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(14.dp))
        }

        // 5. Game Information (Venue, Broadcasts)
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Game Information",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                if (!game.venueName.isNullOrBlank()) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Place, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(16.dp))
                        Text(text = "Venue: ${game.venueName}", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurface)
                    }
                }

                if (game.broadcasts.isNotEmpty()) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Tv, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(16.dp))
                        Text(text = "TV Broadcast: ${game.broadcasts.joinToString(", ")}", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurface)
                    }
                }
            }
        }
    }
}

@Composable
private fun BettingOddsCard(
    odds: GameOdds,
    awayTeam: SportCompetitor,
    homeTeam: SportCompetitor
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(text = "🎲", fontSize = 14.sp)
                    Text(
                        text = "Betting Lines & Odds",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                if (!odds.provider.isNullOrBlank()) {
                    Surface(
                        shape = RoundedCornerShape(4.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant
                    ) {
                        Text(
                            text = odds.provider,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Spread / Favorite
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "POINT SPREAD",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = odds.details ?: "Even",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                // Over / Under Total
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "OVER / UNDER",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = if (odds.overUnder != null && odds.overUnder > 0) "${odds.overUnder} pts" else "--",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                // Moneylines (if available)
                if (odds.awayMoneyLine != null || odds.homeMoneyLine != null) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "MONEYLINE",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "${awayTeam.abbreviation} ${formatMoneyLine(odds.awayMoneyLine)} / ${homeTeam.abbreviation} ${formatMoneyLine(odds.homeMoneyLine)}",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }
    }
}

private fun formatMoneyLine(ml: Int?): String {
    if (ml == null) return "--"
    return if (ml > 0) "+$ml" else "$ml"
}

@Composable
private fun TeamStatsTab(game: MultiSportGame, boxScore: GameBoxScore?, isLoading: Boolean) {
    if (isLoading) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp)
        ) {
            CircularProgressIndicator(modifier = Modifier.size(32.dp))
        }
        return
    }

    val stats = boxScore?.teamStats.orEmpty()
    if (stats.isEmpty()) {
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.BarChart,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(32.dp)
                )
                Text(
                    text = if (game.isScheduled) "Team stats will be available once the game kicks off." else "Team statistics not available for this event.",
                    fontSize = 13.sp,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        return
    }

    Surface(
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            // Header: Away on Left, Home on Right
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = game.awayTeam.abbreviation,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Black,
                    color = MaterialTheme.colorScheme.secondary
                )
                Text(
                    text = "TEAM STATS COMPARISON",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = game.homeTeam.abbreviation,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Black,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 10.dp),
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
            )

            stats.forEachIndexed { index, stat ->
                if (index > 0) {
                    Spacer(modifier = Modifier.height(10.dp))
                }
                StatComparisonRow(stat = stat)
            }
        }
    }
}

@Composable
private fun StatComparisonRow(stat: TeamStatComparison) {
    val awayNum = extractFirstNumber(stat.awayValue)
    val homeNum = extractFirstNumber(stat.homeValue)

    val awayRatio = when {
        awayNum != null && homeNum != null && (awayNum + homeNum) > 0 -> {
            (awayNum / (awayNum + homeNum)).toFloat().coerceIn(0.1f, 0.9f)
        }
        else -> 0.5f
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stat.awayValue,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f)
            )

            Text(
                text = stat.label,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(2f)
            )

            Text(
                text = stat.homeValue,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.End,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Visual comparative bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(3.dp))
        ) {
            Box(
                modifier = Modifier
                    .weight(awayRatio)
                    .fillMaxHeight()
                    .background(MaterialTheme.colorScheme.secondary.copy(alpha = 0.85f))
            )
            Spacer(modifier = Modifier.width(2.dp))
            Box(
                modifier = Modifier
                    .weight(1f - awayRatio)
                    .fillMaxHeight()
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.85f))
            )
        }
    }
}

private fun extractFirstNumber(valStr: String): Double? {
    val clean = valStr.replace("%", "").trim()
    val match = Regex("""^([0-9]+(?:\.[0-9]+)?)""").find(clean)
    return match?.groupValues?.get(1)?.toDoubleOrNull()
}

@Composable
private fun BoxScoreTab(game: MultiSportGame, boxScore: GameBoxScore?, isLoading: Boolean) {
    if (isLoading) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp)
        ) {
            CircularProgressIndicator(modifier = Modifier.size(32.dp))
        }
        return
    }

    val categories = boxScore?.playerCategories.orEmpty()

    if (categories.isEmpty()) {
        // Fallback to game leaders if full player boxscore is not yet populated
        if (game.leaders.isNotEmpty()) {
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "Key Player Leaders",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(bottom = 10.dp)
                    )

                    game.leaders.forEachIndexed { index, leader ->
                        if (index > 0) {
                            HorizontalDivider(
                                modifier = Modifier.padding(vertical = 8.dp),
                                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
                            )
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column {
                                Text(
                                    text = leader.category.uppercase(),
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Text(
                                    text = leader.athleteName,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                            Text(
                                text = leader.displayValue,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        } else {
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Scoreboard,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(32.dp)
                    )
                    Text(
                        text = if (game.isScheduled) "Box score player statistics will update live during the game." else "Player statistics not available for this event.",
                        fontSize = 13.sp,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
        return
    }

    var selectedTeamIndex by remember { mutableIntStateOf(0) }
    val teams = listOf(game.awayTeam, game.homeTeam)
    val selectedTeam = teams[selectedTeamIndex]

    // Team Header Item Selector (ESPN App Design Philosophy)
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        ) {
            teams.forEachIndexed { index, team ->
                val isSelected = index == selectedTeamIndex
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = if (isSelected) MaterialTheme.colorScheme.primaryContainer else Color.Transparent,
                    modifier = Modifier
                        .weight(1f)
                        .clickable { selectedTeamIndex = index }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(vertical = 10.dp, horizontal = 8.dp)
                    ) {
                        if (!team.logoUrl.isNullOrBlank()) {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(team.logoUrl)
                                    .crossfade(true)
                                    .build(),
                                contentDescription = team.name,
                                contentScale = ContentScale.Fit,
                                modifier = Modifier
                                    .size(24.dp)
                                    .clip(CircleShape)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                        }
                        Text(
                            text = team.abbreviation.ifBlank { team.name },
                            fontSize = 14.sp,
                            fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.SemiBold,
                            color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        if (!team.score.toString().isBlank() && !game.isScheduled) {
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "(${team.score})",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }
    }

    Spacer(modifier = Modifier.height(14.dp))

    // Filter categories for the selected team
    val teamCategories = categories.filter { cat ->
        cat.teamId == selectedTeam.rawId ||
        cat.teamId == selectedTeam.id ||
        cat.teamName.contains(selectedTeam.name, ignoreCase = true) ||
        cat.teamName.contains(selectedTeam.abbreviation, ignoreCase = true)
    }.ifEmpty {
        // Fallback: If category names don't match team name/ID, split into halves
        if (categories.size >= 2) {
            val half = categories.size / 2
            if (selectedTeamIndex == 0) categories.take(half) else categories.drop(half)
        } else categories
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        teamCategories.forEach { cat ->
            EspnCategoryStatsGrid(category = cat)
        }
    }
}

@Composable
private fun EspnCategoryStatsGrid(category: PlayerStatCategory) {
    val hScrollState = rememberScrollState()
    val athleteColWidth = 126.dp
    val statColWidth = 54.dp
    val rowHeight = 36.dp

    Surface(
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Category Title Banner
            Surface(
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = category.categoryName.replaceFirstChar { it.uppercase() },
                        fontSize = 13.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    if (category.labels.size > 3) {
                        Text(
                            text = "scroll for more ➔",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                        )
                    }
                }
            }

            HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.25f))

            // Grid Container
            Row(modifier = Modifier.fillMaxWidth()) {
                // Fixed / Sticky Left Column (Athlete Name & Position)
                Column(
                    modifier = Modifier.width(athleteColWidth)
                ) {
                    // Header Cell
                    Box(
                        contentAlignment = Alignment.CenterStart,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(rowHeight)
                            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f))
                            .padding(horizontal = 10.dp)
                    ) {
                        Text(
                            text = "ATHLETE",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Black,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.15f))

                    // Athlete Rows
                    category.athletes.forEachIndexed { index, ath ->
                        val rowBg = if (index % 2 == 0) Color.Transparent else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.15f)
                        Box(
                            contentAlignment = Alignment.CenterStart,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(rowHeight)
                                .background(rowBg)
                                .padding(horizontal = 10.dp)
                        ) {
                            Column {
                                Text(
                                    text = ath.name,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                val posInfo = listOfNotNull(ath.jersey?.let { "#$it" }, ath.position).joinToString(" ")
                                if (posInfo.isNotBlank()) {
                                    Text(
                                        text = posInfo,
                                        fontSize = 9.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        maxLines = 1
                                    )
                                }
                            }
                        }
                        HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.1f))
                    }

                    // Totals Row Sticky Cell
                    if (category.totals.isNotEmpty()) {
                        Box(
                            contentAlignment = Alignment.CenterStart,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(rowHeight)
                                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
                                .padding(horizontal = 10.dp)
                        ) {
                            Text(
                                text = "TOTALS",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Black,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }

                // Vertical Divider between Sticky Column and Scrollable Grid
                VerticalDivider(
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.25f),
                    modifier = Modifier.height(rowHeight * (category.athletes.size + 1 + if (category.totals.isNotEmpty()) 1 else 0))
                )

                // Horizontally Scrollable Stats Grid
                Box(modifier = Modifier.weight(1f)) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(hScrollState)
                    ) {
                        Column {
                            // Column Headers Row
                            Row(
                                modifier = Modifier
                                    .height(rowHeight)
                                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f)),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                val labels = if (category.labels.isNotEmpty()) category.labels else List(category.athletes.firstOrNull()?.stats?.size ?: 0) { "COL ${it + 1}" }
                                labels.forEach { label ->
                                    Box(
                                        contentAlignment = Alignment.Center,
                                        modifier = Modifier
                                            .width(statColWidth)
                                            .fillMaxHeight()
                                            .padding(horizontal = 4.dp)
                                    ) {
                                        Text(
                                            text = label,
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Black,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                }
                            }

                            HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.15f))

                            // Athletes Stats Data Rows
                            category.athletes.forEachIndexed { index, ath ->
                                val rowBg = if (index % 2 == 0) Color.Transparent else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.15f)
                                val colCount = if (category.labels.isNotEmpty()) category.labels.size else ath.stats.size
                                Row(
                                    modifier = Modifier
                                        .height(rowHeight)
                                        .background(rowBg),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    for (colIdx in 0 until colCount) {
                                        val statVal = ath.stats.getOrNull(colIdx) ?: "--"
                                        Box(
                                            contentAlignment = Alignment.Center,
                                            modifier = Modifier
                                                .width(statColWidth)
                                                .fillMaxHeight()
                                                .padding(horizontal = 4.dp)
                                        ) {
                                            Text(
                                                text = if (statVal.isBlank()) "--" else statVal,
                                                fontSize = 11.sp,
                                                fontWeight = FontWeight.Medium,
                                                color = MaterialTheme.colorScheme.onSurface,
                                                textAlign = TextAlign.Center
                                            )
                                        }
                                    }
                                }
                                HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.1f))
                            }

                            // Totals Data Row
                            if (category.totals.isNotEmpty()) {
                                Row(
                                    modifier = Modifier
                                        .height(rowHeight)
                                        .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    val colCount = if (category.labels.isNotEmpty()) category.labels.size else category.totals.size
                                    for (colIdx in 0 until colCount) {
                                        val totalVal = category.totals.getOrNull(colIdx) ?: "--"
                                        Box(
                                            contentAlignment = Alignment.Center,
                                            modifier = Modifier
                                                .width(statColWidth)
                                                .fillMaxHeight()
                                                .padding(horizontal = 4.dp)
                                        ) {
                                            Text(
                                                text = if (totalVal.isBlank()) "--" else totalVal,
                                                fontSize = 11.sp,
                                                fontWeight = FontWeight.ExtraBold,
                                                color = MaterialTheme.colorScheme.primary,
                                                textAlign = TextAlign.Center
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun LinescoreTable(away: SportCompetitor, home: SportCompetitor, sport: Sport) {
    val defaultPeriods = when (sport) {
        Sport.MLB, Sport.COLLEGE_BASEBALL -> 9
        Sport.NHL -> 3
        else -> 4
    }
    val periods = maxOf(away.lineScores.size, home.lineScores.size, defaultPeriods)

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "TEAM",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.weight(2f)
            )

            for (p in 1..periods) {
                val label = when (sport) {
                    Sport.MLB, Sport.COLLEGE_BASEBALL -> "$p"
                    Sport.NHL -> "P$p"
                    else -> "Q$p"
                }
                Text(
                    text = label,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.weight(1f)
                )
            }

            Text(
                text = "T",
                fontSize = 12.sp,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.End,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f)
            )
        }

        HorizontalDivider(
            modifier = Modifier.padding(vertical = 8.dp),
            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
        )

        LinescoreRow(competitor = away, periods = periods)
        Spacer(modifier = Modifier.height(6.dp))
        LinescoreRow(competitor = home, periods = periods)
    }
}

@Composable
private fun LinescoreRow(competitor: SportCompetitor, periods: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = competitor.abbreviation,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(2f)
        )

        for (p in 0 until periods) {
            val score = competitor.lineScores.getOrNull(p)
            Text(
                text = score?.toString() ?: "-",
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.weight(1f)
            )
        }

        Text(
            text = "${competitor.score}",
            fontSize = 15.sp,
            fontWeight = FontWeight.ExtraBold,
            textAlign = TextAlign.End,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f)
        )
    }
}
