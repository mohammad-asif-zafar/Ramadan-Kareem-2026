package com.hathway.ramadankareem2026.ui.dua.components


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hathway.ramadankareem2026.ui.dua.model.DuaItem
import com.hathway.ramadankareem2026.ui.dua.model.LocalizedDuaText

/**
 * Displays a horizontally scrollable list of Duʿā cards.
 *
 * Used on:
 * - Home screen (Ramadan highlights)
 * - Featured / recommended duʿās section
 */
@Composable
fun RamadanDuaHorizontal(
    duas: List<DuaItem>,
    onClick: (DuaItem) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {

        // ✅ Stable key improves scroll + recomposition performance
        items(
            items = duas,
            key = { it.id }
        ) { dua ->
            RamadanDuaCard(
                dua = dua,
                onClick = { onClick(dua) }
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun RamadanDuaHorizontalPreview() {

    val previewDuas = listOf(
        DuaItem(
            id = "laylat_qadr_1",
            categoryId = "ramadan",
            title = LocalizedDuaText(
                english = "Laylat al-Qadr",
                hindi = "लैलतुल क़द्र",
                urdu = "لیلة القدر",
                malaysian = "Lailatul Qadar"
            ),
            arabic = "اللَّهُمَّ إِنَّكَ عَفُوٌّ تُحِبُّ الْعَفْوَ فَاعْفُ عَنِّي",
            transliteration = "Allahumma innaka ‘afuwwun tuḥibbul-‘afwa fa‘fu ‘annī",
            translation = LocalizedDuaText(
                english = "O Allah, You are Most Forgiving, and You love forgiveness, so forgive me.",
                hindi = "ऐ अल्लाह, आप क्षमाशील हैं और क्षमा पसंद करते हैं, मुझे क्षमा करें।",
                urdu = "اے اللہ، تو معاف کرنے والا ہے اور معافی کو پسند کرتا ہے، مجھے معاف فرما۔",
                malaysian = "Wahai Allah, Engkau Maha Pengampun dan menyukai keampunan, maka ampunilah aku."
            ),
            source = "Tirmidhi"
        ),
        DuaItem(
            id = "laylat_qadr_2",
            categoryId = "ramadan",
            title = LocalizedDuaText(
                english = "Laylat al-Qadr",
                hindi = "लैलतुल क़द्र",
                urdu = "لیلة القدر",
                malaysian = "Lailatul Qadar"
            ),
            arabic = "اللَّهُمَّ إِنَّكَ عَفُوٌّ تُحِبُّ الْعَفْوَ فَاعْفُ عَنِّي",
            transliteration = "Allahumma innaka ‘afuwwun tuḥibbul-‘afwa fa‘fu ‘annī",
            translation = LocalizedDuaText(
                english = "O Allah, You are Most Forgiving, and You love forgiveness, so forgive me.",
                hindi = "ऐ अल्लाह, आप क्षमाशील हैं और क्षमा पसंद करते हैं, मुझे क्षमा करें।",
                urdu = "اے اللہ، تو معاف کرنے والا ہے اور معافی کو پسند کرتا ہے، مجھے معاف فرما۔",
                malaysian = "Wahai Allah, Engkau Maha Pengampun dan menyukai keampunan, maka ampunilah aku."
            ),
            source = "Tirmidhi"
        )
    )

    RamadanDuaHorizontal(
        duas = previewDuas,
        onClick = {}
    )
}


