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
import com.hathway.ramadankareem2026.ui.home.components.SectionTitle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hathway.ramadankareem2026.ui.prayer.PrayerViewModel
import com.hathway.ramadankareem2026.ui.home.model.PrayerDomain
import com.hathway.ramadankareem2026.ui.prayer.PrayerTimeUiMapper
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.hathway.ramadankareem2026.R
import com.hathway.ramadankareem2026.ui.prayer.PrayerTimeUiMapper.minutesUntil
import com.hathway.ramadankareem2026.ui.prayer.data.PrayerViewModelFactory

/**
 * Displays a horizontal list of daily prayer times.
 *
 * DATA FLOW:
 * ViewModel (PrayerTimeUiState)
 * → PrayerTimeUiMapper (pure mapping logic)
 * → List<PrayerDomain> (UI-ready model)
 *
 * Responsibility:
 * - Owns ViewModel
 * - Collects state
 * - Displays section UI
 */
@Composable
fun PrayerTimeSection() {

    // Access application context for ViewModel factory
    val context = LocalContext.current
    val app = context.applicationContext as Application

    /**
     * ViewModel created using custom factory
     * (needed for Application / repository injection)
     */
    val viewModel: PrayerViewModel = viewModel(
        factory = PrayerViewModelFactory(app)
    )

    val state by viewModel.state.collectAsState()
    val now = remember { LocalTime.now() }

    val prayers = remember(state) {
        PrayerTimeUiMapper.map(
            state = state, now = now
        )
    }

    // Safety guard: do not render section if no prayers available
    if (prayers.isEmpty()) return

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    ) {

        // Section header
        SectionTitle(stringResource(R.string.prayer_times))

        Spacer(modifier = Modifier.height(12.dp))

        /**
         * Card wrapper for horizontal prayer list
         */
        Card(
            modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(20.dp)
        ) {

            /**
             * Horizontal scrolling list of prayer items
             */
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                items(items = prayers, key = { it.name } // Stable key per prayer
                ) { prayer ->
                    PrayerItem(
                        prayer = prayer, onClick = {})
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}

/**
 * Single prayer item displayed inside horizontal list.
 *
 * Visual states:
 * - Current prayer → highlighted
 * - Next prayer → soft highlight + countdown
 * - Future prayer → "Coming in"
 * - Past prayer → "Passed"
 */
@Composable
fun PrayerItem(
    prayer: PrayerDomain, onClick: (PrayerDomain) -> Unit
) {

    val highlight = Color(0xFF2E7D32)

    // Background color based on prayer state
    val background = when {
        prayer.isCurrent -> Color(0xFFE6F4EA)
        prayer.isNext -> Color(0xFFF1F8E9)
        else -> Color.Transparent
    }

    // Text & icon color logic
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

        // Prayer icon
        Icon(
            imageVector = iconForPrayer(prayer.name),
            contentDescription = prayer.name,
            tint = contentColor,
            modifier = Modifier.size(24.dp)
        )

        Spacer(Modifier.height(4.dp))

        // Prayer name
        Text(
            text = prayer.name, style = MaterialTheme.typography.labelSmall, color = contentColor
        )

        // Prayer time
        Text(
            text = prayer.time.format(
                DateTimeFormatter.ofPattern("hh:mm a")
            ), style = MaterialTheme.typography.bodySmall
        )

        Spacer(Modifier.height(4.dp))

        /**
         * Status label logic
         */
        when {
            // 🟢 Current prayer
            prayer.isCurrent -> {
                Text(
                    "Now", color = highlight, style = MaterialTheme.typography.labelSmall
                )
            }

            // 🔵 Next prayer with remaining time
            prayer.isNext && prayer.remainingMinutes != null -> {
                Text(
                    PrayerTimeUiMapper.formatRemaining(
                        prayer.remainingMinutes, isCurrent = false
                    ), color = highlight, style = MaterialTheme.typography.labelSmall
                )
            }

            // ⏳ Future prayer
            !prayer.isPast -> {
                val mins = minutesUntil(prayer.time).coerceAtLeast(0)

                Text(
                    "Coming in ${PrayerTimeUiMapper.formatDuration(mins)}",
                    color = Color.Gray,
                    style = MaterialTheme.typography.labelSmall
                )
            }

            // ⚪ Past prayer
            else -> {
                Text(
                    "Passed", color = Color.Gray, style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}

/**
 * Maps prayer name to corresponding icon.
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

@Composable
private fun PrayerTimeSectionContent(
    prayers: List<PrayerDomain>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        SectionTitle("Prayer Times")

        Spacer(modifier = Modifier.height(12.dp))

        Card(shape = RoundedCornerShape(20.dp)) {
            LazyRow(
                modifier = Modifier.padding(vertical = 16.dp),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(prayers) { prayer ->
                    PrayerItem(prayer = prayer, onClick = {})
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PrayerTimeSectionPreview() {

    val now = LocalTime.now()

    val previewPrayers = listOf(
        PrayerDomain("Fajr", now.minusMinutes(30), isPast = true),
        PrayerDomain("Dhuhr", now.plusMinutes(10), isNext = true, remainingMinutes = 10),
        PrayerDomain("Asr", now.plusHours(3)),
        PrayerDomain("Maghrib", now.plusHours(6)),
        PrayerDomain("Isha", now.plusHours(8))
    )

    PrayerTimeSectionContent(prayers = previewPrayers)
}

/*

Add unit tests for PrayerTimeUiMapper

Refactor to UiState + sealed UI events

Optimize time updates without recomposition storms

        */
