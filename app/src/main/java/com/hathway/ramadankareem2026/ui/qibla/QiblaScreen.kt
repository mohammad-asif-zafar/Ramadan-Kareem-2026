package com.hathway.ramadankareem2026.ui.qibla

import android.hardware.SensorManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.hathway.ramadankareem2026.R
import com.hathway.ramadankareem2026.ui.components.RamadanToolbar
import com.hathway.ramadankareem2026.ui.navigation.Routes


@Composable
fun QiblaScreen(
    viewModel: QiblaViewModel = viewModel(),
    title: String,
    navController: NavController,
    onBack: () -> Unit,
    onViewFullCalendar: () -> Unit,
    onSettings: () -> Unit
) {


    val state by viewModel.uiState.collectAsState()

    DisposableEffect(Unit) { onDispose { viewModel.stopCompass() } }

    LaunchedEffect(Unit) { viewModel.startCompass() }

    val needsCalibration =
        state.sensorAccuracy == SensorManager.SENSOR_STATUS_UNRELIABLE || state.sensorAccuracy == SensorManager.SENSOR_STATUS_ACCURACY_LOW

    if (needsCalibration && state.preferences.showCalibrationHint) {
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

    Scaffold(
        topBar = {
            RamadanToolbar(
                title = stringResource(R.string.qibla_direction),
                showBack = true,
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
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
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
            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = accuracyLabel(state.sensorAccuracy),
                style = MaterialTheme.typography.bodySmall,
                color = accuracyColor(state.sensorAccuracy)
            )
            if (state.isAligned && state.preferences.showAlignmentText) {
                Text(
                    text = "✓ Qibla aligned",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }


    }


    /* if (state.isAligned) {
         Spacer(modifier = Modifier.height(16.dp))
         Text(
             text = "✓ Qibla aligned",
             style = MaterialTheme.typography.bodyMedium,
             color = MaterialTheme.colorScheme.primary
         )
     }
     IconButton(
         onClick = {
             navController.navigate(Routes.QIBLA_SETTINGS)
         })
          {
         Icon(
             imageVector = Icons.Outlined.Settings, contentDescription = "Qibla Settings"
         )
     }*/
}

