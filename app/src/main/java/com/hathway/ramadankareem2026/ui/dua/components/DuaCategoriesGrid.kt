package com.hathway.ramadankareem2026.ui.dua.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MedicalServices
import androidx.compose.material.icons.outlined.Mosque
import androidx.compose.material.icons.outlined.People
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hathway.ramadankareem2026.ui.dua.model.DuaCategory

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DuaCategoriesGrid(
    categories: List<DuaCategory>, onClick: (DuaCategory) -> Unit
) {
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        maxItemsInEachRow = 2,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        categories.forEach { category ->
            DuaCategoryCard(
                modifier = Modifier.weight(1f),
                category = category,
                onClick = { onClick(category) })
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DuaCategoriesGridPreview() {
    MaterialTheme {
        DuaCategoriesGrid(
            categories = listOf(
                DuaCategory(
                    id = "faith",
                    title = "Faith & Guidance",
                    subtitle = "Strengthen īmān and guidance",
                    icon = Icons.Outlined.Mosque
                ), DuaCategory(
                    id = "family",
                    title = "Family & Children",
                    subtitle = "Duʿāʾs for parents & offspring",
                    icon = Icons.Outlined.People
                )
            ), onClick = {})
    }
}

@Preview(
    showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun DuaCategoriesGridDarkPreview() {
    MaterialTheme {
        DuaCategoriesGrid(
            categories = listOf(
                DuaCategory(
                    id = "faith",
                    title = "Faith & Guidance",
                    subtitle = "Strengthen īmān and guidance",
                    icon = Icons.Outlined.Mosque
                ), DuaCategory(
                    id = "family",
                    title = "Family & Children",
                    subtitle = "Duʿāʾs for parents & offspring",
                    icon = Icons.Outlined.People
                ), DuaCategory(
                    id = "health",
                    title = "Health & Healing",
                    subtitle = "Duʿāʾs for cure & wellbeing",
                    icon = Icons.Outlined.MedicalServices
                )
            ), onClick = {})
    }
}

