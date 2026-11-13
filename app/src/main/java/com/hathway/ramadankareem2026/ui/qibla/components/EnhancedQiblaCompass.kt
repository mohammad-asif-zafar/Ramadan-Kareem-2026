package com.hathway.ramadankareem2026.ui.qibla.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hathway.ramadankareem2026.R
import com.hathway.ramadankareem2026.ui.theme.Emerald
import com.hathway.ramadankareem2026.ui.theme.Gold
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun EnhancedQiblaCompassCircle(
    deviceRotation: Float,
    qiblaRotation: Float,
    isAligned: Boolean,
    accuracy: Int
) {
    val pulseAnimation by rememberInfiniteTransition(label = "pulse").animateFloat(
        initialValue = 1f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = EaseInOutCubic),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    val isDark = MaterialTheme.colorScheme.background.luminance() < 0.5f

    Box(
        modifier = Modifier
            .size(320.dp)
            .shadow(
                elevation = if (isAligned) 16.dp else 8.dp,
                shape = CircleShape,
                ambientColor = if (isDark) Emerald.copy(alpha = 0.5f) else Color.Black.copy(alpha = 0.1f),
                spotColor = if (isDark) Emerald.copy(alpha = 0.5f) else Color.Black.copy(alpha = 0.1f)
            )
            .background(
                brush = Brush.radialGradient(
                    colors = if (isDark) {
                        listOf(Color(0xFF132B27), Color(0xFF071413))
                    } else {
                        listOf(Color.White, Color(0xFFF1F4F2))
                    }
                ),
                shape = CircleShape
            )
            .border(
                width = 2.dp,
                brush = Brush.sweepGradient(
                    colors = listOf(Emerald, Emerald.copy(alpha = 0.6f), Emerald)
                ),
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        // Outer decorative pulse ring when aligned
        if (isAligned) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        scaleX = pulseAnimation
                        scaleY = pulseAnimation
                    }
                    .border(
                        width = 2.dp,
                        brush = Brush.radialGradient(
                            colors = listOf(Emerald.copy(alpha = 0.7f), Color.Transparent)
                        ),
                        shape = CircleShape
                    )
            )
        }

        // Compass dial ticks and marks drawn via Canvas
        Canvas(modifier = Modifier.fillMaxSize().padding(12.dp)) {
            val radius = size.minDimension / 2f
            val centerX = size.width / 2f
            val centerY = size.height / 2f

            for (i in 0 until 36 step 10) {
                val angleRad = Math.toRadians(i.toDouble() - 90.0)
                val isMajor = i % 30 == 0
                val tickLength = if (isMajor) 12.dp.toPx() else 6.dp.toPx()
                val strokeWidth = if (isMajor) 2.5.dp.toPx() else 1.5.dp.toPx()
                val tickColor = if (i == 0) Emerald else (if (isDark) Color.White else Color.Black).copy(alpha = if (isMajor) 0.7f else 0.3f)

                val startX = centerX + (radius - tickLength) * cos(angleRad).toFloat()
                val startY = centerY + (radius - tickLength) * sin(angleRad).toFloat()
                val endX = centerX + radius * cos(angleRad).toFloat()
                val endY = centerY + radius * sin(angleRad).toFloat()

                drawLine(
                    color = tickColor,
                    start = Offset(startX, startY),
                    end = Offset(endX, endY),
                    strokeWidth = strokeWidth,
                    cap = StrokeCap.Round
                )
            }
        }

        // Direction labels (N, E, S, W) rotated with device rotation
        CompassDirectionLabels(deviceRotation, isDark)

        // Qibla needle (Main focus)
        Box(
            modifier = Modifier
                .size(240.dp)
                .graphicsLayer {
                    rotationZ = qiblaRotation
                },
            contentAlignment = Alignment.Center
        ) {
            androidx.compose.foundation.Image(
                painter = painterResource(R.drawable.ic_qibla_needle),
                contentDescription = stringResource(R.string.qibla_direction),
                modifier = Modifier.fillMaxSize(),
                colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(
                    if (isAligned) Emerald else Gold
                )
            )
        }

        // Center cap
        EnhancedCenterCap(isAligned = isAligned)

        // Bottom accuracy indicator
        Box(modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 36.dp)) {
            AccuracyIndicator(accuracy = accuracy)
        }
    }
}

@Composable
private fun CompassDirectionLabels(rotation: Float, isDark: Boolean) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp)
            .graphicsLayer { rotationZ = rotation }
    ) {
        val labelColor = if (isDark) Color.White.copy(alpha = 0.8f) else Color.Black.copy(alpha = 0.7f)
        val northColor = Emerald

        // North
        Text(
            text = "N",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.ExtraBold),
            color = northColor,
            modifier = Modifier.align(Alignment.TopCenter)
        )

        // East
        Text(
            text = "E",
            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
            color = labelColor,
            modifier = Modifier.align(Alignment.CenterEnd)
        )

        // South
        Text(
            text = "S",
            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
            color = labelColor,
            modifier = Modifier.align(Alignment.BottomCenter)
        )

        // West
        Text(
            text = "W",
            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
            color = labelColor,
            modifier = Modifier.align(Alignment.CenterStart)
        )
    }
}

@Composable
private fun EnhancedCenterCap(isAligned: Boolean) {
    val capColor by animateColorAsState(
        targetValue = if (isAligned) Emerald else Gold,
        animationSpec = tween(300),
        label = "capColor"
    )

    Box(
        modifier = Modifier
            .size(26.dp)
            .shadow(elevation = 6.dp, shape = CircleShape)
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(Color.White, capColor)
                ),
                shape = CircleShape
            )
            .border(2.dp, Color.White.copy(alpha = 0.8f), CircleShape)
    )
}

@Composable
private fun AccuracyIndicator(accuracy: Int) {
    val accuracyColor = when (accuracy) {
        3 -> Emerald
        2 -> Gold
        else -> Color(0xFFEF4444)
    }

    val accuracyText = when (accuracy) {
        3 -> "High Accuracy"
        2 -> "Low Accuracy"
        else -> "Unreliable"
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(12.dp))
            .border(1.dp, accuracyColor.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
            .padding(horizontal = 10.dp, vertical = 5.dp)
    ) {
        Box(
            modifier = Modifier
                .size(6.dp)
                .background(accuracyColor, CircleShape)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = accuracyText,
            style = MaterialTheme.typography.labelSmall.copy(
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold
            ),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
