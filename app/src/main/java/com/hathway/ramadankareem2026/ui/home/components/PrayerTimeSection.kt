package com.hathway.ramadankareem2026.ui.home.components

import android.app.Application
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material.icons.outlined.NightsStay
import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.material.icons.outlined.WbTwilight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hathway.ramadankareem2026.R
import com.hathway.ramadankareem2026.ui.home.model.PrayerDomain
import com.hathway.ramadankareem2026.ui.prayer.PrayerTimeUiMapper
import com.hathway.ramadankareem2026.ui.prayer.PrayerType
import com.hathway.ramadankareem2026.ui.prayer.PrayerViewModel
import com.hathway.ramadankareem2026.ui.prayer.data.PrayerDemoData
import com.hathway.ramadankareem2026.ui.prayer.data.PrayerViewModelFactory
import com.hathway.ramadankareem2026.ui.theme.NoorMuslimCompanionTheme
import kotlinx.coroutines.delay
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun PrayerTimeSection(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val app = context.applicationContext as? Application

    // Clean structural separation handling fallback data safely outside core logic blocks
    val state = if (app != null) {
        val viewModel: PrayerViewModel = viewModel(factory = PrayerViewModelFactory(app))
        viewModel.state.collectAsState().value
    } else {
        PrayerDemoData.demo()
    }

    // Fixed: Collected the ticker state here to ensure the UI updates every minute
    val currentTimeMillis by rememberMinuteTicker()
    val currentTime = remember(currentTimeMillis) { LocalTime.now() }

    val prayers = remember(state, currentTime) {
        PrayerTimeUiMapper.map(state, currentTime)
    }

    if (prayers.isEmpty()) return

    PrayerTimeSectionContent(
        prayers = prayers, modifier = modifier
    )
}

/**
 * Pure display composable built entirely without view model initialization hooks,
 * allowing Android Studio previews to map layouts across different themes safely.
 */
