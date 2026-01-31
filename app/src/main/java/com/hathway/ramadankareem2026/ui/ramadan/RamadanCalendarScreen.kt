package com.hathway.ramadankareem2026.ui.ramadan

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.hathway.ramadankareem2026.R
import com.hathway.ramadankareem2026.ui.components.RamadanToolbar
import com.hathway.ramadankareem2026.ui.ramadan.model.FastingDayStatus
import com.hathway.ramadankareem2026.ui.ramadan.model.RamadanDayUiModel
import com.hathway.ramadankareem2026.ui.theme.RamadanGold
import com.hathway.ramadankareem2026.ui.theme.RamadanGreen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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
    onSettings: () -> Unit,
    onRefresh: () -> Unit = {}
) {
    var expanded by remember { mutableStateOf(false) }
    var isRefreshing by remember { mutableStateOf(false) }
    val visibleDays = if (expanded) days else days.take(12)
    var selectedDay by remember { mutableStateOf<RamadanDayUiModel?>(null) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val gridState = rememberLazyGridState()
    val coroutineScope = rememberCoroutineScope()
    
    // Auto-scroll to today's position
    val todayIndex = days.indexOfFirst { it.status == FastingDayStatus.TODAY || it.status == FastingDayStatus.FASTING }
    LaunchedEffect(todayIndex) {
        if (todayIndex >= 0 && !expanded) {
            gridState.animateScrollToItem(todayIndex)
        }
    }

    Scaffold(
        topBar = {
            EnhancedRamadanCalendarToolbar(
                title = stringResource(R.string.feature_calendar),
                showBack = true,
                onBackClick = onBack,
                onRefreshClick = {
                    isRefreshing = true
                    onRefresh()
                    // Simulate refresh completion
                    coroutineScope.launch {
                        delay(1000)
                        isRefreshing = false
                    }
                },
                onSettingsClick = onSettings,
                isRefreshing = isRefreshing
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Loading State
            if (days.isEmpty() && isRefreshing) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
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
                            text = "Loading Ramadan Calendar...",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            } else {
                // Enhanced Header with Stats
                RamadanCalendarHeader(days = days)
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Calendar Grid
                Box(modifier = Modifier.weight(1f)) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        state = gridState,
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        userScrollEnabled = expanded
                    ) {
                        items(
                            items = visibleDays,
                            key = { it.date.toString() }
                        ) { day ->
                            EnhancedRamadanDayCard(
                                day = day,
                                onClick = { selectedDay = it },
                                isToday = day.status == FastingDayStatus.TODAY || day.status == FastingDayStatus.FASTING
                            )
                        }
                    }
                }
                
                // Expand/Collapse Button with Animation
                if (days.size > 12) {
                    AnimatedVisibility(
                        visible = true,
                        enter = fadeIn() + slideInVertically(),
                        exit = fadeOut() + slideOutVertically()
                    ) {
                        EnhancedExpandButton(
                            expanded = expanded,
                            onClick = { expanded = !expanded },
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(vertical = 16.dp)
                        )
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
            }
        ) {
            EnhancedRamadanDayDetailSheet(
                day = selectedDay!!,
                onDismiss = { selectedDay = null }
            )
        }
    }
}


// Enhanced Toolbar with Refresh
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EnhancedRamadanCalendarToolbar(
    title: String,
    showBack: Boolean,
    onBackClick: () -> Unit,
    onRefreshClick: () -> Unit,
    onSettingsClick: () -> Unit,
    isRefreshing: Boolean
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        navigationIcon = {
            if (showBack) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        },
        actions = {
            // Refresh Button
            IconButton(
                onClick = onRefreshClick,
                enabled = !isRefreshing
            ) {
                if (isRefreshing) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp,
                        color = MaterialTheme.colorScheme.primary
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Refresh",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
            
            // Settings Button
            IconButton(onClick = onSettingsClick) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Settings",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
            titleContentColor = MaterialTheme.colorScheme.onSurface
        )
    )
}

// Enhanced Header with Statistics
@Composable
private fun RamadanCalendarHeader(days: List<RamadanDayUiModel>) {
    val completedDays = days.count { it.status == FastingDayStatus.COMPLETED }
    val currentDay = days.find { it.status == FastingDayStatus.TODAY || it.status == FastingDayStatus.FASTING }
    val totalDays = days.size
    val progress = completedDays.toFloat() / totalDays.toFloat()
    
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(20.dp),
        tonalElevation = 2.dp,
        shadowElevation = 4.dp,
        color = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier.padding(24.dp)
        ) {
            // Title
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Ramadan 2026",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                
                Icon(
                    imageVector = Icons.Default.CalendarToday,
                    contentDescription = null,
                    tint = RamadanGreen,
                    modifier = Modifier.size(28.dp)
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Progress Bar
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Progress",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "$completedDays/$totalDays days",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = RamadanGreen
                    )
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    color = RamadanGreen,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Current Status
            currentDay?.let { day ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Current Day",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "Day ${day.ramadanDay} - ${day.status.name}",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = when (day.status) {
                            FastingDayStatus.FASTING -> RamadanGreen
                            FastingDayStatus.TODAY -> RamadanGold
                            else -> MaterialTheme.colorScheme.onSurfaceVariant
                        }
                    )
                }
            }
        }
    }
}

