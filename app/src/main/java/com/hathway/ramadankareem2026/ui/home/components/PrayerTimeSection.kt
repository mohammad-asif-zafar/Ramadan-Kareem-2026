package com.hathway.ramadankareem2026.ui.home.components

import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material.icons.outlined.NightsStay
import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.material.icons.outlined.WbTwilight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hathway.ramadankareem2026.R
import com.hathway.ramadankareem2026.ui.home.model.PrayerDomain
import com.hathway.ramadankareem2026.ui.prayer.PrayerTimeUiMapper
import com.hathway.ramadankareem2026.ui.prayer.PrayerTimeUiMapper.minutesUntil
import com.hathway.ramadankareem2026.ui.prayer.PrayerViewModel
import com.hathway.ramadankareem2026.ui.prayer.data.PrayerViewModelFactory
import java.time.LocalTime
import java.time.format.DateTimeFormatter

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

    // Find the current prayer to auto-scroll to it
    val currentPrayerIndex = remember(prayers) {
        prayers.indexOfFirst { it.isCurrent }
    }
    
    // Lazy list state for controlling scroll position
    val listState = rememberLazyListState()

    // State for prayer details dialog
    var selectedPrayer by remember { mutableStateOf<PrayerDomain?>(null) }

    // Auto-scroll to current prayer when prayer time starts
    LaunchedEffect(currentPrayerIndex) {
        if (currentPrayerIndex >= 0) {
            listState.animateScrollToItem(currentPrayerIndex)
        }
    }

    // Safety guard: do not render section if no prayers available
    if (prayers.isEmpty()) return

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
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
                state = listState,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                items(items = prayers, key = { it.name } // Stable key per prayer
                ) { prayer ->
                    PrayerItem(
                        prayer = prayer, 
                        onClick = { selectedPrayer = prayer }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
    }

    // Show prayer details dialog when a prayer is selected
    selectedPrayer?.let { prayer ->
        PrayerDetailsDialog(
            prayer = prayer,
            onDismiss = { selectedPrayer = null }
        )
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

/**
 * Prayer details dialog that shows comprehensive information about a prayer
 */
@Composable
fun PrayerDetailsDialog(
    prayer: PrayerDomain,
    onDismiss: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Header with prayer name and icon
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = iconForPrayer(prayer.name),
                            contentDescription = prayer.name,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(28.dp)
                        )
                        
                        Spacer(modifier = Modifier.width(12.dp))
                        
                        Text(
                            text = prayer.name,
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    
                    // Close button
                    Icon(
                        imageVector = Icons.Outlined.Close,
                        contentDescription = "Close",
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier
                            .clickable { onDismiss() }
                            .size(24.dp)
                    )
                }
                
                // Prayer time section
                Column {
                    Text(
                        text = "Prayer Time",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    
                    Text(
                        text = prayer.time.format(DateTimeFormatter.ofPattern("hh:mm a")),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                
                // Prayer description section
                Column {
                    Text(
                        text = "About ${prayer.name}",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    
                    Text(
                        text = getPrayerDescription(prayer.name),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        lineHeight = 20.sp,
                        maxLines = 3,
                        overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                    )
                }
                
                // Prayer status section
                Column {
                    Text(
                        text = "Prayer Status",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    
                    val statusText = when {
                        prayer.isCurrent -> "Currently praying"
                        prayer.isNext -> "Next prayer"
                        prayer.isPast -> "Prayer passed"
                        else -> "Upcoming"
                    }
                    
                    val statusColor = when {
                        prayer.isCurrent -> Color(0xFF2E7D32)
                        prayer.isNext -> Color(0xFF1976D2)
                        prayer.isPast -> Color(0xFF757575)
                        else -> MaterialTheme.colorScheme.onSurface
                    }
                    
                    Text(
                        text = statusText,
                        style = MaterialTheme.typography.bodyLarge,
                        color = statusColor
                    )
                    
                    // Show remaining time for next prayer
                    if (prayer.isNext && prayer.remainingMinutes != null) {
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Text(
                            text = "Time remaining: ${PrayerTimeUiMapper.formatRemaining(prayer.remainingMinutes, isCurrent = false)}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
                
                // Prayer details (simplified)
                Column {
                    Text(
                        text = "Prayer Details",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    
                    when (prayer.name) {
                        "Fajr" -> {
                            Text(
                                text = "• 2 Rak'ahs (Fard) + 2 Rak'ahs (Sunnah)\n• Before sunrise\n• First prayer of the day",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface,
                                lineHeight = 20.sp,
                                maxLines = 3,
                                overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                            )
                        }
                        "Dhuhr" -> {
                            Text(
                                text = "• 4 Rak'ahs (Fard) + 4 Rak'ahs (Sunnah)\n• After sun passes zenith\n• Midday prayer",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface,
                                lineHeight = 20.sp,
                                maxLines = 3,
                                overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                            )
                        }
                        "Asr" -> {
                            Text(
                                text = "• 4 Rak'ahs (Fard)\n• Late afternoon\n• Third prayer of the day",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface,
                                lineHeight = 20.sp,
                                maxLines = 3,
                                overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                            )
                        }
                        "Maghrib" -> {
                            Text(
                                text = "• 3 Rak'ahs (Fard) + 2 Rak'ahs (Sunnah)\n• Immediately after sunset\n• Time for breaking fast",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface,
                                lineHeight = 20.sp,
                                maxLines = 3,
                                overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                            )
                        }
                        "Isha" -> {
                            Text(
                                text = "• 4 Rak'ahs (Fard) + 2 Rak'ahs (Sunnah)\n• After twilight disappears\n• Last prayer of the day",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface,
                                lineHeight = 20.sp,
                                maxLines = 3,
                                overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                            )
                        }
                    }
                }
                
                // Action button
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Text(
                        text = "Tap outside to close",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.clickable { onDismiss() }
                    )
                }
            }
        }
    }
}

/**
 * Get description for each prayer (basic implementation)
 */
private fun getPrayerDescription(prayerName: String): String {
    return when (prayerName) {
        "Fajr" -> "Fajr is the first prayer of the day, performed before sunrise. It consists of 2 rak'ahs and is a time of great blessings."
        "Dhuhr" -> "Dhuhr is the midday prayer, performed after the sun passes its zenith. It consists of 4 rak'ahs and is observed during noon break."
        "Asr" -> "Asr is the afternoon prayer, performed in the late afternoon. It consists of 4 rak'ahs and marks the middle of the day."
        "Maghrib" -> "Maghrib is the sunset prayer, performed just after the sun sets. It consists of 3 rak'ahs and is the time for breaking fast during Ramadan."
        "Isha" -> "Isha is the night prayer, performed after twilight has disappeared. It consists of 4 rak'ahs and is the last prayer of the day."
        else -> "One of the five daily prayers in Islam, each with its own significance and prescribed time."
    }
}

/*
Add unit tests for PrayerTimeUiMapper

Refactor to UiState + sealed UI events

Optimize time updates without recomposition storms

        */
