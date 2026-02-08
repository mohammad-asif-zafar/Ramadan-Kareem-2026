package com.hathway.ramadankareem2026.ui.dua.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hathway.ramadankareem2026.core.localization.LocalizationManager
import com.hathway.ramadankareem2026.ui.dua.model.DuaItem
import com.hathway.ramadankareem2026.ui.dua.model.LocalizedDuaText
import com.hathway.ramadankareem2026.ui.theme.RamadanKareemTheme

@Composable
fun DuaItemCard(
    dua: DuaItem,
    onClick: (() -> Unit)? = null
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .then(
                if (onClick != null) Modifier.clickable { onClick() }
                else Modifier
            ),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            // Arabic
            Text(
                text = dua.arabic,
                style = MaterialTheme.typography.headlineSmall
            )

            // Transliteration
            dua.transliteration.takeIf { it.isNotBlank() }?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Title (localized)
            Text(
                text = dua.title.asString(),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )

            // Translation (localized)
            Text(
                text = dua.translation.asString(),
                style = MaterialTheme.typography.bodyMedium
            )

            // Source
            Text(
                text = dua.source,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DuaItemCardPreview_Clickable() {
    RamadanKareemTheme {
        DuaItemCard(
            dua = DuaItem(
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
                    hindi = "मैं कल रमज़ान के महीने में रोज़ा रखने की नियत करता हूँ।",
                    urdu = "میں کل رمضان کے مہینے میں روزہ رکھنے کی نیت کرتا ہوں۔",
                    malaysian = "Saya berniat untuk berpuasa esok dalam bulan Ramadan."
                ),
                source = "Fiqh"
            ),
            onClick = {}
        )
    }
}

