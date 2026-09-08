package com.themagicsportslami.app.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Widgets
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.themagicsportslami.app.data.local.SportslamiUserPreferences
import com.themagicsportslami.app.data.local.TeamNotificationConfig
import com.themagicsportslami.app.data.model.ConferenceEntity
import com.themagicsportslami.app.data.model.FavoriteTeamEntity
import com.themagicsportslami.app.data.model.MultiSportCatalog
import com.themagicsportslami.app.data.model.Sport
import com.themagicsportslami.app.ui.components.TeamNotificationSheet
import com.themagicsportslami.app.ui.theme.MagicGold

enum class FavoritesTab {
    MY_FAVORITES,
    BROWSE,
    CONFERENCES
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesManagerScreen(
    userPreferences: SportslamiUserPreferences,
    onToggleTeam: (String) -> Unit,
    onToggleSport: (String) -> Unit,
    onToggleConference: (String) -> Unit = {},
    onSaveTeamNotificationConfig: (TeamNotificationConfig) -> Unit,
    onBack: () -> Unit
) {
    BackHandler(onBack = onBack)

    var currentTab by remember {
        mutableStateOf(
            if (userPreferences.favoriteTeamIds.isNotEmpty() || userPreferences.favoriteConferenceIds.isNotEmpty()) FavoritesTab.MY_FAVORITES
            else FavoritesTab.BROWSE
        )
    }

    var configuringTeamForNotifications by remember { mutableStateOf<FavoriteTeamEntity?>(null) }

    // Browse Tab State
    var browseSportFilter by remember { mutableStateOf<Sport?>(null) }
    var browseSearchQuery by remember { mutableStateOf("") }

    val browseFilteredTeams = remember(browseSearchQuery, browseSportFilter) {
        val baseList = if (browseSearchQuery.isBlank()) {
            if (browseSportFilter == null) MultiSportCatalog.POPULAR_TEAMS
            else MultiSportCatalog.ALL_TEAMS.filter { it.sport == browseSportFilter }
        } else {
            MultiSportCatalog.ALL_TEAMS
        }
        baseList.filter { team ->
            val matchesSport = browseSportFilter == null || team.sport == browseSportFilter
            val matchesQuery = team.matches(browseSearchQuery)
            matchesSport && matchesQuery
        }
    }

    // My Favorites Tab State
    val myFavoriteTeams = remember(userPreferences.favoriteTeamIds) {
        userPreferences.favoriteTeamIds.mapNotNull { id ->
            MultiSportCatalog.findById(id) ?: FavoriteTeamEntity(
                id = id,
                name = id,
                abbreviation = id.take(4).uppercase(),
                sport = Sport.ALL
            )
        }
    }

    var myFavSearchQuery by remember { mutableStateOf("") }
    var myFavSportFilter by remember { mutableStateOf<Sport?>(null) }

    val myFilteredFavorites = remember(myFavoriteTeams, myFavSearchQuery, myFavSportFilter) {
        myFavoriteTeams.filter { team ->
            val matchesSport = myFavSportFilter == null || team.sport == myFavSportFilter
            val matchesQuery = team.matches(myFavSearchQuery)
            matchesSport && matchesQuery
        }
    }

    // Conferences Tab State
    var confSportFilter by remember { mutableStateOf<Sport?>(null) }
    var confSearchQuery by remember { mutableStateOf("") }

    val filteredConferences = remember(confSearchQuery, confSportFilter) {
        val list = if (confSportFilter == null) {
            MultiSportCatalog.CONFERENCES
        } else {
            MultiSportCatalog.getConferencesForSport(confSportFilter!!)
        }
        if (confSearchQuery.isBlank()) list
        else list.filter { it.matches(confSearchQuery) }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Favorite Teams & Leagues", fontWeight = FontWeight.Bold) },
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
        ) {
            // Tab Row: My Favorites vs Teams vs Conferences
            val totalFavoritesCount = userPreferences.favoriteTeamIds.size + userPreferences.favoriteConferenceIds.size
            TabRow(
                selectedTabIndex = currentTab.ordinal,
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.primary
            ) {
                Tab(
                    selected = currentTab == FavoritesTab.MY_FAVORITES,
                    onClick = { currentTab = FavoritesTab.MY_FAVORITES },
                    text = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                tint = if (currentTab == FavoritesTab.MY_FAVORITES) MagicGold else MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = "Favorites ($totalFavoritesCount)",
                                fontWeight = if (currentTab == FavoritesTab.MY_FAVORITES) FontWeight.Bold else FontWeight.Medium
                            )
                        }
                    }
                )
                Tab(
                    selected = currentTab == FavoritesTab.BROWSE,
                    onClick = { currentTab = FavoritesTab.BROWSE },
                    text = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = "Teams",
                                fontWeight = if (currentTab == FavoritesTab.BROWSE) FontWeight.Bold else FontWeight.Medium
                            )
                        }
                    }
                )
                Tab(
                    selected = currentTab == FavoritesTab.CONFERENCES,
                    onClick = { currentTab = FavoritesTab.CONFERENCES },
                    text = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Groups,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = "Conferences",
                                fontWeight = if (currentTab == FavoritesTab.CONFERENCES) FontWeight.Bold else FontWeight.Medium
                            )
                        }
                    }
                )
            }

            when (currentTab) {
                FavoritesTab.MY_FAVORITES -> {
                    if (myFavoriteTeams.isEmpty() && userPreferences.favoriteConferenceIds.isEmpty()) {
                        // Empty State
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                Surface(
                                    shape = CircleShape,
                                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
                                    modifier = Modifier.size(72.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Icon(
                                            imageVector = Icons.Outlined.StarOutline,
                                            contentDescription = null,
                                            tint = MagicGold,
                                            modifier = Modifier.size(40.dp)
                                        )
                                    }
                                }
                                Text(
                                    text = "No Favorites Added",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = "Star teams and conferences across NFL, College Football, Basketball, Baseball, and more to see live scores in your widget and get custom notifications.",
                                    fontSize = 13.sp,
                                    textAlign = TextAlign.Center,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                    Button(
                                        onClick = { currentTab = FavoritesTab.BROWSE },
                                        shape = RoundedCornerShape(20.dp)
                                    ) {
                                        Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(18.dp))
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text("Browse Teams")
                                    }
                                    OutlinedButton(
                                        onClick = { currentTab = FavoritesTab.CONFERENCES },
                                        shape = RoundedCornerShape(20.dp)
                                    ) {
                                        Icon(Icons.Default.Groups, contentDescription = null, modifier = Modifier.size(18.dp))
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text("Conferences")
                                    }
                                }
                            }
                        }
                    } else {
                        val presentSports = remember(myFavoriteTeams) {
                            myFavoriteTeams.map { it.sport }.distinct().filter { it.isLeague }
                        }

                        LazyColumn(
                            contentPadding = PaddingValues(bottom = 32.dp),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            // Info banner
                            item {
                                Surface(
                                    color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.35f),
                                    shape = RoundedCornerShape(8.dp),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp, vertical = 8.dp)
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                                        modifier = Modifier.padding(12.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.NotificationsActive,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.primary,
                                            modifier = Modifier.size(20.dp)
                                        )
                                        Text(
                                            text = "Manage your ${myFavoriteTeams.size} favorited teams and ${userPreferences.favoriteConferenceIds.size} conferences in one place. Star to toggle, or tap the bell for team alerts.",
                                            fontSize = 12.sp,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                    }
                                }
                            }

                            // Favorited Conferences Section
                            if (userPreferences.favoriteConferenceIds.isNotEmpty()) {
                                item {
                                    Text(
                                        text = "Favorited Conferences & Divisions (${userPreferences.favoriteConferenceIds.size})",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 10.dp, bottom = 4.dp)
                                    )
                                }
                                items(userPreferences.favoriteConferenceIds.toList(), key = { "fav_conf_$it" }) { confId ->
                                    val conf = MultiSportCatalog.findConferenceById(confId) ?: ConferenceEntity(
                                        id = confId,
                                        name = confId,
                                        shortName = confId,
                                        sport = Sport.ALL
                                    )
                                    ConferenceRowItem(
                                        conference = conf,
                                        isFav = true,
                                        onToggleFav = { onToggleConference(conf.id) }
                                    )
                                }
                                item {
                                    Spacer(modifier = Modifier.height(12.dp))
                                }
                            }

                            // Favorited Teams Section
                            if (myFavoriteTeams.isNotEmpty()) {
                                item {
                                    Text(
                                        text = "Favorited Teams (${myFilteredFavorites.size})",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 4.dp)
                                    )
                                }

                                if (presentSports.size > 1) {
                                    item {
                                        LazyRow(
                                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
                                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                                        ) {
                                            item {
                                                FilterChip(
                                                    selected = myFavSportFilter == null,
                                                    onClick = { myFavSportFilter = null },
                                                    label = { Text("All (${myFavoriteTeams.size})") }
                                                )
                                            }
                                            items(presentSports) { sp ->
                                                val count = myFavoriteTeams.count { it.sport == sp }
                                                FilterChip(
                                                    selected = myFavSportFilter == sp,
                                                    onClick = { myFavSportFilter = if (myFavSportFilter == sp) null else sp },
                                                    label = { Text("${sp.emoji} ${sp.shortName} ($count)") }
                                                )
                                            }
                                        }
                                    }
                                }

                                if (myFavoriteTeams.size > 4) {
                                    item {
                                        OutlinedTextField(
                                            value = myFavSearchQuery,
                                            onValueChange = { myFavSearchQuery = it },
                                            placeholder = { Text("Search your favorite teams...") },
                                            leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = null) },
                                            trailingIcon = {
                                                if (myFavSearchQuery.isNotEmpty()) {
                                                    IconButton(onClick = { myFavSearchQuery = "" }) {
                                                        Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear")
                                                    }
                                                }
                                            },
                                            singleLine = true,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(horizontal = 16.dp, vertical = 6.dp)
                                        )
                                    }
                                }

                                items(myFilteredFavorites, key = { "fav_${it.sport.id}_${it.id}" }) { team ->
                                    val hasCustom = userPreferences.teamNotificationConfigs[team.id]?.customSettingsEnabled == true
                                    FavoriteTeamRowItem(
                                        team = team,
                                        isFav = true,
                                        hasCustomNotifs = hasCustom,
                                        onToggleFav = { onToggleTeam(team.id) },
                                        onConfigureNotifs = { configuringTeamForNotifications = team }
                                    )
                                }
                            }
                        }
                    }
                }

                FavoritesTab.BROWSE -> {
                    // Widget helper banner
                    Surface(
                        color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            modifier = Modifier.padding(12.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Widgets,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                text = "Favorited teams across all sports show up in your Home Screen Widget and float to the top of your scores.",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }

                    // Sport Filter Chips
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        item {
                            FilterChip(
                                selected = browseSportFilter == null,
                                onClick = { browseSportFilter = null },
                                label = { Text("All Sports") }
                            )
                        }
                        items(Sport.entries.filter { it.isLeague }) { sp ->
                            FilterChip(
                                selected = browseSportFilter == sp,
                                onClick = { browseSportFilter = if (browseSportFilter == sp) null else sp },
                                label = { Text("${sp.emoji} ${sp.shortName}") }
                            )
                        }
                    }

                    // Search Bar
                    OutlinedTextField(
                        value = browseSearchQuery,
                        onValueChange = { browseSearchQuery = it },
                        placeholder = { Text("Search teams (Cowboys, Texas, Lakers, Yankees)...") },
                        leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = null) },
                        trailingIcon = {
                            if (browseSearchQuery.isNotEmpty()) {
                                IconButton(onClick = { browseSearchQuery = "" }) {
                                    Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear")
                                }
                            }
                        },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    )

                    // Header counter
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (browseSearchQuery.isNotBlank()) "Search Results (${browseFilteredTeams.size})"
                                   else if (browseSportFilter != null) "${browseSportFilter?.displayName} Teams (${browseFilteredTeams.size})"
                                   else "Popular Suggestions (${browseFilteredTeams.size})",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    if (browseFilteredTeams.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No teams found matching \"$browseSearchQuery\"\nTry searching by nickname, state, or abbreviation.",
                                textAlign = TextAlign.Center,
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    } else {
                        // Teams List
                        LazyColumn(
                            contentPadding = PaddingValues(bottom = 32.dp),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(browseFilteredTeams, key = { "${it.sport.id}_${it.id}" }) { team ->
                                val isFav = team.id in userPreferences.favoriteTeamIds || team.scopedId in userPreferences.favoriteTeamIds
                                val hasCustom = userPreferences.teamNotificationConfigs[team.id]?.customSettingsEnabled == true
                                FavoriteTeamRowItem(
                                    team = team,
                                    isFav = isFav,
                                    hasCustomNotifs = hasCustom,
                                    onToggleFav = { onToggleTeam(team.id) },
                                    onConfigureNotifs = { configuringTeamForNotifications = team }
                                )
                            }
                        }
                    }
                }

                FavoritesTab.CONFERENCES -> {
                    // Explanatory banner
                    Surface(
                        color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            modifier = Modifier.padding(12.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Groups,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                text = "Favorite a conference or division to track all member teams in your Top Events and daily feeds. (Widgets only include conferences if enabled in Settings).",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }

                    // Sport Filter Chips
                    val confSports = listOf(Sport.CFB, Sport.NFL, Sport.NBA, Sport.MLB, Sport.NHL, Sport.CBB)
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        item {
                            FilterChip(
                                selected = confSportFilter == null,
                                onClick = { confSportFilter = null },
                                label = { Text("All (${MultiSportCatalog.CONFERENCES.size})") }
                            )
                        }
                        items(confSports) { sp ->
                            val count = MultiSportCatalog.getConferencesForSport(sp).size
                            FilterChip(
                                selected = confSportFilter == sp,
                                onClick = { confSportFilter = if (confSportFilter == sp) null else sp },
                                label = { Text("${sp.emoji} ${sp.shortName} ($count)") }
                            )
                        }
                    }

                    // Search Bar
                    OutlinedTextField(
                        value = confSearchQuery,
                        onValueChange = { confSearchQuery = it },
                        placeholder = { Text("Search conferences (SEC, Big Ten, AFC North)...") },
                        leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = null) },
                        trailingIcon = {
                            if (confSearchQuery.isNotEmpty()) {
                                IconButton(onClick = { confSearchQuery = "" }) {
                                    Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear")
                                }
                            }
                        },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    )

                    // Header counter
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (confSearchQuery.isNotBlank()) "Search Results (${filteredConferences.size})"
                                   else if (confSportFilter != null) "${confSportFilter?.displayName} (${filteredConferences.size})"
                                   else "All Conferences & Divisions (${filteredConferences.size})",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    if (filteredConferences.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No conferences or divisions found matching \"$confSearchQuery\"",
                                textAlign = TextAlign.Center,
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    } else {
                        LazyColumn(
                            contentPadding = PaddingValues(bottom = 32.dp),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(filteredConferences, key = { it.id }) { conf ->
                                val isFav = conf.id in userPreferences.favoriteConferenceIds
                                ConferenceRowItem(
                                    conference = conf,
                                    isFav = isFav,
                                    onToggleFav = { onToggleConference(conf.id) }
                                )
                            }
                        }
                    }
                }
            }
        }

        configuringTeamForNotifications?.let { team ->
            TeamNotificationSheet(
                team = team,
                userPreferences = userPreferences,
                onSaveConfig = onSaveTeamNotificationConfig,
                onDismiss = { configuringTeamForNotifications = null }
            )
        }
    }
}

