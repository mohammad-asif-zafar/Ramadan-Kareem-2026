package com.hathway.ramadankareem2026.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hathway.ramadankareem2026.R

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
        label = stringResource(R.string.counter_progress)
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
            trackColor = ProgressIndicatorDefaults.circularIndeterminateTrackColor,
            strokeCap = StrokeCap.Butt,
        )

        // Foreground progress
        CircularProgressIndicator(
            progress = { animatedProgress },
            modifier = Modifier.fillMaxSize(),
            color = progressColor,
            strokeWidth = 6.dp,
            trackColor = ProgressIndicatorDefaults.circularIndeterminateTrackColor,
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
                text = stringResource(R.string.left), fontSize = 9.sp, color = Color.Gray
            )
        }
    }
}

private fun formatMinutes(minutes: Int): String {
    val h = minutes / 60
    val m = minutes % 60
    return if (h > 0) "${h}h ${m}m" else "${m}m"
}

@Preview(showBackground = true)
@Composable
fun CountdownCircularProgressPreview() {
    MaterialTheme {
        CountdownCircularProgress(
            totalMinutes = 120, remainingMinutes = 45
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CountdownCircularProgressAlmostDonePreview() {
    MaterialTheme {
        CountdownCircularProgress(
            totalMinutes = 60,
            remainingMinutes = 5,
            progressColor = Color(0xFFE53935) // red warning style
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CountdownCircularProgressFullPreview() {
    MaterialTheme {
        CountdownCircularProgress(
            totalMinutes = 30, remainingMinutes = 30
        )
    }
}
