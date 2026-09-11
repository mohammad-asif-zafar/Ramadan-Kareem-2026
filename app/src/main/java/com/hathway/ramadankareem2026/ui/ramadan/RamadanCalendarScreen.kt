package com.hathway.ramadankareem2026.ui.ramadan

import android.annotation.SuppressLint
import androidx.compose.animation.core.EaseOutQuart
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.NightsStay
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
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
    var selectedDay by remember { mutableStateOf<RamadanDayUiModel?>(null) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val gridState = rememberLazyGridState()
    val coroutineScope = rememberCoroutineScope()

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
        if (todayIndex >= 0 && !expanded) {
            gridState.animateScrollToItem(todayIndex)
        }
    }

    Scaffold(topBar = {
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
    }, floatingActionButton = {
        if (days.isNotEmpty()) {
            ExpandCollapseFloatingButton(
                expanded = expanded, onClick = { expanded = !expanded })
        }
    }) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    if (androidx.compose.foundation.isSystemInDarkTheme()) Color(0xFF0F1720) 
                    else Color(0xFFF7F8F5)
                )
                .padding(16.dp)
                .padding(top = 48.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Loading State
            if (days.isEmpty() && isRefreshing) {
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
                            .weight(1f), // Changed to weight(1f) to take available space
                        contentPadding = PaddingValues(bottom = 80.dp), // Space for FAB
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        userScrollEnabled = expanded // Keep scroll disabled if only showing top 6
                    ) {
                        items(if (expanded) days.size else 6) { index ->
                            val day = days[index]
                            RamadanDayCard(
                                day = day, onClick = { selectedDay = day })
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
        rightIcon1 = ToolbarIcon.Vector(Icons.Default.Refresh),
        onRightIcon1Click = { if (!isRefreshing) onRefreshClick() },
        rightIcon2 = ToolbarIcon.Vector(Icons.Default.Settings),
        onRightIcon2Click = onSettingsClick,
        backgroundColor = if (androidx.compose.foundation.isSystemInDarkTheme()) Color(0xFF0F1720) else Color.White,
        contentColor = if (androidx.compose.foundation.isSystemInDarkTheme()) Color.White else Color.Black
    )
}


@Composable
private fun RamadanProgressHeader(
    days: List<RamadanDayUiModel>
) {
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
        color = if (androidx.compose.foundation.isSystemInDarkTheme()) Color(0xFF1B252E) else Color.White,
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
                            color = if (androidx.compose.foundation.isSystemInDarkTheme()) Color.White else Color(0xFF101820)
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
                            if (androidx.compose.foundation.isSystemInDarkTheme()) Color.White.copy(alpha = 0.05f) 
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


@Composable
private fun ExpandCollapseFloatingButton(
    expanded: Boolean, onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    // Scale animation for press effect
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f, animationSpec = tween(150), label = "fab_scale"
    )

    // Rotation animation for arrow
    val rotation by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f,
        animationSpec = tween(300),
        label = "arrow_rotation"
    )

    FloatingActionButton(
        onClick = onClick,
        modifier = Modifier
            .padding(bottom = 16.dp, end = 16.dp)
            .scale(scale),
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        shape = RoundedCornerShape(20.dp)
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
                text = if (expanded) "Less" else "More",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onPrimary
            )
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

@Preview(
    showBackground = true, backgroundColor = 0xFFF5F5F5, name = "Ramadan Calendar – Full Month"
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
                    day = day, onClick = {})
            }
        }
    }
}
