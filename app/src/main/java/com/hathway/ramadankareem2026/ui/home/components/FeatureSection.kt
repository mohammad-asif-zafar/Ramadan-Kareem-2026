package com.hathway.ramadankareem2026.ui.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.material.icons.outlined.NotificationsNone
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.hathway.ramadankareem2026.R
import com.hathway.ramadankareem2026.ui.home.model.FeatureIcon
import com.hathway.ramadankareem2026.ui.home.model.FeatureModel
import com.hathway.ramadankareem2026.ui.navigation.Routes
import com.hathway.ramadankareem2026.ui.theme.RamadanGold

private val featureList = listOf(
    FeatureModel(
        R.string.feature_dua, FeatureIcon.Vector(Icons.Outlined.FavoriteBorder), Routes.DUA
    ),
    FeatureModel(
        R.string.feature_zakat, FeatureIcon.Vector(Icons.Outlined.VolunteerActivism), Routes.ZAKAT
    ),
    FeatureModel(
        R.string.mosque, FeatureIcon.Vector(Icons.Outlined.Mosque), Routes.MOSQUES
    ),
    FeatureModel(R.string.feature_tips, FeatureIcon.Vector(Icons.Outlined.Lightbulb), Routes.TIPS),
    FeatureModel(
        R.string.allah, FeatureIcon.Text("ﷲ"), Routes.ALLAH_NAMES
    ),
    FeatureModel(R.string.feature_qibla, FeatureIcon.Vector(Icons.Outlined.Explore), Routes.QIBLA),
    FeatureModel(
        R.string.feature_quran,
        FeatureIcon.Vector(Icons.AutoMirrored.Outlined.MenuBook),
        Routes.QURAN
    ),
    FeatureModel(
        R.string.feature_calendar,
        FeatureIcon.Vector(Icons.Outlined.CalendarMonth),
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
        SectionTitle("Features")

        Spacer(modifier = Modifier.height(12.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            userScrollEnabled = false,
            modifier = Modifier.height(200.dp)
        ) {
            items(featureList) { item ->
                FeatureItem(
                    item = item, onClick = {
                        navController.navigate(item.route)
                    })
            }
        }
    }

    Spacer(modifier = Modifier.height(20.dp))
}

@Composable
fun FeatureItem(
    item: FeatureModel, onClick: () -> Unit = {}
) {

    Card(
        modifier = Modifier
            .aspectRatio(1f)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null, // Material3 handles ripple internally
                onClick = onClick
            ), shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(
            containerColor = Color(0xFFEFEAF0)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            when (val icon = item.icon) {
                is FeatureIcon.Vector -> {
                    Icon(
                        imageVector = icon.imageVector,
                        contentDescription = stringResource(item.titleRes),
                        modifier = Modifier.size(28.dp),
                        tint = Color(0xFFB89A2B)
                    )
                }

                is FeatureIcon.Drawable -> {
                    Image(
                        painter = painterResource(icon.resId),
                        contentDescription = stringResource(item.titleRes),
                        modifier = Modifier.size(158.dp),

                        )
                }

                is FeatureIcon.Text -> {
                    Text(
                        text = icon.value,
                        fontSize = 24.sp,
                        color = RamadanGold,
                        fontFamily = FontFamily.Serif
                    )
                }

            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(item.titleRes), style = MaterialTheme.typography.labelMedium
            )
        }
    }
}
