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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.hathway.ramadankareem2026.R
import com.hathway.ramadankareem2026.ui.components.RamadanToolbar
import com.hathway.ramadankareem2026.ui.dua.data.DuaRepository
import com.hathway.ramadankareem2026.ui.dua.model.DuaItem
import com.hathway.ramadankareem2026.ui.dua.model.LocalizedDuaText
import com.hathway.ramadankareem2026.ui.navigation.Routes
import com.hathway.ramadankareem2026.ui.theme.RamadanKareemTheme

/**
 *  Screen that shows all Duʿās for a selected category
 *
 * Example:
 *  - Qur’an Duʿās
 *  - Ramadan Duʿās
 *  - Forgiveness Duʿās
 */
@Composable
fun DuaCategoryScreen(
    categoryId: String,          //  Category passed from navigation
    navController: NavController // For back + detail navigation
) {

    //  Repository instance (simple data source for now)
    val repository = remember { DuaRepository() }

    //  Fetch duʿās only for this category
    val duas = remember(categoryId) {
        repository.getDuasByCategory(categoryId)
    }

    //  Screen structure
    Scaffold(

        // Top toolbar with back button
        topBar = {
            RamadanToolbar(
                title = categoryId, showBack = true, onBackClick = { navController.popBackStack() })
        }

    ) { padding ->

        //  Scrollable list of Duʿās
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)      // Respect Scaffold padding
                .padding(16.dp),       // Screen spacing
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(4.dp))
            }

            //  Each Duʿā rendered as a modern card
            items(duas) { dua ->

                DuaCardItem(
                    title = dua.title.asString(),
                    subtitle = dua.source, // e.g. "Qur’an 2:128"

                    //  Navigate to Duʿā detail screen
                    onClick = {
                        navController.navigate(
                            "${Routes.DUA_DETAIL}/${dua.id}"
                        )
                    })
            }
            item {
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}


@Preview(
    name = "Dua Category Screen",
    device = Devices.PIXEL_6,
    showBackground = true
)
@Composable
fun DuaCategoryScreenPreview() {

    val previewDuas = listOf(
        DuaItem(
            id = "1",
            categoryId = "ramadan",
            title = LocalizedDuaText(
                english = "Ramadan Moon Sighting",
                hindi = "रमज़ान के चाँद की दुआ",
                urdu = "رمضان کے چاند کی دعا",
                malaysian = "Doa Melihat Bulan Ramadan"
            ),
            arabic = "اللَّهُمَّ أَهْلِلْهُ عَلَيْنَا",
            transliteration = "Allahumma ahlilhu ‘alaynā",
            translation = LocalizedDuaText(
                english = "O Allah, bring it upon us with blessings and faith.",
                hindi = "ऐ अल्लाह, इसे हमारे लिए बरकत और ईमान के साथ लाएँ।",
                urdu = "اے اللہ، اسے ہمارے لیے برکت اور ایمان کے ساتھ لائے۔",
                malaysian = "Wahai Allah, bawakan ia kepada kami dengan keberkatan dan iman."
            ),
            source = "Ramadan Duʿā"
        )
    )

    RamadanKareemTheme {
        LazyColumn(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(previewDuas) { dua ->
                DuaItemCard(
                    dua = dua,
                    onClick = {}
                )
            }
        }
    }
}

