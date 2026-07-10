package com.hathway.ramadankareem2026.ui.commoncomponents

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SearchFieldOutlinedText(
    query: String,
    onQueryChange: (String) -> Unit,
    placeholderText: String,
    modifier: Modifier = Modifier,
    onSearchAction: () -> Unit = {}
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    BasicTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp) // Exact height matching the compact design ratio
            .background(
                color = Color(0xFFF9F9F8), // Subtle, off-white container fill color
                shape = RoundedCornerShape(23.dp)
            )
            .border(
                width = 1.dp, color = Color(0xFFEFEFEF), // Very light soft boundary gray outline
                shape = RoundedCornerShape(23.dp)
            ),
        singleLine = true,
        textStyle = TextStyle(
            color = Color(0xFF2C2C2C), fontSize = 15.sp, fontWeight = FontWeight.Normal
        ),
        cursorBrush = SolidColor(Color(0xFF1B3D36)),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearchAction()
                keyboardController?.hide()
            }),
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Left Icon: Thin light placeholder search graphic
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = Color(0xFF9EA0A2), // Muted slate gray
                    modifier = Modifier.size(18.dp)
                )

                Spacer(modifier = Modifier.width(10.dp))

                // Input field + Placeholder stacking logic
                Box(modifier = Modifier.weight(1f)) {
                    if (query.isEmpty()) {
                        Text(
                            text = placeholderText,
                            color = Color(0xFF757779), // Medium-neutral gray matching image text contrast
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Normal
                        )
                    }
                    innerTextField()
                }

                Spacer(modifier = Modifier.width(10.dp))

                // Right Icon: High contrast dark search action button
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search button",
                    tint = Color(0xFF2C2E30), // Dark charcoal gray icon matching layout target
                    modifier = Modifier
                        .size(18.dp)
                        .clickable(enabled = query.isNotEmpty()) {
                            onSearchAction()
                            keyboardController?.hide()
                        })
            }
        })
}