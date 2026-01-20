package com.hathway.ramadankareem2026.ui.dua.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.MenuBook
import androidx.compose.material.icons.outlined.*
import com.hathway.ramadankareem2026.ui.dua.model.DuaCategory

object DuaCategoryData {

    val list = listOf(

        // 🌙 Ramadan
        DuaCategory(
            id = "ramadan",
            title = "Ramadan Duʿāʾs",
            subtitle = "Special supplications for Ramadan",
            icon = Icons.Outlined.NightsStay
        ),

        // 📖 Qur’an
        DuaCategory(
            id = "quran",
            title = "Duʿāʾs from Qur’an",
            subtitle = "Supplications revealed in the Qur’an",
            icon = Icons.AutoMirrored.Outlined.MenuBook
        ),

        // 🕌 Prophets
        DuaCategory(
            id = "prophets",
            title = "Duʿāʾs of Prophets",
            subtitle = "Supplications of Allah’s messengers",
            icon = Icons.Outlined.AutoStories
        ),

        // 🧠 Faith
        DuaCategory(
            id = "faith",
            title = "Faith & Guidance",
            subtitle = "Strengthen īmān and guidance",
            icon = Icons.Outlined.Mosque
        ),

        // 👨‍👩‍👧‍👦 Family
        DuaCategory(
            id = "family",
            title = "Family & Children",
            subtitle = "Duʿāʾs for parents & offspring",
            icon = Icons.Outlined.People
        ),

        // 💍 Marriage
        DuaCategory(
            id = "marriage",
            title = "Marriage & Spouse",
            subtitle = "Duʿāʾs for nikāḥ & harmony",
            icon = Icons.Outlined.FavoriteBorder
        ),

        // ❤️ Forgiveness
        DuaCategory(
            id = "forgiveness",
            title = "Forgiveness & Mercy",
            subtitle = "Seeking Allah’s mercy & tawbah",
            icon = Icons.Outlined.VolunteerActivism
        ),

        // 🛡️ Protection
        DuaCategory(
            id = "protection",
            title = "Protection & Safety",
            subtitle = "Duʿāʾs for protection from harm",
            icon = Icons.Outlined.Security
        ),

        // 🏥 Health
        DuaCategory(
            id = "health",
            title = "Health & Healing",
            subtitle = "Duʿāʾs for cure & wellbeing",
            icon = Icons.Outlined.MedicalServices
        ),

        // 🌅 Daily
        DuaCategory(
            id = "daily",
            title = "Daily Duʿāʾs",
            subtitle = "Morning, evening & daily life",
            icon = Icons.Outlined.WbSunny
        ),

        // 📿 Adhkār
        DuaCategory(
            id = "adhkar",
            title = "Morning & Evening Adhkār",
            subtitle = "Daily remembrance",
            icon = Icons.Outlined.AutoAwesome
        ),

        // 📘 Hisnul Muslim
        DuaCategory(
            id = "hisnul",
            title = "Hisnul Muslim",
            subtitle = "Authentic daily supplications",
            icon = Icons.Outlined.MenuBook
        ),

        // 👴 Parents (separate from family)
        DuaCategory(
            id = "parents",
            title = "Parents",
            subtitle = "Duʿāʾs for mother & father",
            icon = Icons.Outlined.Elderly
        )
    )
}
