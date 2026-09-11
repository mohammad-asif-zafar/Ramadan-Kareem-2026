package com.hathway.ramadankareem2026.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.automirrored.outlined.MenuBook
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.Alarm
import androidx.compose.material.icons.outlined.Bedtime
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.NightsStay
import androidx.compose.material.icons.outlined.Restaurant
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hathway.ramadankareem2026.R
import com.hathway.ramadankareem2026.ui.home.model.HeaderType
import com.hathway.ramadankareem2026.ui.tips.presentation.viewmodel.RandomRamadanTipsViewModelFactory

private const val TAG = "HeaderCard"

@Composable
fun HeaderCard(
    type: HeaderType,
    title: String,
    subtitle: String,
    hint: String,
    footer: String = "",
    locationLabel: String = "",
    isAlarmEnabled: Boolean = false,
    onAlarmToggle: (() -> Unit)? = null,
    onReminderClick: ((Int) -> Unit)? = null,
    language: String = "en"
) {
    if (type == HeaderType.REMINDER) {
        DynamicReminderCard(
            title = title,
            subtitle = subtitle,
            hint = hint,
            language = language,
            onClick = onReminderClick
        )
        return
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .padding(end = 4.dp),
        shape = RoundedCornerShape(26.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (type == HeaderType.DYNAMIC_PRAYER) 6.dp else 2.dp
        )
    ) {
        Box(
            modifier = Modifier
                .background(headerGradient(type))
                .fillMaxSize()
        ) {
            SliderBackgroundImage(type)
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(foregroundScrim(type))
            )

            when (type) {
                HeaderType.DYNAMIC_PRAYER,
                HeaderType.NEXT_PRAYER -> PrayerHeroContent(
                    title = title,
                    subtitle = subtitle,
                    hint = hint,
                    locationLabel = locationLabel
                )

                HeaderType.IFTAR_TIME,
                HeaderType.SUHOOR_TIME -> FastingTimeContent(
                    type = type,
                    title = title,
                    subtitle = subtitle,
                    hint = hint,
                    footer = footer,
                    isAlarmEnabled = isAlarmEnabled,
                    onAlarmToggle = onAlarmToggle
                )

                HeaderType.REMINDER -> Unit
            }
        }
    }
}

private fun headerIcon(type: HeaderType) = when (type) {
    HeaderType.DYNAMIC_PRAYER -> Icons.Outlined.NightsStay
    HeaderType.NEXT_PRAYER -> Icons.Outlined.AccessTime
    HeaderType.REMINDER -> Icons.AutoMirrored.Outlined.MenuBook
    HeaderType.IFTAR_TIME -> Icons.Outlined.Restaurant
    HeaderType.SUHOOR_TIME -> Icons.Outlined.Bedtime
}

@Composable
private fun headerGradient(type: HeaderType): Brush = when (type) {
    HeaderType.DYNAMIC_PRAYER -> Brush.linearGradient(
        colors = listOf(
            Color(0xFF004D45), Color(0xFF063931), Color(0xFF042B27)
        )
    )

    HeaderType.NEXT_PRAYER -> Brush.linearGradient(
        colors = listOf(
            Color(0xFF004D45), Color(0xFF063931)
        )
    )

    HeaderType.REMINDER -> Brush.linearGradient(
        colors = listOf(
            Color(0xFFE9F7EF), Color(0xFFCAE9DC), Color(0xFF8CCDB8)
        )
    )

    HeaderType.IFTAR_TIME -> Brush.linearGradient(
        colors = listOf(
            Color(0xFFFFD899), Color(0xFFFFB75E), Color(0xFFEF7E22)
        )
    )

    HeaderType.SUHOOR_TIME -> Brush.linearGradient(
        colors = listOf(
            Color(0xFF08295F), Color(0xFF0D4387), Color(0xFF061F49)
        )
    )
}

@Composable
private fun foregroundScrim(type: HeaderType): Brush = when (type) {
    HeaderType.DYNAMIC_PRAYER,
    HeaderType.NEXT_PRAYER -> Brush.horizontalGradient(
        0f to Color(0xFF004A43).copy(alpha = 0.98f),
        0.42f to Color(0xFF004A43).copy(alpha = 0.86f),
        1f to Color.Transparent
    )

    HeaderType.IFTAR_TIME -> Brush.horizontalGradient(
        0f to Color(0xFFFFDDA8).copy(alpha = 0.98f),
        0.42f to Color(0xFFFFCB7B).copy(alpha = 0.76f),
        1f to Color.Transparent
    )

    HeaderType.SUHOOR_TIME -> Brush.horizontalGradient(
        0f to Color(0xFF08295F).copy(alpha = 0.98f),
        0.48f to Color(0xFF0B3678).copy(alpha = 0.70f),
        1f to Color.Transparent
    )

    HeaderType.REMINDER -> Brush.horizontalGradient(
        0f to Color(0xFFE9F7EF).copy(alpha = 0.98f),
        0.52f to Color(0xFFE9F7EF).copy(alpha = 0.84f),
        1f to Color.Transparent
    )
}

