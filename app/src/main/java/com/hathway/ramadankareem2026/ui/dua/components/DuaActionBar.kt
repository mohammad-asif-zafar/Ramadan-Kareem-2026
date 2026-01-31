package com.hathway.ramadankareem2026.ui.dua.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.VolumeUp
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.StopCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hathway.ramadankareem2026.R
import com.hathway.ramadankareem2026.ui.dua.model.DuaItem
import com.hathway.ramadankareem2026.ui.dua.viewmodel.DuaTtsViewModel
import com.hathway.ramadankareem2026.ui.theme.RamadanKareemTheme

@Composable
fun DuaActionBar(
    dua: DuaItem, viewModel: DuaTtsViewModel = viewModel()
) {
    val isSpeaking by viewModel.isSpeaking

    Surface(tonalElevation = 3.dp) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            /*  Save */
            IconButton(onClick = { /* save */ }) {
                Icon(
                    Icons.Outlined.FavoriteBorder,
                    contentDescription = stringResource(R.string.save)
                )
            }

            /*  Text to Speech */
            IconButton(
                onClick = {
                    if (isSpeaking) {
                        viewModel.stop()
                    } else {
                        viewModel.speakArabicThenTranslation(
                            arabic = dua.arabic, translation = dua.translation
                        )
                    }
                }) {
                Icon(
                    imageVector = if (isSpeaking) Icons.Outlined.StopCircle
                    else Icons.AutoMirrored.Outlined.VolumeUp,
                    contentDescription = stringResource(R.string.read_dua),
                    modifier = Modifier.size(36.dp)
                )
            }

            /*  Share */
            IconButton(onClick = { /* share */ }) {
                Icon(Icons.Outlined.Share, contentDescription = stringResource(R.string.share))
            }
        }
    }
}


private val previewDua = DuaItem(
    id = "1",
    title = "Ramadan Moon Sighting",
    arabic = "اللَّهُمَّ أَهْلِلْهُ عَلَيْنَا بِالْيُمْنِ وَالإِيمَانِ",
    transliteration = "Allahumma ahlilhu ‘alaynā bil-yumni wal-īmān",
    translation = "O Allah, bring it upon us with blessings and faith",
    source = "Ramadan Duas",
    categoryId = "ramadan"
)

@Preview(
    name = "Dua Action Bar", showBackground = true, device = Devices.PIXEL_6
)
@Composable
fun DuaActionBarPreview() {
    RamadanKareemTheme {
        DuaActionBar(
            dua = previewDua
        )
    }
}