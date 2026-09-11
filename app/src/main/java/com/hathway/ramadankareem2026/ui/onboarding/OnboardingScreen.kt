package com.hathway.ramadankareem2026.ui.onboarding

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hathway.ramadankareem2026.R
import com.hathway.ramadankareem2026.ui.theme.Emerald
import kotlinx.coroutines.launch

data class OnboardingPage(
    val title: Int,
    val description: String,
    val image: Int
)

@Composable
fun OnboardingScreen(
    onFinished: () -> Unit
) {
    val isDark = androidx.compose.foundation.isSystemInDarkTheme()
    
    val pages = listOf(
        OnboardingPage(
            title = R.string.assalamu_alaykum,
            description = "All the tools you need to strengthen your Imaan - in one beautiful app.",
            image = R.drawable.mosque_illuminated
        ),
        OnboardingPage(
            title = R.string.feature_quran,
            description = "Read, listen, and understand the Holy Quran with translations in your language.",
            image = R.drawable.serenquran
        ),
        OnboardingPage(
            title = R.string.prayer_times,
            description = "Accurate prayer times, Qibla finder, and Ramadan tools at your fingertips.",
            image = R.drawable.kaaba_with_mosque_and_serene_court_yard
        )
    )

    val pagerState = rememberPagerState(pageCount = { pages.size })
    val scope = rememberCoroutineScope()

    Scaffold(
        containerColor = if (isDark) Color(0xFF0F1720) else Color(0xFFF7F8F5)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top Bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                if (pagerState.currentPage > 0) {
                    IconButton(
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage - 1)
                            }
                        },
                        modifier = Modifier.align(Alignment.CenterStart)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIosNew,
                            contentDescription = "Back",
                            modifier = Modifier.size(20.dp),
                            tint = if (isDark) Color.White.copy(alpha = 0.6f) else Color.DarkGray
                        )
                    }
                }

                TextButton(
                    onClick = onFinished,
                    modifier = Modifier.align(Alignment.CenterEnd)
                ) {
                    Text(
                        text = "Skip",
                        color = if (isDark) Color.White.copy(alpha = 0.5f) else Color.Gray,
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) { position ->
                OnboardingPageContent(page = pages[position], isDark = isDark)
            }

            // Pager Indicator
            Row(
                modifier = Modifier
                    .padding(vertical = 40.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(pages.size) { index ->
                    val isActive = pagerState.currentPage == index
                    val width by animateDpAsState(
                        targetValue = if (isActive) 12.dp else 8.dp,
                        label = "indicator_width"
                    )
                    Box(
                        modifier = Modifier
                            .size(width, 8.dp)
                            .clip(CircleShape)
                            .background(
                                if (isActive) Emerald 
                                else (if (isDark) Color.White else Color.Black).copy(alpha = 0.1f)
                            )
                    )
                }
            }

            // Next / Get Started Button
            Button(
                onClick = {
                    if (pagerState.currentPage < pages.size - 1) {
                        scope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    } else {
                        onFinished()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Emerald,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = if (pagerState.currentPage == pages.size - 1) "Get Started" else "Next",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
private fun OnboardingPageContent(page: OnboardingPage, isDark: Boolean) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .aspectRatio(1f),
            contentAlignment = Alignment.Center
        ) {
            // Circular background decoration
            Surface(
                modifier = Modifier.fillMaxSize(),
                shape = CircleShape,
                color = Emerald.copy(alpha = if (isDark) 0.08f else 0.05f)
            ) {}
            
            Image(
                painter = painterResource(id = page.image),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize(0.85f),
                contentScale = ContentScale.Fit
            )
        }
        
        Spacer(modifier = Modifier.height(60.dp))
        
        Text(
            text = if (page.title == R.string.assalamu_alaykum) "Welcome to Noor" else androidx.compose.ui.res.stringResource(page.title),
            style = MaterialTheme.typography.headlineMedium.copy(
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            ),
            color = if (isDark) Color.White else Emerald,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(20.dp))
        
        Text(
            text = page.description,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 16.sp,
                lineHeight = 24.sp
            ),
            color = if (isDark) Color.White.copy(alpha = 0.7f) else Color(0xFF6B7280),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 32.dp)
        )
    }
}
