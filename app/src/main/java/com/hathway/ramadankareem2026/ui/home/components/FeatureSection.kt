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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.hathway.ramadankareem2026.R
import com.hathway.ramadankareem2026.ui.home.model.FeatureIcon
import com.hathway.ramadankareem2026.ui.home.model.FeatureModel
import com.hathway.ramadankareem2026.ui.icons.RamadanIcons
import com.hathway.ramadankareem2026.ui.navigation.Routes
import com.hathway.ramadankareem2026.ui.theme.Gold
import com.hathway.ramadankareem2026.ui.theme.NoorMuslimCompanionTheme

private val featureList = listOf(
    FeatureModel(
        R.string.feature_quran, FeatureIcon.Vector(RamadanIcons.QuranOnStandLineArt), Routes.QURAN
    ),
    FeatureModel(
        R.string.feature_dua, FeatureIcon.Vector(RamadanIcons.PrayingHandsFromImage), Routes.DUA
    ),
    FeatureModel(R.string.feature_tips, FeatureIcon.Vector(RamadanIcons.Tips), Routes.TIPS),
    FeatureModel(
        R.string.allah, FeatureIcon.Text("ﷲ"), Routes.ALLAH_NAMES, color = Gold
    ),
    FeatureModel(R.string.feature_qibla, FeatureIcon.Vector(RamadanIcons.Qibla), Routes.QIBLA),

    FeatureModel(
        R.string.feature_calendar,
        FeatureIcon.Vector(RamadanIcons.Calendar),
        Routes.RAMADAN_CALENDAR
    )
)

@Composable
fun FeatureSection(
    onFeatureClick: (String) -> Unit,
    onViewAllClick: () -> Unit,
    modifier: Modifier.Companion = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        // Section Header Layout
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            SectionTitle(stringResource(R.string.features))
            Text(
                text = stringResource(R.string.view_all),
                style = MaterialTheme.typography.labelLarge.copy(
                    fontWeight = FontWeight.SemiBold
                ),
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .clickable { onViewAllClick() }
                    .padding(horizontal = 4.dp, vertical = 2.dp))
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Dynamic Height Grid Implementation (3 Items Per Row Chunks)
        val columns = 3
        val rows = featureList.chunked(columns)

        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()
        ) {
            rows.forEach { rowItems ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    rowItems.forEach { item ->
                        Box(
                            modifier = Modifier.weight(1f)
                        ) {
                            FeatureItem(
                                item = item, onClick = { onFeatureClick(item.route) })
                        }
                    }

                    // Appends matching filler spaces if a row is incomplete
                    if (rowItems.size < columns) {
                        repeat(columns - rowItems.size) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FeatureItem(
    item: FeatureModel, onClick: () -> Unit, modifier: Modifier.Companion = Modifier
) {
    val cardShape = RoundedCornerShape(16.dp)

    Card(
        modifier = modifier
            .aspectRatio(1f)
            .clip(cardShape)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                // Custom targeted M3 touch feedback layout bounds
                indication = ripple(bounded = true), onClick = onClick
            ), shape = cardShape, colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ), elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 6.dp, vertical = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Balanced graphic element frame
            Box(
                modifier = Modifier.size(36.dp), contentAlignment = Alignment.Center
            ) {
                when (val icon = item.icon) {
                    is FeatureIcon.Vector -> {
                        Icon(
                            imageVector = icon.imageVector,
                            contentDescription = stringResource(item.titleRes),
                            modifier = Modifier.size(26.dp),
                            tint = item.color
                        )
                    }

                    is FeatureIcon.Drawable -> {
                        Image(
                            painter = painterResource(icon.resId),
                            contentDescription = stringResource(item.titleRes),
                            modifier = Modifier.size(28.dp),
                            contentScale = ContentScale.Fit
                        )
                    }

                    is FeatureIcon.Text -> {
                        Text(
                            text = icon.value,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = item.color,
                            fontFamily = FontFamily.Serif,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Robust text description constraints
            Text(
                text = stringResource(item.titleRes),
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurface
                ),
                textAlign = TextAlign.Center,
                maxLines = 2, // Allowed multi-line wrap to safely contain complex language lengths
                minLines = 1,
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
        ), onClick = TODO(), modifier = TODO()
    )
}


/* ----------------------------------------------------
 * OPTIMIZED HIGH CONTRAST PREVIEWS
 * ---------------------------------------------------*/

@Preview(name = "Dark Theme Grid Layout", showBackground = true, widthDp = 360)
@Composable
fun FeatureSectionDarkPreview() {
    NoorMuslimCompanionTheme(darkTheme = true) {
        Surface(color = MaterialTheme.colorScheme.background) {
            Box(modifier = Modifier.padding(vertical = 16.dp)) {
                FeatureSection(onFeatureClick = {}, onViewAllClick = {})
            }
        }
    }
}
