package com.hathway.ramadankareem2026.ui.home.components

import androidx.compose.animation.core.animateFloat
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hathway.ramadankareem2026.R
import com.hathway.ramadankareem2026.core.location.LocationSelectionMode
import com.hathway.ramadankareem2026.core.location.LocationSource
import com.hathway.ramadankareem2026.core.location.LocationUiState

@Composable
fun HomeTopBarSection(
    locationState: LocationUiState,
    onLocationClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Location section (left side)
        Column(
            modifier = Modifier
                .weight(1f)
                .clickable { /* onLocationClick() */ }
        ) {
            // Greeting
            Text(
                text = stringResource(R.string.assalamoalikum),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(6.dp))

            when (locationState) {
                // Loading state
                is LocationUiState.Loading -> {
                    LocationShimmer()
                }

                // Error state
                is LocationUiState.Error -> {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = locationState.message,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.error
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        TextButton(
                            onClick = onLocationClick,
                            contentPadding = PaddingValues(horizontal = 8.dp)
                        ) {
                            Text(stringResource(R.string.retry))
                        }
                    }
                }

                // Success state (DEMO / GPS / NETWORK)
                is LocationUiState.Success -> {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = stringResource(R.string.location),
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.padding(end = 4.dp)
                            )

                            Text(
                                text = "${locationState.city}, ${locationState.country}",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )

                            Spacer(modifier = Modifier.width(6.dp))

                            Text(
                                text = stringResource(R.string.right_open_icon),
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }

        // Profile icon (right side)
        IconButton(
            onClick = onProfileClick,
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = CircleShape
                )
        ) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Profile",
                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
private fun rememberShimmerBrush(): Brush {
    val transition = androidx.compose.animation.core.rememberInfiniteTransition(label = stringResource(R.string.shimmer))

    val translateAnim by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = androidx.compose.animation.core.infiniteRepeatable(
            animation = androidx.compose.animation.core.tween(1200, easing = androidx.compose.animation.core.LinearEasing),
            repeatMode = androidx.compose.animation.core.RepeatMode.Restart
        ),
        label = stringResource(R.string.shimmerTranslate)
    )

    return Brush.linearGradient(
        colors = listOf(
            Color.LightGray.copy(alpha = 0.6f),
            Color.LightGray.copy(alpha = 0.3f),
            Color.LightGray.copy(alpha = 0.6f)
        ),
        start = Offset(translateAnim - 1000f, 0f),
        end = Offset(translateAnim, 0f)
    )
}

@Composable
private fun LocationShimmer() {
    val shimmer = rememberShimmerBrush()

    Column {
        Box(
            modifier = Modifier
                .width(160.dp)
                .height(18.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(shimmer)
        )

        Spacer(modifier = Modifier.height(6.dp))

        Box(
            modifier = Modifier
                .width(120.dp)
                .height(14.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(shimmer)
        )
    }
}

@Preview(showBackground = true, name = "HomeTopBar – Success")
@Composable
private fun PreviewHomeTopBar_Success() {
    HomeTopBarSection(
        locationState = LocationUiState.Success(
            city = "Kuala Lumpur",
            country = "Malaysia",
            latitude = 3.1085715,
            longitude = 101.6769629,
            source = LocationSource.DEMO,
            selectionMode = LocationSelectionMode.AUTO_DETECTED
        ),
        onLocationClick = {},
        onProfileClick = {}
    )
}

@Preview(showBackground = true, name = "HomeTopBar – Loading")
@Composable
private fun PreviewHomeTopBar_Loading() {
    HomeTopBarSection(
        locationState = LocationUiState.Loading,
        onLocationClick = {},
        onProfileClick = {}
    )
}
