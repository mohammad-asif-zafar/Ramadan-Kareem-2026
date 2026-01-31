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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hathway.ramadankareem2026.ui.home.HeaderCard
import com.hathway.ramadankareem2026.ui.home.mapper.buildDynamicPrayerHeader
import com.hathway.ramadankareem2026.ui.home.model.HeaderPage
import com.hathway.ramadankareem2026.ui.home.model.HeaderType
import com.hathway.ramadankareem2026.ui.prayer.PrayerTimeUiMapper
import com.hathway.ramadankareem2026.ui.prayer.PrayerViewModel

import kotlinx.coroutines.delay
import java.time.LocalTime

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
    prayerViewModel: PrayerViewModel = viewModel()
) {

    /**
     * Collect prayer state from ViewModel.
     * StateFlow → Compose State
     */
    val prayerState by prayerViewModel.state.collectAsState()

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
     * Build pager pages dynamically.
     * First page = dynamic prayer card
     * Other pages = static inspirational cards
     */
    val pages = remember(nextPrayer) {
        listOf(

            // Dynamic prayer header
            buildDynamicPrayerHeader(
                prayer = nextPrayer,
                gregorianDate = prayerState.gregorianDate,
                hijriDate = prayerState.hijriDate
            ),

            // Static encouragement card
            HeaderPage(
                type = HeaderType.NEXT_PRAYER,
                title = "Next Prayer",
                subtitle = "Stay connected to Allah",
                hint = "Prayer is better than sleep"
            ),

            // Static reminder card
            HeaderPage(
                type = HeaderType.REMINDER,
                title = "Daily Reminder",
                subtitle = "Increase your dhikr today",
                hint = "Small deeds, big rewards"
            )
        )
    }

    /**
     * Pager state:
     * - initialPage = 0
     * - pageCount driven by pages list size
     */
    val pagerState = rememberPagerState(
        initialPage = 0, pageCount = { pages.size })

    /**
     * Auto-scroll logic.
     * Cycles pages every 4 seconds.
     * Safe because it uses modulo page count.
     */
    LaunchedEffect(pagerState) {
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
                hint = pages[page].hint
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



/*
 Live countdown to next prayer
 Hijri date formatter
 Animated card transitions
 UI test strategy
*/
