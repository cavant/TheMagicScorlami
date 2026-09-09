package com.themagicscorlami.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.themagicscorlami.data.model.Sport

@Composable
fun SportTabsBar(
    selectedSport: Sport,
    onSelectSport: (Sport) -> Unit,
    modifier: Modifier = Modifier
) {
    val sports = listOf(
        Sport.ALL,
        Sport.FAVORITES,
        Sport.NFL,
        Sport.CFB,
        Sport.CFB_FCS,
        Sport.CFB_D2,
        Sport.CFB_D3,
        Sport.NBA,
        Sport.CBB,
        Sport.MLB,
        Sport.NHL,
        Sport.MMA_UFC,
        Sport.SOCCER_EPL,
        Sport.SOCCER_UCL,
        Sport.SOCCER_LALIGA,
        Sport.SOCCER_MLS,
        Sport.COLLEGE_BASEBALL,
        Sport.WCBB,
        Sport.F1
    )

    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        items(sports, key = { it.id }) { sport ->
            val isSelected = sport == selectedSport

            FilterChip(
                selected = isSelected,
                onClick = { onSelectSport(sport) },
                label = {
                    Text(
                        text = "${sport.emoji} ${sport.shortName}",
                        fontSize = 13.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                    )
                },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = MaterialTheme.colorScheme.primary,
                    selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    }
}