// Enhanced Expand Button
@Composable
private fun EnhancedExpandButton(
    expanded: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = tween(150),
        label = "button_scale"
    )
    
    Surface(
        modifier = modifier
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            ),
        shape = RoundedCornerShape(20.dp),
        tonalElevation = 2.dp,
        shadowElevation = 4.dp,
        color = MaterialTheme.colorScheme.surface
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 24.dp, vertical = 16.dp)
                .animateContentSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (expanded) "Show Less" else "View Full Calendar",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                color = RamadanGreen
            )
            
            Spacer(modifier = Modifier.width(8.dp))
            
            Icon(
                imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                contentDescription = null,
                tint = RamadanGreen
            )
        }
    }
}

// Enhanced Ramadan Day Card
@Composable
private fun EnhancedRamadanDayCard(
    day: RamadanDayUiModel,
    onClick: (RamadanDayUiModel) -> Unit,
    isToday: Boolean,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    
    val elevation by animateDpAsState(
        targetValue = if (isPressed) 8.dp else if (isToday) 6.dp else 2.dp,
        animationSpec = tween(150),
        label = "card_elevation"
    )
    
    val pulseAlpha by animateFloatAsState(
        targetValue = if (isToday) 0.3f else 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1400, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "today_pulse"
    )
    
    Surface(
        modifier = modifier
            .aspectRatio(1f)
            .background(
                if (isToday) RamadanGreen.copy(alpha = pulseAlpha) else Color.Transparent,
                shape = RoundedCornerShape(20.dp)
            )
            .padding(2.dp)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = { onClick(day) }
            ),
        shape = RoundedCornerShape(20.dp),
        tonalElevation = elevation,
        shadowElevation = elevation,
        color = MaterialTheme.colorScheme.surface
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .border(
                    width = if (isToday) 2.dp else 1.dp,
                    color = if (isToday) RamadanGreen else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                    shape = RoundedCornerShape(18.dp)
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(14.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Day number badge
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .background(
                                color = RamadanGreen.copy(alpha = 0.12f),
                                shape = RoundedCornerShape(12.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "${day.ramadanDay}",
                            style = MaterialTheme.typography.labelMedium,
                            color = RamadanGreen,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(6.dp))
                    
                    // Weekday
                    Text(
                        text = day.weekday.take(3).uppercase(),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center
                    )
                    
                    Spacer(modifier = Modifier.height(2.dp))
                    
                    // Date
                    Text(
                        text = "${day.date.dayOfMonth}",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center
                    )
                }
                
                // Status Indicator
                EnhancedDayStatusIndicator(day.status)
            }
        }
    }
}

// Enhanced Status Indicator
@Composable
private fun EnhancedDayStatusIndicator(status: FastingDayStatus) {
    val (text, color) = when (status) {
        FastingDayStatus.FASTING -> "Fasting" to RamadanGreen
        FastingDayStatus.TODAY -> "Today" to RamadanGold
        FastingDayStatus.UPCOMING -> "Upcoming" to MaterialTheme.colorScheme.onSurfaceVariant
        FastingDayStatus.COMPLETED -> "Done" to RamadanGreen
    }
    
    val backgroundColor = when (status) {
        FastingDayStatus.FASTING -> RamadanGreen.copy(alpha = 0.12f)
        FastingDayStatus.TODAY -> RamadanGold.copy(alpha = 0.12f)
        FastingDayStatus.UPCOMING -> MaterialTheme.colorScheme.surfaceVariant
        FastingDayStatus.COMPLETED -> RamadanGreen.copy(alpha = 0.08f)
    }
    
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(backgroundColor)
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            color = color,
            fontWeight = FontWeight.SemiBold
        )
    }
}

// Enhanced Detail Sheet
@Composable
private fun EnhancedRamadanDayDetailSheet(
    day: RamadanDayUiModel,
    onDismiss: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 20.dp)
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Ramadan Day ${day.ramadanDay}",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = "${day.weekday}, ${day.date.dayOfMonth} ${day.month}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            IconButton(onClick = onDismiss) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Status Chip
        EnhancedStatusChip(day.status)
        
        Spacer(modifier = Modifier.height(20.dp))
        
        // Prayer Times Section
        Text(
            text = "Prayer Times",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface
        )
        
        Spacer(modifier = Modifier.height(12.dp))
        
        EnhancedPrayerTimeCard("Imsak", day.imsak.toString())
        Spacer(modifier = Modifier.height(8.dp))
        EnhancedPrayerTimeCard("Fajr", day.fajr.toString())
        Spacer(modifier = Modifier.height(8.dp))
        EnhancedPrayerTimeCard("Maghrib", day.maghrib.toString())
        
        Spacer(modifier = Modifier.height(20.dp))
    }
}

// Enhanced Status Chip
@Composable
private fun EnhancedStatusChip(status: FastingDayStatus) {
    val (text, color) = when (status) {
        FastingDayStatus.TODAY -> "Today" to RamadanGold
        FastingDayStatus.FASTING -> "Fasting" to RamadanGreen
        FastingDayStatus.UPCOMING -> "Upcoming" to MaterialTheme.colorScheme.onSurfaceVariant
        FastingDayStatus.COMPLETED -> "Completed" to RamadanGreen
    }
    
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = color.copy(alpha = 0.12f)
        ) {
            Text(
                text = text,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
                color = color,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

// Enhanced Prayer Time Card
@Composable
private fun EnhancedPrayerTimeCard(label: String, time: String) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        tonalElevation = 1.dp,
        shadowElevation = 2.dp,
        color = MaterialTheme.colorScheme.surface
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Text(
                text = time,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
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



