package com.hathway.ramadankareem2026.ui.qibla.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hathway.ramadankareem2026.ui.qibla.accuracyColor
import com.hathway.ramadankareem2026.ui.qibla.accuracyLabel

@Composable
fun AlignmentStatus(
    accuracy: Int, isAligned: Boolean, showAlignedText: Boolean
) {
    Text(
        text = accuracyLabel(accuracy),
        style = MaterialTheme.typography.bodySmall,
        color = accuracyColor(accuracy)
    )

    if (isAligned && showAlignedText) {
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "✓ Qibla aligned",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary
        )
    }
}
