package com.hathway.ramadankareem2026.ui.allahnames

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hathway.ramadankareem2026.ui.allahnames.domain.model.AllahName
import com.hathway.ramadankareem2026.ui.components.RamadanToolbar
import com.hathway.ramadankareem2026.ui.theme.LightTextPrimary
import com.hathway.ramadankareem2026.ui.theme.RamadanGold
import com.hathway.ramadankareem2026.ui.theme.RamadanGreen

@Composable
fun AllahNameDetailScreen(
    name: AllahName, onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            RamadanToolbar(
                title = name.transliteration, showBack = true, onBackClick = onBack
            )
        }, containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 24.dp)
        ) {

            // 🕋 CENTER CONTENT (Arabic + titles)
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = name.arabic,
                    style = MaterialTheme.typography.displayLarge,
                    color = LightTextPrimary,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = name.transliteration,
                    style = MaterialTheme.typography.titleMedium,
                    color = RamadanGreen
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = name.meaning,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // 📖 BOTTOM MEANING
            Surface(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 32.dp)
                    .fillMaxWidth(), shape = MaterialTheme.shapes.large, tonalElevation = 2.dp
            ) {
                Text(
                    text = name.english,
                    modifier = Modifier.padding(20.dp),
                    style = MaterialTheme.typography.bodyLarge,
                    color = RamadanGold,
                    textAlign = TextAlign.Center,
                    lineHeight = 26.sp
                )
            }
        }
    }
}


@Preview(
    name = "Allah Name Detail – Light", showBackground = true
)
@Composable
fun AllahNameDetailPreviewLight() {
    MaterialTheme {
        AllahNameDetailScreen(
            name = AllahName(
                id = 4,
                arabic = "الْقُدُّوسُ",
                transliteration = "Al-Quddoos",
                english = "The Most Holy",
                meaning = "The One who is pure from any imperfection and clear from children and adversaries."
            ), onBack = {})
    }
}



