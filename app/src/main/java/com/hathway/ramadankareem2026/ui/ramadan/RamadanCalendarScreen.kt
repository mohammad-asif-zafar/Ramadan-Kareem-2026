package com.hathway.ramadankareem2026.ui.ramadan

import android.annotation.SuppressLint
import androidx.compose.animation.core.EaseOutQuart
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.NightsStay
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.hathway.ramadankareem2026.R
import com.hathway.ramadankareem2026.core.location.LocationProvider
import com.hathway.ramadankareem2026.ui.components.RamadanToolbar
import com.hathway.ramadankareem2026.ui.components.ToolbarIcon
import com.hathway.ramadankareem2026.ui.ramadan.model.FastingDayStatus
import com.hathway.ramadankareem2026.ui.ramadan.model.RamadanDayUiModel
import com.hathway.ramadankareem2026.ui.theme.Emerald
import com.hathway.ramadankareem2026.ui.theme.Gold
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RamadanCalendarScreen(
    navController: NavController,
    days: List<RamadanDayUiModel>,
    ramadanDays: List<RamadanDayUiModel> = emptyList(),
    isLoading: Boolean = false,
    error: String? = null,
    isHijriMode: Boolean = true,
    monthTitle: String = "Ramadan 1448 هـ",
    monthSubtitle: String = "February – March 2027",
    onTabSelected: (Boolean) -> Unit = {},
    onNextMonth: () -> Unit = {},
    onPrevMonth: () -> Unit = {},
    onBack: () -> Unit,
    onViewFullCalendar: () -> Unit,
    onSettings: () -> Unit,
    onRefresh: () -> Unit = {},
    onLocationUpdate: (Double, Double) -> Unit = { _, _ -> },
    onClearError: () -> Unit = {}
) {
    var showProgressHeader by remember { mutableStateOf(false) }
    var selectedDay by remember { mutableStateOf<RamadanDayUiModel?>(null) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val gridState = rememberLazyGridState()

    // Location detection for dynamic prayer times
    val context = LocalContext.current
    val locationProvider = remember { LocationProvider(context) }

    // Auto-detect location on first load
    LaunchedEffect(Unit) {
        try {
            val location = locationProvider.fetchLocation()
            location?.let {
                onLocationUpdate(it.latitude, it.longitude)
            }
        } catch (e: Exception) {
            // Silently fail - will use default prayer times
        }
    }

    // Handle error display
    LaunchedEffect(error) {
        error?.let {
            // Could show a snackbar or toast here
        }
    }

    // Auto-scroll to today's position
    val todayIndex =
        days.indexOfFirst { it.status == FastingDayStatus.TODAY || it.status == FastingDayStatus.FASTING }
    LaunchedEffect(todayIndex) {
        if (todayIndex >= 0) {
            gridState.animateScrollToItem(todayIndex)
        }
    }

    val isDark = MaterialTheme.colorScheme.background.luminance() < 0.5f
    val bgDrawable = if (isDark) R.drawable.calendar_bg_dark else R.drawable.calendar_bg_light
    val todayModel = days.firstOrNull { it.status == FastingDayStatus.TODAY || it.status == FastingDayStatus.FASTING } ?: days.firstOrNull()

    Scaffold(topBar = {
        EnhancedRamadanCalendarToolbar(
            title = "Islamic Calendar",
            showBack = true,
            onBackClick = onBack,
            onToggleHeader = { showProgressHeader = !showProgressHeader },
            showHeader = showProgressHeader,
            onSettingsClick = onSettings
        )
    }) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .paint(
                    painter = painterResource(bgDrawable),
                    contentScale = ContentScale.Crop
                )
                .padding(padding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Loading State
                if (days.isEmpty() && isLoading) {
                    Box(
                        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(48.dp),
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "Loading Islamic Calendar...",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                } else if (showProgressHeader && ramadanDays.isNotEmpty()) {
                    // When Ramadan Toggle is ON: Show Ramadan Progress Header and 3-Column Ramadan 2027 Grid in a single unified scrollable LazyVerticalGrid supporting both light and dark themes
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        contentPadding = PaddingValues(bottom = 24.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        userScrollEnabled = true
                    ) {
                        item(span = { GridItemSpan(3) }) {
                            RamadanProgressHeader(days = ramadanDays)
                        }
                        item(span = { GridItemSpan(3) }) {
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "🌙 Ramadan 2027 - 30-Day Journey",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = if (isDark) Color.White else Color(0xFF101820)
                            )
                        }
                        items(ramadanDays.size) { index ->
                            val day = ramadanDays[index]
                            RamadanProgressCard(day = day, onClick = { selectedDay = day })
                        }
                    }
                } else {
                    // When Ramadan Toggle is OFF: Show Full Calendar view (untouched 7-column calendar)
                    // Top Tab Switcher: Hijri Calendar vs Gregorian
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = if (isDark) Color(0xFF132220) else Color(0xFFE8ECEB),
                                shape = RoundedCornerShape(24.dp)
                            )
                            .padding(4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(20.dp))
                                .background(if (isHijriMode) Emerald else Color.Transparent)
                                .clickable { onTabSelected(true) }
                                .padding(vertical = 10.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Hijri Calendar",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                color = if (isHijriMode) Color.White else Color.Gray
                            )
                        }

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(20.dp))
                                .background(if (!isHijriMode) Emerald else Color.Transparent)
                                .clickable { onTabSelected(false) }
                                .padding(vertical = 10.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Gregorian",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                color = if (!isHijriMode) Color.White else Color.Gray
                            )
                        }
                    }

                    // Month Navigation Header matching reference UI (with working < and > clicks)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = if (isDark) Color(0xFF132220).copy(alpha = 0.85f) else Color.White.copy(alpha = 0.85f),
                                shape = RoundedCornerShape(16.dp)
                            )
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = onPrevMonth) {
                            Icon(imageVector = Icons.Default.ChevronLeft, contentDescription = "Previous Month", tint = Emerald)
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(text = "🌙", fontSize = 24.sp)
                            Column(horizontalAlignment = Alignment.Start) {
                                Text(
                                    text = monthTitle,
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                    color = if (isDark) Color.White else Color(0xFF101820)
                                )
                                Text(
                                    text = monthSubtitle,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Emerald
                                )
                            }
                        }
                        IconButton(onClick = onNextMonth) {
                            Icon(imageVector = Icons.Default.ChevronRight, contentDescription = "Next Month", tint = Emerald)
                        }
                    }

                    // Weekday Header Row (Sun, Mon, Tue, Wed, Thu, Fri, Sat)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        val weekdays = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
                        weekdays.forEachIndexed { index, day ->
                            Text(
                                text = day,
                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                color = if (index == 0) Emerald else Color.White.copy(alpha = 0.6f),
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    // 7-Column Calendar Grid matching reference UI
                    if (days.isNotEmpty()) {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(7),
                            state = gridState,
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                            contentPadding = PaddingValues(bottom = 12.dp),
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp),
                            userScrollEnabled = true
                        ) {
                            items(days.size) { index ->
                                val day = days[index]
                                RamadanDayCard(
                                    day = day, onClick = { selectedDay = day })
                            }
                        }
                    }

                    // Today / Selected Date Summary Card at Bottom
                    if (todayModel != null) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { selectedDay = todayModel },
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (isDark) Color(0xFF112220).copy(alpha = 0.95f) else MaterialTheme.colorScheme.surfaceVariant
                            ),
                            border = androidx.compose.foundation.BorderStroke(1.dp, Emerald.copy(alpha = 0.4f))
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(42.dp)
                                            .background(Emerald.copy(alpha = 0.2f), RoundedCornerShape(12.dp)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.CalendarMonth,
                                            contentDescription = null,
                                            tint = Emerald,
                                            modifier = Modifier.size(24.dp)
                                        )
                                    }
                                    Column {
                                        val formattedDate = todayModel.date.format(DateTimeFormatter.ofPattern("EEE, d MMM yyyy", Locale.ENGLISH))
                                        Text(
                                            text = formattedDate,
                                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                            color = if (isDark) Color.White else MaterialTheme.colorScheme.onSurface
                                        )
                                        Text(
                                            text = "${todayModel.ramadanDay} Ramadan 1448 هـ",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = Emerald
                                        )
                                    }
                                }
                                Icon(
                                    imageVector = Icons.Default.ChevronRight,
                                    contentDescription = null,
                                    tint = if (isDark) Color.White.copy(alpha = 0.6f) else MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }

                    // Events Section at Bottom
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Event,
                                    contentDescription = null,
                                    tint = Emerald,
                                    modifier = Modifier.size(20.dp)
                                )
                                Text(
                                    text = "Events",
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                    color = if (isDark) Color.White else MaterialTheme.colorScheme.onSurface
                                )
                            }
                            Text(
                                text = "View All",
                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                color = Emerald,
                                modifier = Modifier.clickable { }
                            )
                        }

                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (isDark) Color(0xFF112220).copy(alpha = 0.95f) else MaterialTheme.colorScheme.surfaceVariant
                            )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Text(text = "🌙", fontSize = 28.sp)
                                Column {
                                    Text(
                                        text = "Ramadan (1 – 30 Ramadan)",
                                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                        color = if (isDark) Color.White else MaterialTheme.colorScheme.onSurface
                                    )
                                    Text(
                                        text = "8 Feb – 9 Mar 2027",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = if (isDark) Color.White.copy(alpha = 0.6f) else MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Bottom Sheet for Day Details
    if (selectedDay != null) {
        ModalBottomSheet(
            onDismissRequest = { selectedDay = null },
            sheetState = sheetState,
            dragHandle = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .width(40.dp)
                            .height(4.dp)
                            .clip(RoundedCornerShape(50))
                            .background(MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f))
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }) {
            RamadanDayDetailSheet(day = selectedDay!!)
        }
    }
}

