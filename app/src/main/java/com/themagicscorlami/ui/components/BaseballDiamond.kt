package com.themagicscorlami.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.themagicscorlami.ui.theme.MagicGold

@Composable
fun BaseballDiamond(
    onFirst: Boolean,
    onSecond: Boolean,
    onThird: Boolean,
    outs: Int,
    balls: Int,
    strikes: Int,
    modifier: Modifier = Modifier
) {
    val description = buildString {
        append("Runners: ")
        val runners = mutableListOf<String>()
        if (onFirst) runners.add("1st")
        if (onSecond) runners.add("2nd")
        if (onThird) runners.add("3rd")
        if (runners.isEmpty()) append("none. ") else append(runners.joinToString(", ") + ". ")
        append("Count: $balls and $strikes. $outs out.")
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.semantics { contentDescription = description }
    ) {
        // Diamond Layout (24x24dp box)
        Box(
            modifier = Modifier.size(24.dp),
            contentAlignment = Alignment.Center
        ) {
            // 2nd Base (Top)
            BaseSquare(
                isOccupied = onSecond,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .offset(y = 2.dp)
            )

            // 3rd Base (Left)
            BaseSquare(
                isOccupied = onThird,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .offset(x = 2.dp)
            )

            // 1st Base (Right)
            BaseSquare(
                isOccupied = onFirst,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .offset(x = (-2).dp)
            )
        }

        // Count & Outs text
        Column {
            Text(
                text = "$balls-$strikes",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "$outs OUT",
                fontSize = 10.sp,
                fontWeight = FontWeight.ExtraBold,
                color = if (outs == 2) MagicGold else MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun BaseSquare(
    isOccupied: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(7.dp)
            .rotate(45f)
            .background(
                color = if (isOccupied) MagicGold else Color.Gray.copy(alpha = 0.4f),
                shape = RoundedCornerShape(1.dp)
            )
    )
}
