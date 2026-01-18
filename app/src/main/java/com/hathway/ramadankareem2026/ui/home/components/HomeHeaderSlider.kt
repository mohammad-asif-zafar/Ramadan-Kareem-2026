package com.hathway.ramadankareem2026.ui.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hathway.ramadankareem2026.ui.home.HeaderCard
import com.hathway.ramadankareem2026.ui.home.mapper.buildDynamicPrayerHeader
import com.hathway.ramadankareem2026.ui.home.model.HeaderPage
import com.hathway.ramadankareem2026.ui.home.model.HeaderType
import com.hathway.ramadankareem2026.ui.prayer.PrayerTimeUiMapper
import com.hathway.ramadankareem2026.ui.prayer.PrayerViewModel
import kotlinx.coroutines.delay


@Composable
fun HomeHeaderSlider(
    prayerViewModel: PrayerViewModel = viewModel()
) {
    val prayerState by prayerViewModel.prayerState.collectAsState()

    // Find current prayer
    val currentPrayer = remember(prayerState) {
        prayerState?.let {
            PrayerTimeUiMapper.map(it).firstOrNull { p -> p.isCurrent }
        }
    }
    val nextPrayer = remember(prayerState) {
        prayerState?.let {
            PrayerTimeUiMapper.map(it).firstOrNull { p -> p.isNext }
        }
    }

    // 🔹 Build pages dynamically
    val pages = remember(currentPrayer) {
        listOf(
            buildDynamicPrayerHeader(nextPrayer),

            HeaderPage(
                type = HeaderType.NEXT_PRAYER,
                title = "Next Prayer",
                subtitle = "Stay connected to Allah",
                hint = "Prayer is better than sleep"
            ),

            HeaderPage(
                type = HeaderType.REMINDER,
                title = "Daily Reminder",
                subtitle = "Increase your dhikr today",
                hint = "Small deeds, big rewards"
            )
        )
    }

    val pagerState = rememberPagerState(
        initialPage = 0, pageCount = { pages.size })

    // ✅ Safe auto-scroll
    LaunchedEffect(pagerState) {
        while (true) {
            delay(4000)
            val nextPage = (pagerState.currentPage + 1) % pages.size
            pagerState.animateScrollToPage(nextPage)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 6.dp, vertical = 6.dp)
    ) {

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            pageSpacing = 12.dp, // 👈 SPACE BETWEEN SLIDES
            contentPadding = PaddingValues(horizontal = 12.dp) // 👈 EDGE SPACE
        ) { page ->

            HeaderCard(/* title = pages[page].title, subtitle = pages[page].subtitle, hint = pages[page].hint*/
                type = pages[page].type,
                title = pages[page].title,
                subtitle = pages[page].subtitle,
                hint = pages[page].hint
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
