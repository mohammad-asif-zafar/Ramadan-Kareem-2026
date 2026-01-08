package com.hathway.ramadankareem2026.ui.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material.icons.outlined.NightsStay
import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.material.icons.outlined.WbTwilight
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.hathway.ramadankareem2026.ui.home.HeaderCard
import com.hathway.ramadankareem2026.ui.home.model.HeaderPage
import com.hathway.ramadankareem2026.ui.home.components.PagerDots
import com.hathway.ramadankareem2026.ui.home.model.PrayerTimeModel
import com.hathway.ramadankareem2026.ui.theme.RamadanGold
import kotlinx.coroutines.delay

private val prayerTimes = listOf(
    PrayerTimeModel("Fajr", "5:45", Icons.Outlined.WbTwilight),
    PrayerTimeModel("Dhuhr", "1:15", Icons.Outlined.LightMode),
    PrayerTimeModel("Asr", "4:40", Icons.Outlined.WbSunny),
    PrayerTimeModel("Maghrib", "7:10", Icons.Outlined.NightsStay
        , isCurrent = true // 👈 highlight this
    ),
    PrayerTimeModel("Isha", "8:25", Icons.Outlined.DarkMode)
)

@Composable
fun PrayerTimeSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        SectionTitle("Prayer Times")

        Spacer(modifier = Modifier.height(12.dp))

        Card(
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                prayerTimes.forEach {
                    PrayerItem(it)
                }
            }
        }
    }

    Spacer(modifier = Modifier.height(20.dp))
}

@Composable
fun PrayerItem(item: PrayerTimeModel) {

    val backgroundColor =
        if (item.isCurrent) Color(0xFFE6F4EA) else Color.Transparent

    val iconTint =
        if (item.isCurrent) Color(0xFF2E7D32) else MaterialTheme.colorScheme.onSurface

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(backgroundColor)
            .padding(horizontal = 12.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = item.icon,
            contentDescription = item.name,
            modifier = Modifier.size(24.dp),
            tint = iconTint
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = item.name,
            style = MaterialTheme.typography.labelSmall,
            color = iconTint
        )

        Text(
            text = item.time,
            style = MaterialTheme.typography.bodySmall,
            color = iconTint
        )

        if (item.isCurrent) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "NEXT",
                style = MaterialTheme.typography.labelSmall,
                color = Color(0xFF2E7D32)
            )
        }
    }
}

@Composable
fun HomeHeaderSlider() {
    val pages = listOf(
        HeaderPage(
            title = "Ramadan Kareem 🌙",
            subtitle = "Maghrib in 32 minutes",
            hint = "Prepare for Iftar & prayer"
        ),
        HeaderPage(
            title = "Next Prayer",
            subtitle = "Maghrib • 18:36",
            hint = "Don’t miss the blessing"
        ),
        HeaderPage(
            title = "Daily Reminder",
            subtitle = "Increase your dhikr today",
            hint = "Small deeds, big rewards"
        )
    )

    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { pages.size }
    )

    // ✅ Safe auto-scroll
    LaunchedEffect(pagerState) {
        while (true) {
            delay(3500)
            val nextPage = (pagerState.currentPage + 1) % pages.size
            pagerState.animateScrollToPage(nextPage)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        ) { page ->

            HeaderCard(
                title = pages[page].title,
                subtitle = pages[page].subtitle,
                hint = pages[page].hint
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        PagerDots(
            totalDots = pages.size,
            selectedIndex = pagerState.currentPage
        )
    }

    Spacer(modifier = Modifier.height(20.dp))
}