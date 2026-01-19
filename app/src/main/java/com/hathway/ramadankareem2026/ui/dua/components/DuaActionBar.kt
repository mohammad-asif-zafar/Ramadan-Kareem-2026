package com.hathway.ramadankareem2026.ui.dua.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ContentCopy
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hathway.ramadankareem2026.ui.dua.model.DuaItem

@Composable
fun DuaActionBar(dua: DuaItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        OutlinedButton(
            modifier = Modifier.weight(1f), onClick = {
                // TODO: copy to clipboard
            }) {
            Icon(Icons.Outlined.ContentCopy, contentDescription = null)
            Spacer(Modifier.width(8.dp))
            Text("Copy")
        }

        Button(
            modifier = Modifier.weight(1f), onClick = {
                // TODO: share intent
            }) {
            Icon(Icons.Outlined.Share, contentDescription = null)
            Spacer(Modifier.width(8.dp))
            Text("Share")
        }
    }
}

