package com.hathway.ramadankareem2026.ui.mosques.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Directions
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.hathway.ramadankareem2026.R
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hathway.ramadankareem2026.ui.mosques.domain.model.Mosque
import com.hathway.ramadankareem2026.ui.mosques.presentation.viewmodel.MosqueBookmarkViewModel

@SuppressLint("DefaultLocale")
@Composable
fun MosqueListItem(
    mosque: Mosque, 
    bookmarkViewModel: MosqueBookmarkViewModel,
    onClick: () -> Unit, 
    onDirectionsClick: () -> Unit
) {
    val isBookmarked by bookmarkViewModel.isBookmarked(mosque.id)
        .collectAsStateWithLifecycle(initialValue = false)
    
    // Check bookmark status when item is first displayed
    LaunchedEffect(mosque.id) {
        bookmarkViewModel.checkBookmarkStatus(mosque.id)
    }
    
    val distanceKm = mosque.distanceMeters / 1000f

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = mosque.name, style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(4.dp))

            if (mosque.address.isNotBlank()) {
                Text(
                    text = mosque.address,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(6.dp))
            }

            Text(
                text = String.format("%.1f km", distanceKm),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                AssistChip(
                    onClick = {
                        bookmarkViewModel.toggleBookmark(
                            itemId = mosque.id,
                            title = mosque.name,
                            content = mosque.address
                        )
                    },
                    label = { 
                        Text(
                            text = if (isBookmarked) stringResource(R.string.remove_bookmark) 
                            else stringResource(R.string.add_bookmark)
                        ) 
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.BookmarkBorder,
                            contentDescription = if (isBookmarked) stringResource(R.string.remove_bookmark) else stringResource(R.string.add_bookmark)
                        )
                    })
                
                AssistChip(
                    onClick = onDirectionsClick,
                    label = { Text(stringResource(R.string.directions)) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.Directions,
                            contentDescription = stringResource(R.string.directions)
                        )
                    })
            }
        }
    }
}

