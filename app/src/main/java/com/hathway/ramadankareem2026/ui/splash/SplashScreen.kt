package com.hathway.ramadankareem2026.ui.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hathway.ramadankareem2026.R
import com.hathway.ramadankareem2026.ui.theme.EmeraldDark
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onNavigateNext: () -> Unit
) {
    LaunchedEffect(Unit) {
        delay(2000)
        onNavigateNext()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF041B17),
                        EmeraldDark,
                        Color(0xFF052B23)
                    )
                )
            )
    ) {
        // Starry Night & Mosque Backdrop
        Image(
            painter = painterResource(R.drawable.splash),
            contentDescription = null,
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.FillBounds
        )
    }
}
