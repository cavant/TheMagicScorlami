package com.themagicscorlami.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

data class DateItem(
    val label: String,
    val queryDate: String?, // null for Top Events, or format YYYYMMDD
    val isTopEvents: Boolean = false
)

@Composable
fun DateCarouselBar(
    selectedDateString: String?,
    onSelectDate: (String?) -> Unit,
    modifier: Modifier = Modifier
) {
    val dateItems = remember {
        val list = mutableListOf<DateItem>()
        val queryFmt = SimpleDateFormat("yyyyMMdd", Locale.US)
        val dayFmt = SimpleDateFormat("EEE M/d", Locale.US)

        list.add(DateItem(label = "🔥 Top Events", queryDate = null, isTopEvents = true))

        for (offset in -3..5) {
            val c = Calendar.getInstance()
            c.add(Calendar.DAY_OF_YEAR, offset)
            val qDate = queryFmt.format(c.time)
            val label = when (offset) {
                -1 -> "Yesterday"
                0 -> "Today"
                1 -> "Tomorrow"
                else -> dayFmt.format(c.time)
            }
            list.add(DateItem(label = label, queryDate = qDate, isTopEvents = false))
        }
        list
    }

    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 2.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        modifier = modifier
    ) {
        items(dateItems, key = { it.queryDate ?: "top_events" }) { item ->
            val isSelected = if (selectedDateString == null) item.isTopEvents else selectedDateString == item.queryDate

            FilterChip(
                selected = isSelected,
                onClick = {
                    onSelectDate(item.queryDate)
                },
                label = {
                    Text(
                        text = item.label,
                        fontSize = 12.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                    )
                },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                    selectedLabelColor = MaterialTheme.colorScheme.onSecondaryContainer
                )
            )
        }
    }
}