@Composable
private fun SliderBackgroundImage(type: HeaderType) {
    val resId = when (type) {
        HeaderType.IFTAR_TIME -> R.drawable.top_left_mosque_home_graound
        HeaderType.REMINDER -> R.drawable.serenquran
        else -> R.drawable.mosque_illuminated
    }

    Image(
        painter = painterResource(resId),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxSize()
            .alpha(
                when (type) {
                    HeaderType.DYNAMIC_PRAYER -> 0.78f
                    HeaderType.SUHOOR_TIME -> 0.70f
                    HeaderType.IFTAR_TIME -> 0.62f
                    HeaderType.REMINDER -> 0.56f
                    HeaderType.NEXT_PRAYER -> 0.72f
                }
            )
    )
}

@Composable
private fun PrayerHeroContent(
    title: String,
    subtitle: String,
    hint: String,
    locationLabel: String
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, top = 14.dp, end = 16.dp, bottom = 12.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.Top) {
            IconBadge(
                icon = Icons.Outlined.NightsStay,
                background = Color(0xFF075A4F),
                tint = Color(0xFFFFDE78),
                border = Color(0xFF73C7A0)
            )

            Spacer(Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    color = Color(0xFFFFC84A),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(Modifier.height(4.dp))

                Text(
                    text = subtitle,
                    color = Color.White,
                    fontSize = 20.sp,
                    lineHeight = 24.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                if (locationLabel.isNotBlank()) {
                    Spacer(Modifier.height(4.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Outlined.LocationOn,
                            contentDescription = null,
                            tint = Color.White.copy(alpha = 0.70f),
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(Modifier.width(4.dp))
                        Text(
                            text = locationLabel,
                            color = Color.White.copy(alpha = 0.78f),
                            fontSize = 12.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }

        PillButton(
            text = hint,
            icon = Icons.Outlined.AccessTime,
            background = Color(0xFF168477).copy(alpha = 0.76f),
            contentColor = Color.White,
            modifier = Modifier
                .padding(start = 62.dp)
                .fillMaxWidth(),
            showChevron = true
        )
    }
}

@Composable
private fun FastingTimeContent(
    type: HeaderType,
    title: String,
    subtitle: String,
    hint: String,
    footer: String,
    isAlarmEnabled: Boolean,
    onAlarmToggle: (() -> Unit)?
) {
    val isIftar = type == HeaderType.IFTAR_TIME
    val titleColor = if (isIftar) Color(0xFF6B2B0A) else Color(0xFF57C5F3)
    val textColor = if (isIftar) Color(0xFF2A1205) else Color.White
    val mutedColor = if (isIftar) Color(0xFF5D321A) else Color.White.copy(alpha = 0.72f)
    val chipBackground = if (isIftar) Color.White.copy(alpha = 0.48f) else Color(0xFF3E7BC6).copy(alpha = 0.62f)
    val badgeBackground = if (isIftar) Color(0xFFFF8C12) else Color(0xFF103D7E)
    val badgeBorder = if (isIftar) Color(0xFFFFB865) else Color(0xFF5E9DEA)

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 14.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.Top) {
                IconBadge(
                    icon = headerIcon(type),
                    background = badgeBackground,
                    tint = Color.White,
                    border = badgeBorder
                )

                Spacer(Modifier.width(12.dp))

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(top = 2.dp)
                ) {
                    Text(
                        text = title,
                        color = titleColor,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(Modifier.height(4.dp))

                    Text(
                        text = subtitle,
                        color = textColor,
                        fontSize = 28.sp,
                        lineHeight = 32.sp,
                        fontWeight = FontWeight.ExtraBold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        text = hint,
                        color = mutedColor,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            if (footer.isNotBlank()) {
                PillButton(
                    text = footer,
                    icon = Icons.Outlined.AccessTime,
                    background = chipBackground,
                    contentColor = textColor,
                    modifier = Modifier
                        .padding(start = 62.dp)
                        .fillMaxWidth(),
                    showChevron = false
                )
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(14.dp)
                .size(32.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White.copy(alpha = if (isIftar) 0.54f else 0.12f))
                .border(
                    width = 1.dp,
                    color = if (isIftar) Color.White.copy(alpha = 0.38f) else Color.White.copy(alpha = 0.28f),
                    shape = RoundedCornerShape(16.dp)
                )
                .clickable(enabled = onAlarmToggle != null) { onAlarmToggle?.invoke() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = if (isAlarmEnabled) Icons.Outlined.Alarm else Icons.Outlined.ChevronRight,
                contentDescription = null,
                tint = if (isIftar) Color(0xFF3A1908) else Color.White,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
private fun IconBadge(
    icon: ImageVector,
    background: Color,
    tint: Color,
    border: Color
) {
    Box(
        modifier = Modifier
            .size(50.dp)
            .clip(RoundedCornerShape(25.dp))
            .background(background)
            .border(1.dp, border.copy(alpha = 0.82f), RoundedCornerShape(25.dp)),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = tint,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
private fun PillButton(
    text: String,
    icon: ImageVector?,
    background: Color,
    contentColor: Color,
    modifier: Modifier = Modifier,
    showChevron: Boolean
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(26.dp))
            .background(background)
            .border(1.dp, Color.White.copy(alpha = 0.18f), RoundedCornerShape(26.dp))
            .padding(horizontal = 14.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = contentColor,
                modifier = Modifier.size(19.dp)
            )
            Spacer(Modifier.width(9.dp))
        }
        Text(
            text = text,
            color = contentColor,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f, fill = false)
        )
        if (showChevron) {
            Spacer(Modifier.width(8.dp))
            Icon(
                imageVector = Icons.Outlined.ChevronRight,
                contentDescription = null,
                tint = contentColor,
                modifier = Modifier.size(22.dp)
            )
        }
    }
}

@Composable
private fun DynamicReminderCard(
    title: String,
    subtitle: String,
    hint: String,
    language: String = "en",
    onClick: ((Int) -> Unit)? = null
) {
    val viewModel: com.hathway.ramadankareem2026.ui.tips.presentation.viewmodel.RandomRamadanTipsViewModel =
        viewModel(factory = RandomRamadanTipsViewModelFactory())
    val currentTip by viewModel.currentTip.collectAsStateWithLifecycle()

    // Auto-refresh tip periodically
    LaunchedEffect(Unit) {
        while (true) {
            kotlinx.coroutines.delay(20000) // Refresh every 20 seconds
            viewModel.loadNewRandomTip()
        }
    }

    val isDark = androidx.compose.foundation.isSystemInDarkTheme()
    val cardBackground = if (isDark) MaterialTheme.colorScheme.surfaceVariant else MaterialTheme.colorScheme.primaryContainer
    val primaryColor = MaterialTheme.colorScheme.primary
    val onSurfaceColor = MaterialTheme.colorScheme.onSurface
    val onSurfaceVariantColor = MaterialTheme.colorScheme.onSurfaceVariant

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .padding(end = 4.dp),
        shape = RoundedCornerShape(26.dp),
        colors = CardDefaults.cardColors(containerColor = cardBackground),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(R.drawable.serenquran),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(if (isDark) 0.05f else 0.12f)
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(14.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.Top) {
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .clip(RoundedCornerShape(25.dp))
                            .background(primaryColor)
                            .border(1.dp, Color.White.copy(alpha = 0.2f), RoundedCornerShape(25.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = headerIcon(HeaderType.REMINDER),
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Spacer(Modifier.width(14.dp))

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(top = 2.dp)
                    ) {
                        val tip = currentTip
                        Text(
                            text = title,
                            color = primaryColor,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = tip?.title?.getText(language) ?: subtitle,
                            color = onSurfaceColor,
                            fontSize = 18.sp,
                            lineHeight = 22.sp,
                            fontWeight = FontWeight.ExtraBold,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )

                        Spacer(modifier = Modifier.height(2.dp))

                        Text(
                            text = tip?.content?.getText(language) ?: hint,
                            color = onSurfaceVariantColor,
                            fontSize = 13.sp,
                            lineHeight = 17.sp,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                PillButton(
                    text = "Read More",
                    icon = null,
                    background = primaryColor.copy(alpha = 0.15f),
                    contentColor = primaryColor,
                    modifier = Modifier
                        .padding(start = 62.dp)
                        .width(130.dp)
                        .clickable(enabled = onClick != null) {
                            currentTip?.id?.let { id -> onClick?.invoke(id) }
                        },
                    showChevron = true
                )
            }
        }
    }
}
