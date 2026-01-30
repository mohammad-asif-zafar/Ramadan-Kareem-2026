package com.hathway.ramadankareem2026.ui.dua.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hathway.ramadankareem2026.ui.dua.model.DuaItem

/**
 * Displays a horizontally scrollable list of Duʿā cards.
 *
 * Used on:
 * - Home screen (Ramadan highlights)
 * - Featured / recommended duʿās section
 *
 */
@Composable
fun RamadanDuaHorizontal(
    duas: List<DuaItem>,              // List of duʿās to display
    onClick: (DuaItem) -> Unit        // Click callback for each duʿā
) {

    /**
     * LazyRow is the horizontal version of LazyColumn.
     * Only visible items are composed → efficient scrolling.
     */
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp), // Space between cards
        contentPadding = PaddingValues(horizontal = 16.dp)   // Padding on screen edges
    ) {

        /**
         * Each duʿā is rendered as a RamadanDuaCard.
         */
        items(duas) { dua ->
            RamadanDuaCard(dua = dua, onClick = { onClick(dua) } // Forward clicked item
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RamadanDuaHorizontalPreview() {

    // Fake data for preview

    val previewDuas = listOf(
        DuaItem(
            id = "laylat_qadr",
            categoryId = "ramadan",
            title = "Laylat al-Qadr",
            arabic = "اللَّهُمَّ إِنَّكَ عَفُوٌّ تُحِبُّ الْعَفْوَ فَاعْفُ عَنِّي",
            transliteration = "Allahumma innaka ‘afuwwun tuḥibbul-‘afwa fa‘fu ‘annī",
            translation = "O Allah, You are Most Forgiving, and You love forgiveness, so forgive me.",
            source = "Tirmidhi"
        ), DuaItem(
            id = "laylat_qadr",
            categoryId = "ramadan",
            title = "Laylat al-Qadr",
            arabic = "اللَّهُمَّ إِنَّكَ عَفُوٌّ تُحِبُّ الْعَفْوَ فَاعْفُ عَنِّي",
            transliteration = "Allahumma innaka ‘afuwwun tuḥibbul-‘afwa fa‘fu ‘annī",
            translation = "O Allah, You are Most Forgiving, and You love forgiveness, so forgive me.",
            source = "Tirmidhi"
        ), DuaItem(
            id = "laylat_qadr",
            categoryId = "ramadan",
            title = "Laylat al-Qadr",
            arabic = "اللَّهُمَّ إِنَّكَ عَفُوٌّ تُحِبُّ الْعَفْوَ فَاعْفُ عَنِّي",
            transliteration = "Allahumma innaka ‘afuwwun tuḥibbul-‘afwa fa‘fu ‘annī",
            translation = "O Allah, You are Most Forgiving, and You love forgiveness, so forgive me.",
            source = "Tirmidhi"
        )
    )

    RamadanDuaHorizontal(
        duas = previewDuas, onClick = {})
}

