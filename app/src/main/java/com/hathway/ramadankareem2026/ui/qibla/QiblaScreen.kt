package com.hathway.ramadankareem2026.ui.qibla

import android.hardware.SensorManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.hathway.ramadankareem2026.R
import com.hathway.ramadankareem2026.ui.components.RamadanToolbar
import com.hathway.ramadankareem2026.ui.qibla.components.AlignmentStatus
import com.hathway.ramadankareem2026.ui.qibla.components.EnhancedQiblaCompassCircle
import com.hathway.ramadankareem2026.ui.qibla.components.PrayerDirectionGuide
import com.hathway.ramadankareem2026.ui.qibla.components.QiblaCompassCircle
import com.hathway.ramadankareem2026.ui.qibla.components.QiblaHeader
import com.hathway.ramadankareem2026.ui.qibla.components.QiblaInformationCards
import com.hathway.ramadankareem2026.ui.qibla.components.snappedQiblaRotation

@Composable
fun QiblaScreen(
    viewModel: QiblaViewModel = viewModel(),
    navController: NavController,
    onBack: () -> Unit,
    onViewFullCalendar: () -> Unit,
    onSettings: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()

    DisposableEffect(Unit) {
        viewModel.startCompass()
        onDispose { viewModel.stopCompass() }
    }

    // Calculate alignment status
    val (snappedRotation, isAligned) = snappedQiblaRotation(
        deviceAzimuth = state.deviceAzimuth, qiblaBearing = state.qiblaBearing
    )

    Scaffold(
        topBar = {
            RamadanToolbar(
                title = if (isAligned) "✅ Qibla Aligned - Makkah, Saudi Arabia" else stringResource(
                    R.string.qibla_direction
                ), showBack = true, onBackClick = { navController.popBackStack() })
        }) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.surface,
                            MaterialTheme.colorScheme.surfaceVariant
                        )
                    )
                )
                .verticalScroll(scrollState), horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(24.dp))


            EnhancedQiblaCompassCircle(
                deviceRotation = -state.deviceAzimuth,
                qiblaRotation = snappedRotation,
                isAligned = isAligned,
                accuracy = SensorManager.SENSOR_STATUS_ACCURACY_HIGH
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Prayer Direction Guide
            PrayerDirectionGuide()

            Spacer(modifier = Modifier.height(16.dp))

            // Information Cards
            QiblaInformationCards()

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Preview(
    name = "Qibla – Aligned", showBackground = true, device = Devices.PIXEL_6
)
@Composable
fun QiblaScreenPreviewAligned() {
    MaterialTheme {
        QiblaScreenContent(
            degree = 81,
            location = "Makkah, Saudi Arabia",
            deviceAzimuth = 80f,   // phone facing almost Qibla
            qiblaBearing = 81f     // Qibla direction
        )
    }
}


@Composable
private fun QiblaScreenContent(
    degree: Int = 81, location: String, deviceAzimuth: Float, qiblaBearing: Float
) {
    // SNAP LOGIC IS USED HERE
    val (snappedRotation, isAligned) = snappedQiblaRotation(
        deviceAzimuth = deviceAzimuth, qiblaBearing = qiblaBearing
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        QiblaHeader(
            degree = degree, location = location
        )

        Spacer(modifier = Modifier.height(32.dp))

        QiblaCompassCircle(
            deviceRotation = -deviceAzimuth, qiblaRotation = snappedRotation
        )

        Spacer(modifier = Modifier.height(24.dp))

        AlignmentStatus(
            accuracy = SensorManager.SENSOR_STATUS_ACCURACY_HIGH,
            isAligned = isAligned,
            showAlignedText = true
        )
    }
}

