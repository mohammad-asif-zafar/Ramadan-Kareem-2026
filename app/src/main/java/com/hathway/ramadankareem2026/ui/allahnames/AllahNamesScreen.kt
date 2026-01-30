package com.hathway.ramadankareem2026.ui.allahnames

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hathway.ramadankareem2026.R
import com.hathway.ramadankareem2026.ui.allahnames.components.AllahNameCard
import com.hathway.ramadankareem2026.ui.allahnames.domain.model.AllahName
import com.hathway.ramadankareem2026.ui.components.RamadanToolbar

@Composable
fun AllahNamesScreen(
    names: List<AllahName>, onBack: () -> Unit, onNameClick: (AllahName) -> Unit
) {
    Scaffold(
        topBar = {
            RamadanToolbar(
                title = stringResource(R.string.allah_name), showBack = true, onBackClick = onBack
            )
        }, containerColor = MaterialTheme.colorScheme.background
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {
            items(names) { name ->
                AllahNameCard(
                    name = name, onClick = { onNameClick(name) })
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AllahNamesScreenPreview() {
    MaterialTheme {
        AllahNamesScreen(
            names = listOf(
            AllahName(
                id = 1,
                arabic = "الرَّحْمٰنُ",
                transliteration = "Ar-Rahman",
                english = "The Most Merciful",
                meaning = "The One who has plenty of mercy for the believers."
            ), AllahName(
                id = 2,
                arabic = "الرَّحِيمُ",
                transliteration = "Ar-Raheem",
                english = "The Most Compassionate",
                meaning = "The One who bestows mercy upon the believers."
            ), AllahName(
                id = 3,
                arabic = "الْمَلِكُ",
                transliteration = "Al-Malik",
                english = "The King",
                meaning = "The Absolute Ruler of the universe."
            )
        ), onBack = {}, onNameClick = {})
    }
}

@Preview(
    showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun AllahNamesScreenDarkPreview() {
    MaterialTheme {
        AllahNamesScreen(
            names = listOf(
            AllahName(
                id = 55,
                arabic = "الرَّحْمٰنُ",
                transliteration = "Ar-Rahman",
                english = "The Most Merciful",
                meaning = "The One who has plenty of mercy for the believers."
            )
        ), onBack = {}, onNameClick = {})
    }
}
