package com.hathway.ramadankareem2026.ui.qibla

import android.hardware.SensorManager
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.hathway.ramadankareem2026.R
import com.hathway.ramadankareem2026.ui.components.RamadanToolbar
import com.hathway.ramadankareem2026.ui.qibla.components.AlignmentStatus
import com.hathway.ramadankareem2026.ui.qibla.components.QiblaCompassCircle
import com.hathway.ramadankareem2026.ui.qibla.components.QiblaHeader
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

    DisposableEffect(Unit) {
        viewModel.startCompass()
        onDispose { viewModel.stopCompass() }
    }


    Scaffold(
        topBar = {
            RamadanToolbar(
                title = stringResource(R.string.qibla_direction),
                showBack = false,
                onBackClick = onBack,
                rightIcon1 = R.drawable.bell,
                onRightIcon1Click = onViewFullCalendar,
                rightIcon2 = R.drawable.bell,
                onRightIcon2Click = onSettings
            )
        }) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            QiblaHeader(
                degree = 81, location = "Makkah, Saudi Arabia"
            )

            Spacer(modifier = Modifier.height(32.dp))

            QiblaCompassCircle(
                deviceRotation = -state.deviceAzimuth,
                qiblaRotation = state.qiblaBearing - state.deviceAzimuth
            )
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

