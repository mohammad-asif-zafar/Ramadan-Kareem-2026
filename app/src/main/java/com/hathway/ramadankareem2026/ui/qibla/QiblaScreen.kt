package com.hathway.ramadankareem2026.ui.qibla

import android.hardware.SensorManager
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.hathway.ramadankareem2026.R
import com.hathway.ramadankareem2026.ui.components.RamadanToolbar
import com.hathway.ramadankareem2026.ui.qibla.components.*
import com.hathway.ramadankareem2026.ui.qibla.components.snappedQiblaRotation
import com.hathway.ramadankareem2026.ui.theme.RamadanGreen
import com.hathway.ramadankareem2026.ui.theme.RamadanGold

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
        deviceAzimuth = state.deviceAzimuth, 
        qiblaBearing = state.qiblaBearing
    )

    Scaffold(
        topBar = {
            RamadanToolbar(
                title = if (isAligned) "✅ Qibla Aligned - Makkah, Saudi Arabia" else stringResource(R.string.qibla_direction),
                showBack = true,
                onBackClick = onBack
            )
        }
    ) { padding ->
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
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
           // Spacer(modifier = Modifier.height(16.dp))

            // Enhanced Header with more information
           /* EnhancedQiblaHeader(
                degree = 81,
                location = "Makkah, Saudi Arabia",
                currentCity = "Your Location", // This should come from location services
                distanceToMakkah = "1,234 km", // This should be calculated
                isAligned = isAligned
            )
*/
          //  Spacer(modifier = Modifier.height(4.dp))

            // Decorative elements
           // QiblaCompassDecorations()

            Spacer(modifier = Modifier.height(24.dp))

            // Enhanced Compass
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
    degree: Int, location: String, deviceAzimuth: Float, qiblaBearing: Float
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

