package com.hathway.ramadankareem2026.ui.dua.components

import android.annotation.SuppressLint
import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.VolumeUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.NotificationManagerCompat
import com.hathway.ramadankareem2026.R
import com.hathway.ramadankareem2026.core.service.DuaTtsNotification
import com.hathway.ramadankareem2026.core.service.DuaTtsService
import com.hathway.ramadankareem2026.core.tts.TtsActions
import com.hathway.ramadankareem2026.ui.components.RamadanToolbar
import com.hathway.ramadankareem2026.ui.dua.model.DuaItem

@SuppressLint("MissingPermission")
@Composable
fun DuaDetailScreen(
    dua: DuaItem, onBack: () -> Unit
) {
    Scaffold(

        /*  Top App Bar */
        topBar = {
            RamadanToolbar(
                title = dua.categoryId, showBack = true, onBackClick = onBack,
                // Saved
                rightIcon1 = R.drawable.ic_saved, onRightIcon1Click = { },

                //  Notification  icon
                rightIcon2 = R.drawable.bell, onRightIcon2Click = { })

        },

        /*  Bottom action bar (Save / Share / Audio etc.) */
        bottomBar = {
            DuaActionBar(dua)
        }

    ) { padding ->

        val context = LocalContext.current

        IconButton(onClick = {
            val intent = Intent(context, DuaTtsService::class.java).apply {
                action = TtsActions.READ
                putExtra(TtsActions.EXTRA_TEXT, dua.arabic) // ✅ ONLY ARABIC
            }
            context.startService(intent)

            val notification = DuaTtsNotification.build(
                context = context, isPlaying = false
            )
            NotificationManagerCompat.from(context)
                .notify(DuaTtsNotification.NOTIFICATION_ID, notification)
        }) {
            Icon(Icons.AutoMirrored.Outlined.VolumeUp, contentDescription = "Read Dua")
        }


        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            item {
                Spacer(modifier = Modifier.height(4.dp))
            }
            item {
                Text(
                    text = dua.title,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
            }

            item {
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant
                ) {
                    Text(
                        text = dua.arabic,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        style = MaterialTheme.typography.headlineMedium,
                        textAlign = TextAlign.Center,
                        lineHeight = 44.sp
                    )
                }
            }

            if (dua.transliteration.isNotBlank()) {
                item {
                    Text(
                        text = dua.transliteration,
                        style = MaterialTheme.typography.bodyMedium,
                        fontStyle = FontStyle.Italic,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            if (dua.translation.isNotBlank()) {
                item {
                    Text(
                        text = dua.translation,
                        style = MaterialTheme.typography.bodyLarge,
                        lineHeight = 22.sp
                    )
                }
            }

            item {
                Text(
                    text = dua.source,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            item {
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
}


@Preview(
    name = "Dua Detail Screen", showBackground = true, device = Devices.PIXEL_6
)
@Composable
fun DuaDetailScreenPreview() {
    MaterialTheme {
        DuaDetailScreen(
            dua = DuaItem(
                id = "1",
                title = "Ramadan Moon Sighting Duʿāʾ",
                arabic = "اللَّهُمَّ أَهْلِلْهُ عَلَيْنَا بِالْيُمْنِ وَالإِيمَانِ",
                transliteration = "Allahumma ahlilhu ‘alaynā bil-yumni wal-īmān",
                translation = "O Allah, bring it upon us with blessings and faith.",
                source = "Tirmidhi",
                categoryId = "ramadan"
            ), onBack = {})
    }
}
