package com.hathway.ramadankareem2026.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.hathway.ramadankareem2026.R

@Composable
fun RamadanToolbar(
    title: String,
    showBack: Boolean = true,
    onBackClick: () -> Unit = {},
    rightIcon1: ImageVector? = null,
    onRightIcon1Click: () -> Unit = {},
    rightIcon2: ImageVector? = null,
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

            // 🔙 Back Button
            if (showBack) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = contentColor
                    )
                }
            } else {
                Spacer(modifier = Modifier.width(48.dp))
            }

            // 📝 Title
            Text(
                text = stringResource(R.string.feature_dua),
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp),
                style = MaterialTheme.typography.titleLarge,
                color = contentColor
            )

            // 🔔 Right Icon 1
            rightIcon1?.let {
                IconButton(onClick = onRightIcon1Click) {
                    Icon(
                        imageVector = it, contentDescription = "Action 1", tint = contentColor
                    )
                }
            }

            // ⚙️ Right Icon 2
            rightIcon2?.let {
                IconButton(onClick = onRightIcon2Click) {
                    Icon(
                        imageVector = it, contentDescription = "Action 2", tint = contentColor
                    )
                }
            }
        }
    }
}
