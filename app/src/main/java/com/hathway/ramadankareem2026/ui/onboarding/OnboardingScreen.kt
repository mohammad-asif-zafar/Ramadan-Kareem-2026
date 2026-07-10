package com.hathway.ramadankareem2026.ui.onboarding

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
import com.hathway.ramadankareem2026.ui.theme.BackgroundLight
import kotlinx.coroutines.launch

data class OnboardingPage(
    val title: String,
    val description: String,
    val image: Int
)

@Composable
fun OnboardingScreen(
    onFinished: () -> Unit
) {
    val pages = listOf(
        OnboardingPage(
            title = "Welcome to Noor",
            description = "All the tools you need to strengthen your Imaan - in one beautiful app.",
            image = R.drawable.mosque_illuminated
        ),
        OnboardingPage(
            title = "Learn & Grow",
            description = "Quran, Duas, Islamic insights and more to help you grow every day.",
            image = R.drawable.serenquran
        ),
        OnboardingPage(
            title = "Worship with Ease",
            description = "Prayer times, Qibla direction, Zakat calculator and more essentials.",
            image = R.drawable.kaaba_with_mosque_and_serene_court_yard
        )
    )

    val pagerState = rememberPagerState(pageCount = { pages.size })
    val scope = rememberCoroutineScope()

    Scaffold(
        containerColor = BackgroundLight
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top Bar
            Row(
                modifier = Modifier.fillMaxWidth().height(56.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (pagerState.currentPage > 0) {
                    IconButton(onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage - 1)
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIosNew,
                            contentDescription = "Back",
                            modifier = Modifier.size(20.dp),
                            tint = Color.DarkGray
                        )
                    }
                } else {
                    Text(
                        text = "",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color.DarkGray,
                        modifier = Modifier.padding(start = 12.dp)
                    )
                }

                TextButton(onClick = onFinished) {
                    Text(
                        text = "Skip",
                        color = Color.DarkGray,
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) { position ->
                OnboardingPageContent(page = pages[position])
            }

            // Pager Indicator
            Row(
                modifier = Modifier
                    .padding(vertical = 32.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(pages.size) { index ->
                    val isActive = pagerState.currentPage == index
                    Box(
                        modifier = Modifier
                            .size(if (isActive) 10.dp else 8.dp)
                            .clip(CircleShape)
                            .background(
                                if (isActive) Emerald 
                                else Color.LightGray.copy(alpha = 0.6f)
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
                colors = ButtonDefaults.buttonColors(containerColor = Emerald)
            ) {
                Text(
                    text = if (pagerState.currentPage == pages.size - 1) "Get Started" else "Next",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
            
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun OnboardingPageContent(page: OnboardingPage) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.75f)
                .aspectRatio(1f),
            contentAlignment = Alignment.Center
        ) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                shape = CircleShape,
                color = Emerald.copy(alpha = 0.03f)
            ) {}
            
            Image(
                painter = painterResource(id = page.image),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(1.0f),
                contentScale = ContentScale.Inside
            )
        }
        
        Spacer(modifier = Modifier.height(48.dp))
        
        Text(
            text = page.title,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1B3D36),
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = page.description,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            lineHeight = 24.sp,
            modifier = Modifier.padding(horizontal = 32.dp)
        )
    }
}
