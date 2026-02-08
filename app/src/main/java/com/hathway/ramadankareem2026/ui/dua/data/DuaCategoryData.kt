package com.hathway.ramadankareem2026.ui.dua.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.MenuBook
import androidx.compose.material.icons.outlined.*
import com.hathway.ramadankareem2026.ui.dua.model.DuaCategory
import com.hathway.ramadankareem2026.ui.dua.model.LocalizedCategoryNames

object DuaCategoryData {

    val list = listOf(

        // Ramadan
        DuaCategory(
            id = "ramadan",
            title = LocalizedCategoryNames(
                english = "Ramadan Duʿāʾs",
                hindi = "रमज़ान की दुआएं",
                urdu = "رمضان کی دعائیں",
                malaysian = "Doa Ramadan"
            ),
            subtitle = LocalizedCategoryNames(
                english = "Special supplications for Ramadan",
                hindi = "रमज़ान के लिए विशेष दुआएं",
                urdu = "رمضان کے لیے خاص دعائیں",
                malaysian = "Doa khas untuk Ramadan"
            ),
            icon = Icons.Outlined.NightsStay
        ),

        // Qur'an
        DuaCategory(
            id = "quran",
            title = LocalizedCategoryNames(
                english = "Duʿāʾs from Qur'an",
                hindi = "क़ुरआन से दुआएं",
                urdu = "قرآن سے دعائیں",
                malaysian = "Doa dari Al-Quran"
            ),
            subtitle = LocalizedCategoryNames(
                english = "Supplications revealed in Qur'an",
                hindi = "क़ुरआन में आई दुआएं",
                urdu = "قرآن میں نازل ہونے والی دعائیں",
                malaysian = "Doa yang terdapat dalam Al-Quran"
            ),
            icon = Icons.AutoMirrored.Outlined.MenuBook
        ),

        // Prophets
        DuaCategory(
            id = "prophets",
            title = LocalizedCategoryNames(
                english = "Duʿāʾs of Prophets",
                hindi = "पैगंबरों की दुआएं",
                urdu = "انبیاء کی دعائیں",
                malaysian = "Doa Para Nabi"
            ),
            subtitle = LocalizedCategoryNames(
                english = "Supplications of Allah’s messengers",
                hindi = "अल्लाह के पैगंबरों की दुआएं",
                urdu = "اللہ کے رسولوں کی دعائیں",
                malaysian = "Doa para utusan Allah"
            ),
            icon = Icons.Outlined.AutoStories
        ),

        // Faith
        DuaCategory(
            id = "faith",
            title = LocalizedCategoryNames(
                english = "Faith & Guidance",
                hindi = "ईमान और मार्गदर्शन",
                urdu = "ایمان اور رہنمائی",
                malaysian = "Iman & Petunjuk"
            ),
            subtitle = LocalizedCategoryNames(
                english = "Strengthen īmān and guidance",
                hindi = "ईमान और मार्गदर्शन को मज़बूत करें",
                urdu = "ایمان اور رہنمائی کو مضبوط کریں",
                malaysian = "Menguatkan iman dan petunjuk"
            ),
            icon = Icons.Outlined.Mosque
        ),

        // Family
        DuaCategory(
            id = "family",
            title = LocalizedCategoryNames(
                english = "Family & Children",
                hindi = "परिवार और संतान",
                urdu = "خاندان اور اولاد",
                malaysian = "Keluarga & Anak-anak"
            ),
            subtitle = LocalizedCategoryNames(
                english = "Duʿāʾs for family and children",
                hindi = "परिवार और बच्चों के लिए दुआएं",
                urdu = "خاندان اور بچوں کے لیے دعائیں",
                malaysian = "Doa untuk keluarga dan anak-anak"
            ),
            icon = Icons.Outlined.People
        ),

        // Marriage
        DuaCategory(
            id = "marriage",
            title = LocalizedCategoryNames(
                english = "Marriage & Spouse",
                hindi = "शादी और जीवनसाथी",
                urdu = "شادی اور شریکِ حیات",
                malaysian = "Perkahwinan & Pasangan"
            ),
            subtitle = LocalizedCategoryNames(
                english = "Duʿāʾs for nikāḥ & harmony",
                hindi = "निकाह और आपसी तालमेल के लिए दुआएं",
                urdu = "نکاح اور ہم آہنگی کے لیے دعائیں",
                malaysian = "Doa untuk nikah dan keharmonian"
            ),
            icon = Icons.Outlined.Favorite
        ),

        // Forgiveness
        DuaCategory(
            id = "forgiveness",
            title = LocalizedCategoryNames(
                english = "Forgiveness & Mercy",
                hindi = "क्षमा और दया",
                urdu = "بخشش اور رحمت",
                malaysian = "Keampunan & Rahmat"
            ),
            subtitle = LocalizedCategoryNames(
                english = "Seeking Allah’s mercy and forgiveness",
                hindi = "अल्लाह की दया और क्षमा की तलाश",
                urdu = "اللہ کی رحمت اور بخشش کی تلاش",
                malaysian = "Mencari rahmat dan keampunan Allah"
            ),
            icon = Icons.Outlined.VolunteerActivism
        ),

        // Protection
        DuaCategory(
            id = "protection",
            title = LocalizedCategoryNames(
                english = "Protection & Safety",
                hindi = "सुरक्षा और हिफ़ाज़त",
                urdu = "حفاظت اور سلامتی",
                malaysian = "Perlindungan & Keselamatan"
            ),
            subtitle = LocalizedCategoryNames(
                english = "Duʿāʾs for protection from harm",
                hindi = "नुकसान से बचाव की दुआएं",
                urdu = "نقصان سے بچاؤ کی دعائیں",
                malaysian = "Doa perlindungan daripada bahaya"
            ),
            icon = Icons.Outlined.Security
        ),

        // Health
        DuaCategory(
            id = "health",
            title = LocalizedCategoryNames(
                english = "Health & Healing",
                hindi = "स्वास्थ्य और शिफ़ा",
                urdu = "صحت اور شفا",
                malaysian = "Kesihatan & Penyembuhan"
            ),
            subtitle = LocalizedCategoryNames(
                english = "Duʿāʾs for cure and wellbeing",
                hindi = "इलाज और सेहत के लिए दुआएं",
                urdu = "علاج اور صحت کے لیے دعائیں",
                malaysian = "Doa untuk kesihatan dan kesejahteraan"
            ),
            icon = Icons.Outlined.MedicalServices
        ),

        // Daily
        DuaCategory(
            id = "daily",
            title = LocalizedCategoryNames(
                english = "Daily Duʿāʾs",
                hindi = "दैनिक दुआएं",
                urdu = "روزمرہ کی دعائیں",
                malaysian = "Doa Harian"
            ),
            subtitle = LocalizedCategoryNames(
                english = "Morning, evening & daily life",
                hindi = "सुबह, शाम और रोज़मर्रा की ज़िंदगी",
                urdu = "صبح، شام اور روزمرہ زندگی",
                malaysian = "Pagi, petang dan kehidupan harian"
            ),
            icon = Icons.Outlined.WbSunny
        ),

        // Adhkār
        DuaCategory(
            id = "adhkar",
            title = LocalizedCategoryNames(
                english = "Morning & Evening Adhkār",
                hindi = "सुबह और शाम के अज़कार",
                urdu = "صبح و شام کے اذکار",
                malaysian = "Zikir Pagi & Petang"
            ),
            subtitle = LocalizedCategoryNames(
                english = "Daily remembrance of Allah",
                hindi = "अल्लाह का दैनिक ज़िक्र",
                urdu = "اللہ کا روزانہ ذکر",
                malaysian = "Zikir harian kepada Allah"
            ),
            icon = Icons.Outlined.AutoAwesome
        ),

        // Hisnul Muslim
        DuaCategory(
            id = "hisnul",
            title = LocalizedCategoryNames(
                english = "Hisnul Muslim",
                hindi = "हिस्नुल मुस्लिम",
                urdu = "حصن المسلم",
                malaysian = "Hisnul Muslim"
            ),
            subtitle = LocalizedCategoryNames(
                english = "Authentic daily supplications",
                hindi = "प्रामाणिक रोज़ाना दुआएं",
                urdu = "مستند روزمرہ دعائیں",
                malaysian = "Doa harian yang sahih"
            ),
            icon = Icons.Outlined.MenuBook
        ),

        // Parents
        DuaCategory(
            id = "parents",
            title = LocalizedCategoryNames(
                english = "Parents",
                hindi = "माता-पिता",
                urdu = "والدین",
                malaysian = "Ibu Bapa"
            ),
            subtitle = LocalizedCategoryNames(
                english = "Duʿāʾs for mother and father",
                hindi = "माता और पिता के लिए दुआएं",
                urdu = "ماں اور والد کے لیے دعائیں",
                malaysian = "Doa untuk ibu dan bapa"
            ),
            icon = Icons.Outlined.Elderly
        )
    )

}
