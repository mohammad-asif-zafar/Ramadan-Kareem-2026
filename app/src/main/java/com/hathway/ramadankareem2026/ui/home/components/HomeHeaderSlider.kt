package com.hathway.ramadankareem2026.ui.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hathway.ramadankareem2026.ui.home.HeaderCard
import com.hathway.ramadankareem2026.ui.home.model.HeaderPage
import com.hathway.ramadankareem2026.ui.home.model.HeaderType
import com.hathway.ramadankareem2026.ui.home.viewmodel.AlarmViewModel
import com.hathway.ramadankareem2026.ui.home.viewmodel.AlarmViewModelFactory
import kotlinx.coroutines.delay

@Composable
fun HomeHeaderSlider() {
    val context = LocalContext.current
    val alarmViewModel: AlarmViewModel = viewModel(factory = AlarmViewModelFactory())

    LaunchedEffect(Unit) {
        alarmViewModel.initializeAlarmStates(context)
    }
    var pendingAlarmAction by remember { mutableStateOf<(() -> Unit)?>(null) }

    NotificationPermissionHandler(
        pendingAction = pendingAlarmAction, onActionConsumed = {
            pendingAlarmAction = null
        })

    val isIftarAlarmEnabled by alarmViewModel.isIftarAlarmEnabled
    val isSuhoorAlarmEnabled by alarmViewModel.isSuhoorAlarmEnabled
    val alarmTrigger by alarmViewModel.alarmTrigger
    val isAlarmPlaying by alarmViewModel.isAlarmPlaying

    // Simple pages for testing AlarmManager integration
    val pages = remember(alarmTrigger, isAlarmPlaying) {
        val basePages = listOf(

            HeaderPage(
                type = HeaderType.IFTAR_TIME,
                title = "Iftar Time",
                subtitle = "06:30 PM",
                hint = "Time to break your fast",
                isAlarmEnabled = isIftarAlarmEnabled,
                onAlarmToggle = {
                    pendingAlarmAction = {
                        alarmViewModel.toggleIftarAlarm(context)
                    }
                }), HeaderPage(
                type = HeaderType.SUHOOR_TIME,
                title = "Suhoor Time",
                subtitle = "05:30 AM",
                hint = "Time for pre-dawn meal",
                isAlarmEnabled = isSuhoorAlarmEnabled,
                onAlarmToggle = {
                    pendingAlarmAction = {
                        alarmViewModel.toggleSuhoorAlarm(context)
                    }
                }), HeaderPage(
                type = HeaderType.REMINDER,
                title = "Daily Reminder",
                subtitle = "Increase your dhikr today",
                hint = "Small deeds, big rewards"
            )
        )
        
        // Add stop alarm page at the beginning if alarm is playing
        if (isAlarmPlaying) {
            listOf(
                HeaderPage(
                    type = HeaderType.REMINDER,
                    title = "Alarm Playing",
                    subtitle = "Tap to stop alarm",
                    hint = "Alarm is currently playing",
                    isAlarmEnabled = false,
                    onAlarmToggle = { alarmViewModel.stopAlarm(context) }
                )
            ) + basePages
        } else {
            basePages
        }
    }

    val pagerState = rememberPagerState(
        initialPage = if (isAlarmPlaying) 0 else 0, pageCount = { pages.size })

    LaunchedEffect(pagerState) {
        while (true) {
            delay(4_000)
            val nextPage = (pagerState.currentPage + 1) % pages.size
            pagerState.animateScrollToPage(nextPage)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            pageSpacing = 12.dp,
            contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 0.dp)
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

    Spacer(modifier = Modifier.height(20.dp))
}

@Composable
fun HomeHeaderSliderPreview() {
    HomeHeaderSlider()
}
