package com.hathway.ramadankareem2026.ui.tips.data.model

data class Tip(
    val id: Int,
    val title: LocalizedText,
    val content: LocalizedText,
    val category: TipCategory,
    val isDaily: Boolean = false
)

data class LocalizedText(
    val english: String, val hindi: String = "", val urdu: String = "", val malaysian: String = ""
) {
    fun getText(language: String): String {
        return when (language.lowercase()) {
            "hi", "hindi" -> hindi.ifBlank { english }
            "ur", "urdu" -> urdu.ifBlank { english }
            "ms", "malaysian" -> malaysian.ifBlank { english }
            "en", "english" -> english
            else -> english
        }
    }
}

enum class TipCategory(
    val displayName: LocalizedCategoryNames, val priority: Int
) {
    DAILY_RAMADAN(
        LocalizedCategoryNames(
            english = "Daily Ramadan",
            hindi = "दैनिक रमज़ान",
            urdu = "روزانہ رمضان",
            malaysian = "Harian Ramadan"
        ), 1
    ),
    FASTING(
        LocalizedCategoryNames(
            english = "Fasting", hindi = "रोज़े", urdu = "روزے", malaysian = "Puasa"
        ), 2
    ),
    PRAYER(
        LocalizedCategoryNames(
            english = "Prayer", hindi = "नमाज़", urdu = "نماز", malaysian = "Solat"
        ), 3
    ),
    DUA_DHIKR(
        LocalizedCategoryNames(
            english = "Duʿā & Dhikr",
            hindi = "दुआ और ज़िक्र",
            urdu = "دعا اور ذکر",
            malaysian = "Doa & Zikir"
        ), 4
    ),
    LIFESTYLE(
        LocalizedCategoryNames(
            english = "Lifestyle", hindi = "जीवनशैली", urdu = "طریق زندگی", malaysian = "Gaya Hidup"
        ), 5
    ),
    HADITH_AYAH(
        LocalizedCategoryNames(
            english = "Hadith & Ayah",
            hindi = "हदीस और आयत",
            urdu = "حدیث اور آیت",
            malaysian = "Hadis & Ayat"
        ), 6
    ),
    GOOD_DEEDS(
        LocalizedCategoryNames(
            english = "Good Deeds",
            hindi = "अच्छे काम",
            urdu = "اچھے اعمال",
            malaysian = "Amalan Baik"
        ), 7
    )
}

data class LocalizedCategoryNames(
    val english: String, val hindi: String = "", val urdu: String = "", val malaysian: String = ""
) {
    fun getName(language: String): String {
        return when (language.lowercase()) {
            "hi", "hindi" -> if (hindi.isNotBlank()) hindi else english
            "ur", "urdu" -> if (urdu.isNotBlank()) urdu else english
            "ms", "malaysian" -> if (malaysian.isNotBlank()) malaysian else english
            "en", "english" -> english
            else -> english
        }
    }
}
