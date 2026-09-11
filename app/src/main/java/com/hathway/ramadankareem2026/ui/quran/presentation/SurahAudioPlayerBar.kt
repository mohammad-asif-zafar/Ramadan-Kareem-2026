package com.hathway.ramadankareem2026.ui.quran.presentation


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SurahAudioPlayerBar(
    isPlaying: Boolean,
    onPlay: () -> Unit,
    onPause: () -> Unit,
    onStop: () -> Unit
) {
    Surface(
        tonalElevation = 8.dp,
        shadowElevation = 12.dp,
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
    ) {
        Row(
            modifier = Modifier.padding(vertical = 12.dp, horizontal = 24.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Stop button
            IconButton(
                onClick = onStop,
                modifier = Modifier
                    .size(44.dp)
                    .background(
                        color = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.2f),
                        shape = CircleShape
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.Stop, 
                    contentDescription = "Stop",
                    tint = MaterialTheme.colorScheme.error,
                    modifier = Modifier.size(24.dp)
                )
            }

            // Play/Pause button
            IconButton(
                onClick = if (isPlaying) onPause else onPlay,
                modifier = Modifier
                    .size(56.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = CircleShape
                    )
            ) {
                Icon(
                    imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                    contentDescription = "Play/Pause",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}
