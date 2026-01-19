package com.hathway.ramadankareem2026.ui.dua.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.MenuBook
import androidx.compose.material.icons.outlined.AutoStories
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Mosque
import androidx.compose.material.icons.outlined.People
import androidx.compose.material.icons.outlined.WbSunny
import com.hathway.ramadankareem2026.ui.dua.model.DuaCategory

object DuaCategoryData {

    val list = listOf(
        DuaCategory(
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
            id = "faith",
            title = "Faith & Guidance",
            subtitle = "Strengthen īmān and submission",
            icon = Icons.Outlined.Mosque
        ), DuaCategory(
            id = "family",
            title = "Family & Children",
            subtitle = "Duʿāʾs for parents & offspring",
            icon = Icons.Outlined.People
        ), DuaCategory(
            id = "forgiveness",
            title = "Forgiveness & Mercy",
            subtitle = "Seeking Allah’s mercy & tawbah",
            icon = Icons.Outlined.FavoriteBorder
        ), DuaCategory(
            id = "daily",
            title = "Daily Duʿāʾs",
            subtitle = "Morning, evening & daily life",
            icon = Icons.Outlined.WbSunny
        )
    )
}
