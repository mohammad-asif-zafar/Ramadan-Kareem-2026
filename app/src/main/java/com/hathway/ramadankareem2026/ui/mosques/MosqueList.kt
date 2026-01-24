package com.hathway.ramadankareem2026.ui.mosques

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hathway.ramadankareem2026.ui.mosques.domain.model.Mosque
import com.hathway.ramadankareem2026.ui.mosques.presentation.MosqueListItem

@Composable
fun MosqueList(
    mosques: List<Mosque>,
    onMosqueClick: (Mosque) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        Text(
            text = "Nearby Mosques",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(Modifier.height(12.dp))

        mosques.forEach { mosque ->
            MosqueListItem(
                mosque = mosque,
                onClick = { onMosqueClick(mosque) }
            )
        }
    }
}

