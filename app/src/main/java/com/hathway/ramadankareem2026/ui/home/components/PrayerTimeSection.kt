package com.hathway.ramadankareem2026.ui.home.components

import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hathway.ramadankareem2026.R
import com.hathway.ramadankareem2026.ui.home.model.PrayerDomain
import com.hathway.ramadankareem2026.ui.prayer.PrayerTimeUiMapper
import com.hathway.ramadankareem2026.ui.prayer.PrayerType
import com.hathway.ramadankareem2026.ui.prayer.PrayerViewModel
import com.hathway.ramadankareem2026.ui.prayer.data.PrayerViewModelFactory
import kotlinx.coroutines.delay
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import androidx.compose.runtime.State


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

    val context = LocalContext.current
    val app = context.applicationContext as Application

    val viewModel: PrayerViewModel = viewModel(
        factory = PrayerViewModelFactory(app)
    )

    val state by viewModel.state.collectAsState()
    val now = remember { LocalTime.now() }

    val prayers = remember(state) {
        PrayerTimeUiMapper.map(state, now)
    }

    val currentIndex = prayers.indexOfFirst { it.isCurrent }
    val listState = rememberLazyListState()
    var selectedPrayer by remember { mutableStateOf<PrayerDomain?>(null) }

    LaunchedEffect(currentIndex) {
        if (currentIndex >= 0) {
            listState.animateScrollToItem(currentIndex)
        }
    }

    if (prayers.isEmpty()) return

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {

        SectionTitle(stringResource(R.string.prayer_times))
        Spacer(Modifier.height(12.dp))

        Card(shape = RoundedCornerShape(20.dp)) {
            LazyRow(
                state = listState,
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.padding(vertical = 16.dp)
            ) {
                items(prayers, key = { it.type }) { prayer ->
                    PrayerItem(
                        prayer = prayer, onClick = { selectedPrayer = prayer })
                }
            }
        }
    }

    selectedPrayer?.let {
        PrayerDetailsDialog(
            prayer = it, onDismiss = { selectedPrayer = null })
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


    val background = when {
        prayer.isCurrent -> Color(0xFFE6F4EA)
        else -> MaterialTheme.colorScheme.surface
    }

    val borderColor = when {
        prayer.isCurrent -> Color(0xFF2E7D32)
        else -> Color.Transparent
    }

    val contentColor = when {
        prayer.isCurrent -> highlight
        prayer.isNext -> highlight.copy(alpha = 0.8f)
        else -> MaterialTheme.colorScheme.onSurface
    }

    Column(
        modifier = Modifier
            .widthIn(min = 92.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(background)
            .border(
                width = 1.dp, color = borderColor, shape = RoundedCornerShape(14.dp)
            )
            .clickable { onClick(prayer) }
            .padding(horizontal = 12.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {

        Icon(
            imageVector = iconForPrayer(prayer.type),
            contentDescription = prayer.type.displayName(),
            tint = contentColor,
            modifier = Modifier.size(24.dp)
        )

        Spacer(Modifier.height(6.dp))

        Text(
            text = prayer.type.displayName(),
            style = MaterialTheme.typography.labelMedium,
            color = contentColor
        )

        Spacer(Modifier.height(2.dp))

        Text(
            text = prayer.time.format(DateTimeFormatter.ofPattern("hh:mm a")),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(Modifier.height(6.dp))

        // 🔒 Fixed-height status area
        Box(
            modifier = Modifier.height(20.dp), contentAlignment = Alignment.Center
        ) {
            when {
                prayer.isCurrent -> Text("Now", color = highlight)

                prayer.isNext -> CountdownText(prayer)

                prayer.isPast -> Text("Passed", color = Color.Gray)

                else -> Text("Upcoming", color = Color.Gray)
            }

        }
    }
}


/**
 * Maps prayer name to corresponding icon.
 */
@Composable
private fun iconForPrayer(type: PrayerType) = when (type) {
    PrayerType.FAJR -> Icons.Outlined.WbTwilight
    PrayerType.DHUHR -> Icons.Outlined.LightMode
    PrayerType.ASR -> Icons.Outlined.WbSunny
    PrayerType.MAGHRIB -> Icons.Outlined.NightsStay
    PrayerType.ISHA -> Icons.Outlined.DarkMode
}

@Composable
fun PrayerDetailsDialog(
    prayer: PrayerDomain, onDismiss: () -> Unit
) {
    Box(
        Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Card(shape = RoundedCornerShape(16.dp)) {
            Column(
                Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                Row(
                    Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = iconForPrayer(prayer.type),
                            contentDescription = prayer.type.displayName(),
                            modifier = Modifier.size(28.dp)
                        )
                        Spacer(Modifier.width(12.dp))
                        Text(
                            prayer.type.displayName(),
                            style = MaterialTheme.typography.headlineSmall
                        )
                    }

                    Icon(
                        Icons.Outlined.Close,
                        contentDescription = "Close",
                        modifier = Modifier.clickable { onDismiss() })

                }

                Text(
                    prayer.time.format(DateTimeFormatter.ofPattern("hh:mm a")),
                    style = MaterialTheme.typography.titleLarge
                )

                Text(
                    prayer.type.description(),
                    style = MaterialTheme.typography.bodyMedium,
                    lineHeight = 20.sp
                )
            }
        }
    }
}

@Composable
fun PrayerType.displayName(): String = stringResource(
    when (this) {
        PrayerType.FAJR -> R.string.prayer_fajr
        PrayerType.DHUHR -> R.string.prayer_dhuhr
        PrayerType.ASR -> R.string.prayer_asr
        PrayerType.MAGHRIB -> R.string.prayer_maghrib
        PrayerType.ISHA -> R.string.prayer_isha
    }
)

@Composable
fun PrayerType.description(): String = stringResource(
    when (this) {
        PrayerType.FAJR -> R.string.prayer_desc_fajr
        PrayerType.DHUHR -> R.string.prayer_desc_dhuhr
        PrayerType.ASR -> R.string.prayer_desc_asr
        PrayerType.MAGHRIB -> R.string.prayer_desc_maghrib
        PrayerType.ISHA -> R.string.prayer_desc_isha
    }
)


@Composable
fun rememberMinuteTicker(): State<Long> {
    return produceState(initialValue = System.currentTimeMillis()) {
        while (true) {
            val now = System.currentTimeMillis()
            val delayMs = 60_000 - (now % 60_000)
            delay(delayMs)
            value = System.currentTimeMillis()
        }
    }
}

@Composable
private fun CountdownText(prayer: PrayerDomain) {
    if (!prayer.isNext) return

    val ticker by rememberMinuteTicker()

    val minutesLeft = remember(ticker) {
        PrayerTimeUiMapper.minutesUntil(prayer.time).coerceAtLeast(0)
    }

    Text(
        text = "Coming in ${PrayerTimeUiMapper.formatDuration(minutesLeft)}",
        color = Color.Gray,
        style = MaterialTheme.typography.labelSmall
    )
}

