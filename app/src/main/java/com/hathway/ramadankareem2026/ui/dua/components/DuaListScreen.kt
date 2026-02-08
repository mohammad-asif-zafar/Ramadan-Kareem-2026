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
import com.hathway.ramadankareem2026.ui.dua.model.LocalizedDuaText

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

    val previewDuas = listOf(
        DuaItem(
            id = "sehri",
            categoryId = "ramadan",
            title = LocalizedDuaText(
                english = "Intention for Fasting (Sehri)",
                hindi = "रोज़े की नियत (सेहरी)",
                urdu = "روزے کی نیت (سحری)",
                malaysian = "Niat Berpuasa (Sahur)"
            ),
            arabic = "وَبِصَوْمِ غَدٍ نَوَيْتُ مِنْ شَهْرِ رَمَضَانَ",
            transliteration = "Wa bisawmi ghadin nawaitu min shahri Ramaḍān",
            translation = LocalizedDuaText(
                english = "I intend to keep the fast for tomorrow in the month of Ramadan.",
                hindi = "मैं रमज़ान के महीने में कल रोज़ा रखने की नियत करता हूँ।",
                urdu = "میں رمضان کے مہینے میں کل روزہ رکھنے کی نیت کرتا ہوں۔",
                malaysian = "Saya berniat berpuasa esok dalam bulan Ramadan."
            ),
            source = "Fiqh"
        ),

        DuaItem(
            id = "iftar",
            categoryId = "ramadan",
            title = LocalizedDuaText(
                english = "Duʿāʾ at Iftar",
                hindi = "इफ्तार की दुआ",
                urdu = "افطار کی دعا",
                malaysian = "Doa Berbuka Puasa"
            ),
            arabic = "ذَهَبَ الظَّمَأُ وَابْتَلَّتِ الْعُرُوقُ وَثَبَتَ الْأَجْرُ",
            transliteration = "Dhahabaẓ-ẓama’u wabtallatil-‘urūq wa thabatal-ajru",
            translation = LocalizedDuaText(
                english = "The thirst has gone, the veins are moistened, and the reward is assured.",
                hindi = "प्यास बुझ गई, नसें तर हो गईं और सवाब साबित हो गया।",
                urdu = "پیاس بجھ گئی، رگیں تر ہو گئیں اور اجر ثابت ہو گیا۔",
                malaysian = "Dahaga telah hilang, urat-urat menjadi basah, dan pahala telah ditetapkan."
            ),
            source = "Abu Dawood"
        ),

        DuaItem(
            id = "laylat_qadr",
            categoryId = "ramadan",
            title = LocalizedDuaText(
                english = "Laylat al-Qadr",
                hindi = "लैलतुल क़द्र",
                urdu = "لیلۃ القدر",
                malaysian = "Lailatul Qadr"
            ),
            arabic = "اللَّهُمَّ إِنَّكَ عَفُوٌّ تُحِبُّ الْعَفْوَ فَاعْفُ عَنِّي",
            transliteration = "Allahumma innaka ‘afuwwun tuḥibbul-‘afwa fa‘fu ‘annī",
            translation = LocalizedDuaText(
                english = "O Allah, You are Most Forgiving, and You love forgiveness, so forgive me.",
                hindi = "ऐ अल्लाह, आप क्षमाशील हैं और क्षमा को पसंद करते हैं, तो मुझे क्षमा करें।",
                urdu = "اے اللہ، تو بہت معاف کرنے والا ہے اور معافی کو پسند کرتا ہے، پس مجھے معاف فرما۔",
                malaysian = "Wahai Allah, Engkau Maha Pengampun dan menyukai keampunan, maka ampunkanlah aku."
            ),
            source = "Tirmidhi"
        )
    )

    DuaListScreen(
        title = "Duʿāʾs", // fine for preview
        duas = previewDuas,
        onBack = {}
    )
}

