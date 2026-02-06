package com.hathway.ramadankareem2026.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.Alarm
import androidx.compose.material.icons.outlined.AlarmOff
import androidx.compose.material.icons.outlined.AutoStories
import androidx.compose.material.icons.outlined.Bedtime
import androidx.compose.material.icons.outlined.NightsStay
import androidx.compose.material.icons.outlined.Restaurant
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hathway.ramadankareem2026.ui.home.model.HeaderType
import com.hathway.ramadankareem2026.ui.theme.RamadanGold
import com.hathway.ramadankareem2026.ui.theme.RamadanGreen
import com.hathway.ramadankareem2026.ui.theme.RamadanGreenDark
import com.hathway.ramadankareem2026.ui.tips.presentation.viewmodel.RandomRamadanTipsViewModelFactory

private const val TAG = "HeaderCard"

@Composable
fun HeaderCard(
    type: HeaderType,
    title: String,
    subtitle: String,
    hint: String,
    isAlarmEnabled: Boolean = false,
    onAlarmToggle: (() -> Unit)? = null // ✅ NORMAL lambda
) {
    // Reminder card stays unchanged
    if (type == HeaderType.REMINDER) {
        DynamicReminderCard(
            title = title, subtitle = subtitle, hint = hint
        )
        return
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp).padding(end = 4.dp),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (type == HeaderType.DYNAMIC_PRAYER) 8.dp else 4.dp
        )
    ) {
        Box(
            modifier = Modifier
                .background(headerGradient(type))
                .padding(horizontal = 20.dp, vertical = 18.dp)
                .fillMaxSize()
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                // 🔝 Icon + Title + Alarm
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = headerIcon(type),
                            contentDescription = null,
                            tint = Color.White.copy(alpha = 0.9f),
                            modifier = Modifier.size(26.dp)
                        )

                        Spacer(Modifier.width(8.dp))

                        Text(
                            text = title,
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.White.copy(alpha = 0.85f),
                            fontWeight = FontWeight.Medium
                        )
                    }

                    // 🔔 Alarm icon
                    if (type == HeaderType.IFTAR_TIME || type == HeaderType.SUHOOR_TIME) {
                        Icon(
                            imageVector = if (isAlarmEnabled) Icons.Outlined.Alarm
                            else Icons.Outlined.AlarmOff,
                            contentDescription = null,
                            tint = if (isAlarmEnabled) Color.White
                            else Color.White.copy(alpha = 0.5f),
                            modifier = Modifier
                                .size(20.dp)
                                .clickable(enabled = onAlarmToggle != null) {
                                    onAlarmToggle?.invoke()
                                })
                    }
                }

                // 🔹 Main content
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2
                )

                Text(
                    text = hint,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White.copy(alpha = 0.75f),
                    maxLines = 1
                )
            }
        }
    }
}


private fun headerIcon(type: HeaderType) = when (type) {
    HeaderType.DYNAMIC_PRAYER -> Icons.Outlined.NightsStay
    HeaderType.NEXT_PRAYER -> Icons.Outlined.AccessTime
    HeaderType.REMINDER -> Icons.Outlined.AutoStories
    HeaderType.IFTAR_TIME -> Icons.Outlined.Restaurant
    HeaderType.SUHOOR_TIME -> Icons.Outlined.Bedtime
}

@Composable
private fun headerGradient(type: HeaderType): Brush = when (type) {

    HeaderType.DYNAMIC_PRAYER -> Brush.linearGradient(
        colors = listOf(
            RamadanGreen, RamadanGreenDark
        )
    )

    HeaderType.NEXT_PRAYER -> Brush.linearGradient(
        colors = listOf(
            RamadanGreenDark, RamadanGreen
        )
    )

    HeaderType.REMINDER -> Brush.linearGradient(
        colors = listOf(
            RamadanGold, RamadanGreen
        )
    )

    HeaderType.IFTAR_TIME -> Brush.linearGradient(
        colors = listOf(
            Color(0xFFFF6B35), Color(0xFFF7931E) // Orange gradient for Iftar
        )
    )

    HeaderType.SUHOOR_TIME -> Brush.linearGradient(
        colors = listOf(
            Color(0xFF4A5568), Color(0xFF718096) // Gray-blue gradient for Suhoor
        )
    )
}

/**
 * Dynamic reminder card that uses tips data but keeps original design
 */
@Composable
private fun DynamicReminderCard(
    title: String, subtitle: String, hint: String
) {
    val viewModel: com.hathway.ramadankareem2026.ui.tips.presentation.viewmodel.RandomRamadanTipsViewModel =
        viewModel(factory = RandomRamadanTipsViewModelFactory())
    val currentTip by viewModel.currentTip.collectAsStateWithLifecycle()

    // Auto-refresh tip periodically
    LaunchedEffect(Unit) {
        while (true) {
            kotlinx.coroutines.delay(20000) // Refresh every 20 seconds
            viewModel.loadNewRandomTip()
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .background(headerGradient(HeaderType.REMINDER))
                .padding(horizontal = 20.dp, vertical = 18.dp)
                .height(150.dp)
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Icon + Title
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = headerIcon(HeaderType.REMINDER),
                            contentDescription = null,
                            tint = Color.White.copy(alpha = 0.9f),
                            modifier = Modifier.size(26.dp)
                        )

                        Spacer(Modifier.width(8.dp))

                        Text(
                            text = title,
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.White.copy(alpha = 0.85f),
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                // Dynamic content from tips
                Column {
                    val tip = currentTip
                    Text(
                        text = tip?.title ?: subtitle,
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = tip?.content ?: hint,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White.copy(alpha = 0.9f),
                        maxLines = 2,
                        overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}



