package com.hathway.ramadankareem2026.ui.allahnames.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.hathway.ramadankareem2026.ui.allahnames.domain.model.AllahName
import com.hathway.ramadankareem2026.ui.components.RamadanToolbar


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AllahNamePagerScreen(
    names: List<AllahName>,
    startIndex: Int,
    onBack: () -> Unit
) {
    val pagerState = rememberPagerState(
        initialPage = startIndex,
        pageCount = { names.size }
    )

    Scaffold(
        topBar = {
            RamadanToolbar(
                title = "Name of Allah",
                showBack = true,
                onBackClick = onBack
            )
        }
    ) { padding ->

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) { page ->

            AllahNameDetailScreenContent(
                name = names[page]
            )
        }
    }
}
