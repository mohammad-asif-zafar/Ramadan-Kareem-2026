package com.hathway.ramadankareem2026.ui.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material.icons.outlined.NightsStay
import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.material.icons.outlined.WbTwilight
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hathway.ramadankareem2026.ui.home.model.PrayerDomain
import com.hathway.ramadankareem2026.ui.prayer.PrayerTimeUiMapper
import com.hathway.ramadankareem2026.ui.prayer.PrayerViewModel

@Composable
fun PrayerTimeSection(
    viewModel: PrayerViewModel = viewModel()
) {
    val prayers by viewModel.prayers.collectAsState()

    if (prayers.isEmpty()) return

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {

        SectionTitle("Prayer Times")

        Spacer(modifier = Modifier.height(12.dp))

        Card(
            modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(20.dp)
        ) {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(prayers, key = { it.name }) { prayer ->
                    PrayerItem(prayer = prayer, onClick = {})
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
fun PrayerItem(
    prayer: PrayerDomain, onClick: (PrayerDomain) -> Unit
) {
    val highlight = Color(0xFF2E7D32)

    val background = when {
        prayer.isCurrent -> Color(0xFFE6F4EA)
        prayer.isNext -> Color(0xFFF1F8E9)
        else -> Color.Transparent
    }

    val contentColor = when {
        prayer.isCurrent -> highlight
        prayer.isNext -> highlight.copy(alpha = 0.8f)
        else -> MaterialTheme.colorScheme.onSurface
    }

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(background)
            .clickable { onClick(prayer) }
            .padding(horizontal = 12.dp, vertical = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {

        Icon(
            imageVector = iconForPrayer(prayer.name),
            contentDescription = prayer.name,
            tint = contentColor,
            modifier = Modifier.size(24.dp)
        )

        Spacer(Modifier.height(4.dp))

        Text(prayer.name, style = MaterialTheme.typography.labelSmall, color = contentColor)
        Text(prayer.time.toString(), style = MaterialTheme.typography.bodySmall)

        Spacer(Modifier.height(4.dp))

        when {
            prayer.isCurrent -> Text(
                "Now", color = highlight, style = MaterialTheme.typography.labelSmall
            )

            prayer.isNext && prayer.remainingMinutes != null -> Text(
                PrayerTimeUiMapper.formatRemaining(prayer.remainingMinutes, false),
                color = highlight,
                style = MaterialTheme.typography.labelSmall
            )

            else -> Text("Passed", color = Color.Gray, style = MaterialTheme.typography.labelSmall)
        }
    }
}

@Composable
private fun iconForPrayer(name: String) = when (name) {
    "Fajr" -> Icons.Outlined.WbTwilight
    "Dhuhr" -> Icons.Outlined.LightMode
    "Asr" -> Icons.Outlined.WbSunny
    "Maghrib" -> Icons.Outlined.NightsStay
    "Isha" -> Icons.Outlined.DarkMode
    else -> Icons.Outlined.AccessTime
}

/*
@Composable
fun PrayerItem(
    item: PrayerDomain, onClick: (PrayerDomain) -> Unit
) {
    val highlight = Color(0xFF2E7D32)

    val background = when {
        item.isCurrent -> Color(0xFFE6F4EA)
        item.isNext -> Color(0xFFF1F8E9)
        else -> Color.Transparent
    }

    val contentColor = when {
        item.isCurrent -> highlight
        item.isNext -> highlight.copy(alpha = 0.8f)
        else -> MaterialTheme.colorScheme.onSurface
    }

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(background)
            .clickable { onClick(item) }
            .padding(horizontal = 12.dp, vertical = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {

        Icon(
            imageVector = item.icon,
            contentDescription = item.name,
            tint = contentColor,
            modifier = Modifier.size(24.dp)
        )

        Spacer(Modifier.height(4.dp))

        Text(
            text = item.name, style = MaterialTheme.typography.labelSmall, color = contentColor
        )

        Text(
            text = item.time, style = MaterialTheme.typography.bodySmall, color = contentColor
        )

        Spacer(Modifier.height(4.dp))

        when {
            item.isCurrent -> {
                Text("Now", color = highlight, style = MaterialTheme.typography.labelSmall)
            }

            item.isNext && item.remainingMinutes != null -> {
                Text(
                    text = PrayerTimeUiMapper.formatRemaining(
                        item.remainingMinutes, isCurrent = false
                    ), color = highlight, style = MaterialTheme.typography.labelSmall
                )
            }

            !item.isCurrent && !item.isNext -> {
                Text(
                    text = "Passed", color = Color.Gray, style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}*/

