package com.hathway.ramadankareem2026.ui.ramadan

import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hathway.ramadankareem2026.core.util.toHijriDate
import com.hathway.ramadankareem2026.ui.components.CountdownCircularProgress
import com.hathway.ramadankareem2026.ui.home.components.displayName
import com.hathway.ramadankareem2026.ui.home.homeViewModel.HomeViewModel
import com.hathway.ramadankareem2026.ui.prayer.PrayerTimeUiMapper
import com.hathway.ramadankareem2026.ui.prayer.PrayerType
import com.hathway.ramadankareem2026.ui.prayer.PrayerViewModel
import com.hathway.ramadankareem2026.ui.prayer.data.PrayerViewModelFactory
import com.hathway.ramadankareem2026.ui.ramadan.model.FastingDayStatus
import com.hathway.ramadankareem2026.ui.ramadan.model.RamadanDayUiModel
import kotlinx.coroutines.delay
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

private const val TAG = "RamadanDayDetailSheet"
@Composable
fun RamadanDayDetailSheet(day: RamadanDayUiModel) {

    val context = LocalContext.current
    val app = context.applicationContext as Application

     // Prayer ViewModel
    val prayerViewModel: PrayerViewModel = viewModel(
        factory = PrayerViewModelFactory(app)
    )
    val homeViewModel: HomeViewModel = viewModel()

    val locationState by homeViewModel.locationState.collectAsState()

    LaunchedEffect(locationState) {
        prayerViewModel.loadWithLocation(locationState, day.date)
    }


    var now by remember { mutableStateOf(LocalTime.now()) }


    val prayerState by prayerViewModel.state.collectAsState()
    val prayers = remember(prayerState, now) {
        PrayerTimeUiMapper.map(prayerState, now)
    }
    
    // Show loading state
    val isLoading = prayerState == null


    val hijri = remember(day.date) { day.date.toHijriDate() }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {

        // Drag handle
        Box(
            modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .width(40.dp)
                    .height(4.dp)
                    .clip(RoundedCornerShape(50))
                    .background(Color.LightGray.copy(alpha = 0.5f))
            )
        }

        Spacer(Modifier.height(16.dp))
        val lang = LanguageManager.current(context)

        // Header
        Text(
            text = "${RamadanStrings.ramadanDay(lang)} ${day.ramadanDay}",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(Modifier.height(4.dp))

        // English date
        Text(
            text = "${day.weekday}, ${day.date.dayOfMonth} ${day.month}",
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        // 🌙 Hijri date (NEW)
        Text(
            text = "${hijri.day} ${hijri.month} ${hijri.year} AH",
            fontSize = 13.sp,
            color = Color(0xFF0F9D58),
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(Modifier.height(12.dp))

        StatusChip(day.status)

        Spacer(Modifier.height(20.dp))

        // Countdown
        if (day.status == FastingDayStatus.FASTING || day.status == FastingDayStatus.TODAY) {

            val liveMinutes = rememberLiveRemainingMinutes(
                initialMinutes = day.remainingMinutes,
                isActive = day.status == FastingDayStatus.FASTING
            )

            Box(
                modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center
            ) {
                CountdownCircularProgress(
                    totalMinutes = day.totalMinutes,
                    remainingMinutes = liveMinutes,
                    modifier = Modifier.size(120.dp)
                )
            }

            Spacer(Modifier.height(24.dp))
        }

        Text(
            text = RamadanStrings.prayerTimes(lang),
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(Modifier.height(12.dp))
        
        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center
            ) {
                Text("Loading prayer times...", color = Color.Gray)
            }
        } else {
            prayers.forEach { prayer ->
                PrayerTimeCard(
                    label = prayer.type.displayName(),
                    time = prayer.time.format(DateTimeFormatter.ofPattern("hh:mm a"))
                )
            }
        }

        Spacer(Modifier.height(16.dp))
    }
}

@Composable
private fun StatusChip(status: FastingDayStatus) {

    val context = LocalContext.current
    val lang = LanguageManager.current(context)

    val (text, color) = when (status) {
        FastingDayStatus.TODAY ->
            RamadanStrings.today(lang) to Color(0xFFFF9800)

        FastingDayStatus.FASTING ->
            RamadanStrings.fasting(lang) to Color(0xFF0F9D58)

        FastingDayStatus.UPCOMING ->
            RamadanStrings.upcoming(lang) to Color.Gray

        FastingDayStatus.COMPLETED ->
            RamadanStrings.completed(lang) to Color(0xFF0B6E3E)
    }

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(color.copy(alpha = 0.15f))
                .padding(horizontal = 14.dp, vertical = 6.dp)
        ) {
            Text(text, color = color, fontWeight = FontWeight.SemiBold)
        }
    }
}


@Composable
private fun PrayerTimeCard(label: String, time: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(1.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(label, color = Color.Gray)
            Text(time, fontWeight = FontWeight.SemiBold)
        }
    }
}