@Composable
private fun RamadanProgressCard(day: RamadanDayUiModel, onClick: (RamadanDayUiModel) -> Unit) {
    val isToday = day.status == FastingDayStatus.TODAY || day.status == FastingDayStatus.FASTING
    val isDark = MaterialTheme.colorScheme.background.luminance() < 0.5f

    Card(
        modifier = Modifier
            .aspectRatio(0.85f)
            .shadow(
                elevation = if (isToday) 8.dp else 2.dp,
                shape = RoundedCornerShape(24.dp),
                ambientColor = if (isToday) Emerald.copy(alpha = 0.5f) else Color.Transparent,
                spotColor = if (isToday) Emerald.copy(alpha = 0.5f) else Color.Transparent
            )
            .clickable { onClick(day) }
            .border(
                width = if (isToday) 2.dp else 1.dp,
                color = if (isToday) Emerald else MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
                shape = RoundedCornerShape(24.dp)
            ),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = when {
                isToday -> if (isDark) Color(0xFF13322B) else Color(0xFFE8F5E9)
                else -> if (isDark) Color(0xFF112220).copy(alpha = 0.95f) else MaterialTheme.colorScheme.surfaceVariant
            }
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Day X (e.g. Day 1)
            Text(
                text = "Day ${day.ramadanDay}",
                style = MaterialTheme.typography.labelSmall.copy(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                ),
                color = if (isToday && !isDark) Color(0xFF2E7D32) else Gold
            )

            // Gregorian Day Number (e.g. 8)
            Text(
                text = day.date.dayOfMonth.toString(),
                style = MaterialTheme.typography.titleLarge.copy(
                    fontSize = 28.sp,
                    fontWeight = FontWeight.ExtraBold
                ),
                color = if (isToday && !isDark) Color(0xFF1B3D36) else (if (isDark) Color.White else Color(0xFF101820))
            )

            // Weekday & Month (e.g. MON February)
            Text(
                text = "${day.date.dayOfWeek.name.take(3).uppercase()} ${day.date.month.name.lowercase().replaceFirstChar { it.uppercase() }}",
                style = MaterialTheme.typography.labelSmall.copy(
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Medium
                ),
                color = if (isDark) Color.White.copy(alpha = 0.6f) else MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )

            // Ramadan Day (e.g. 1 Ramadan)
            Text(
                text = "${day.ramadanDay} Ramadan",
                style = MaterialTheme.typography.labelMedium.copy(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                ),
                color = Emerald
            )
        }
    }
}