@Composable
private fun FavoriteTeamRowItem(
    team: FavoriteTeamEntity,
    isFav: Boolean,
    hasCustomNotifs: Boolean,
    onToggleFav: () -> Unit,
    onConfigureNotifs: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onToggleFav() }
            .padding(horizontal = 16.dp, vertical = 10.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.weight(1f)
        ) {
            if (team.logoUrl != null) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(team.logoUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(34.dp)
                        .clip(CircleShape)
                )
            } else {
                Surface(
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.primaryContainer,
                    modifier = Modifier.size(34.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = team.abbreviation.take(3),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            }

            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = team.name,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    if (hasCustomNotifs) {
                        Surface(
                            color = MaterialTheme.colorScheme.primaryContainer,
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(
                                text = "Custom",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                            )
                        }
                    }
                }
                Text(
                    text = "${team.sport.emoji} ${team.sport.displayName} • ${team.abbreviation}",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            if (isFav) {
                IconButton(onClick = onConfigureNotifs) {
                    Icon(
                        imageVector = if (hasCustomNotifs) Icons.Default.NotificationsActive else Icons.Outlined.Notifications,
                        contentDescription = "Notification Settings for ${team.name}",
                        tint = if (hasCustomNotifs) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(22.dp)
                    )
                }
            }

            IconButton(onClick = onToggleFav) {
                Icon(
                    imageVector = if (isFav) Icons.Default.Star else Icons.Outlined.StarBorder,
                    contentDescription = if (isFav) "Remove favorite" else "Add favorite",
                    tint = if (isFav) MagicGold else MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
    HorizontalDivider(
        modifier = Modifier.padding(horizontal = 16.dp),
        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
    )
}

@Composable
private fun ConferenceRowItem(
    conference: ConferenceEntity,
    isFav: Boolean,
    onToggleFav: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onToggleFav() }
            .padding(horizontal = 16.dp, vertical = 10.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.weight(1f)
        ) {
            if (conference.logoUrl != null) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(conference.logoUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(34.dp)
                        .clip(CircleShape)
                )
            } else {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.primaryContainer,
                    modifier = Modifier.size(34.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = conference.shortName.take(3).uppercase(),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            }

            Column {
                Text(
                    text = conference.name,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                val subtitle = buildString {
                    append("${conference.sport.emoji} ${conference.sport.displayName}")
                    if (conference.division != null) {
                        append(" • ${conference.division}")
                    }
                    if (conference.teamIds.isNotEmpty()) {
                        append(" • ${conference.teamIds.size} teams")
                    }
                }
                Text(
                    text = subtitle,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        IconButton(onClick = onToggleFav) {
            Icon(
                imageVector = if (isFav) Icons.Default.Star else Icons.Outlined.StarBorder,
                contentDescription = if (isFav) "Remove favorite" else "Add favorite",
                tint = if (isFav) MagicGold else MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(24.dp)
            )
        }
    }
    HorizontalDivider(
        modifier = Modifier.padding(horizontal = 16.dp),
        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
    )
}

