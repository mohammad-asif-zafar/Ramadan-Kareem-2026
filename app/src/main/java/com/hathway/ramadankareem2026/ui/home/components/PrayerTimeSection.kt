package com.hathway.ramadankareem2026.ui.home.components

import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hathway.ramadankareem2026.R
import com.hathway.ramadankareem2026.ui.home.model.PrayerDomain
import com.hathway.ramadankareem2026.ui.prayer.PrayerTimeUiMapper
import com.hathway.ramadankareem2026.ui.prayer.PrayerTimeUiState
import com.hathway.ramadankareem2026.ui.prayer.PrayerType
import com.hathway.ramadankareem2026.ui.prayer.PrayerViewModel
import com.hathway.ramadankareem2026.ui.prayer.data.PrayerDemoData
import com.hathway.ramadankareem2026.ui.prayer.data.PrayerViewModelFactory
import kotlinx.coroutines.delay
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun PrayerTimeSection() {
    val context = LocalContext.current
    
    // Safety check for previews/layoutlib
    val app = try {
        context.applicationContext as? Application
    } catch (e: Exception) {
        null
    }

    val state = if (app != null) {
        val viewModel: PrayerViewModel = viewModel(
            factory = PrayerViewModelFactory(app)
        )
        viewModel.state.collectAsState().value
    } else {
        PrayerDemoData.demo()
    }

    val now = remember { LocalTime.now() }
    val prayers = remember(state) {
        PrayerTimeUiMapper.map(state, now)
    }

    var selectedPrayer by remember { mutableStateOf<PrayerDomain?>(null) }

    if (prayers.isEmpty()) return

    Column(
        modifier = Modifier
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
                    .padding(4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                prayers.forEachIndexed { index, prayer ->
                    PrayerItem(
                        modifier = Modifier.weight(1f),
                        prayer = prayer,
                        onClick = { selectedPrayer = prayer }
                    )
                }
            }
        }
    }

    selectedPrayer?.let {
        PrayerDetailsDialog(prayer = it, onDismiss = { selectedPrayer = null })
    }
}

@Composable
fun PrayerItem(
    prayer: PrayerDomain,
    modifier: Modifier = Modifier,
    onClick: (PrayerDomain) -> Unit
) {
    val isDark = isSystemInDarkTheme()
    
    val activeBackgroundColor = if (isDark) {
        MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
    } else {
        MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
    }
    
    val activeContentColor = MaterialTheme.colorScheme.primary
    val inactiveContentColor = MaterialTheme.colorScheme.onSurfaceVariant
    val inactiveTimeColor = MaterialTheme.colorScheme.outline

    val background = if (prayer.isCurrent) activeBackgroundColor else Color.Transparent
    val contentColor = if (prayer.isCurrent) activeContentColor else inactiveContentColor

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(background)
            .clickable { onClick(prayer) }
            .padding(vertical = 12.dp, horizontal = 2.dp),
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
                    fontWeight = FontWeight.Bold,
                    fontSize = 10.sp
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
    Box(
        Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
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
                            modifier = Modifier.size(28.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(Modifier.width(12.dp))
                        Text(
                            prayer.type.displayName(),
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    Icon(
                        Icons.Outlined.Close,
                        contentDescription = "Close",
                        modifier = Modifier.clickable { onDismiss() },
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
                Text(
                    prayer.time.format(DateTimeFormatter.ofPattern("hh:mm a")),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    prayer.type.description(),
                    style = MaterialTheme.typography.bodyMedium,
                    lineHeight = 20.sp,
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
        PrayerType.ISHA -> R.string.prayer_desc_isha})


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
