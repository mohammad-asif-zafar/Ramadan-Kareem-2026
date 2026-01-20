package com.hathway.ramadankareem2026.ui.dua.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.MenuBook
import androidx.compose.material.icons.outlined.*
import com.hathway.ramadankareem2026.ui.dua.model.DuaCategory
import com.hathway.ramadankareem2026.ui.dua.model.DuaItem

object DuaDataSource {

    val ramadanMoonDua = DuaItem(
        id = "ramadan_moon",
        categoryId = "ramadan",
        title = "For Ramadan Moon Sighting Duʿāʾ",
        arabic = "اللَّهُمَّ أَهْلِلْهُ عَلَيْنَا بِالْيُمْنِ وَالإِيمَانِ وَالسَّلاَمَةِ وَالإِسْلاَمِ رَبِّي وَرَبُّكَ اللَّهُ",
        transliteration = "Allahumma ahlilhu `alainā bil-yumni wal-iman, was-salamati wal-Islam, rabbi wa rabbuk Allah",
        translation = "O Allah, bring it over us with blessing and faith, and security and Islam. My Lord and your Lord is Allah.",
        source = "Tirmidhi"
    )


    // 📂 Categories
    val categories = listOf(
        DuaCategory(
            id = "ramadan",
            title = "Ramadan Duʿāʾs",
            subtitle = "Special supplications for Ramadan",
            icon = Icons.Outlined.NightsStay
        ), DuaCategory(
            id = "quran",
            title = "Duʿāʾs from Qur’an",
            subtitle = "Supplications revealed in the Qur’an",
            icon = Icons.AutoMirrored.Outlined.MenuBook
        ), DuaCategory(
            id = "prophets",
            title = "Duʿāʾs of Prophets",
            subtitle = "Supplications of Allah’s messengers",
            icon = Icons.Outlined.AutoStories
        ), DuaCategory(
            id = "daily",
            title = "Daily Duʿāʾs",
            subtitle = "Morning & evening supplications",
            icon = Icons.Outlined.WbSunny
        )
    )

    // 📜 Duas
    val duas = listOf(
        // 🌙 Ramadan Duas
        DuaItem(
            id = "ramadan_moon",
            categoryId = "ramadan",
            title = "For Ramadan Moon Sighting Duʿāʾ",
            arabic = "اللَّهُمَّ أَهِلَّهُ عَلَيْنَا بِالأَمْنِ وَالإِيمَانِ",
            transliteration = "Allahumma ahillahu ‘alayna bil-amni wal-iman",
            translation = "O Allah, let this moon appear over us with security and faith.",
            source = "Tirmidhi"
        ),

        DuaItem(
            id = "sehri",
            categoryId = "ramadan",
            title = "For Fasting – Sehri Duʿāʾ",
            arabic = "وَبِصَوْمِ غَدٍ نَوَيْتُ مِنْ شَهْرِ رَمَضَانَ",
            transliteration = "Wa bisawmi ghadin nawaitu min shahri Ramadan",
            translation = "I intend to keep the fast for tomorrow in the month of Ramadan.",
            source = "Fiqh Books"
        ),

        DuaItem(
            id = "iftar",
            categoryId = "ramadan",
            title = "For Breaking Fast – Iftar Duʿāʾ",
            arabic = "ذَهَبَ الظَّمَأُ وَابْتَلَّتِ الْعُرُوقُ",
            transliteration = "Dhahaba az-zama’u wabtallatil ‘urooq",
            translation = "The thirst is gone, the veins are moistened.",
            source = "Abu Dawud"
        ),

        DuaItem(
            id = "iftar_provider",
            categoryId = "ramadan",
            title = "For Someone Who Provides You Iftar",
            arabic = "أَفْطَرَ عِنْدَكُمُ الصَّائِمُونَ",
            transliteration = "Aftara ‘indakumus-sa’imoon",
            translation = "May those who fast break their fast with you.",
            source = "Abu Dawud"
        ),

        DuaItem(
            id = "first_ashra",
            categoryId = "ramadan",
            title = "For First 10 Days of Ramadan – 1st Ashra",
            arabic = "رَبِّ اغْفِرْ وَارْحَمْ",
            transliteration = "Rabbighfir warham",
            translation = "O my Lord, forgive and have mercy.",
            source = "Traditional"
        ),

        DuaItem(
            id = "second_ashra",
            categoryId = "ramadan",
            title = "For Second Ashra – 2nd Ashra Duʿāʾ",
            arabic = "أَسْتَغْفِرُ اللّٰهَ رَبِّي مِنْ كُلِّ ذَنْبٍ",
            transliteration = "Astaghfirullah Rabbi min kulli dhamb",
            translation = "I seek forgiveness from Allah for every sin.",
            source = "Traditional"
        ),

        DuaItem(
            id = "third_ashra",
            categoryId = "ramadan",
            title = "For Last 10 Days of Ramadan – 3rd Ashra",
            arabic = "اللَّهُمَّ أَجِرْنِي مِنَ النَّارِ",
            transliteration = "Allahumma ajirni min an-naar",
            translation = "O Allah, save me from the Fire.",
            source = "Traditional"
        ),

        DuaItem(
            id = "laylatul_qadr",
            categoryId = "ramadan",
            title = "For Laylatul Qadr",
            arabic = "اللَّهُمَّ إِنَّكَ عَفُوٌّ تُحِبُّ الْعَفْوَ",
            transliteration = "Allahumma innaka ‘afuwwun tuhibbul ‘afwa",
            translation = "O Allah, You are Most Forgiving, and You love forgiveness.",
            source = "Tirmidhi"
        ),
        DuaItem(
            id = "dua_1",
            categoryId = "ramadan",
            arabic = "اللَّهُمَّ بَارِكْ لَنَا فِي رَمَضَان",
            transliteration = "Allahumma bārik lanā fī Ramaḍān",
            translation = "O Allah, bless us in Ramadan",
            source = "Reported Duʿāʾ"
        ),

        DuaItem(
            id = "dua_2",
            categoryId = "quran",
            arabic = "رَبَّنَا تَقَبَّلْ مِنَّا",
            transliteration = "Rabbana taqabbal minnā",
            translation = "Our Lord, accept from us",
            source = "Qur’an 2:127"
        ),

        DuaItem(
            id = "dua_3",
            categoryId = "daily",
            arabic = "رَبِّ اغْفِرْ لِي",
            transliteration = "Rabbighfir lī",
            translation = "My Lord, forgive me",
            source = "Qur’an 14:41"
        )
    )
}

