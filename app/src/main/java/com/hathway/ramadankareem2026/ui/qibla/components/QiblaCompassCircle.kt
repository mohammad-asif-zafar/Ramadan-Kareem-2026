package com.hathway.ramadankareem2026.ui.qibla.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.hathway.ramadankareem2026.R

@Composable
fun QiblaCompassCircle(
    deviceRotation: Float,
    qiblaRotation: Float
) {
    Box(
        modifier = Modifier
            .size(320.dp)
            .shadow(
                elevation = 16.dp,
                shape = CircleShape
            )
            .background(
                color = Color.White,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {

        // Compass background
        Image(
            painter = painterResource(R.drawable.ic_compass_ring),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )

        // Device direction needle (blue / teal)
        Image(
            painter = painterResource(R.drawable.ic_device_needle),
            contentDescription = null,
            modifier = Modifier
                .size(200.dp)
                .graphicsLayer {
                    rotationZ = deviceRotation
                }
        )

        // Qibla needle (red / green)
        Image(
            painter = painterResource(R.drawable.ic_qibla_needle),
            contentDescription = null,
            modifier = Modifier
                .size(200.dp)
                .graphicsLayer {
                    rotationZ = qiblaRotation
                }
        )

        // Center cap
        Box(
            modifier = Modifier
                .size(14.dp)
                .background(Color.Gray, CircleShape)
        )
    }
}
