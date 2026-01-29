package com.hathway.ramadankareem2026.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hathway.ramadankareem2026.R
import com.hathway.ramadankareem2026.core.util.capitalizeFirst

@Composable
fun RamadanToolbar(
    title: String,
    showBack: Boolean = true,
    onBackClick: () -> Unit = {},
    leftIcon: Int? = null,
    onLeftIconClick: () -> Unit = {},
    rightIcon1: Int? = null,
    onRightIcon1Click: () -> Unit = {},
    rightIcon1Badge: Int? = null,
    rightIcon2: Int? = null,
    onRightIcon2Click: () -> Unit = {},
    backgroundColor: Color = Color.White,
    contentColor: Color = Color.Black
) {
    Surface(
        shadowElevation = 6.dp, color = backgroundColor
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // 🔙 Back button or Left icon
            if (showBack) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = contentColor
                    )
                }
            } else if (leftIcon != null) {
                IconButton(onClick = onLeftIconClick) {
                    Icon(
                        painter = painterResource(leftIcon),
                        contentDescription = "Left Action",
                        tint = contentColor
                    )
                }
            } else {
                Spacer(modifier = Modifier.width(48.dp))
            }

            // 📝 Title
            Text(
                text = title.capitalizeFirst(),
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp),
                style = MaterialTheme.typography.titleLarge,
                color = contentColor
            )

            // ⭐ Right icon 1 (e.g. Favorite) with badge
            rightIcon1?.let {
                Box {
                    IconButton(onClick = onRightIcon1Click) {
                        Icon(
                            painter = painterResource(it),
                            contentDescription = "Action 1",
                            tint = contentColor
                        )
                    }
                    // Badge
                    rightIcon1Badge?.let { badgeCount ->
                        if (badgeCount > 0) {
                            Surface(
                                modifier = Modifier
                                    .size(20.dp)
                                    .clip(CircleShape)
                                    .align(Alignment.TopEnd),
                                color = Color.Red
                            ) {
                                Box(
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = if (badgeCount > 99) "99+" else badgeCount.toString(),
                                        color = Color.White,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // 🔔 Right icon 2 (e.g. Notification)
            rightIcon2?.let {
                IconButton(onClick = onRightIcon2Click) {
                    Icon(
                        painter = painterResource(it),
                        contentDescription = "Action 2",
                        tint = contentColor
                    )
                }
            }
        }
    }
}


@Preview(
    name = "Ramadan Toolbar – Dua", showBackground = true
)
@Composable
fun RamadanToolbarPreview() {
    MaterialTheme {
        RamadanToolbar(
            title = "Dua",
            showBack = true,
            rightIcon1 = R.drawable.ic_favorite_outline,
            rightIcon2 = R.drawable.ic_notification,
            onBackClick = {},
            onRightIcon1Click = {},
            onRightIcon2Click = {})
    }
}
