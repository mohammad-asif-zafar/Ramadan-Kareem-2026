package com.hathway.ramadankareem2026.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.hathway.ramadankareem2026.R

@Composable
fun HomeBackground() {
    Box(modifier = Modifier.fillMaxSize()) {

        Image(
            painter = painterResource(id = R.drawable.top_left_mosque_home_graound),
            contentDescription = null,
            // 1. Crop fills the width and cuts off nicely like the design
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                // 2. Set an explicit height for the top background area
                .height(150.dp)
                // 3. Anchor it exactly to the top center/left
                .align(Alignment.TopCenter)
        )
    }
}

