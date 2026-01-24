package com.hathway.ramadankareem2026.ui.mosques

import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hathway.ramadankareem2026.ui.components.RamadanToolbar
import com.hathway.ramadankareem2026.ui.mosques.domain.model.Mosque
import com.hathway.ramadankareem2026.ui.mosques.presentation.viewmodel.NearbyMosquesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NearbyMosquesScreen(
    viewModel: NearbyMosquesViewModel = viewModel(),
    onBack: () -> Unit
) {
    val mosques by viewModel.mosques
    var selectedMosque by remember { mutableStateOf<Mosque?>(null) }

    LaunchedEffect(Unit) {
        viewModel.load(3.1390, 101.6869)
    }

    BottomSheetScaffold(
        sheetPeekHeight = 120.dp,
        topBar = {
            RamadanToolbar(
                title = " المساجد القريبة - Nearby mosques",
                showBack = true,
                onBackClick = onBack
            )
        },
        sheetContent = {
            MosqueList(
                mosques = mosques,
                onMosqueClick = { selectedMosque = it } // ✅ NOW USED
            )
        }
    ) { padding ->
        MosqueMapWithList(
            mosques = mosques,
            focusedMosque = selectedMosque // 👈 pass down
        )
    }
}