// Enhanced Toolbar with Ramadan Month Toggle Button
@Composable
private fun EnhancedRamadanCalendarToolbar(
    title: String,
    showBack: Boolean,
    onBackClick: () -> Unit,
    onToggleHeader: () -> Unit,
    showHeader: Boolean,
    onSettingsClick: () -> Unit
) {
    val isDark = MaterialTheme.colorScheme.background.luminance() < 0.5f
    RamadanToolbar(
        title = title,
        showBack = showBack,
        onBackClick = onBackClick,
        rightIcon1 = ToolbarIcon.Vector(Icons.Filled.NightsStay),
        rightIcon1Badge = if (showHeader) 1 else null, // Highlight indicator when header is toggled on
        onRightIcon1Click = onToggleHeader,
        rightIcon2 = ToolbarIcon.Vector(Icons.Default.Settings),
        onRightIcon2Click = onSettingsClick,
        backgroundColor = if (isDark) Color(0xFF0F1720) else Color.White,
        contentColor = if (isDark) Color.White else Color.Black
    )
}


@Composable
private fun RamadanProgressHeader(
    days: List<RamadanDayUiModel>
) {
    val isDark = MaterialTheme.colorScheme.background.luminance() < 0.5f
    val completedDays = days.count { it.status == FastingDayStatus.COMPLETED }
    val totalDays = days.size
    val progress = if (totalDays > 0) completedDays.toFloat() / totalDays.toFloat() else 0f

    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(1000, easing = EaseOutQuart),
        label = "ramadan_progress"
    )

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        color = if (isDark) Color(0xFF1B252E) else Color.White,
        shadowElevation = 8.dp,
        tonalElevation = 2.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {

            /* ---------------- Header ---------------- */

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(52.dp)
                            .background(Gold.copy(alpha = 0.1f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.NightsStay,
                            contentDescription = null,
                            modifier = Modifier.size(28.dp),
                            tint = Gold
                        )
                    }

                    Column {
                        Text(
                            text = stringResource(R.string.ramadan_title),
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 22.sp
                            ),
                            color = if (isDark) Color.White else Color(0xFF101820)
                        )
                        Text(
                            text = stringResource(R.string.ramadan_progress_subtitle),
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            /* ---------------- Progress Bar ---------------- */

            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = completedDays.toString(),
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp
                        ),
                        color = Emerald
                    )
                    
                    Text(
                        text = "${(animatedProgress * 100).toInt()}%",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp
                        ),
                        color = Gold
                    )
                }
                
                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(14.dp)
                        .clip(RoundedCornerShape(7.dp))
                        .background(
                            if (isDark) Color.White.copy(alpha = 0.05f)
                            else Color(0xFFF1F4F2)
                        )
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(animatedProgress)
                            .fillMaxHeight()
                            .background(
                                Brush.horizontalGradient(
                                    listOf(Emerald, Gold)
                                )
                            )
                    )
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.days_completed),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                    )
                    Text(
                        text = stringResource(R.string.progress),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                    )
                }
            }
        }
    }
}

@Preview(
    showBackground = true, backgroundColor = 0xFFF5F5F5, name = "Ramadan Calendar – Full Month"
)
@Composable
private fun PreviewRamadanCalendarGrid() {
    MaterialTheme {
        val days = previewRamadanMonth()

        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            items(days.size) { dayIndex ->
                val day = days[dayIndex]
                RamadanDayCard(
                    day = day,
                    onClick = { }
                )
            }
        }
    }
}

private fun previewRamadanMonth(): List<RamadanDayUiModel> {
    val startDate = LocalDate.of(2027, 2, 8)

    return (1..30).map { day ->
        RamadanDayUiModel(
            ramadanDay = day,
            date = startDate.plusDays((day - 1).toLong()),
            weekday = startDate.plusDays((day - 1).toLong()).dayOfWeek.name.take(3),
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
