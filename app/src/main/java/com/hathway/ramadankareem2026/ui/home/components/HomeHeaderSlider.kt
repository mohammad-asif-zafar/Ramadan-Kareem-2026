package com.hathway.ramadankareem2026.ui.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Alarm
import androidx.compose.material.icons.outlined.AlarmOff
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.platform.LocalContext
import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.hathway.ramadankareem2026.core.localization.LocalizationManager
import com.hathway.ramadankareem2026.ui.home.HeaderCard
import com.hathway.ramadankareem2026.ui.home.mapper.buildDynamicPrayerHeader
import com.hathway.ramadankareem2026.ui.home.model.HeaderPage
import com.hathway.ramadankareem2026.ui.home.model.HeaderType
import com.hathway.ramadankareem2026.ui.prayer.PrayerTimeUiMapper
import com.hathway.ramadankareem2026.ui.prayer.PrayerViewModel
import com.hathway.ramadankareem2026.ui.ramadan.model.RamadanDayUiModel
import com.hathway.ramadankareem2026.ui.ramadan.viewmodel.RamadanCalendarViewModel
import com.hathway.ramadankareem2026.ui.ramadan.viewmodel.RamadanCalendarViewModelFactory
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.tooling.preview.Preview

import kotlinx.coroutines.delay

/**
 * Sealed class to represent different Ramadan states
 */
sealed class RamdanStatus {
    data class IN_RAMADAN(val day: Int) : RamdanStatus()
    data class BEFORE_RAMADAN(val daysRemaining: Int) : RamdanStatus()
    object AFTER_RAMADAN : RamdanStatus()
}

/**
 * Home screen header slider.
 *
 * Features:
 * - Shows dynamic prayer information (next prayer)
 * - Auto-sliding pager with indicators
 * - Mix of dynamic + static header cards
 *
 * Displayed at:
 * - Home screen top section
 */
