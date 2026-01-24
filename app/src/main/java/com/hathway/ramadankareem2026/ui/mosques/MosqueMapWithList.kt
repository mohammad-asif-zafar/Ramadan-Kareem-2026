package com.hathway.ramadankareem2026.ui.mosques

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.hathway.ramadankareem2026.ui.mosques.domain.model.Mosque

@Composable
fun MosqueMapWithList(
    mosques: List<Mosque>,
    focusedMosque: Mosque?
) {
    val coroutineScope = rememberCoroutineScope()

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            LatLng(3.1390, 101.6869),
            13f
        )
    }

    // 🔁 React to list click
    LaunchedEffect(focusedMosque) {
        focusedMosque?.let {
            cameraPositionState.animate(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(it.lat, it.lng),
                    16f
                )
            )
        }
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        mosques.forEach { mosque ->
            Marker(
                state = MarkerState(LatLng(mosque.lat, mosque.lng)),
                title = mosque.name
            )
        }
    }
}



