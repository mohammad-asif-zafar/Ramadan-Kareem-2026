package com.hathway.ramadankareem2026.ui.home.components

import android.app.Application
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hathway.ramadankareem2026.ui.home.HeaderCard
import com.hathway.ramadankareem2026.ui.home.model.HeaderPage
import com.hathway.ramadankareem2026.ui.home.model.HeaderType
import com.hathway.ramadankareem2026.ui.home.viewmodel.AlarmViewModel
import com.hathway.ramadankareem2026.ui.home.viewmodel.AlarmViewModelFactory
import com.hathway.ramadankareem2026.ui.prayer.PrayerTimeUiMapper
import com.hathway.ramadankareem2026.ui.prayer.PrayerTimeUiMapper.minutesUntil
import com.hathway.ramadankareem2026.ui.prayer.PrayerViewModel
import com.hathway.ramadankareem2026.ui.prayer.data.PrayerViewModelFactory
import com.hathway.ramadankareem2026.ui.tips.presentation.viewmodel.RandomRamadanTipsViewModel
import com.hathway.ramadankareem2026.ui.tips.presentation.viewmodel.TipsViewModelFactory
import kotlinx.coroutines.delay
import java.time.LocalTime
import java.time.format.DateTimeFormatter

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

    val iftarPrayer = prayers.firstOrNull { it.name.equals("Maghrib", true) }
    val suhoorPrayer = prayers.firstOrNull { it.name.equals("Fajr", true) }

    val formatter = remember { DateTimeFormatter.ofPattern("hh:mm a") }

    val iftarTimeText = iftarPrayer?.time?.format(formatter) ?: "--:--"
    val suhoorTimeText = suhoorPrayer?.time?.format(formatter) ?: "--:--"

    // 🔔 Alarm states
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

    // Pages
    val pages = remember(
        alarmTrigger, isAlarmPlaying, iftarTimeText, suhoorTimeText
    ) {
        val basePages = listOf(
            HeaderPage(
                type = HeaderType.IFTAR_TIME,
                title = "Iftar Time",
                subtitle = iftarTimeText,
                hint = "Time to break your fast",
                isAlarmEnabled = isIftarAlarmEnabled,
                onAlarmToggle = {
                    pendingAlarmAction = {
                        alarmViewModel.toggleIftarAlarm(context)
                    }
                }), HeaderPage(
                type = HeaderType.SUHOOR_TIME,
                title = "Suhoor Time",
                subtitle = suhoorTimeText,
                hint = "Time for pre-dawn meal",
                isAlarmEnabled = isSuhoorAlarmEnabled,
                onAlarmToggle = {
                    pendingAlarmAction = {
                        alarmViewModel.toggleSuhoorAlarm(context)
                    }
                }),
            HeaderPage(
                type = HeaderType.REMINDER,
                title = "Daily Reminder",
                subtitle = "",
                hint = ""
            )
        )

        if (isAlarmPlaying) {
            listOf(
                HeaderPage(
                    type = HeaderType.REMINDER,
                    title = "Alarm Playing",
                    subtitle = "Tap to stop alarm",
                    hint = "Alarm is currently playing",
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

    // 🧱 UI
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

