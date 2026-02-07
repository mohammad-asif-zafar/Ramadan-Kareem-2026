package com.hathway.ramadankareem2026.ui.home.components

import android.app.Application
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hathway.ramadankareem2026.R
import com.hathway.ramadankareem2026.ui.home.HeaderCard
import com.hathway.ramadankareem2026.ui.home.mapper.buildDynamicPrayerHeader
import com.hathway.ramadankareem2026.ui.home.model.HeaderPage
import com.hathway.ramadankareem2026.ui.home.model.HeaderType
import com.hathway.ramadankareem2026.ui.home.viewmodel.AlarmViewModel
import com.hathway.ramadankareem2026.ui.home.viewmodel.AlarmViewModelFactory
import com.hathway.ramadankareem2026.ui.prayer.PrayerTimeUiMapper
import com.hathway.ramadankareem2026.ui.prayer.PrayerType
import com.hathway.ramadankareem2026.ui.prayer.PrayerViewModel
import com.hathway.ramadankareem2026.ui.prayer.data.PrayerViewModelFactory
import kotlinx.coroutines.delay
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import com.hathway.ramadankareem2026.ui.prayer.PrayerTimeUiMapper.formatDuration

private const val TAG = "HomeHeaderSlider"

@Composable
fun HomeHeaderSlider() {

    val context = LocalContext.current
    val app = context.applicationContext as Application

    // Alarm ViewModel
    val alarmViewModel: AlarmViewModel = viewModel(
        factory = AlarmViewModelFactory()
    )

    // Prayer ViewModel
    val prayerViewModel: PrayerViewModel = viewModel(
        factory = PrayerViewModelFactory(app)
    )

    //  Keep time fresh (important!)
    var now by remember { mutableStateOf(LocalTime.now()) }
    LaunchedEffect(Unit) {
        while (true) {
            delay(60_000)
            now = LocalTime.now()
        }
    }

    // Prayer state
    val prayerState by prayerViewModel.state.collectAsState()
    val prayers = remember(prayerState, now) {
        PrayerTimeUiMapper.map(prayerState, now)
    }

    val iftarPrayer = prayers.firstOrNull { it.type == PrayerType.MAGHRIB }
    val suhoorPrayer = prayers.firstOrNull { it.type == PrayerType.FAJR }


    val formatter = remember { DateTimeFormatter.ofPattern("hh:mm a") }

    val iftarTimeText = iftarPrayer?.time?.format(formatter) ?: "--:--"
    val suhoorTimeText = suhoorPrayer?.time?.format(formatter) ?: "--:--"

    // Alarm states
    LaunchedEffect(Unit) {
        alarmViewModel.initializeAlarmStates(context)
    }

    val isIftarAlarmEnabled by alarmViewModel.isIftarAlarmEnabled
    val isSuhoorAlarmEnabled by alarmViewModel.isSuhoorAlarmEnabled
    val isAlarmPlaying by alarmViewModel.isAlarmPlaying
    val alarmTrigger by alarmViewModel.alarmTrigger

    //  Pending action for permission
    var pendingAlarmAction by remember { mutableStateOf<(() -> Unit)?>(null) }

    NotificationPermissionHandler(
        pendingAction = pendingAlarmAction, onActionConsumed = { pendingAlarmAction = null })


    val mappedPrayers = remember(prayerState) {
        PrayerTimeUiMapper.map(
            state = prayerState, now = now
        )
    }

    val nextPrayer = remember(mappedPrayers) {
        mappedPrayers.firstOrNull { it.isNext }
    }
    val iftarTitle = stringResource(R.string.iftar_time)
    val iftarHint = stringResource(R.string.iftar_hint)

    val suhoorTitle = stringResource(R.string.suhoor_time)
    val suhoorHint = stringResource(R.string.suhoor_hint)

    val dailyReminderTitle = stringResource(R.string.daily_reminder)

    val alarmPlayingTitle = stringResource(R.string.alarm_playing)
    val alarmPlayingSubtitle = stringResource(R.string.alarm_playing_subtitle)
    val alarmPlayingHint = stringResource(R.string.alarm_playing_hint)

    val ramadanTitle = stringResource(R.string.ramadan_kareem)
    val calculatingText = stringResource(R.string.calculating_prayer)

    val prayerTypeText = nextPrayer?.type?.let {
        when (it) {
            PrayerType.FAJR -> stringResource(R.string.prayer_fajr)
            PrayerType.DHUHR -> stringResource(R.string.prayer_dhuhr)
            PrayerType.ASR -> stringResource(R.string.prayer_asr)
            PrayerType.MAGHRIB -> stringResource(R.string.prayer_maghrib)
            PrayerType.ISHA -> stringResource(R.string.prayer_isha)
        }
    } ?: ""

    val remainingText = nextPrayer?.remainingMinutes?.let { minutes ->
        formatRemaining(
            minutes = minutes,
            isCurrent = nextPrayer.isCurrent,
            getString = { id, args ->
                stringResource(id, *args)
            }
        )
    } ?: ""
    // Pages
    val pages = remember(
        alarmTrigger, isAlarmPlaying, iftarTimeText, suhoorTimeText
    ) {

        val basePages = listOf(

            buildDynamicPrayerHeader(
                prayer = nextPrayer,
                gregorianDate = prayerState.gregorianDate,
                hijriDate = prayerState.hijriDate,
                title = ramadanTitle,
                calculatingText = calculatingText,
                prayerTypeText = prayerTypeText,
                remainingText = remainingText
            ),

            HeaderPage(
                type = HeaderType.IFTAR_TIME,
                title = iftarTitle,
                subtitle = iftarTimeText,
                hint = iftarHint,
                isAlarmEnabled = isIftarAlarmEnabled,
                onAlarmToggle = {
                    pendingAlarmAction = {
                        alarmViewModel.toggleIftarAlarm(context)
                    }
                }),

            HeaderPage(
                type = HeaderType.SUHOOR_TIME,
                title = suhoorTitle,
                subtitle = suhoorTimeText,
                hint = suhoorHint,
                isAlarmEnabled = isSuhoorAlarmEnabled,
                onAlarmToggle = {
                    pendingAlarmAction = {
                        alarmViewModel.toggleSuhoorAlarm(context)
                    }
                }),

            HeaderPage(
                type = HeaderType.REMINDER, title = dailyReminderTitle, subtitle = "", hint = ""
            )
        )

        if (isAlarmPlaying) {
            listOf(
                HeaderPage(
                    type = HeaderType.REMINDER,
                    title = alarmPlayingTitle,
                    subtitle = alarmPlayingSubtitle,
                    hint = alarmPlayingHint,
                    onAlarmToggle = {
                        alarmViewModel.stopAlarm(context)
                    })
            ) + basePages
        } else {
            basePages
        }
    }

    // 📜 Pager
    val pagerState = rememberPagerState(
        pageCount = { pages.size })

    LaunchedEffect(pagerState) {
        while (true) {
            delay(4_000)
            pagerState.animateScrollToPage(
                (pagerState.currentPage + 1) % pages.size
            )
        }
    }

    // UI
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        HorizontalPager(
            state = pagerState, modifier = Modifier.height(150.dp)
        ) { page ->
            HeaderCard(
                type = pages[page].type,
                title = pages[page].title,
                subtitle = pages[page].subtitle,
                hint = pages[page].hint,
                isAlarmEnabled = pages[page].isAlarmEnabled,
                onAlarmToggle = pages[page].onAlarmToggle
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
@Composable
fun formatRemaining(
    minutes: Int?,
    isCurrent: Boolean,
    getString: @Composable (resId: Int, args: Array<out Any>) -> String
): String = when {
    minutes == null ->
        getString(R.string.time_dash, emptyArray())

    isCurrent ->
        getString(R.string.time_now, emptyArray())

    minutes < 0 ->
        getString(R.string.time_passed, emptyArray())

    else ->
        getString(
            R.string.time_starts_in,
            arrayOf(formatDuration(minutes))
        )
}


