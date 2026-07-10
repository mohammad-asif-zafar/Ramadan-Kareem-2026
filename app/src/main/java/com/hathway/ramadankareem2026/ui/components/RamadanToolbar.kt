package com.hathway.ramadankareem2026.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hathway.ramadankareem2026.R

@Composable
fun RamadanToolbar(
    title: String,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    meta: String? = null,
    metaAlpha: Float = 1f,
    toolbarHeight: Dp = 56.dp,
    subtitleAlpha: Float = 1f,
    metaOffsetY: Dp = 0.dp, // 💡 Added back missing offset parameter slot
    showBack: Boolean = true,
    onBackClick: () -> Unit = {},
    leftIcon: ToolbarIcon? = null, // Accepts both vector and drawable
    onLeftIconClick: () -> Unit = {},
    rightIcon1: ToolbarIcon? = null, // Accepts both vector and drawable
    onRightIcon1Click: () -> Unit = {},
    rightIcon1Badge: Int? = null,
    rightIcon2: ToolbarIcon? = null, // Accepts both vector and drawable
    onRightIcon2Click: () -> Unit = {},
    rightIcon2Badge: Int? = null,
    backgroundColor: Color = Color.White,
    contentColor: Color = Color.Black
) {
    Surface(
        shadowElevation = 4.dp,
        color = backgroundColor,
        modifier = modifier
    ) {
        Column {
            // MAIN TOOLBAR ROW
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(toolbarHeight)
                    .padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Back Button or Custom Left Icon
                if (showBack) {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back),
                            tint = contentColor
                        )
                    }
                } else if (leftIcon != null) {
                    IconButton(onClick = onLeftIconClick) {
                        ToolbarIconRenderer(icon = leftIcon, tint = contentColor, contentDescription = "Left Action")
                    }
                } else {
                    Spacer(modifier = Modifier.width(48.dp))
                }

                // Title + Subtitle Column
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp)
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold, fontSize = 18.sp),
                        color = contentColor,
                        maxLines = 1
                    )

                    subtitle?.let {
                        Text(
                            text = it,
                            modifier = Modifier.graphicsLayer { alpha = subtitleAlpha },
                            style = MaterialTheme.typography.bodySmall,
                            color = contentColor.copy(alpha = 0.6f),
                            maxLines = 1
                        )
                    }
                }

                // Right Icon 1 with Badge Support
                rightIcon1?.let { icon ->
                    Box(modifier = Modifier.padding(end = 4.dp)) {
                        IconButton(onClick = onRightIcon1Click) {
                            ToolbarIconRenderer(icon = icon, tint = contentColor, contentDescription = "Right Action 1")
                        }
                        ToolbarBadge(badgeCount = rightIcon1Badge, modifier = Modifier.align(Alignment.TopEnd))
                    }
                }

                // Right Icon 2 with Badge Support
                rightIcon2?.let { icon ->
                    Box {
                        IconButton(onClick = onRightIcon2Click) {
                            ToolbarIconRenderer(icon = icon, tint = contentColor, contentDescription = "Right Action 2")
                        }
                        ToolbarBadge(badgeCount = rightIcon2Badge, modifier = Modifier.align(Alignment.TopEnd))
                    }
                }
            }

            // Meta Rows (Metadata info chips)
            meta?.let {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .graphicsLayer { alpha = metaAlpha }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 56.dp, end = 16.dp, bottom = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        meta.split("•").forEach { item ->
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = contentColor.copy(alpha = 0.06f)
                            ) {
                                Text(
                                    text = item.trim(),
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Medium, fontSize = 11.sp),
                                    color = contentColor.copy(alpha = 0.8f)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * Shared Dynamic Renderer component dealing with Vector / Drawable wrapper variations cleanly.
 */
@Composable
private fun ToolbarIconRenderer(
    icon: ToolbarIcon,
    tint: Color,
    contentDescription: String
) {
    when (icon) {
        is ToolbarIcon.Vector -> {
            Icon(
                imageVector = icon.imageVector,
                contentDescription = contentDescription,
                tint = tint
            )
        }
        is ToolbarIcon.Drawable -> {
            Icon(
                painter = painterResource(id = icon.resId),
                contentDescription = contentDescription,
                tint = tint
            )
        }
    }
}

/**
 * Shared Badge Layout used for action indicators.
 */
@Composable
private fun ToolbarBadge(badgeCount: Int?, modifier: Modifier = Modifier) {
    if (badgeCount != null && badgeCount > 0) {
        Surface(
            modifier = modifier.size(18.dp),
            shape = CircleShape,
            color = Color(0xFFD32F2F)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = if (badgeCount > 99) "99+" else badgeCount.toString(),
                    color = Color.White,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

sealed class ToolbarIcon {
    data class Vector(val imageVector: ImageVector) : ToolbarIcon()
    data class Drawable(val resId: Int) : ToolbarIcon()
}

@Preview(
    name = "Ramadan Toolbar – Dua", showBackground = true
)
@Composable
fun RamadanToolbarPreview() {
    MaterialTheme {
    }
}
