package com.hathway.ramadankareem2026.ui.tips.data.source

import com.hathway.ramadankareem2026.ui.tips.data.model.Tip
import com.hathway.ramadankareem2026.ui.tips.data.model.TipCategory
import java.util.Calendar

object TipsDataSource {
    
    private val dailyRamadanTips = listOf(
        Tip(
            id = 1,
            title = "Delay Suhoor and Hasten Iftar",
            content = "The Prophet (ﷺ) said: 'The people will continue to prosper as long as they hasten in breaking the fast.' Delay suhoor until just before Fajr and break your fast immediately at sunset.",
            category = TipCategory.DAILY_RAMADAN,
            isDaily = true
        ),
        Tip(
            id = 2,
            title = "Make Intention (Niyyah) Before Fasting",
            content = "Renew your intention each night: 'I intend to fast tomorrow for the sake of Allah.' A sincere intention transforms your hunger into worship.",
            category = TipCategory.DAILY_RAMADAN,
            isDaily = true
        ),
        Tip(
            id = 3,
            title = "Control Anger While Fasting",
            content = "Fasting is not just from food and drink, but also from evil speech and actions. When angered, say: 'I am fasting.'",
            category = TipCategory.DAILY_RAMADAN,
            isDaily = true
        ),
        Tip(
            id = 4,
            title = "Increase Dhikr Between Prayers",
            content = "Use the breaks between prayers for remembrance of Allah. Subhan'Allah, Alhamdulillah, Allahu Akbar - these simple words carry immense weight.",
            category = TipCategory.DAILY_RAMADAN,
            isDaily = true
        )
    )
    
    private val fastingTips = listOf(
        Tip(
            id = 101,
            title = "Does Brushing Teeth Break Fast?",
            content = "Using miswak or brushing teeth without swallowing does not break your fast. Be careful not to swallow any water or toothpaste.",
            category = TipCategory.FASTING
        ),
        Tip(
            id = 102,
            title = "Is Injection Allowed During Fast?",
            content = "Nutritional injections break the fast, but medical injections for treatment (like vaccines or insulin) do not break the fast according to most scholars.",
            category = TipCategory.FASTING
        ),
        Tip(
            id = 103,
            title = "Common Mistakes While Fasting",
            content = "Avoid: excessive eating at iftar, missing suhoor, wasting Ramadan time, anger and arguments, forgetting evening prayers.",
            category = TipCategory.FASTING
        ),
        Tip(
            id = 104,
            title = "Medical Allowances",
            content = "Travelers, menstruating women, the ill, and elderly with health issues are exempt from fasting. Make up missed days when able.",
            category = TipCategory.FASTING
        ),
        Tip(
            id = 105,
            title = "Sunnah of Fasting",
            content = "Eat suhoor, break fast with dates, make dua at iftar, pray Taraweeh, increase Quran recitation, give charity, and maintain ties with family.",
            category = TipCategory.FASTING
        )
    )
    
    private val prayerTips = listOf(
        Tip(
            id = 201,
            title = "How to Pray with Khushuʿ",
            content = "Focus on the meaning of recitations, understand you're speaking to Allah, pray as if you see Him, and remember death and the grave.",
            category = TipCategory.PRAYER
        ),
        Tip(
            id = 202,
            title = "Best Duʿās After Salah",
            content = "Astaghfirullah (3x), Subhan'Allah (33x), Alhamdulillah (33x), Allahu Akbar (33x), then recite Ayat al-Kursi and make personal du'a.",
            category = TipCategory.PRAYER
        ),
        Tip(
            id = 203,
            title = "Mistakes in Prayer to Avoid",
            content = "Rushing through prayers, not focusing, incorrect wudu, missing obligatory parts, praying in forbidden times, and forgetting proper attire.",
            category = TipCategory.PRAYER
        )
    )
    
    private val duaDhikrTips = listOf(
        Tip(
            id = 301,
            title = "Best Times to Make Duʿā",
            content = "During sujood, between adhan and iqamah, on Fridays, while fasting, during rain, in the last third of night, and when traveling.",
            category = TipCategory.DUA_DHIKR
        ),
        Tip(
            id = 302,
            title = "Powerful Dhikr After Fajr",
            content = "Morning adhkar protect you all day: Ayat al-Kursi, last 3 verses of Al-Baqarah, and 'La ilaha illallah wahdahu la sharika lah.'",
            category = TipCategory.DUA_DHIKR
        ),
        Tip(
            id = 303,
            title = "Duʿā Between Adhan and Iqamah",
            content = "This is a time of answered prayers. The du'a made between the call to prayer and the iqamah is never rejected.",
            category = TipCategory.DUA_DHIKR
        )
    )
    
    private val lifestyleTips = listOf(
        Tip(
            id = 401,
            title = "Sleep Schedule During Ramadan",
            content = "Sleep after Isha, wake for Tahajjud and suhoor, nap briefly if needed. Quality night sleep beats long daytime naps.",
            category = TipCategory.LIFESTYLE
        ),
        Tip(
            id = 402,
            title = "Healthy Iftar Tips",
            content = "Start with dates and water, then light soup. Wait 10 minutes before main meal. Avoid heavy fried foods and overeating.",
            category = TipCategory.LIFESTYLE
        ),
        Tip(
            id = 403,
            title = "Managing Work While Fasting",
            content = "Schedule important tasks for morning hours, avoid meetings late afternoon, communicate your fasting schedule to colleagues.",
            category = TipCategory.LIFESTYLE
        ),
        Tip(
            id = 404,
            title = "Avoiding Burnout in Last 10 Nights",
            content = "Pace yourself, prioritize worship over quantity, maintain energy with proper nutrition, and remember quality over quantity in deeds.",
            category = TipCategory.LIFESTYLE
        )
    )
    
    private val hadithAyahTips = listOf(
        Tip(
            id = 501,
            title = "Hadith of the Day",
            content = "'Whoever fasts Ramadan out of faith and seeking reward, his previous sins will be forgiven.' (Bukhari, Muslim)",
            category = TipCategory.HADITH_AYAH
        ),
        Tip(
            id = 502,
            title = "Verse of the Day",
            content = "'O you who believe, fasting has been prescribed for you as it was prescribed for those before you, that you may become righteous.' (Quran 2:183)",
            category = TipCategory.HADITH_AYAH
        )
    )
    
    private val goodDeedsTips = listOf(
        Tip(
            id = 601,
            title = "Daily Good Deeds Checklist",
            content = "✔ Smile at Muslims\n✔ Give charity (even a smile)\n✔ Forgive someone\n✔ Call family members\n✔ Share beneficial knowledge\n✔ Make dua for others",
            category = TipCategory.GOOD_DEEDS
        )
    )
    
    fun getAllTips(): List<Tip> {
        return dailyRamadanTips + fastingTips + prayerTips + duaDhikrTips + 
               lifestyleTips + hadithAyahTips + goodDeedsTips
    }

    fun getDailyTip(): Tip {
        val calendar = Calendar.getInstance()
        val dayOfYear = calendar.get(Calendar.DAY_OF_YEAR)
        val tipIndex = dayOfYear % dailyRamadanTips.size
        return dailyRamadanTips[tipIndex]
    }
    
    fun getHadithOrAyahOfTheDay(): Tip {
        val calendar = Calendar.getInstance()
        val dayOfYear = calendar.get(Calendar.DAY_OF_YEAR)
        val tipIndex = dayOfYear % hadithAyahTips.size
        return hadithAyahTips[tipIndex]
    }
}
