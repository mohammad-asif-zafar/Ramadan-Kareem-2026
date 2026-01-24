package com.hathway.ramadankareem2026.ui.mosques

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.platform.LocalContext
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
import kotlinx.coroutines.launch

@SuppressLint("UnusedBoxWithConstraintsScope")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NearbyMosquesScreen(
    state: MosqueUiState,
    onBack: () -> Unit,
    onMosqueClick: (Mosque) -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val openNavigation: (Mosque) -> Unit = { mosque ->
        val googleMapsIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("google.navigation:q=${mosque.lat},${mosque.lng}")
        ).apply {
            setPackage("com.google.android.apps.maps")
        }

        runCatching {
            context.startActivity(googleMapsIntent)
        }.onFailure {
            val fallback = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://www.google.com/maps/dir/?api=1&destination=${mosque.lat},${mosque.lng}")
            )
            context.startActivity(fallback)
        }
    }

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

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val halfScreenPeekHeight = maxHeight / 2
        val scaffoldState = rememberBottomSheetScaffoldState()

        BottomSheetScaffold(
            scaffoldState = scaffoldState,
            sheetShape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
            topBar = {
                RamadanToolbar(
                    title = "المساجد القريبة",
                    showBack = true,
                    onBackClick = onBack
                )
            },
            sheetPeekHeight = halfScreenPeekHeight,
            sheetContent = {
                MosqueList(
                    mosques = state.mosques,
                    onMosqueClick = { mosque ->
                        onMosqueClick(mosque)
                        scope.launch {
                            scaffoldState.bottomSheetState.partialExpand()
                        }
                    },
                    onDirectionsClick = { mosque ->
                        openNavigation(mosque)
                    },
                    onClose = {
                        scope.launch {
                            scaffoldState.bottomSheetState.partialExpand()
                        }
                    }
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
                        title = mosque.name,
                        onClick = {
                            openNavigation(mosque)
                            true
                        }
                    )
                }
            }
        }
    }
}

