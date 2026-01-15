package com.hathway.ramadankareem2026.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CountdownCircularProgress(
    totalMinutes: Int,
    remainingMinutes: Int,
    modifier: Modifier = Modifier,
    progressColor: Color = Color(0xFF4E6CEF),
    trackColor: Color = Color.LightGray.copy(alpha = 0.4f)
) {
    val targetProgress = if (totalMinutes > 0) remainingMinutes / totalMinutes.toFloat() else 0f

    val animatedProgress by animateFloatAsState(
        targetValue = targetProgress.coerceIn(0f, 1f),
        animationSpec = tween(durationMillis = 600),
        label = "CountdownProgress"
    )


    Box(
        modifier = modifier.size(56.dp), contentAlignment = Alignment.Center
    ) {

        // Background ring
        CircularProgressIndicator(
            progress = { 1f },
            modifier = Modifier.fillMaxSize(),
            color = trackColor,
            strokeWidth = 6.dp,
            trackColor = ProgressIndicatorDefaults.circularTrackColor,
            strokeCap = StrokeCap.Butt,
        )

        // Foreground progress
        CircularProgressIndicator(
            progress = { animatedProgress },
            modifier = Modifier.fillMaxSize(),
            color = progressColor,
            strokeWidth = 6.dp,
            trackColor = ProgressIndicatorDefaults.circularTrackColor,
            strokeCap = StrokeCap.Round,
        )

        // Center text
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = formatMinutes(remainingMinutes),
                fontSize = 10.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "left", fontSize = 9.sp, color = Color.Gray
            )
        }
    }
}

private fun formatMinutes(minutes: Int): String {
    val h = minutes / 60
    val m = minutes % 60
    return if (h > 0) "${h}h ${m}m" else "${m}m"
}
