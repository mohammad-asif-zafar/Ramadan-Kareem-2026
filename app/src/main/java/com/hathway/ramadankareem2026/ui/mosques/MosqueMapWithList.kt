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
    mosques: List<Mosque>, focusedMosque: Mosque?
) {
    val cameraState = rememberCameraPositionState()

    LaunchedEffect(focusedMosque) {
        focusedMosque?.let {
            cameraState.animate(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(it.lat, it.lng), 16f
                )
            )
        }
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(), cameraPositionState = cameraState
    ) {
        mosques.forEach {
            Marker(
                state = MarkerState(LatLng(it.lat, it.lng)), title = it.name
            )
        }
    }
}