@Composable
private fun PrayerTimeSectionContent(
    prayers: List<PrayerDomain>, modifier: Modifier = Modifier
) {
    var selectedPrayer by remember { mutableStateOf<PrayerDomain?>(null) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                prayers.forEach { prayer ->
                    PrayerItem(
                        prayer = prayer,
                        onClick = { selectedPrayer = prayer },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }

    selectedPrayer?.let { prayer ->
        PrayerDetailsDialog(prayer = prayer, onDismiss = { selectedPrayer = null })
    }
}

@Composable
fun PrayerItem(
    prayer: PrayerDomain, onClick: () -> Unit, modifier: Modifier = Modifier
) {
    val isDark = isSystemInDarkTheme()
    val itemShape = RoundedCornerShape(18.dp)

    // Smooth container background animations as times switch over
    val targetBackgroundColor = when {
        prayer.isCurrent && isDark -> MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.25f)
        prayer.isCurrent -> MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.45f)
        else -> Color.Transparent
    }
    val containerColor by animateColorAsState(
        targetValue = targetBackgroundColor, label = "bg_color"
    )

    val activeContentColor = MaterialTheme.colorScheme.primary
    val inactiveContentColor = MaterialTheme.colorScheme.onSurfaceVariant
    val inactiveTimeColor = MaterialTheme.colorScheme.outline

    val contentColor = if (prayer.isCurrent) activeContentColor else inactiveContentColor

    Column(
        modifier = modifier
            .clip(itemShape)
            .background(containerColor)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(bounded = true), // Clean bounding M3 ripples restored
                onClick = onClick
            )
            .padding(vertical = 12.dp, horizontal = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = iconForPrayer(prayer.type),
            contentDescription = prayer.type.displayName(),
            tint = contentColor,
            modifier = Modifier.size(24.dp)
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = prayer.type.displayName(),
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = if (prayer.isCurrent) FontWeight.Bold else FontWeight.Medium,
                fontSize = 12.sp
            ),
            color = contentColor,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(Modifier.height(2.dp))

        Text(
            text = prayer.time.format(DateTimeFormatter.ofPattern("hh:mm a")),
            style = MaterialTheme.typography.bodySmall.copy(
                fontSize = 10.sp,
                fontWeight = if (prayer.isCurrent) FontWeight.SemiBold else FontWeight.Normal
            ),
            color = if (prayer.isCurrent) activeContentColor else inactiveTimeColor,
            textAlign = TextAlign.Center,
            maxLines = 1
        )

        if (prayer.isCurrent) {
            Spacer(Modifier.height(4.dp))
            Text(
                text = stringResource(R.string.time_now),
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Bold, fontSize = 10.sp
                ),
                color = activeContentColor,
                textAlign = TextAlign.Center
            )
        }
    }
}

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
    Dialog(onDismissRequest = onDismiss) { // Fixed raw pop-up component using M3 Dialog shell wrapper
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = iconForPrayer(prayer.type),
                            contentDescription = prayer.type.displayName(),
                            modifier = Modifier.size(28.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(Modifier.width(12.dp))
                        Text(
                            text = prayer.type.displayName(),
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(
                            imageVector = Icons.Outlined.Close,
                            contentDescription = "Close",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                Text(
                    text = prayer.time.format(DateTimeFormatter.ofPattern("hh:mm a")),
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = prayer.type.description(),
                    style = MaterialTheme.typography.bodyMedium,
                    lineHeight = 22.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
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
fun rememberMinuteTicker(): State<Long> { // Explicitly add <Long> here
    return produceState(initialValue = System.currentTimeMillis()) {
        while (true) {
            val now = System.currentTimeMillis()
            val delayMs = 60_000 - (now % 60_000)
            delay(delayMs)
            value = System.currentTimeMillis()
        }
    }
}

/* ----------------------------------------------------
• STRUCTURAL DEV PREVIEWS (DARK THEME DRIVEN FIRST)
• ---------------------------------------------------*/
@Preview(name = "Dark Theme Preview", showBackground = true, widthDp = 360)
@Composable
fun PrayerTimeSectionDarkPreview() {
    val mockPrayers = listOf(
        PrayerDomain(PrayerType.FAJR, LocalTime.of(5, 30), isCurrent = false),
        PrayerDomain(PrayerType.DHUHR, LocalTime.of(12, 45), isCurrent = false),
        PrayerDomain(
            PrayerType.ASR, LocalTime.of(16, 0), isCurrent = true
        ), // Current active prayer pointer
        PrayerDomain(PrayerType.MAGHRIB, LocalTime.of(18, 45), isCurrent = false),
        PrayerDomain(PrayerType.ISHA, LocalTime.of(20, 15), isCurrent = false)
    )
    NoorMuslimCompanionTheme(darkTheme = true) {
        Surface(color = MaterialTheme.colorScheme.background) {
            Box(modifier = Modifier.padding(vertical = 16.dp)) {
                PrayerTimeSectionContent(prayers = mockPrayers)
            }
        }
    }
}

@Preview(name = "Light Theme Preview", showBackground = true, widthDp = 360)
@Composable
fun PrayerTimeSectionLightPreview() {
    val mockPrayers = listOf(
        PrayerDomain(PrayerType.FAJR, LocalTime.of(5, 30), isCurrent = false),
        PrayerDomain(PrayerType.DHUHR, LocalTime.of(12, 45), isCurrent = true),
        PrayerDomain(PrayerType.ASR, LocalTime.of(16, 0), isCurrent = false),
        PrayerDomain(PrayerType.MAGHRIB, LocalTime.of(18, 45), isCurrent = false),
        PrayerDomain(PrayerType.ISHA, LocalTime.of(20, 15), isCurrent = false)
    )
    NoorMuslimCompanionTheme(darkTheme = false) {
        Surface(color = MaterialTheme.colorScheme.background) {
            Box(modifier = Modifier.padding(vertical = 16.dp)) {
                PrayerTimeSectionContent(prayers = mockPrayers)
            }
        }
    }
}
//


