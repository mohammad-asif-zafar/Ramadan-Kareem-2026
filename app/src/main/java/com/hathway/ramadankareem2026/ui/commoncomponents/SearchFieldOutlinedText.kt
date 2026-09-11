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
import androidx.compose.material3.MaterialTheme
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
    val colorScheme = MaterialTheme.colorScheme

    BasicTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp)
            .background(
                color = colorScheme.surfaceVariant.copy(alpha = 0.5f),
                shape = RoundedCornerShape(26.dp)
            )
            .border(
                width = 1.dp, 
                color = colorScheme.outline.copy(alpha = 0.2f),
                shape = RoundedCornerShape(26.dp)
            ),
        singleLine = true,
        textStyle = TextStyle(
            color = colorScheme.onSurface, 
            fontSize = 15.sp, 
            fontWeight = FontWeight.Normal
        ),
        cursorBrush = SolidColor(colorScheme.primary),
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
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(20.dp)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Box(modifier = Modifier.weight(1f)) {
                    if (query.isEmpty()) {
                        Text(
                            text = placeholderText,
                            color = colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Normal
                        )
                    }
                    innerTextField()
                }

                if (query.isNotEmpty()) {
                    Icon(
                        imageVector = Icons.Default.Search, // Should probably be a 'clear' icon if query is not empty, but staying consistent with original for now
                        contentDescription = "Search button",
                        tint = colorScheme.primary,
                        modifier = Modifier
                            .size(20.dp)
                            .clickable {
                                onSearchAction()
                                keyboardController?.hide()
                            }
                    )
                }
            }
        })
}