/*  LIVE COUNTDOWN */
@Composable
fun rememberLiveRemainingMinutes(
    initialMinutes: Int, isActive: Boolean
): Int {
    var minutes by remember { mutableIntStateOf(initialMinutes) }

    LaunchedEffect(isActive) {
        if (!isActive) return@LaunchedEffect
        while (minutes > 0) {
            delay(60_000)
            minutes -= 1
        }
    }
    return minutes
}

@Preview(showBackground = true)
@Composable
private fun PreviewRamadanDayDetailSheet() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 24.dp)
    ) {
        RamadanDayDetailSheet(
            day = previewRamadanDay()
        )
    }
}

private fun previewRamadanDay(): RamadanDayUiModel {
    return RamadanDayUiModel(
        ramadanDay = 5,
        date = LocalDate.of(2026, 3, 5),
        weekday = "Thu",
        month = "March",
        imsak = LocalTime.of(5, 30),
        fajr = LocalTime.of(5, 40),
        maghrib = LocalTime.of(18, 45),
        status = FastingDayStatus.FASTING,
        totalMinutes = 780,
        remainingMinutes = 150
    )
}
enum class AppLanguage {
    ENGLISH,
    HINDI,
    URDU,
    MALAY
}
object LanguageManager {

    fun current(context: android.content.Context): AppLanguage {
        // You can later connect this to DataStore / Settings
        val locale = context.resources.configuration.locales[0].language

        return when (locale) {
            "hi" -> AppLanguage.HINDI
            "ur" -> AppLanguage.URDU
            "ms" -> AppLanguage.MALAY
            else -> AppLanguage.ENGLISH
        }
    }
}
object RamadanStrings {

    fun ramadanDay(lang: AppLanguage) = when (lang) {
        AppLanguage.ENGLISH -> "Ramadan Day"
        AppLanguage.HINDI -> "रमज़ान का दिन"
        AppLanguage.URDU -> "رمضان کا دن"
        AppLanguage.MALAY -> "Hari Ramadan"
    }

    fun prayerTimes(lang: AppLanguage) = when (lang) {
        AppLanguage.ENGLISH -> "Prayer Times"
        AppLanguage.HINDI -> "नमाज़ के समय"
        AppLanguage.URDU -> "نماز کے اوقات"
        AppLanguage.MALAY -> "Waktu Solat"
    }

    fun today(lang: AppLanguage) = when (lang) {
        AppLanguage.ENGLISH -> "Today"
        AppLanguage.HINDI -> "आज"
        AppLanguage.URDU -> "آج"
        AppLanguage.MALAY -> "Hari Ini"
    }

    fun fasting(lang: AppLanguage) = when (lang) {
        AppLanguage.ENGLISH -> "Fasting"
        AppLanguage.HINDI -> "रोज़ा"
        AppLanguage.URDU -> "روزہ"
        AppLanguage.MALAY -> "Berpuasa"
    }

    fun upcoming(lang: AppLanguage) = when (lang) {
        AppLanguage.ENGLISH -> "Upcoming"
        AppLanguage.HINDI -> "आगामी"
        AppLanguage.URDU -> "آنے والا"
        AppLanguage.MALAY -> "Akan Datang"
    }

    fun completed(lang: AppLanguage) = when (lang) {
        AppLanguage.ENGLISH -> "Completed"
        AppLanguage.HINDI -> "पूर्ण"
        AppLanguage.URDU -> "مکمل"
        AppLanguage.MALAY -> "Selesai"
    }

    fun loadingPrayerTimes(lang: AppLanguage) = when (lang) {
        AppLanguage.ENGLISH -> "Loading prayer times..."
        AppLanguage.HINDI -> "नमाज़ के समय लोड हो रहे हैं..."
        AppLanguage.URDU -> "نماز کے اوقات لوڈ ہو رہے ہیں..."
        AppLanguage.MALAY -> "Memuat waktu solat..."
    }
}
fun PrayerType.displayName(lang: AppLanguage): String = when (this) {
    PrayerType.FAJR -> when (lang) {
        AppLanguage.ENGLISH -> "Fajr"
        AppLanguage.HINDI -> "फ़ज्र"
        AppLanguage.URDU -> "فجر"
        AppLanguage.MALAY -> "Subuh"
    }

    PrayerType.DHUHR -> when (lang) {
        AppLanguage.ENGLISH -> "Dhuhr"
        AppLanguage.HINDI -> "ज़ुहर"
        AppLanguage.URDU -> "ظہر"
        AppLanguage.MALAY -> "Zohor"
    }

    PrayerType.ASR -> when (lang) {
        AppLanguage.ENGLISH -> "Asr"
        AppLanguage.HINDI -> "असर"
        AppLanguage.URDU -> "عصر"
        AppLanguage.MALAY -> "Asar"
    }

    PrayerType.MAGHRIB -> when (lang) {
        AppLanguage.ENGLISH -> "Maghrib"
        AppLanguage.HINDI -> "मग़रिब"
        AppLanguage.URDU -> "مغرب"
        AppLanguage.MALAY -> "Maghrib"
    }

    PrayerType.ISHA -> when (lang) {
        AppLanguage.ENGLISH -> "Isha"
        AppLanguage.HINDI -> "इशा"
        AppLanguage.URDU -> "عشاء"
        AppLanguage.MALAY -> "Isyak"
    }
}
