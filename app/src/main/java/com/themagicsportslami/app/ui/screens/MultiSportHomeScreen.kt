package com.themagicsportslami.app.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.themagicsportslami.app.R
import com.themagicsportslami.app.data.model.MultiSportGame
import com.themagicsportslami.app.data.model.Sport
import com.themagicsportslami.app.data.repository.MultiSportResult
import com.themagicsportslami.app.ui.components.DateCarouselBar
import com.themagicsportslami.app.ui.components.MultiSportGameCard
import com.themagicsportslami.app.ui.components.SportTabsBar
import com.themagicsportslami.app.ui.theme.LiveGreen
import com.themagicsportslami.app.ui.theme.MagicGold

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MultiSportHomeScreen(
    result: MultiSportResult,
    selectedSport: Sport,
    selectedDateString: String?,
    favoriteTeamIds: Set<String>,
    favoriteConferenceIds: Set<String> = emptySet(),
    isRefreshing: Boolean,
    dataSaverEnabled: Boolean,
    onSelectSport: (Sport) -> Unit,
    onSelectDate: (String?) -> Unit,
    onRefresh: () -> Unit,
    onToggleFavoriteTeam: (String) -> Unit,
    onSelectGame: (MultiSportGame) -> Unit,
    onNavigateToFavorites: () -> Unit,
    onNavigateToSettings: () -> Unit
) {
    val haptic = LocalHapticFeedback.current
    var isSearchActive by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }

    val confTeamIds = remember(favoriteConferenceIds) {
        com.themagicsportslami.app.data.model.MultiSportCatalog.getTeamIdsForFavoritedConferences(favoriteConferenceIds)
    }

    if (isSearchActive) {
        BackHandler {
            isSearchActive = false
            searchQuery = ""
        }
    }

    val displayedGames = remember(result.games, searchQuery) {
        if (searchQuery.isBlank()) result.games
        else {
            val q = searchQuery.trim().lowercase()
            result.games.filter { g ->
                g.homeTeam.name.lowercase().contains(q) ||
                g.homeTeam.abbreviation.lowercase().contains(q) ||
                g.awayTeam.name.lowercase().contains(q) ||
                g.awayTeam.abbreviation.lowercase().contains(q) ||
                g.combatFight?.fighterA?.name?.lowercase()?.contains(q) == true ||
                g.combatFight?.fighterB?.name?.lowercase()?.contains(q) == true
            }
        }
    }

    val isTopEventsMode = selectedSport == Sport.ALL && selectedDateString == null && searchQuery.isBlank()

    Scaffold(
        topBar = {
            if (isSearchActive) {
                TopAppBar(
                    title = {
                        OutlinedTextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            placeholder = { Text("Filter live games...") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { isSearchActive = false; searchQuery = "" }) {
                            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Close search")
                        }
                    },
                    actions = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { searchQuery = "" }) {
                                Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear search")
                            }
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
                )
            } else {
                TopAppBar(
                    title = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.mascot_salami),
                                contentDescription = "Mascot",
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                            )

                            Text(
                                text = "Scorlami",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = MaterialTheme.colorScheme.onSurface
                            )

                            val liveCount = (result.games + result.favoriteMatchups).distinctBy { "${it.sport.id}_${it.id}" }.count { it.isLive }
                            if (liveCount > 0) {
                                Surface(
                                    shape = MaterialTheme.shapes.extraSmall,
                                    color = LiveGreen.copy(alpha = 0.2f)
                                ) {
                                    Text(
                                        text = "$liveCount LIVE",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = LiveGreen,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }
                        }
                    },
                    actions = {
                        IconButton(onClick = { isSearchActive = true }) {
                            Icon(imageVector = Icons.Default.Search, contentDescription = "Search games")
                        }
                        IconButton(onClick = onRefresh) {
                            Icon(imageVector = Icons.Default.Refresh, contentDescription = "Refresh")
                        }
                        IconButton(onClick = onNavigateToFavorites) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = "Favorites",
                                tint = MagicGold
                            )
                        }
                        IconButton(onClick = onNavigateToSettings) {
                            Icon(imageVector = Icons.Default.Settings, contentDescription = "Settings")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Sport selector tabs
            SportTabsBar(
                selectedSport = selectedSport,
                onSelectSport = onSelectSport
            )

            // Date selector carousel
            DateCarouselBar(
                selectedDateString = selectedDateString,
                onSelectDate = onSelectDate
            )

            // Live games list
            PullToRefreshBox(
                isRefreshing = isRefreshing,
                onRefresh = onRefresh,
                modifier = Modifier.fillMaxSize()
            ) {
                val hasNoGames = displayedGames.isEmpty() && (!isTopEventsMode || result.favoriteMatchups.isEmpty())
                if (hasNoGames && !isRefreshing) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(32.dp)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text(
                                text = if (searchQuery.isNotBlank()) {
                                    "No games match \"$searchQuery\"."
                                } else if (selectedSport == Sport.FAVORITES) {
                                    if (favoriteTeamIds.isEmpty() && favoriteConferenceIds.isEmpty()) {
                                        "No favorite teams or conferences selected yet."
                                    } else {
                                        "No games scheduled for your favorites on this date."
                                    }
                                } else {
                                    "No games found for ${selectedSport.displayName} on this date."
                                },
                                fontSize = 15.sp,
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            if (selectedSport == Sport.FAVORITES && favoriteTeamIds.isEmpty() && favoriteConferenceIds.isEmpty()) {
                                Button(
                                    onClick = onNavigateToFavorites,
                                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                                ) {
                                    Icon(imageVector = Icons.Default.Star, contentDescription = null, tint = MagicGold)
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("Pick Your Favorites")
                                }
                            }
                        }
                    }
                } else {
                    LazyColumn(
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        // In Top Events mode, render Favorite Matchups at the top if present
                        if (isTopEventsMode && result.favoriteMatchups.isNotEmpty()) {
                            item(key = "fav_matchups_header") {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    modifier = Modifier.padding(top = 4.dp, bottom = 2.dp)
                                ) {
                                    Text("⭐", fontSize = 16.sp)
                                    Text(
                                        text = "FAVORITE MATCHUPS",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = MagicGold,
                                        letterSpacing = 0.5.sp
                                    )
                                    Surface(
                                        shape = RoundedCornerShape(4.dp),
                                        color = MagicGold.copy(alpha = 0.15f)
                                    ) {
                                        Text(
                                            text = "PAST 7D • NEXT 14D",
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = MagicGold,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                }
                            }

                            items(result.favoriteMatchups, key = { "fav_${it.sport.id}_${it.id}" }) { game ->
                                val isFav = game.isFavorite(favoriteTeamIds, confTeamIds)
                                MultiSportGameCard(
                                    game = game,
                                    isFavorite = isFav,
                                    onToggleFavorite = {
                                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                        val targetId = if (game.homeTeam.matchesFavorite(favoriteTeamIds, confTeamIds)) {
                                            game.homeTeam.id
                                        } else if (game.awayTeam.matchesFavorite(favoriteTeamIds, confTeamIds)) {
                                            game.awayTeam.id
                                        } else {
                                            game.homeTeam.id
                                        }
                                        onToggleFavoriteTeam(targetId)
                                    },
                                    onClick = { onSelectGame(game) },
                                    dataSaverEnabled = dataSaverEnabled
                                )
                            }

                            item(key = "todays_action_divider") {
                                Column(modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)) {
                                    HorizontalDivider(
                                        thickness = 2.dp,
                                        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.25f)
                                    )
                                    Spacer(modifier = Modifier.height(10.dp))
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                                        ) {
                                            Text("🔴", fontSize = 14.sp)
                                            Text(
                                                text = "LIVE & TODAY'S ACTION",
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.ExtraBold,
                                                color = MaterialTheme.colorScheme.primary,
                                                letterSpacing = 0.5.sp
                                            )
                                        }
                                        val todayLiveCount = displayedGames.count { it.isLive }
                                        if (todayLiveCount > 0) {
                                            Surface(
                                                shape = RoundedCornerShape(4.dp),
                                                color = LiveGreen.copy(alpha = 0.2f)
                                            ) {
                                                Text(
                                                    text = "$todayLiveCount LIVE",
                                                    fontSize = 10.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = LiveGreen,
                                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        items(displayedGames, key = { "${it.sport.id}_${it.id}" }) { game ->
                            val isFav = game.isFavorite(favoriteTeamIds, confTeamIds)
                            MultiSportGameCard(
                                game = game,
                                isFavorite = isFav,
                                onToggleFavorite = {
                                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                    val targetId = if (game.homeTeam.matchesFavorite(favoriteTeamIds, confTeamIds)) {
                                        game.homeTeam.id
                                    } else if (game.awayTeam.matchesFavorite(favoriteTeamIds, confTeamIds)) {
                                        game.awayTeam.id
                                    } else {
                                        game.homeTeam.id
                                    }
                                    onToggleFavoriteTeam(targetId)
                                },
                                onClick = { onSelectGame(game) },
                                dataSaverEnabled = dataSaverEnabled
                            )
                        }
                    }
                }
            }
        }
    }
}
