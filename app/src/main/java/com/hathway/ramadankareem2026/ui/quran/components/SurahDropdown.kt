package com.hathway.ramadankareem2026.ui.quran.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hathway.ramadankareem2026.R
import com.hathway.ramadankareem2026.ui.quran.domain.model.Surah
import com.hathway.ramadankareem2026.ui.quran.util.highlightText

@Composable
fun SurahDropdown(
    surahList: List<Surah>, selected: Surah?, onSelect: (Surah) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }

    val recentSurahs = remember { mutableStateListOf<Surah>() }

    val pinnedSurahIds = remember { setOf(1, 36, 55) }

    val filteredSurahs = remember(searchQuery, surahList) {
        surahList.filter {
            it.name.contains(searchQuery, true) || it.englishName.contains(
                searchQuery, true
            ) || it.id.toString() == searchQuery
        }
    }

    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    fun selectSurah(surah: Surah) {
        expanded = false
        onSelect(surah)

        if (!recentSurahs.contains(surah)) {
            recentSurahs.add(0, surah)
            if (recentSurahs.size > 3) recentSurahs.removeLast()
        }
    }

    Column {

        OutlinedCard(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    expanded = true
                    searchQuery = ""
                }) {
            Row(
                modifier = Modifier.padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = selected?.let {
                    "${it.id}. ${it.name} (${it.englishName})"
                } ?: "Select Surah")
                Icon(Icons.Default.KeyboardArrowDown, null)
            }
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth(0.95f)
        ) {

            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text(stringResource(R.string.search_surah_name_or_number)) },
                singleLine = true,
                keyboardActions = KeyboardActions(
                    onDone = {
                        filteredSurahs.firstOrNull()?.let {
                            selectSurah(it)
                        }
                    }),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )

            if (recentSurahs.isNotEmpty()) {
                Text(stringResource(R.string.recent), modifier = Modifier.padding(8.dp))
                recentSurahs.forEach {
                    DropdownMenuItem(
                        text = { Text(stringResource(R.string.surah_format, it.id, it.name)) },
                        onClick = { selectSurah(it) })
                }
            }

            Text(stringResource(R.string.pinned), modifier = Modifier.padding(8.dp))
            surahList.filter { it.id in pinnedSurahIds }.forEach {
                DropdownMenuItem(
                    text = { Text(stringResource(R.string.pinned_surah_format, it.id, it.name)) },
                    onClick = { selectSurah(it) })
            }

            HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)

            filteredSurahs.forEach { surah ->
                DropdownMenuItem(text = {
                    Column {
                        Text(
                            highlightText(
                                "${surah.id}. ${surah.name}",
                                searchQuery,
                                MaterialTheme.colorScheme.primary
                            )
                        )
                        Text(
                            highlightText(
                                surah.englishName, searchQuery, MaterialTheme.colorScheme.secondary
                            ), style = MaterialTheme.typography.bodySmall
                        )
                    }
                }, onClick = { selectSurah(surah) })
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SurahDropdownSearchPreview() {
    val surahs = listOf(
        Surah(
            1,
            "سُورَةُ ٱلْفَاتِحَةِ",
            "Al-Faatiha",
            numberOfAyahs = 7,
            englishNameTranslation = "Meccan"
        ), Surah(
            2,
            "سُورَةُ البَقَرَةِ",
            "Al-Baqara",
            numberOfAyahs = 286,
            englishNameTranslation = "Medinan"
        ), Surah(
            114, "سُورَةُ النَّاسِ", "An-Naas", numberOfAyahs = 6, englishNameTranslation = "Meccan"
        )
    )

    MaterialTheme {
        SurahDropdown(
            surahList = surahs, selected = surahs.first(), onSelect = {})
    }
}
