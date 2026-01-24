package com.hathway.ramadankareem2026.ui.mosques.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hathway.ramadankareem2026.ui.mosques.domain.model.Mosque

@SuppressLint("DefaultLocale")
@Composable
fun MosqueListItem(
    mosque: Mosque,
    onClick: () -> Unit
) {
    val distanceKm = mosque.distanceMeters / 1000f

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() }
    ) {
        Text(
            text = mosque.name,
            style = MaterialTheme.typography.bodyLarge
        )


        Text(
            text = String.format("%.1f km away", distanceKm),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

    }
}
