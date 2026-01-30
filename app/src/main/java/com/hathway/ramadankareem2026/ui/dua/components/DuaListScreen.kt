package com.hathway.ramadankareem2026.ui.dua.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hathway.ramadankareem2026.ui.components.RamadanToolbar
import com.hathway.ramadankareem2026.ui.dua.model.DuaItem

/**
 * Screen that displays a list of Duʿās.
 *
 * Used for:
 * - Duʿā category details
 * - All Duʿās screen
 *
 * Structure:
 */
@Composable
fun DuaListScreen(
    title: String,             // Screen title (e.g. "Morning Duʿās")
    duas: List<DuaItem>,       // List of duʿās to display
    onBack: () -> Unit         // Back navigation action
) {

    /**
     * Scaffold provides basic screen structure:
     * - topBar → Toolbar
     * - content → Body area
     */
    Scaffold(
        topBar = {

            /**
             * Custom Ramadan toolbar.
             * showBack = true → shows back arrow
             */
            RamadanToolbar(
                title = title, showBack = true, onBackClick = onBack
            )
        }) { padding ->

        /**
         * LazyColumn for efficient vertical scrolling lists.
         * Only visible items are composed.
         */
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)              // Handles Scaffold insets
                .padding(horizontal = 16.dp), // Screen side padding
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // Small top spacer for breathing room below toolbar
            item {
                Spacer(modifier = Modifier.height(4.dp))
            }

            /**
             * Display each duʿā as a card.
             */
            items(duas) { dua ->
                DuaItemCard(dua = dua)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DuaListScreenPreview() {

    // Fake preview data
    val previewDuas = listOf(
        DuaItem(
            id = "sehri",
            categoryId = "ramadan",
            title = "Intention for Fasting (Sehri)",
            arabic = "وَبِصَوْمِ غَدٍ نَوَيْتُ مِنْ شَهْرِ رَمَضَانَ",
            transliteration = "Wa bisawmi ghadin nawaitu min shahri Ramaḍān",
            translation = "I intend to keep the fast for tomorrow in the month of Ramadan.",
            source = "Fiqh"
        ), DuaItem(
            id = "iftar",
            categoryId = "ramadan",
            title = "Duʿāʾ at Iftar",
            arabic = "ذَهَبَ الظَّمَأُ وَابْتَلَّتِ الْعُرُوقُ وَثَبَتَ الْأَجْرُ",
            transliteration = "Dhahabaẓ-ẓama’u wabtallatil-‘urūq wa thabatal-ajru",
            translation = "The thirst has gone, the veins are moistened, and the reward is assured.",
            source = "Abu Dawood"
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

    DuaListScreen(
        title = "Duʿāʾs", duas = previewDuas, onBack = {})
}
