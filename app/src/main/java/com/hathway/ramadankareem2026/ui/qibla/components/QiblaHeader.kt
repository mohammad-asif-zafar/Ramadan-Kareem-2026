package com.hathway.ramadankareem2026.ui.qibla.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.hathway.ramadankareem2026.ui.theme.Emerald

@Composable
fun QiblaHeader(
    degree: Int, location: String
) {
    val isDark = androidx.compose.foundation.isSystemInDarkTheme()
    
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Qibla Direction", 
            style = MaterialTheme.typography.titleMedium,
            color = (if (isDark) Color.White else Color.Black).copy(alpha = 0.6f)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "$degree°",
            style = MaterialTheme.typography.displayMedium,
            fontWeight = FontWeight.ExtraBold,
            color = if (isDark) Color.White else Color(0xFF1B3D36)
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = location,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium,
            color = Emerald
        )
    }
}
