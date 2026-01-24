package com.hathway.ramadankareem2026.ui.ramadan

import android.annotation.SuppressLint
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.hathway.ramadankareem2026.R
import com.hathway.ramadankareem2026.ui.components.RamadanToolbar
import com.hathway.ramadankareem2026.ui.ramadan.model.FastingDayStatus
import com.hathway.ramadankareem2026.ui.ramadan.model.RamadanDayUiModel
import java.time.LocalDate
import java.time.LocalTime

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RamadanCalendarScreen(
    navController: NavController,
    days: List<RamadanDayUiModel>,
    onBack: () -> Unit,
    onViewFullCalendar: () -> Unit,
    onSettings: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val visibleDays = if (expanded) days else days.take(10)

    var selectedDay by remember { mutableStateOf<RamadanDayUiModel?>(null) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    Scaffold(
        topBar = {
            RamadanToolbar(
                title = stringResource(R.string.feature_calendar),
                showBack = true,
                onBackClick = onBack,
                rightIcon1 = R.drawable.bell,
                onRightIcon1Click = onViewFullCalendar,
                rightIcon2 =  R.drawable.bell,
                onRightIcon2Click = onSettings
            )
        }) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .animateContentSize(
                    animationSpec = tween(durationMillis = 300)
                )
        ) {

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),   // 👈 IMPORTANT
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                userScrollEnabled = expanded
            ) {
                items(items = visibleDays, key = { it.date } // or it.ramadanDay
                ) { day ->
                    RamadanDayCard(
                        day = day, onClick = { selectedDay = it })
                }
            }

            // 🔽 ALWAYS VISIBLE CTA
            if (days.size > 10) {
                TextButton(
                    onClick = { expanded = !expanded },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(vertical = 8.dp)
                ) {
                    Text(
                        text = if (expanded) "Show less ↑" else "View full calendar ↓",
                        color = Color(0xFF2E7D32),
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }


    }
    if (selectedDay != null) {
        ModalBottomSheet(
            onDismissRequest = { selectedDay = null }, sheetState = sheetState
        ) {
            RamadanDayDetailSheet(day = selectedDay!!)
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun PreviewRamadanCalendarScreen() {
    RamadanCalendarScreen(
        navController = rememberNavController(),
        days = previewDays(),
        onBack = {},
        onViewFullCalendar = {},
        onSettings = {})
}

private fun previewDays(): List<RamadanDayUiModel> {
    val today = LocalDate.now()

    return (1..15).map { day ->
        RamadanDayUiModel(
            ramadanDay = day,
            date = today.plusDays(day.toLong()),
            weekday = today.plusDays(day.toLong()).dayOfWeek.name.take(3),
            month = "February",
            imsak = LocalTime.of(5, 40),
            fajr = LocalTime.of(5, 50),
            maghrib = LocalTime.of(18, 45),
            status = when (day) {
                3 -> FastingDayStatus.TODAY
                4 -> FastingDayStatus.FASTING
                in 1..2 -> FastingDayStatus.COMPLETED
                else -> FastingDayStatus.UPCOMING
            },
            totalMinutes = 780,
            remainingMinutes = if (day == 4) 150 else 0
        )
    }
}

private fun previewRamadanMonth(): List<RamadanDayUiModel> {
    val startDate = LocalDate.of(2026, 3, 1)

    return (1..30).map { day ->
        RamadanDayUiModel(
            ramadanDay = day,
            date = startDate.plusDays((day - 1).toLong()),
            weekday = startDate.plusDays((day - 1).toLong())
                .dayOfWeek.name.take(3),
            month = "March",
            imsak = LocalTime.of(5, 30),
            fajr = LocalTime.of(5, 40),
            maghrib = LocalTime.of(18, 45),
            status = when (day) {
                5 -> FastingDayStatus.TODAY
                6 -> FastingDayStatus.FASTING
                in 1..4 -> FastingDayStatus.COMPLETED
                else -> FastingDayStatus.UPCOMING
            },
            totalMinutes = 780,
            remainingMinutes = if (day == 6) 150 else 0
        )
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFF5F5F5,
    name = "Ramadan Calendar – Full Month"
)
@Composable
private fun PreviewRamadanCalendarGrid() {

    val days = previewRamadanMonth()

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(
            items = days,
            key = { it.ramadanDay }
        ) { day ->
            RamadanDayCard(
                day = day,
                onClick = {}
            )
        }
    }
}



