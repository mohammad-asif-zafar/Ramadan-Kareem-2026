package com.hathway.ramadankareem2026.ui.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hathway.ramadankareem2026.ui.home.data.LocationSource
import com.hathway.ramadankareem2026.ui.home.data.LocationUiState

@Composable
fun TopBarSection(
    locationState: LocationUiState, onLocationClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp,vertical = 16.dp)
            .clickable { onLocationClick() }) {
        Text(
            text = "Assalamu Alaikum", style = MaterialTheme.typography.titleMedium
        )

        Text(
            text = when (locationState.source) {
                LocationSource.NONE -> "Select location"

                else -> "${locationState.city}, ${locationState.country}"
            }, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary
        )
    }
}
