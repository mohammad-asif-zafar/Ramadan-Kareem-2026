package com.hathway.ramadankareem2026.ui.allahnames


/*
import android.app.Application
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Replay10
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Forward10
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.hathway.ramadankareem2026.R
import com.hathway.ramadankareem2026.ui.allahnames.domain.model.AllahName
import com.hathway.ramadankareem2026.ui.allahnames.presentation.viewmodel.AllahNameBookmarkCountViewModel
import com.hathway.ramadankareem2026.ui.allahnames.viewmodel.AllahNamesBookmarkViewModel
import com.hathway.ramadankareem2026.ui.components.RamadanToolbar
import com.hathway.ramadankareem2026.ui.components.ToolbarIcon


@Composable
fun AllahNameDetailScreen(
    name: AllahName,
    onBack: () -> Unit,
    navController: NavController,
    bookmarkViewModel: AllahNamesBookmarkViewModel,
    allahNameBookmarkCountViewModel: AllahNameBookmarkCountViewModel
) {
    val isBookmarked by bookmarkViewModel.isBookmarked(name.id.toString())
        .collectAsStateWithLifecycle(initialValue = false)
    val bookmarkCount by allahNameBookmarkCountViewModel.allahNameBookmarkCount.collectAsStateWithLifecycle(initialValue = 0)

    // Simulation states for audio track timeline matching mockup numbers
    var sliderPosition by remember { mutableFloatStateOf(4f) }

    LaunchedEffect(name.id) {
        bookmarkViewModel.checkBookmarkStatus(name.id.toString())
        bookmarkViewModel.setBookmarkCountChangedCallback { delta ->
            allahNameBookmarkCountViewModel.updateAllahNameBookmarkCountImmediate(delta)
        }
    }

    Scaffold(
        topBar = {
            RamadanToolbar(
                title = name.transliteration,
                showBack = true,
                onBackClick = onBack,
                backgroundColor = Color.White,
                contentColor = Color.Black,
                // Right bookmark action linking status dynamically with top bar slot icon
                rightIcon2 = if (isBookmarked) {
                    ToolbarIcon.Drawable(R.drawable.ic_saved) // Active green/filled saved state drawable
                } else {
                    ToolbarIcon.Vector(Icons.Outlined.BookmarkBorder)
                },
                onRightIcon2Click = {
                    bookmarkViewModel.toggleBookmark(
                        itemId = name.id.toString(),
                        title = "${name.transliteration} - ${name.english}",
                        content = name.arabic
                    )
                }
            )
        },
        containerColor = Color.White
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            // 🌊 Wavy Floating Vector Background Layer at bottom half of screen
            WavyBackgroundCanvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp) // Perfect height ratio for the bottom profile curve
                    .align(Alignment.BottomCenter)
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                // 1. Structural Circular Ornamental Ring Plate View Top Section
                Box(
                    modifier = Modifier
                        .padding(top = 28.dp)
                        .weight(1f, fill = false),
                    contentAlignment = Alignment.Center
                ) {
                    OrnamentalNamePlate(arabicText = name.arabic)
                }

                // 2. Audio Tracking Timeline View Controller Container Block
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Progress Slider Bar Layout Component
                    Slider(
                        value = sliderPosition,
                        onValueChange = { sliderPosition = it },
                        valueRange = 0f..18f,
                        modifier = Modifier.fillMaxWidth(),
                        colors = SliderDefaults.colors(
                            thumbColor = Color(0xFF0F5A3E),
                            activeTrackColor = Color(0xFF0F5A3E),
                            inactiveTrackColor = Color(0xFFE5EBE7)
                        )
                    )

                    // Track Time Metrics Indicators Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "00:${String.format("%02d", sliderPosition.toInt())}",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                        Text(
                            text = "00:18",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Media Control playback buttons group
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Skip Backward 10 Seconds
                        IconButton(onClick = { sliderPosition = (sliderPosition - 10f).coerceAtLeast(0f) }) {
                            Icon(
                                imageVector = Icons.Filled.Replay10,
                                contentDescription = "Rewind 10 seconds",
                                tint = Color.DarkGray,
                                modifier = Modifier.size(28.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(24.dp))

                        // Large Circular Play Button
                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .clip(CircleShape)
                                .background(
                                    brush = Brush.radialGradient(
                                        colors = listOf(Color(0xFF0F5A3E), Color(0xFF0A442E))
                                    )
                                )
                                .clickable {
 Handle media playback trigger
 },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.PlayArrow,
                                contentDescription = "Play Audio",
                                tint = Color.White,
                                modifier = Modifier.size(36.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(24.dp))
                        // Skip Forward 10 Seconds
                        IconButton(onClick = { sliderPosition = (sliderPosition + 10f).coerceAtMost(18f) }) {
                            Icon(
                                imageVector = Icons.Outlined.Forward10,
                                contentDescription = "Forward 10 seconds",
                                tint = Color.DarkGray,modifier = Modifier.size(28.dp))}}}

                // 3. Meaning Card Box Bottom Sheet Layout Item
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFFAF9F6) // Off-white cream card fill tint
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = "Meaning icon information label",
                            tint = Color(0xFF0F5A3E),
                            modifier = Modifier.size(22.dp)
                        )

                        Spacer(modifier = Modifier.width(14.dp))

                        Column {
                            Text(
                                text = "Meaning",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = name.meaning,
                                fontSize = 13.sp,
                                color = Color(0xFF333333),
                                lineHeight = 18.sp
                            )
                        }
                    }
                }
            }
        }
    }
}




@Composable
fun OrnamentalNamePlate(
    arabicText: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(230.dp)
            .aspectRatio(1f),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height
            val cx = w / 2f
            val cy = h / 2f

            // Outer golden ring outline track
            drawCircle(
                color = Color(0xFFB59349),
                radius = cx * 0.96f,
                style = Stroke(width = 2.5f)
            )
            drawCircle(
                color = Color(0xFF0F5A3E).copy(alpha = 0.15f),
                radius = cx * 0.92f,
                style = Fill
            )

            // Intricate multi-layered geometric sequence loops replicating the mandala snippet look
            for (i in 0 until 24) {
                val angle = Math.toRadians(i * (360.0 / 24.0))
                val cos = Math.cos(angle).toFloat()
                val sin = Math.sin(angle).toFloat()

                // Concentric geometric star point loop paths
                drawCircle(
                    color = Color(0xFF0F5A3E),
                    radius = cx * 0.12f,
                    center = androidx.compose.ui.geometry.Offset(
                        x = cx + cx * 0.78f * cos,
                        y = cy + cy * 0.78f * sin
                    ),
                    style = Stroke(width = 1.5f)
                )
            }

            // Inner pristine white nameplate circle boundary
            drawCircle(
                color = Color(0xFFB59349),
                radius = cx * 0.65f,
                style = Stroke(width = 2f)
            )
            drawCircle(
                color = Color.White,
                radius = cx * 0.64f,
                style = Fill
            )
        }

        // Concentrated Dynamic Text Layer
        Text(
            text = arabicText,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Serif,
            color = Color(0xFF0F5A3E),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(42.dp)
        )
    }
}




@Composable
fun WavyBackgroundCanvas(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        val wavePath = Path().apply {
            moveTo(0f, h * 0.4f)
            cubicTo(
                x1 = w * 0.25f, y1 = h * 0.2f,
                x2 = w * 0.6f, y2 = h * 0.7f,
                x3 = w, y3 = h * 0.3f
            )
            lineTo(w, h)
            lineTo(0f, h)
            close()
        }

        drawPath(
            path = wavePath,
            color = Color(0xFF0F5A3E).copy(alpha = 0.04f),
            style = Fill
        )
    }
}*/
