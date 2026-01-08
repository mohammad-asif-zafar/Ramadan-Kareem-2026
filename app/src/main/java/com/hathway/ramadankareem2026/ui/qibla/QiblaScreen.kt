package com.hathway.ramadankareem2026.ui.qibla

import android.hardware.SensorManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hathway.ramadankareem2026.R

@Composable
fun QiblaScreen(
    viewModel: QiblaViewModel = viewModel(), title: String
) {
    val state by viewModel.uiState.collectAsState()

    DisposableEffect(Unit) { onDispose { viewModel.stopCompass() } }

    LaunchedEffect(Unit) { viewModel.startCompass() }

    val needsCalibration =
        state.sensorAccuracy == SensorManager.SENSOR_STATUS_UNRELIABLE
                || state.sensorAccuracy == SensorManager.SENSOR_STATUS_ACCURACY_LOW

    if (needsCalibration) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Move your phone in a figure-8 to calibrate compass",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.error
        )
    }

    if (!state.isSensorAvailable) {
        Box(
            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            Text("Compass not available on this device")
        }
        return
    }

    val rotation = state.qiblaBearing - state.deviceAzimuth

    Column(
        modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Qibla Direction", style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        Box(
            modifier = Modifier.size(300.dp), contentAlignment = Alignment.Center
        ) {

            // 🧭 Compass ring (static)
            Image(
                painter = painterResource(R.drawable.ic_compass_ring),
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )

            // 🧭 Rotating arrow (Qibla)
            Image(
                painter = painterResource(R.drawable.ic_qibla_arrow),
                contentDescription = "Qibla Arrow",
                modifier = Modifier
                    .size(200.dp)
                    .graphicsLayer {
                        rotationZ = rotation
                    })

            // 🕋 Kaaba icon (center)
            Image(
                painter = painterResource(R.drawable.ic_kaaba),
                contentDescription = "Kaaba",
                modifier = Modifier.size(48.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Rotate your phone to align", style = MaterialTheme.typography.bodySmall
        )
    }
}