@Composable
fun HomeHeaderSlider(
    prayerViewModel: PrayerViewModel = viewModel(),
    ramadanViewModel: RamadanCalendarViewModel = viewModel(
        factory = RamadanCalendarViewModelFactory(
            LocalContext.current.applicationContext as android.app.Application
        )
    )
) {

    /**
     * Collect prayer state from ViewModel.
     * StateFlow → Compose State
     */
    val prayerState by prayerViewModel.state.collectAsState()

    /**
     * Collect Ramadan state from ViewModel.
     */
    val ramadanDays by ramadanViewModel.days.collectAsState()

    /**
     * Current time captured once when composable enters composition.
     * Used for determining next prayer.
     */
    val now = remember { LocalTime.now() }

    /**
     * Map domain prayer state → UI-friendly model.
     * Recomputed only when prayerState changes.
     */
    val mappedPrayers = remember(prayerState) {
        PrayerTimeUiMapper.map(
            state = prayerState, now = now
        )
    }

    /**
     * Find the next upcoming prayer.
     */
    val nextPrayer = remember(mappedPrayers) {
        mappedPrayers.firstOrNull { it.isNext }
    }

    /**
     * Find the current prayer time (if any prayer is happening now)
     */
    val currentPrayer = remember(mappedPrayers) {
        mappedPrayers.firstOrNull { it.isCurrent }
    }

    /**
     * Get today's Ramadan day info for Iftar and Suhoor times.
     */
    val todayRamadanDay = remember(ramadanDays) {
        ramadanDays.firstOrNull { it.status == com.hathway.ramadankareem2026.ui.ramadan.model.FastingDayStatus.TODAY }
    }

    /**
     * Check if today is Ramadan and calculate days remaining if not
     */
    val ramadanStatus = remember {
        val today = LocalDate.now()
        val ramadanStartDate = LocalDate.of(2026, 2, 18) // February 18, 2026
        val ramadanEndDate = ramadanStartDate.plusDays(29) // 30 days total

        when {
            today.isBefore(ramadanStartDate) -> {
                val daysUntilRamadan = ChronoUnit.DAYS.between(today, ramadanStartDate)
                RamdanStatus.BEFORE_RAMADAN(daysUntilRamadan.toInt())
            }

            today.isAfter(ramadanEndDate) -> {
                RamdanStatus.AFTER_RAMADAN
            }

            else -> {
                val ramadanDay = ChronoUnit.DAYS.between(ramadanStartDate, today).toInt() + 1
                RamdanStatus.IN_RAMADAN(ramadanDay)
            }
        }
    }

    fun getAlarmPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences("alarm_prefs", Context.MODE_PRIVATE)
    }

    fun isIftarAlarmEnabled(context: Context): Boolean {
        return getAlarmPreferences(context).getBoolean("iftar_alarm_enabled", false)
    }

    fun isSuhoorAlarmEnabled(context: Context): Boolean {
        return getAlarmPreferences(context).getBoolean("suhoor_alarm_enabled", false)
    }

    fun toggleIftarAlarm(context: Context) {
        val currentState = isIftarAlarmEnabled(context)
        getAlarmPreferences(context).edit { putBoolean("iftar_alarm_enabled", !currentState) }
    }

    fun toggleSuhoorAlarm(context: Context) {
        val currentState = isSuhoorAlarmEnabled(context)
        getAlarmPreferences(context).edit { putBoolean("suhoor_alarm_enabled", !currentState) }
    }

    /**
     * Get Iftar and Suhoor times - use Ramadan data if available, otherwise use default times
     */
    val iftarTime = remember(todayRamadanDay) {
        todayRamadanDay?.maghrib ?: LocalTime.of(18, 30) // Default 6:30 PM
    }

    val suhoorTime = remember(todayRamadanDay) {
        todayRamadanDay?.imsak ?: LocalTime.of(4, 30) // Default 4:30 AM
    }

    /**
     * Build pager pages dynamically.
     * First page = dynamic prayer card
     * Next pages = current prayer card (if prayer time), Ramadan timing cards (Iftar, Suhoor) - ALWAYS SHOWN with dynamic content
     * Other pages = static inspirational cards
     */
    val pages = remember(nextPrayer, currentPrayer, iftarTime, suhoorTime, ramadanStatus) {
        val pageList = mutableListOf<HeaderPage>()

        // Use mock alarm states for now to avoid composable context issues
        // TODO: Implement real SharedPreferences with proper context handling
        val isIftarAlarmEnabled = false
        val isSuhoorAlarmEnabled = false

        // Dynamic prayer header
        pageList.add(
            buildDynamicPrayerHeader(
                prayer = nextPrayer,
                gregorianDate = prayerState.gregorianDate,
                hijriDate = prayerState.hijriDate
            )
        )

        // Add current prayer card if there's a prayer happening now
        currentPrayer?.let { prayer ->
            pageList.add(
                HeaderPage(
                    type = HeaderType.NEXT_PRAYER,
                    title = "Now: ${prayer.name}",
                    subtitle = prayer.time.format(DateTimeFormatter.ofPattern("hh:mm a")),
                    hint = "Prayer time is now",
                    isAlarmEnabled = false // No alarm for current prayer
                )
            )
        }

        // ALWAYS add Iftar and Suhoor cards (pages 2 and 3) with dynamic content
        when (ramadanStatus) {
            is RamdanStatus.IN_RAMADAN -> {
                pageList.add(
                    HeaderPage(
                        type = HeaderType.IFTAR_TIME,
                        title = "Iftar Time",
                        subtitle = iftarTime.format(DateTimeFormatter.ofPattern("hh:mm a")),
                        hint = "Time to break your fast",
                        isAlarmEnabled = isIftarAlarmEnabled,
                        onAlarmToggle = { /* TODO: Implement SharedPreferences toggle */ })
                )

                pageList.add(
                    HeaderPage(
                        type = HeaderType.SUHOOR_TIME,
                        title = "Suhoor Time",
                        subtitle = suhoorTime.format(DateTimeFormatter.ofPattern("hh:mm a")),
                        hint = "Time for pre-dawn meal",
                        isAlarmEnabled = isSuhoorAlarmEnabled,
                        onAlarmToggle = { /* TODO: Implement SharedPreferences toggle */ })
                )
            }

            is RamdanStatus.BEFORE_RAMADAN -> {
                pageList.add(
                    HeaderPage(
                        type = HeaderType.SUHOOR_TIME,
                        title = "Prepare for Suhoor",
                        subtitle = "Set your alarm routine",
                        hint = "${ramadanStatus.daysRemaining} days until Ramadan",
                        isAlarmEnabled = isSuhoorAlarmEnabled,
                        onAlarmToggle = { /* TODO: Implement SharedPreferences toggle */ })
                )
            }

            is RamdanStatus.AFTER_RAMADAN -> {
                pageList.add(
                    HeaderPage(
                        type = HeaderType.IFTAR_TIME,
                        title = "Ramadan Completed",
                        subtitle = "See you next year",
                        hint = "Insha Allah",
                        isAlarmEnabled = isIftarAlarmEnabled,
                        onAlarmToggle = { /* TODO: Implement SharedPreferences toggle */ })
                )

                pageList.add(
                    HeaderPage(
                        type = HeaderType.SUHOOR_TIME,
                        title = "Ramadan Mubarak",
                        subtitle = "May Allah accept our fasting",
                        hint = "Eid Mubarak!",
                        isAlarmEnabled = isSuhoorAlarmEnabled,
                        onAlarmToggle = { /* TODO: Implement SharedPreferences toggle */ })
                )
            }
        }

        // Static reminder card with dynamic tips data
        pageList.add(
            HeaderPage(
                type = HeaderType.REMINDER,
                title = "Daily Reminder",
                subtitle = "Increase your dhikr today",
                hint = "Small deeds, big rewards"
            )
        )

        pageList
    }

    /**
     * Pager state:
     * - initialPage = 0 (or current prayer page if prayer is happening now)
     * - pageCount driven by pages list size
     */
    val initialPageIndex = remember(currentPrayer) {
        // If there's a current prayer, start with that page (index 1)
        // Otherwise start with page 0
        if (currentPrayer != null) 1 else 0
    }

    val pagerState = rememberPagerState(
        initialPage = initialPageIndex, pageCount = { pages.size })

    /**
     * Auto-scroll logic.
     * Cycles pages every 4 seconds.
     * If prayer time starts, immediately scroll to prayer page.
     */
    LaunchedEffect(pagerState, currentPrayer) {
        // If prayer time starts, immediately scroll to prayer page
        currentPrayer?.let {
            pagerState.animateScrollToPage(1) // Prayer page is always at index 1
        }

        // Continue with auto-scroll
        while (true) {
            delay(4_000)
            val nextPage = (pagerState.currentPage + 1) % pages.size
            pagerState.animateScrollToPage(nextPage)
        }
    }

    /**
     * Main layout container.
     */
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
    ) {

        /**
         * Horizontal pager for sliding header cards.
         */
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            pageSpacing = 12.dp,
            contentPadding = PaddingValues(horizontal = 0.dp)
        ) { page ->

            // Render each header card
            HeaderCard(
                type = pages[page].type,
                title = pages[page].title,
                subtitle = pages[page].subtitle,
                hint = pages[page].hint,
                isAlarmEnabled = pages[page].isAlarmEnabled
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        /**
         * Pager dots indicator.
         */
        PagerIndicator(
            pageCount = pages.size,
            currentPage = pagerState.currentPage,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }

    // Extra bottom spacing to separate from next section
    Spacer(modifier = Modifier.height(20.dp))
}


@Composable
private fun HomeHeaderSliderContent(
    pages: List<HeaderPage>
) {
    val pagerState = rememberPagerState(
        initialPage = 0, pageCount = { pages.size })

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp)
    ) {

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            pageSpacing = 12.dp,
            contentPadding = PaddingValues(horizontal = 12.dp)
        ) { page ->
            HeaderCard(
                type = pages[page].type,
                title = pages[page].title,
                subtitle = pages[page].subtitle,
                hint = pages[page].hint,
                isAlarmEnabled = pages[page].isAlarmEnabled
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        PagerIndicator(
            pageCount = pages.size,
            currentPage = pagerState.currentPage,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeHeaderSliderPreview() {

    val previewPages = listOf(
        HeaderPage(
            type = HeaderType.NEXT_PRAYER,
            title = "Fajr",
            subtitle = "05:32 AM",
            hint = "Hijri: 15 Ramadan"
        ), HeaderPage(
            type = HeaderType.NEXT_PRAYER,
            title = "Dhuhr",
            subtitle = "01:15 PM",
            hint = "Next prayer time"
        ), HeaderPage(
            type = HeaderType.REMINDER,
            title = "Daily Reminder",
            subtitle = "Remember Allah often",
            hint = "Indeed hearts find rest"
        )
    )

    HomeHeaderSliderContent(pages = previewPages)
}

private fun previewNormalDayPages(): List<HeaderPage> = listOf(
    HeaderPage(
        type = HeaderType.NEXT_PRAYER,
        title = "Next Prayer",
        subtitle = "Dhuhr • 01:15 PM",
        hint = "Hijri: 12 Sha'ban"
    ), HeaderPage(
        type = HeaderType.REMINDER,
        title = "Daily Reminder",
        subtitle = "Remember Allah often",
        hint = "Indeed hearts find rest"
    ), HeaderPage(
        type = HeaderType.NEXT_PRAYER,
        title = "Maghrib",
        subtitle = "06:42 PM",
        hint = "Prepare for prayer"
    )
)

private fun previewRamadanDayPages(): List<HeaderPage> = listOf(
    HeaderPage(
        type = HeaderType.NEXT_PRAYER,
        title = "Asr",
        subtitle = "04:55 PM",
        hint = "Hijri: 15 Ramadan"
    ), HeaderPage(
        type = HeaderType.IFTAR_TIME,
        title = "Iftar Time",
        subtitle = "06:41 PM",
        hint = "Day 15 of Ramadan",
        isAlarmEnabled = true
    ), HeaderPage(
        type = HeaderType.SUHOOR_TIME,
        title = "Suhoor Time",
        subtitle = "05:02 AM",
        hint = "Day 16 of Ramadan",
        isAlarmEnabled = false
    ), HeaderPage(
        type = HeaderType.REMINDER,
        title = "Daily Reminder",
        subtitle = "Increase your dhikr today",
        hint = "Small deeds, big rewards"
    )
)


@Preview(
    name = "Home Header – Normal Day", showBackground = true
)
@Composable
fun HomeHeaderSliderPreview_NormalDay() {
    HomeHeaderSliderContent(
        pages = previewNormalDayPages()
    )
}

@Preview(
    name = "Home Header – Ramadan Day", showBackground = true
)
@Composable
fun HomeHeaderSliderPreview_RamadanDay() {
    HomeHeaderSliderContent(
        pages = previewRamadanDayPages()
    )
}

@Preview(
    name = "Home Header – Ramadan (Dark)",
    showBackground = true,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun HomeHeaderSliderPreview_RamadanDark() {
    HomeHeaderSliderContent(
        pages = previewRamadanDayPages()
    )
}


private fun previewAllHeaderPages(): List<HeaderPage> = listOf(

    // Normal day – next prayer
    HeaderPage(
        type = HeaderType.NEXT_PRAYER,
        title = "Next Prayer",
        subtitle = "Dhuhr • 01:15 PM",
        hint = "Hijri: 12 Sha'ban"
    ),

    // Ramadan – Iftar
    HeaderPage(
        type = HeaderType.IFTAR_TIME,
        title = "Iftar Time",
        subtitle = "06:41 PM",
        hint = "Day 15 of Ramadan",
        isAlarmEnabled = true
    ),

    // Ramadan – Suhoor
    HeaderPage(
        type = HeaderType.SUHOOR_TIME,
        title = "Suhoor Time",
        subtitle = "05:02 AM",
        hint = "Day 16 of Ramadan",
        isAlarmEnabled = false
    )
)

@Composable
private fun HomeHeaderSliderAllPagesPreviewContent(
    pages: List<HeaderPage>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        pages.forEachIndexed { index, page ->
            HeaderCard(
                type = page.type,
                title = page.title,
                subtitle = page.subtitle,
                hint = page.hint,
                isAlarmEnabled = page.isAlarmEnabled,
                onAlarmToggle = page.onAlarmToggle
            )

            if (index != pages.lastIndex) {
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}


@Preview(
    name = "Home Header – All Pages", showBackground = true
)
@Composable
fun HomeHeaderSliderPreview_AllPages() {
    HomeHeaderSliderAllPagesPreviewContent(
        pages = previewAllHeaderPages()
    )
}


/*
 Live countdown to next prayer
 Hijri date formatter
 Animated card transitions
 UI test strategy
*/
