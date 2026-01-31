package com.hathway.ramadankareem2026.ui.home.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.hathway.ramadankareem2026.core.location.LocationSelectionMode
import com.hathway.ramadankareem2026.core.location.LocationSource
import com.hathway.ramadankareem2026.core.location.LocationUiState
import com.hathway.ramadankareem2026.R

@Composable
fun TopBarSection(
    locationState: LocationUiState, onLocationClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 16.dp)
            .clickable { onLocationClick() }) {

        // 🌙 Greeting
        Text(
            text = stringResource(R.string.assalamoalikum),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(6.dp))

        when (locationState) {

            // ⏳ Loading state
            is LocationUiState.Loading -> {
                LocationShimmer()
            }

            // 🔴 Error state
            is LocationUiState.Error -> {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = locationState.message,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.error
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    TextButton(
                        onClick = onLocationClick, contentPadding = PaddingValues(horizontal = 8.dp)
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

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = when (locationState.source) {
                            LocationSource.DEMO -> stringResource(R.string.using_demo_location)
                            LocationSource.GPS -> stringResource(R.string.using_gps_location)
                            LocationSource.NETWORK -> stringResource(R.string.using_network_location)
                            else -> stringResource(R.string.location)
                        },
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
private fun rememberShimmerBrush(): Brush {
    val transition = rememberInfiniteTransition(label = stringResource(R.string.shimmer))

    val translateAnim by transition.animateFloat(
        initialValue = 0f, targetValue = 1000f, animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = LinearEasing), repeatMode = RepeatMode.Restart
        ), label = stringResource(R.string.shimmerTranslate)
    )

    return Brush.linearGradient(
        colors = listOf(
            Color.LightGray.copy(alpha = 0.6f),
            Color.LightGray.copy(alpha = 0.3f),
            Color.LightGray.copy(alpha = 0.6f)
        ), start = Offset(translateAnim - 1000f, 0f), end = Offset(translateAnim, 0f)
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

@Composable
private fun TopBarRtlPreviewContainer(
    locationState: LocationUiState
) {
    CompositionLocalProvider(
        LocalLayoutDirection provides LayoutDirection.Rtl
    ) {
        TopBarPreviewContainer(
            locationState = locationState
        )
    }
}

@Preview(
    showBackground = true,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES,
    name = "TopBar – RTL Dark"
)
@Composable
private fun PreviewTopBar_RTL_Dark() {
    TopBarRtlPreviewContainer(
        locationState = LocationUiState.Success(
            city = "المدينة المنورة",
            country = "المملكة العربية السعودية",
            latitude = 24.5247,
            longitude = 39.5692,
            source = LocationSource.GPS,
            selectionMode = LocationSelectionMode.USER_SELECTED
        )
    )
}

@Preview(showBackground = true, name = "TopBar – RTL (Arabic)")
@Composable
private fun PreviewTopBar_RTL_Arabic() {
    TopBarRtlPreviewContainer(
        locationState = LocationUiState.Success(
            city = "مكة",
            country = "المملكة العربية السعودية",
            latitude = 21.4225,
            longitude = 39.8262,
            source = LocationSource.GPS,
            selectionMode = LocationSelectionMode.USER_SELECTED
        )
    )
}


@Preview(showBackground = true, fontScale = 1.3f, name = "TopBar – Font Scale 1.3x")
@Composable
private fun PreviewTopBar_FontScale_1_3x() {
    TopBarPreviewContainer(
        locationState = LocationUiState.Success(
            city = "Kuala Lumpur",
            country = "Malaysia",
            latitude = 3.1085715,
            longitude = 101.6769629,
            source = LocationSource.DEMO,
            selectionMode = LocationSelectionMode.AUTO_DETECTED
        )
    )
}

@Composable
fun TopBarPreviewContainer(locationState: LocationUiState) {
    TopBarSection(
        locationState = locationState, onLocationClick = {})
}

@Preview(showBackground = true, fontScale = 1.6f, name = "TopBar – Font Scale 1.6x")
@Composable
private fun PreviewTopBar_FontScale_1_6x() {
    TopBarPreviewContainer(
        locationState = LocationUiState.Success(
            city = "Kuala Lumpur",
            country = "Malaysia",
            latitude = 3.1085715,
            longitude = 101.6769629,
            source = LocationSource.DEMO,
            selectionMode = LocationSelectionMode.AUTO_DETECTED
        )
    )
}

@Preview(showBackground = true, fontScale = 2.0f, name = "TopBar – Font Scale 2.0x")
@Composable
private fun PreviewTopBar_FontScale_2x() {
    TopBarPreviewContainer(
        locationState = LocationUiState.Success(
            city = "Kuala Lumpur",
            country = "Malaysia",
            latitude = 3.1085715,
            longitude = 101.6769629,
            source = LocationSource.DEMO,
            selectionMode = LocationSelectionMode.AUTO_DETECTED
        )
    )
}

@Preview(
    name = "TopBar – Tablet Landscape", showBackground = true, widthDp = 1024, heightDp = 600
)
@Composable
private fun PreviewTopBar_Tablet_Landscape() {
    TopBarPreviewContainer(
        locationState = LocationUiState.Success(
            city = "Kuala Lumpur",
            country = "Malaysia",
            latitude = 3.1085715,
            longitude = 101.6769629,
            source = LocationSource.DEMO,
            selectionMode = LocationSelectionMode.AUTO_DETECTED
        )
    )
}



