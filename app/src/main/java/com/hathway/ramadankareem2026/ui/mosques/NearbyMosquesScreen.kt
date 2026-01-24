package com.hathway.ramadankareem2026.ui.mosques

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.hathway.ramadankareem2026.ui.components.RamadanToolbar
import com.hathway.ramadankareem2026.ui.mosques.domain.model.Mosque
import com.hathway.ramadankareem2026.ui.mosques.presentation.state.MosqueUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NearbyMosquesScreen(
    state: MosqueUiState,
    onBack: () -> Unit,
    onMosqueClick: (Mosque) -> Unit
) {
   // val cameraPositionState = rememberCameraPositionState()
    val cameraPositionState = rememberCameraPositionState {
        state.userLocation?.let {
            CameraPosition.fromLatLngZoom(
                LatLng(it.latitude, it.longitude),
                14f
            )
        } ?: CameraPosition.fromLatLngZoom(
            LatLng(3.1390, 101.6869),
            12f
        )
    }



    // 🎯 Center on user location (DEMO → REAL)
    LaunchedEffect(state.userLocation) {
        state.userLocation?.let {
            cameraPositionState.animate(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(it.latitude, it.longitude),
                    14f
                )
            )
        }
    }

    // 🎯 Focus selected mosque
    LaunchedEffect(state.selectedMosque) {
        state.selectedMosque?.let {
            cameraPositionState.animate(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(it.lat, it.lng),
                    16f
                )
            )
        }
    }

    BottomSheetScaffold(
        topBar = {
            RamadanToolbar(
                title = "المساجد القريبة",
                showBack = true,
                onBackClick = onBack
            )
        },
        sheetPeekHeight = 120.dp,
        sheetContent = {
            MosqueList(
                mosques = state.mosques,
                onMosqueClick = onMosqueClick
            )
        }
    ) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            state.mosques.forEach { mosque ->
                Marker(
                    state = MarkerState(
                        LatLng(mosque.lat, mosque.lng)
                    ),
                    title = mosque.name
                )
            }
        }
    }
}
