package com.hathway.ramadankareem2026.ui.ramadan

import android.annotation.SuppressLint
import androidx.compose.animation.core.EaseOutQuart
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
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
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.NightsStay
import androidx.compose.material.icons.filled.Today
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
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
    isLoading: Boolean = false,
    error: String? = null,
    onBack: () -> Unit,
    onViewFullCalendar: () -> Unit,
    onSettings: () -> Unit,
    onRefresh: () -> Unit = {},
    onLocationUpdate: (Double, Double) -> Unit = { _, _ -> },
    onClearError: () -> Unit = {}
) {
    var expanded by remember { mutableStateOf(false) }
    var isRefreshing by remember { mutableStateOf(false) }
    val visibleDays = if (expanded) days else days.take(12)
    var selectedDay by remember { mutableStateOf<RamadanDayUiModel?>(null) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val gridState = rememberLazyGridState()
    val coroutineScope = rememberCoroutineScope()
    
    // Location detection for dynamic prayer times
    val context = LocalContext.current
    val locationProvider = remember { com.hathway.ramadankareem2026.core.location.LocationProvider(context) }
    
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
    val todayIndex = days.indexOfFirst { it.status == FastingDayStatus.TODAY || it.status == FastingDayStatus.FASTING }
    LaunchedEffect(todayIndex) {
        if (todayIndex >= 0 && !expanded) {
            gridState.animateScrollToItem(todayIndex)
        }
    }

    Scaffold(
        topBar = {
            EnhancedRamadanCalendarToolbar(
                title = stringResource(R.string.ramadan_calendar),
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
        },
        floatingActionButton = {
            if (days.isNotEmpty()) {
                ExpandCollapseFloatingButton(
                    expanded = expanded,
                    onClick = { expanded = !expanded }
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(top = 48.dp), // More space after toolbar
            verticalArrangement = Arrangement.spacedBy(8.dp) // Reduced from 16dp to 8dp
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
                // Ramadan Progress Header
                if (days.isNotEmpty()) {
                    RamadanProgressHeader(days = days)
                    
                    Spacer(modifier = Modifier.height(8.dp)) // Reduced from 16dp to 8dp
                }
                
                // Calendar Grid
                if (days.isNotEmpty()) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        state = gridState,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(if (expanded) 600.dp else 240.dp), // Increased collapsed height
                        contentPadding = PaddingValues(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        userScrollEnabled = expanded
                    ) {
                        items(if (expanded) days.size else 6) { index ->
                            val day = days[index]
                            RamadanDayCard(
                                day = day,
                                onClick = { selectedDay = day }
                            )
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
@Composable
private fun EnhancedRamadanCalendarToolbar(
    title: String,
    showBack: Boolean,
    onBackClick: () -> Unit,
    onRefreshClick: () -> Unit,
    onSettingsClick: () -> Unit,
    isRefreshing: Boolean
) {
    RamadanToolbar(
        title = title,
        showBack = showBack,
        onBackClick = onBackClick,
        onRightIcon1Click = { if (!isRefreshing) onRefreshClick() },
        onRightIcon2Click = onSettingsClick,
        backgroundColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface
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
        tonalElevation = 3.dp,
        shadowElevation = 6.dp,
        color = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier.padding(24.dp)
        ) {
            // Enhanced Title Section with Icons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Enhanced Ramadan moon icon
                    Surface(
                        modifier = Modifier.size(36.dp),
                        shape = RoundedCornerShape(10.dp),
                        color = RamadanGold.copy(alpha = 0.15f)
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "🌙",
                                style = MaterialTheme.typography.titleLarge
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Text(
                            text = "Ramadan 2026",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "1445 AH • Holy Month",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                // Enhanced Current day indicator
                currentDay?.let { day ->
                    Surface(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp)),
                        color = when (day.status) {
                            FastingDayStatus.TODAY -> RamadanGold.copy(alpha = 0.15f)
                            FastingDayStatus.FASTING -> RamadanGreen.copy(alpha = 0.15f)
                            else -> MaterialTheme.colorScheme.surfaceVariant
                        }
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = when (day.status) {
                                    FastingDayStatus.TODAY -> "⭐"
                                    FastingDayStatus.FASTING -> "🌅"
                                    else -> "📅"
                                },
                                style = MaterialTheme.typography.titleSmall
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Day ${day.ramadanDay}",
                                style = MaterialTheme.typography.labelMedium,
                                color = when (day.status) {
                                    FastingDayStatus.TODAY -> RamadanGold
                                    FastingDayStatus.FASTING -> RamadanGreen
                                    else -> MaterialTheme.colorScheme.onSurfaceVariant
                                },
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Enhanced Progress Section
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "📊",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Ramadan Progress",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    Text(
                        text = "$completedDays/$totalDays days",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = RamadanGreen
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp)
                        .clip(RoundedCornerShape(5.dp)),
                    color = RamadanGreen,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${(progress * 100).toInt()}% Complete",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Text(
                        text = "${totalDays - completedDays} days remaining",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Enhanced Current Status
            currentDay?.let { day ->
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    color = when (day.status) {
                        FastingDayStatus.TODAY -> RamadanGold.copy(alpha = 0.1f)
                        FastingDayStatus.FASTING -> RamadanGreen.copy(alpha = 0.1f)
                        else -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                    }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = when (day.status) {
                                    FastingDayStatus.FASTING -> "🤲"
                                    FastingDayStatus.TODAY -> "🕌"
                                    FastingDayStatus.UPCOMING -> "⏳"
                                    FastingDayStatus.COMPLETED -> "✅"
                                },
                                style = MaterialTheme.typography.titleMedium
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Today's Status",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }

                        Text(
                            text = when (day.status) {
                                FastingDayStatus.FASTING -> "Fasting"
                                FastingDayStatus.TODAY -> "Today"
                                FastingDayStatus.UPCOMING -> "Upcoming"
                                FastingDayStatus.COMPLETED -> "Completed"
                            },
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
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
                shape = RoundedCornerShape(16.dp)
            )
            .padding(3.dp)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = { onClick(day) }
            ),
        shape = RoundedCornerShape(16.dp),
        tonalElevation = elevation,
        shadowElevation = elevation,
        color = MaterialTheme.colorScheme.surface
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .border(
                    width = if (isToday) 2.dp else 1.dp,
                    color = when {
                        isToday -> RamadanGreen
                        day.status == FastingDayStatus.COMPLETED -> RamadanGreen.copy(alpha = 0.5f)
                        day.status == FastingDayStatus.FASTING -> RamadanGold.copy(alpha = 0.6f)
                        else -> MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
                    },
                    shape = RoundedCornerShape(14.dp)
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Enhanced day number badge with better design
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .background(
                                color = when {
                                    isToday -> RamadanGold.copy(alpha = 0.2f)
                                    day.status == FastingDayStatus.COMPLETED -> RamadanGreen.copy(alpha = 0.2f)
                                    day.status == FastingDayStatus.FASTING -> RamadanGold.copy(alpha = 0.15f)
                                    else -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
                                },
                                shape = RoundedCornerShape(16.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "${day.ramadanDay}",
                            style = MaterialTheme.typography.labelMedium,
                            color = when {
                                isToday -> RamadanGold
                                day.status == FastingDayStatus.COMPLETED -> RamadanGreen
                                day.status == FastingDayStatus.FASTING -> RamadanGold
                                else -> MaterialTheme.colorScheme.onSurface
                            },
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Weekday
                    Text(
                        text = day.weekday.take(3).uppercase(),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    // Date
                    Text(
                        text = "${day.date.dayOfMonth}",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center
                    )
                }

                // Enhanced Status Indicator
                CompactDayStatusIndicator(day.status)
            }
        }
    }
}

// Compact Status Indicator for better UI
@Composable
private fun CompactDayStatusIndicator(status: FastingDayStatus) {
    val color = when (status) {
        FastingDayStatus.FASTING -> RamadanGreen
        FastingDayStatus.TODAY -> RamadanGold
        FastingDayStatus.UPCOMING -> MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
        FastingDayStatus.COMPLETED -> RamadanGreen.copy(alpha = 0.7f)
    }

    Box(
        modifier = Modifier
            .size(6.dp)
            .background(
                color = color,
                shape = RoundedCornerShape(3.dp)
            )
    )
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

@Composable
private fun RamadanProgressHeader(days: List<RamadanDayUiModel>) {
    val completedDays = days.count { it.status == FastingDayStatus.COMPLETED }
    val currentDay = days.find { it.status == FastingDayStatus.TODAY || it.status == FastingDayStatus.FASTING }
    val totalDays = days.size
    val progress = if (totalDays > 0) (completedDays.toFloat() / totalDays.toFloat()) else 0f

    // Animated progress
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(1000, easing = EaseOutQuart),
        label = "progress_animation"
    )

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f),
        shadowElevation = 8.dp,
        tonalElevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            // Header with icon and title
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Moon icon
                    Surface(
                        shape = CircleShape,
                        color = RamadanGold.copy(alpha = 0.15f),
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.NightsStay,
                            contentDescription = "Ramadan",
                            modifier = Modifier
                                .size(24.dp)
                                .padding(8.dp),
                            tint = RamadanGold
                        )
                    }
                    
                    Column(
                        verticalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Text(
                            text = "Ramadan 2026",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "Holy Month Progress",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                
                currentDay?.let { day ->
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = when (day.status) {
                            FastingDayStatus.TODAY -> RamadanGold.copy(alpha = 0.2f)
                            FastingDayStatus.FASTING -> RamadanGreen.copy(alpha = 0.2f)
                            else -> MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
                        },
                        border = BorderStroke(
                            1.5.dp,
                            when (day.status) {
                                FastingDayStatus.TODAY -> RamadanGold
                                FastingDayStatus.FASTING -> RamadanGreen
                                else -> MaterialTheme.colorScheme.primary
                            }
                        )
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            when (day.status) {
                                FastingDayStatus.TODAY -> {
                                    Icon(
                                        imageVector = Icons.Filled.Today,
                                        contentDescription = "Today",
                                        modifier = Modifier.size(16.dp),
                                        tint = RamadanGold
                                    )
                                }
                                FastingDayStatus.FASTING -> {
                                    Icon(
                                        imageVector = Icons.Filled.LocalFireDepartment,
                                        contentDescription = "Fasting",
                                        modifier = Modifier.size(16.dp),
                                        tint = RamadanGreen
                                    )
                                }
                                else -> {
                                    Icon(
                                        imageVector = Icons.Filled.Event,
                                        contentDescription = "Upcoming",
                                        modifier = Modifier.size(16.dp),
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                            Text(
                                text = "Day ${day.ramadanDay}",
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.Bold,
                                color = when (day.status) {
                                    FastingDayStatus.TODAY -> RamadanGold
                                    FastingDayStatus.FASTING -> RamadanGreen
                                    else -> MaterialTheme.colorScheme.primary
                                }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Progress section
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Progress bar with gradient
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(12.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(
                            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                        )
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(animatedProgress)
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(6.dp))
                            .background(
                                Brush.horizontalGradient(
                                    colors = listOf(
                                        RamadanGreen.copy(alpha = 0.8f),
                                        RamadanGreen,
                                        RamadanGold.copy(alpha = 0.9f)
                                    )
                                )
                            )
                    )
                }

                // Stats row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Days completed
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = RamadanGreen.copy(alpha = 0.15f),
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.CheckCircle,
                                contentDescription = "Completed",
                                modifier = Modifier
                                    .size(18.dp)
                                    .padding(7.dp),
                                tint = RamadanGreen
                            )
                        }
                        Column(
                            verticalArrangement = Arrangement.spacedBy(1.dp)
                        ) {
                            Text(
                                text = "$completedDays",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "Days Completed",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    // Percentage
                    Column(
                        horizontalAlignment = Alignment.End,
                        verticalArrangement = Arrangement.spacedBy(1.dp)
                    ) {
                        Text(
                            text = "${(animatedProgress * 100).toInt()}%",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "Progress",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ExpandCollapseFloatingButton(
    expanded: Boolean,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    
    // Scale animation for press effect
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = tween(150),
        label = "fab_scale"
    )
    
    // Rotation animation for arrow
    val rotation by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f,
        animationSpec = tween(300),
        label = "arrow_rotation"
    )

    FloatingActionButton(
        onClick = onClick,
        modifier = Modifier.scale(scale),
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.ExpandMore,
                contentDescription = if (expanded) "Collapse" else "Expand",
                modifier = Modifier
                    .size(24.dp)
                    .rotate(rotation),
                tint = MaterialTheme.colorScheme.onPrimary
            )
            
            Spacer(modifier = Modifier.width(8.dp))
            
            Text(
                text = if (expanded) "Less" else "More",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Composable
private fun EnhancedExpandButton(
    expanded: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    
    // Scale animation for press effect
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.96f else 1f,
        animationSpec = tween(150),
        label = "button_scale"
    )
    
    // Rotation animation for arrow
    val rotation by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f,
        animationSpec = tween(300),
        label = "arrow_rotation"
    )

    Button(
        onClick = onClick,
        modifier = modifier.scale(scale),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.ExpandMore,
                contentDescription = if (expanded) "Collapse" else "Expand",
                modifier = Modifier
                    .size(24.dp)
                    .rotate(rotation),
                tint = MaterialTheme.colorScheme.onPrimary
            )
            
            Spacer(modifier = Modifier.width(8.dp))
            
            Text(
                text = if (expanded) "Show Less" else "Show More",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
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
    MaterialTheme {
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
            items(days.size) { index ->
                val day = days[index]
                RamadanDayCard(
                    day = day,
                    onClick = {}
                )
            }
        }
    }
}



