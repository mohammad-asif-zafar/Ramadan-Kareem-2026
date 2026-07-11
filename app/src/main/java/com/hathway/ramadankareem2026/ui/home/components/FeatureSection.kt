package com.hathway.ramadankareem2026.ui.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.MenuBook
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Lightbulb
import androidx.compose.material.icons.outlined.Mosque
import androidx.compose.material.icons.outlined.VolunteerActivism
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.hathway.ramadankareem2026.R
import com.hathway.ramadankareem2026.ui.home.model.FeatureIcon
import com.hathway.ramadankareem2026.ui.home.model.FeatureModel
import com.hathway.ramadankareem2026.ui.icons.ComposeIcons
import com.hathway.ramadankareem2026.ui.icons.RamadanIcons
import com.hathway.ramadankareem2026.ui.navigation.Routes
import com.hathway.ramadankareem2026.ui.theme.Gold

private val featureList = listOf(
    FeatureModel(
        R.string.feature_quran, FeatureIcon.Vector(RamadanIcons.QuranOnStandLineArt), Routes.QURAN
    ),
    FeatureModel(
        R.string.feature_dua, FeatureIcon.Vector(RamadanIcons.PrayingHandsFromImage), Routes.DUA
    ),
    FeatureModel(R.string.feature_tips, FeatureIcon.Vector(RamadanIcons.Tips), Routes.TIPS),
    FeatureModel(
        R.string.allah, FeatureIcon.Text("ﷲ"), Routes.ALLAH_NAMES,
        color = Gold
    ),
    FeatureModel(R.string.feature_qibla, FeatureIcon.Vector(RamadanIcons.Qibla), Routes.QIBLA),

    FeatureModel(
        R.string.feature_calendar,
        FeatureIcon.Vector(RamadanIcons.Calendar),
        Routes.RAMADAN_CALENDAR
    )
)

@Composable
fun FeatureSection(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            SectionTitle(stringResource(R.string.features))
            SectionTitle(stringResource(R.string.view_all))
        }

        Spacer(modifier = Modifier.height(6.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            userScrollEnabled = false,
            modifier = Modifier.height(220.dp)
        ) {
            items(featureList) { item ->
                FeatureItem(
                    item = item, onClick = {
                        navController.navigate(item.route)
                    })
            }
        }
    }
}

@Composable
fun FeatureItem(
    item: FeatureModel,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .aspectRatio(1f)
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(24.dp),
                ambientColor = Color.Black.copy(alpha = 0.03f),
                spotColor = Color.Black.copy(alpha = 0.06f)
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            ),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 20.dp, bottom = 14.dp, start = 12.dp, end = 12.dp), // Rebalanced padding to push elements slightly closer
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Icon rendering container
            Box(
                modifier = Modifier.size(48.dp),
                contentAlignment = Alignment.Center
            ) {
                when (val icon = item.icon) {
                    is FeatureIcon.Vector -> {
                        Icon(
                            imageVector = icon.imageVector,
                            contentDescription = stringResource(item.titleRes),
                            modifier = Modifier.size(36.dp),
                            tint = item.color
                        )
                    }

                    is FeatureIcon.Drawable -> {
                        Image(
                            painter = painterResource(icon.resId),
                            contentDescription = stringResource(item.titleRes),
                            modifier = Modifier.size(40.dp),
                            contentScale = ContentScale.Fit
                        )
                    }

                    is FeatureIcon.Text -> {
                        Text(
                            text = icon.value,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color =  item.color,
                            fontFamily = FontFamily.Serif,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(6.dp)) // Reduced significantly from 14.dp to 6.dp for tight design alignment

            // Clean text label
            Text(
                text = stringResource(item.titleRes),
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    color = Color(0xFF111111)
                ),
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FeatureItemPreview() {
    FeatureItem(
        item = FeatureModel(
            titleRes = R.string.feature_dua,
            icon = FeatureIcon.Vector(Icons.Outlined.FavoriteBorder),
            route = Routes.DUA
        )
    )
}

