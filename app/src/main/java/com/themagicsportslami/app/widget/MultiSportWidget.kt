package com.themagicsportslami.app.widget

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.*
import androidx.glance.action.actionStartActivity
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.provideContent
import androidx.glance.layout.*
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import com.themagicsportslami.app.MainActivity
import com.themagicsportslami.app.data.api.MultiSportHttpClient
import com.themagicsportslami.app.data.local.SportslamiPreferences
import com.themagicsportslami.app.data.model.MultiSportGame
import com.themagicsportslami.app.data.model.Sport
import com.themagicsportslami.app.data.model.SportGameState
import com.themagicsportslami.app.data.repository.MultiSportRepository

import kotlinx.coroutines.flow.first

class MultiSportWidget : GlanceAppWidget() {

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val prefs = SportslamiPreferences(context)
        val repo = MultiSportRepository(
            httpClient = MultiSportHttpClient(context.cacheDir),
            preferences = prefs
        )

        val userPrefs = try {
            prefs.preferencesFlow.first()
        } catch (_: Exception) {
            null
        }
        val includeConferences = userPrefs?.widgetIncludeConferences == true
        val hasFavorites = (userPrefs?.favoriteTeamIds?.isNotEmpty() == true) ||
            (includeConferences && userPrefs?.favoriteConferenceIds?.isNotEmpty() == true)

        val result = try {
            repo.getGames(Sport.FAVORITES)
        } catch (_: Exception) {
            null
        }

        val allFavGames = result?.games ?: emptyList()
        val filteredGames = if (includeConferences) {
            allFavGames
        } else {
            val favTeams = userPrefs?.favoriteTeamIds ?: emptySet()
            allFavGames.filter { it.isFavorite(favTeams, emptySet()) }
        }
        val games = filteredGames.take(3)

        provideContent {
            GlanceTheme {
                MultiSportWidgetContent(games = games, hasFavorites = hasFavorites)
            }
        }
    }

    @Composable
    private fun MultiSportWidgetContent(games: List<MultiSportGame>, hasFavorites: Boolean) {
        Box(
            modifier = GlanceModifier
                .fillMaxSize()
                .cornerRadius(16.dp)
                .background(GlanceTheme.colors.surface)
                .padding(12.dp)
                .clickable(actionStartActivity<MainActivity>())
        ) {
            Column(modifier = GlanceModifier.fillMaxSize()) {
                // Header
                Row(
                    modifier = GlanceModifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "SCORLAMI LIVE",
                        style = TextStyle(
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = GlanceTheme.colors.primary
                        )
                    )
                    Spacer(modifier = GlanceModifier.defaultWeight())
                    Text(
                        text = "⭐ Favorites",
                        style = TextStyle(
                            fontSize = 11.sp,
                            color = GlanceTheme.colors.onSurfaceVariant
                        )
                    )
                }

                Spacer(modifier = GlanceModifier.height(8.dp))

                if (games.isEmpty()) {
                    Box(
                        modifier = GlanceModifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (!hasFavorites) {
                                "No favorite teams picked yet.\nTap to choose your teams!"
                            } else {
                                "No games today for your favorites.\nTap to view all live scores!"
                            },
                            style = TextStyle(
                                fontSize = 12.sp,
                                color = GlanceTheme.colors.onSurfaceVariant
                            )
                        )
                    }
                } else {
                    Column(
                        modifier = GlanceModifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        games.forEachIndexed { index, game ->
                            if (index > 0) {
                                Spacer(modifier = GlanceModifier.height(6.dp))
                            }
                            WidgetSportGameRow(game = game)
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun WidgetSportGameRow(game: MultiSportGame) {
        val away = game.awayTeam
        val home = game.homeTeam

        Column(
            modifier = GlanceModifier
                .fillMaxWidth()
                .background(GlanceTheme.colors.surfaceVariant)
                .cornerRadius(8.dp)
                .padding(horizontal = 8.dp, vertical = 6.dp)
        ) {
            Row(
                modifier = GlanceModifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Sport icon
                Text(
                    text = "${game.sport.emoji} ",
                    style = TextStyle(fontSize = 11.sp)
                )

                // Matchup & Score
                Text(
                    text = "${away.abbreviation} ${if (game.isScheduled) "" else "${away.score} "}@ ${home.abbreviation} ${if (game.isScheduled) "" else "${home.score}"}",
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = GlanceTheme.colors.onSurface
                    )
                )

                Spacer(modifier = GlanceModifier.defaultWeight())

                // Status text
                Text(
                    text = when (game.status.state) {
                        SportGameState.IN_PROGRESS -> game.situation?.downDistanceText ?: game.status.shortDetail
                        SportGameState.FINAL -> "FINAL"
                        else -> game.status.shortDetail.take(10)
                    },
                    style = TextStyle(
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (game.isLive) GlanceTheme.colors.primary else GlanceTheme.colors.onSurfaceVariant
                    )
                )
            }
        }
    }
}
