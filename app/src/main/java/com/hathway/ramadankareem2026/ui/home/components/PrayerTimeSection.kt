package com.hathway.ramadankareem2026.ui.home.components

import android.app.Application
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
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
import com.hathway.ramadankareem2026.ui.prayer.PrayerViewModel
import com.hathway.ramadankareem2026.ui.home.model.PrayerDomain
import com.hathway.ramadankareem2026.ui.prayer.PrayerTimeUiMapper
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.platform.LocalContext
import com.hathway.ramadankareem2026.ui.prayer.PrayerTimeUiMapper.minutesUntil
import com.hathway.ramadankareem2026.ui.prayer.data.PrayerViewModelFactory

/**
 * Displays horizontal list of daily prayer times.
 *
 * DATA FLOW:
 * ViewModel (PrayerTimeUiState)
 * → PrayerTimeUiMapper
 * → List<PrayerDomain>
 */
@Composable
fun PrayerTimeSection(

) {
    val context = LocalContext.current
    val app = context.applicationContext as Application

    val viewModel: PrayerViewModel = viewModel(
        factory = PrayerViewModelFactory(app)
    )
    // 🔹 Collect single source of truth from ViewModel
    val state by viewModel.state.collectAsState()

    // 🔹 Current time used to determine current/next prayer
    val now = remember { LocalTime.now() }

    // 🔹 Map UI state → prayer list (PURE logic)
    val prayers = remember(state) {
        PrayerTimeUiMapper.map(
            state = state, now = now
        )
    }

    // 🔹 Safety: if empty, render nothing
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
                    PrayerItem(
                        prayer = prayer, onClick = {})
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}

/**
 * Single prayer item card
 */
@Composable
fun PrayerItem(
    prayer: PrayerDomain, onClick: (PrayerDomain) -> Unit
) {
    val highlight = Color(0xFF2E7D32)

    // 🔹 Background color logic
    val background = when {
        prayer.isCurrent -> Color(0xFFE6F4EA)
        prayer.isNext -> Color(0xFFF1F8E9)
        else -> Color.Transparent
    }

    // 🔹 Text & icon color logic
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

        // 🔹 Prayer icon
        Icon(
            imageVector = iconForPrayer(prayer.name),
            contentDescription = prayer.name,
            tint = contentColor,
            modifier = Modifier.size(24.dp)
        )

        Spacer(Modifier.height(4.dp))

        // 🔹 Prayer name
        Text(
            text = prayer.name, style = MaterialTheme.typography.labelSmall, color = contentColor
        )

        // 🔹 Prayer time (formatted)
        Text(
            text = prayer.time.format(DateTimeFormatter.ofPattern("hh:mm a")),
            style = MaterialTheme.typography.bodySmall
        )

        Spacer(Modifier.height(4.dp))

        // 🔹 Status label
        when {
            // 🟢 Current prayer
            prayer.isCurrent -> {
                Text(
                    "Now", color = highlight, style = MaterialTheme.typography.labelSmall
                )
            }

            // 🔵 Next prayer (exact countdown from mapper)
            prayer.isNext && prayer.remainingMinutes != null -> {
                Text(
                    PrayerTimeUiMapper.formatRemaining(
                        prayer.remainingMinutes, isCurrent = false
                    ), color = highlight, style = MaterialTheme.typography.labelSmall
                )
            }

            // ⚪ Future prayer (after next)
            !prayer.isPast -> {
                val mins = minutesUntil(prayer.time).coerceAtLeast(0)

                Text(
                    "Coming in ${PrayerTimeUiMapper.formatDuration(mins)}",
                    color = Color.Gray,
                    style = MaterialTheme.typography.labelSmall
                )
            }

            // 🔴 Past prayer
            else -> {
                Text(
                    "Passed", color = Color.Gray, style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}

/**
 * Maps prayer name → icon
 */
@Composable
private fun iconForPrayer(name: String) = when (name) {
    "Fajr" -> Icons.Outlined.WbTwilight
    "Dhuhr" -> Icons.Outlined.LightMode
    "Asr" -> Icons.Outlined.WbSunny
    "Maghrib" -> Icons.Outlined.NightsStay
    "Isha" -> Icons.Outlined.DarkMode
    else -> Icons.Outlined.AccessTime
}