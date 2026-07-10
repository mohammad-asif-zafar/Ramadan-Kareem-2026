package com.hathway.ramadankareem2026.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.hathway.ramadankareem2026.ui.icons.ComposeIcons.HolyQuran
import com.hathway.ramadankareem2026.ui.icons.ComposeIcons.NearbyMosque
import com.hathway.ramadankareem2026.ui.icons.ComposeIcons.QiblaCompass
import com.hathway.ramadankareem2026.ui.icons.ComposeIcons.ZakatDonation
import com.hathway.ramadankareem2026.ui.theme.Emerald
import com.hathway.ramadankareem2026.ui.theme.Gold


object ComposeIcons {

    // Replace with your project's color theme reference or Color(0xFFEAA135)
    private val IconColor = Color(0xFFEAA135)

    val ZakatDonation: ImageVector by lazy {
        ImageVector.Builder(
            name = "ZakatDonation",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).path(
            stroke = SolidColor(IconColor),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            // 1. Coin Circle (Top Center)
            moveTo(14.5f, 6.5f)
            curveTo(14.5f, 8.43f, 12.93f, 10f, 11f, 10f)
            curveTo(9.07f, 10f, 7.5f, 8.43f, 7.5f, 6.5f)
            curveTo(7.5f, 4.57f, 9.07f, 3f, 11f, 3f)
            curveTo(12.93f, 3f, 14.5f, 4.57f, 14.5f, 6.5f)
            close()

            // 2. Star Detail inside the Coin (Cross Star lines)
            moveTo(11f, 4.5f)
            lineTo(11f, 8.5f)
            moveTo(9f, 6.5f)
            lineTo(13f, 6.5f)
            moveTo(9.6f, 5.1f)
            lineTo(12.4f, 7.9f)
            moveTo(12.4f, 5.1f)
            lineTo(9.6f, 7.9f)

            // 3. Main Supporting Hand Curve
            moveTo(6f, 14.5f)
            lineTo(10.5f, 11f)
            curveTo(11.8f, 10f, 13.5f, 10.5f, 14.5f, 11.5f)
            lineTo(20f, 11.5f)
            curveTo(21f, 11.5f, 21.5f, 12.5f, 20.5f, 13f)
            lineTo(14f, 18.5f)
            curveTo(12.5f, 19.5f, 10.5f, 19.5f, 9.2f, 18.2f)
            close()

            // 4. Cuff / Sleeve Block at Wrist base
            moveTo(2.5f, 14.5f)
            lineTo(5.5f, 11.5f)
            lineTo(8f, 14f)
            lineTo(5f, 17f)
            close()

            // 5. Cuff Button Dot
            moveTo(5.2f, 14.2f)
            curveTo(5.2f, 14.5f, 5f, 14.7f, 4.7f, 14.7f)
            curveTo(4.4f, 14.7f, 4.2f, 14.5f, 4.2f, 14.2f)
            curveTo(4.2f, 13.9f, 4.4f, 13.7f, 4.7f, 13.7f)
            curveTo(5f, 13.7f, 5.2f, 13.9f, 5.2f, 14.2f)
            close()
        }.build()
    }

    val NearbyMosque: ImageVector by lazy {
        ImageVector.Builder(
            name = "NearbyMosque",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).path(
            stroke = SolidColor(IconColor),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            // 1. Central Dome
            moveTo(12f, 13f)
            curveTo(12f, 9f, 15.5f, 9f, 16.5f, 10f)
            curveTo(16.5f, 13f, 12f, 13f, 12f, 13f) // Right dome base
            close()

            moveTo(12f, 13f)
            curveTo(12f, 9f, 8.5f, 9f, 7.5f, 10f)
            curveTo(7.5f, 13f, 12f, 13f, 12f, 13f) // Left dome base
            close()

            // Dome Top Finial/Crescent Tip
            moveTo(12f, 9f)
            lineTo(12f, 7.5f)

            // 2. Main Building Foundation Wall
            moveTo(6f, 13f)
            lineTo(6f, 21f)
            lineTo(18f, 21f)
            lineTo(18f, 13f)
            close()

            // 3. Central Main Arched Entrance
            moveTo(10f, 21f)
            lineTo(10f, 18f)
            curveTo(10f, 16.8f, 11f, 16f, 12f, 16f)
            curveTo(13f, 16f, 14f, 16.8f, 14f, 18f)
            lineTo(14f, 21f)

            // 4. Left Minaret Tower
            moveTo(5f, 21f)
            lineTo(5f, 8f)
            lineTo(3f, 8f)
            lineTo(3f, 21f)
            // Left Minaret Balcony
            moveTo(2.5f, 11f)
            lineTo(5.5f, 11f)
            // Left Minaret Top Dome Tip
            moveTo(3f, 8f)
            curveTo(3f, 5.5f, 4f, 5f, 4f, 3f)
            curveTo(4f, 5f, 5f, 5.5f, 5f, 8f)

            // 5. Right Minaret Tower
            moveTo(19f, 21f)
            lineTo(19f, 8f)
            lineTo(21f, 8f)
            lineTo(21f, 21f)
            // Right Minaret Balcony
            moveTo(18.5f, 11f)
            lineTo(21.5f, 11f)
            // Right Minaret Top Dome Tip
            moveTo(19f, 8f)
            curveTo(19f, 5.5f, 20f, 5f, 20f, 3f)
            curveTo(20f, 5f, 21f, 5.5f, 21f, 8f)
        }.build()
    }

    val QiblaCompass: ImageVector by lazy {
        ImageVector.Builder(
            name = "QiblaCompass",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).path(
            stroke = SolidColor(IconColor),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            // 1. Outer Compass Ring
            moveTo(12f, 2f)
            curveTo(6.48f, 2f, 2f, 6.48f, 2f, 12f)
            curveTo(2f, 17.52f, 6.48f, 22f, 12f, 22f)
            curveTo(17.52f, 22f, 22f, 17.52f, 22f, 12f)
            curveTo(22f, 6.48f, 17.52f, 2f, 12f, 2f)
            close()

            // 2. Compass Dial Marks (North, South, East, West ticks)
            moveTo(12f, 4f)
            lineTo(12f, 5.5f) // North

            moveTo(12f, 18.5f)
            lineTo(12f, 20f) // South

            moveTo(4f, 12f)
            lineTo(5.5f, 12f) // West

            moveTo(18.5f, 12f)
            lineTo(20f, 12f) // East

            // 3. Diagonal Needle (Main pointer)
            moveTo(16.5f, 7.5f)  // Top-right tip
            lineTo(12.5f, 11.5f) // Center pivot entry
            lineTo(7.5f, 16.5f)  // Bottom-left tip
            lineTo(11.5f, 12.5f) // Center pivot return
            close()

            // Split dividing line for the needle structure
            moveTo(7.5f, 16.5f)
            lineTo(16.5f, 7.5f)

            // 4. Center Pivot Pin/Dot
            moveTo(12.5f, 12f)
            curveTo(12.5f, 12.28f, 12.28f, 12.5f, 12f, 12.5f)
            curveTo(11.72f, 12.5f, 11.5f, 12.28f, 11.5f, 12f)
            curveTo(11.5f, 11.72f, 11.72f, 11.5f, 12f, 11.5f)
            curveTo(12.28f, 11.5f, 12.5f, 11.72f, 12.5f, 12f)
            close()
        }.build()
    }

    val HolyQuran: ImageVector by lazy {
        ImageVector.Builder(
            name = "HolyQuran",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).path(
            stroke = SolidColor(IconColor),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            // 1. Center spine / dividing line
            moveTo(12f, 5f)
            lineTo(12f, 19f)

            // 2. Left Page Outlines
            moveTo(12f, 5f)
            curveTo(9f, 4f, 5f, 4f, 3f, 5f)
            lineTo(3f, 18f)
            curveTo(5f, 17f, 9f, 17f, 12f, 18f)

            // 3. Right Page Outlines
            moveTo(12f, 5f)
            curveTo(15f, 4f, 19f, 4f, 21f, 5f)
            lineTo(21f, 18f)
            curveTo(19f, 17f, 15f, 17f, 12f, 18f)

            // 4. Left Page Text Script Lines
            moveTo(5.5f, 8f)
            curveTo(7f, 7.5f, 8.5f, 7.5f, 9.5f, 8f)

            moveTo(5.5f, 11f)
            curveTo(7f, 10.5f, 8.5f, 10.5f, 9.5f, 11f)

            moveTo(5.5f, 14f)
            curveTo(7f, 13.5f, 8.5f, 13.5f, 9.5f, 14f)

            // 5. Right Page Text Script Lines
            moveTo(14.5f, 8f)
            curveTo(15.5f, 7.5f, 17f, 7.5f, 18.5f, 8f)

            moveTo(14.5f, 11f)
            curveTo(15.5f, 10.5f, 17f, 10.5f, 18.5f, 11f)

            moveTo(14.5f, 14f)
            curveTo(15.5f, 13.5f, 17f, 13.5f, 18.5f, 14f)
        }.build()
    }
}

object RamadanIcons {
    // Modify this color token or dynamically link it to MaterialTheme.colorScheme inside your comps
    val DefaultColor = Emerald

    // 🤲 1. DUA (Praying Hands)
    val Dua: ImageVector by lazy {
        ImageVector.Builder("Dua", 24.dp, 24.dp, 24f, 24f).path(
            stroke = SolidColor(DefaultColor), strokeLineWidth = 2f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round
        ) {
            moveTo(10f, 20f); curveTo(7.5f, 19.5f, 5.5f, 17.5f, 5.5f, 13.5f); lineTo(6.5f, 7.5f)
            moveTo(7.5f, 11f); lineTo(8.5f, 5.5f); moveTo(9.5f, 10.5f); lineTo(10.5f, 4.5f)
            moveTo(11.5f, 11.5f); lineTo(12.5f, 6.5f); curveTo(12.5f, 11f, 11.5f, 13f, 10f, 14.5f)
            moveTo(14f, 20f); curveTo(16.5f, 19.5f, 18.5f, 17.5f, 18.5f, 13.5f); lineTo(17.5f, 7.5f)
            moveTo(16.5f, 11f); lineTo(15.5f, 5.5f); moveTo(14.5f, 10.5f); lineTo(13.5f, 4.5f)
            moveTo(12.5f, 11.5f); lineTo(11.5f, 6.5f); curveTo(11.5f, 11f, 12.5f, 13f, 14f, 14.5f)
        }.build()
    }

    // 🪙 2. ZAKAT (Hand giving Coin)
    val Zakat: ImageVector by lazy {
        ImageVector.Builder("Zakat", 24.dp, 24.dp, 24f, 24f).path(
            stroke = SolidColor(Gold), strokeLineWidth = 2f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round
        ) {
            moveTo(14.5f, 6.5f); curveTo(14.5f, 8.4f, 12.9f, 10f, 11f, 10f); curveTo(9.1f, 10f, 7.5f, 8.4f, 7.5f, 6.5f); curveTo(7.5f, 4.6f, 9.1f, 3f, 11f, 3f); curveTo(12.9f, 3f, 14.5f, 4.6f, 14.5f, 6.5f); close()
            moveTo(11f, 5f); lineTo(11f, 8f); moveTo(9.5f, 6.5f); lineTo(12.5f, 6.5f)
            moveTo(6f, 15f); lineTo(10f, 11.5f); curveTo(11.5f, 10.5f, 13f, 11f, 14f, 12f); lineTo(20f, 12f); curveTo(21f, 12f, 21f, 13f, 20f, 13.5f); lineTo(14f, 18.5f); curveTo(12.5f, 19.5f, 10.5f, 19.5f, 9f, 18f); close()
            moveTo(2.5f, 15f); lineTo(5.5f, 12f); lineTo(7.5f, 14f); lineTo(4.5f, 17f); close()
        }.build()
    }

    // 🕌 3. MOSQUE (Islamic Masjid Dome)
    val Mosque: ImageVector by lazy {
        ImageVector.Builder("Mosque", 24.dp, 24.dp, 24f, 24f).path(
            stroke = SolidColor(DefaultColor), strokeLineWidth = 2f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round
        ) {
            moveTo(12f, 13f); curveTo(12f, 9f, 15.5f, 9f, 16.5f, 10f); curveTo(16.5f, 13f, 12f, 13f, 12f, 13f); close()
            moveTo(12f, 13f); curveTo(12f, 9f, 8.5f, 9f, 7.5f, 10f); curveTo(7.5f, 13f, 12f, 13f, 12f, 13f); close()
            moveTo(12f, 9f); lineTo(12f, 7.5f)
            moveTo(6f, 13f); lineTo(6f, 21f); lineTo(18f, 21f); lineTo(18f, 13f); close()
            moveTo(10f, 21f); lineTo(10f, 18f); curveTo(10f, 16.8f, 11f, 16f, 12f, 16f); curveTo(13f, 16f, 14f, 16.8f, 14f, 18f); lineTo(14f, 21f)
            moveTo(4f, 21f); lineTo(4f, 9f); moveTo(3f, 11f); lineTo(5f, 11f)
            moveTo(20f, 21f); lineTo(20f, 9f); moveTo(19f, 11f); lineTo(21f, 11f)
        }.build()
    }

    // 💡 4. TIPS (Islamic Lantern / Noor Lightbulb Concept)
    val Tips: ImageVector by lazy {
        ImageVector.Builder("Tips", 24.dp, 24.dp, 24f, 24f).path(
            stroke = SolidColor(DefaultColor), strokeLineWidth = 2f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round
        ) {
            moveTo(12f, 2f); lineTo(12f, 4f)
            moveTo(6f, 7f); curveTo(6f, 3.5f, 8.5f, 4f, 12f, 4f); curveTo(15.5f, 4f, 18f, 3.5f, 18f, 7f)
            lineTo(16f, 15f); lineTo(8f, 15f); close()
            moveTo(9f, 15f); lineTo(9f, 19f); curveTo(9f, 20f, 10f, 21f, 12f, 21f); curveTo(14f, 21f, 15f, 20f, 15f, 19f); lineTo(15f, 15f)
            moveTo(10f, 11f); lineTo(14f, 11f); moveTo(11f, 8f); lineTo(13f, 8f)
        }.build()
    }

    // 📿 5. ALLAH / DHIKR (Tasbih Prayer Beads Wrap)
    val Allah: ImageVector by lazy {
        ImageVector.Builder("Allah", 24.dp, 24.dp, 24f, 24f).path(
            stroke = SolidColor(DefaultColor), strokeLineWidth = 2f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round
        ) {
            moveTo(12f, 13f); curveTo(7.5f, 13f, 4f, 10.5f, 4f, 7.5f); curveTo(4f, 4.5f, 7.5f, 2f, 12f, 2f); curveTo(16.5f, 2f, 20f, 4.5f, 20f, 7.5f); curveTo(20f, 10.5f, 16.5f, 13f, 12f, 13f); close()
            // Tasbih nodes styling indicators
            moveTo(6f, 5f); lineTo(6.1f, 5f); moveTo(9f, 3.5f); lineTo(9.1f, 3.5f); moveTo(15f, 3.5f); lineTo(15.1f, 3.5f); moveTo(18f, 5f); lineTo(18.1f, 5f)
            moveTo(6f, 9f); lineTo(6.1f, 9f); moveTo(18f, 9f); lineTo(18.1f, 9f); moveTo(9f, 11.5f); lineTo(9.1f, 11.5f); moveTo(15f, 11.5f); lineTo(15.1f, 11.5f)
            // Pendant Minaret piece hanging below string loop base
            moveTo(12f, 13f); lineTo(12f, 17f); moveTo(11f, 17f); lineTo(13f, 17f); lineTo(12f, 21f); close()
        }.build()
    }

    // 📖 6. QURAN (Open Holy Book on Rehal)
    val Quran: ImageVector by lazy {
        ImageVector.Builder("Quran", 24.dp, 24.dp, 24f, 24f).path(
            stroke = SolidColor(DefaultColor), strokeLineWidth = 2f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round
        ) {
            moveTo(12f, 6f); lineTo(12f, 18f)
            moveTo(12f, 6f); curveTo(9.5f, 4.5f, 5.5f, 4.5f, 3.5f, 6f); lineTo(3.5f, 17f); curveTo(5.5f, 15.5f, 9.5f, 15.5f, 12f, 17f)
            moveTo(12f, 6f); curveTo(14.5f, 4.5f, 18.5f, 4.5f, 20.5f, 6f); lineTo(20.5f, 17f); curveTo(18.5f, 15.5f, 14.5f, 15.5f, 12f, 17f)
            moveTo(5.5f, 9f); lineTo(9.5f, 9f); moveTo(5.5f, 12f); lineTo(9.5f, 12f)
            moveTo(14.5f, 9f); lineTo(18.5f, 9f); moveTo(14.5f, 12f); lineTo(18.5f, 12f)
        }.build()
    }

    // 📅 7. CALENDAR (Ramadan Kareem / Hijri Date Tracker)
    val Calendar: ImageVector by lazy {
        ImageVector.Builder("Calendar", 24.dp, 24.dp, 24f, 24f).path(
            stroke = SolidColor(DefaultColor), strokeLineWidth = 2f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round
        ) {
            moveTo(19f, 4f); lineTo(5f, 4f); curveTo(3.9f, 4f, 3f, 4.9f, 3f, 6f); lineTo(3f, 20f); curveTo(3f, 21.1f, 3.9f, 22f, 5f, 22f); lineTo(19f, 22f); curveTo(20.1f, 22f, 21f, 21.1f, 21f, 20f); lineTo(21f, 6f); curveTo(21f, 4.9f, 20.1f, 4f, 19f, 4f); close()
            moveTo(16f, 2f); lineTo(16f, 6f); moveTo(8f, 2f); lineTo(8f, 6f); moveTo(3f, 10f); lineTo(21f, 10f)
            // Tiny Crescent Moon motif instead of generic placeholder grid dots inside the calendar body
            moveTo(13.5f, 13.5f); curveTo(11.5f, 13.5f, 10f, 15f, 10f, 17f); curveTo(10f, 18.5f, 11f, 19.5f, 12.5f, 19.5f); curveTo(11.5f, 19f, 11.5f, 17.5f, 12.5f, 16.5f); curveTo(13.5f, 15.5f, 14.5f, 15.5f, 14.5f, 14.5f); curveTo(14.5f, 14f, 14f, 13.5f, 13.5f, 13.5f); close()
        }.build()
    }

    // 🧭 8. QIBLA (Kaaba Pointing Directional Compass)
    val Qibla: ImageVector by lazy {
        ImageVector.Builder("Qibla", 24.dp, 24.dp, 24f, 24f).path(
            stroke = SolidColor(DefaultColor), strokeLineWidth = 2f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round
        ) {
            moveTo(12f, 22f); curveTo(17.52f, 22f, 22f, 17.52f, 22f, 12f); curveTo(22f, 6.48f, 17.52f, 2f, 12f, 2f); curveTo(6.48f, 2f, 2f, 6.48f, 2f, 12f); curveTo(2f, 17.52f, 6.48f, 22f, 12f, 22f); close()
            moveTo(12f, 4f); lineTo(12f, 6f); moveTo(4f, 12f); lineTo(6f, 12f)
            // Pointer needle
            moveTo(16f, 8f); lineTo(11.5f, 11.5f); lineTo(8f, 16f); lineTo(12.5f, 12.5f); close()
            moveTo(12f, 12f); curveTo(12.3f, 12f, 12.5f, 11.7f, 12.5f, 11.4f); curveTo(12.5f, 11.1f, 12.3f, 10.9f, 12f, 10.9f); curveTo(11.7f, 10.9f, 11.5f, 11.1f, 11.5f, 11.4f); curveTo(11.5f, 11.7f, 11.7f, 12f, 12f, 12f); close()
        }.build()
    }

    val QuranOnStand: ImageVector by lazy {
        ImageVector.Builder(
            name = "QuranOnStand",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).path(
            stroke = SolidColor(DefaultColor),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            // 1. Top Open Book Pages (Angled Perspective)
            // Center Spine
            moveTo(12f, 4f)
            lineTo(12f, 13f)

            // Left Page Outline
            moveTo(12f, 4f)
            lineTo(5f, 6.5f)
            lineTo(5f, 15.5f)
            lineTo(12f, 13f)
            close()

            // Right Page Outline
            moveTo(12f, 4f)
            lineTo(19f, 6.5f)
            lineTo(19f, 15.5f)
            lineTo(12f, 13f)
            close()

            // Left Page Inner Text Lines
            moveTo(7f, 9.5f)
            lineTo(10f, 8.5f)
            moveTo(7f, 12.5f)
            lineTo(10f, 11.5f)

            // Right Page Inner Text Lines
            moveTo(14f, 8.5f)
            lineTo(17f, 9.5f)
            moveTo(14f, 11.5f)
            lineTo(17f, 12.5f)

            // 2. X-Shaped Rihal Stand (Crossing legs underneath)
            // Front-Left Leg extending to bottom right
            moveTo(6.5f, 14.5f)
            lineTo(4f, 19.5f)
            lineTo(7f, 19.5f)
            lineTo(12f, 13.5f)

            // Front-Right Leg extending to bottom left
            moveTo(17.5f, 14.5f)
            lineTo(20f, 19.5f)
            lineTo(17f, 19.5f)
            lineTo(12f, 13.5f)
        }.build()
    }

    val PrayingHandsFromImage: ImageVector by lazy {
        ImageVector.Builder(
            name = "PrayingHandsFromImage",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).path(
            stroke = SolidColor(DefaultColor),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            // --- LEFT HAND ---
            // Outer palm wall boundary
            moveTo(8f, 21f)
            lineTo(5f, 21f)
            lineTo(5f, 13f)
            curveTo(5f, 11f, 6f, 10f, 6.5f, 8.5f)

            // Index Finger
            lineTo(8.5f, 3.5f)
            moveTo(8.5f, 6f)
            lineTo(8.5f, 12f)

            // Middle Finger
            moveTo(8.5f, 10f)
            lineTo(10f, 3f)
            moveTo(10f, 5.5f)
            lineTo(10f, 12.5f)

            // Ring Finger
            moveTo(10f, 10.5f)
            lineTo(11.5f, 4f)
            moveTo(11.5f, 6.5f)
            lineTo(11.5f, 13f)

            // Little Finger
            moveTo(11.5f, 11.5f)
            lineTo(13f, 5f)

            // Thumb structure fold back down to the wrist
            curveTo(13f, 9.5f, 10.5f, 12f, 9f, 13.5f)
            lineTo(9f, 21f)
            close()

            // --- RIGHT HAND (Perfectly Mirrored Symmetry) ---
            // Outer palm wall boundary
            moveTo(16f, 21f)
            lineTo(19f, 21f)
            lineTo(19f, 13f)
            curveTo(19f, 11f, 18f, 10f, 17.5f, 8.5f)

            // Index Finger
            lineTo(15.5f, 3.5f)
            moveTo(15.5f, 6f)
            lineTo(15.5f, 12f)

            // Middle Finger
            moveTo(15.5f, 10f)
            lineTo(14f, 3f)
            moveTo(14f, 5.5f)
            lineTo(14f, 12.5f)

            // Ring Finger
            moveTo(14f, 10.5f)
            lineTo(12.5f, 4f)
            moveTo(12.5f, 6.5f)
            lineTo(12.5f, 13f)

            // Little Finger
            moveTo(12.5f, 11.5f)
            lineTo(11f, 5f)

            // Thumb structure fold back down to the wrist
            curveTo(11f, 9.5f, 13.5f, 12f, 15f, 13.5f)
            lineTo(15f, 21f)
            close()
        }.build()
    }

    val PrayingHandsDuaStyle: ImageVector by lazy {
        ImageVector.Builder(
            name = "PrayingHandsDuaStyle",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).path(
            stroke = SolidColor(DefaultColor),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            // --- LEFT HAND ---
            // Little finger
            moveTo(5f, 9.5f)
            lineTo(7f, 4.5f)
            // Ring finger
            moveTo(6.5f, 10.5f)
            lineTo(8.5f, 4f)
            // Middle finger
            moveTo(8f, 11.5f)
            lineTo(10f, 4.5f)
            // Index finger
            moveTo(9.5f, 12.5f)
            lineTo(11f, 5.5f)

            // Left Palm Outline & Base
            moveTo(5f, 9.5f)
            lineTo(4.5f, 13f)
            curveTo(4.5f, 16.5f, 6.5f, 19.5f, 9.5f, 20f)
            lineTo(10.5f, 20f)
            lineTo(10f, 15f) // Thumb inner crease
            // Left Thumb
            moveTo(10f, 15f)
            lineTo(7.5f, 13.5f)

            // --- RIGHT HAND ---
            // Little finger
            moveTo(19f, 9.5f)
            lineTo(17f, 4.5f)
            // Ring finger
            moveTo(17.5f, 10.5f)
            lineTo(15.5f, 4f)
            // Middle finger
            moveTo(16f, 11.5f)
            lineTo(14f, 4.5f)
            // Index finger
            moveTo(14.5f, 12.5f)
            lineTo(13f, 5.5f)

            // Right Palm Outline & Base
            moveTo(19f, 9.5f)
            lineTo(19.5f, 13f)
            curveTo(19.5f, 16.5f, 17.5f, 19.5f, 14.5f, 20f)
            lineTo(13.5f, 20f)
            lineTo(14f, 15f) // Thumb inner crease
            // Right Thumb
            moveTo(14f, 15f)
            lineTo(16.5f, 13.5f)
        }.build()
    }

    val QuranOnStandLineArt: ImageVector by lazy {
        ImageVector.Builder(
            name = "QuranOnStandLineArt",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).path(
            stroke = SolidColor(DefaultColor),
            strokeLineWidth = 1.8f, // Balanced weight to match your line-art style
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            // --- 1. OUTER BOOK BASE COVER FRAME ---
            moveTo(7f, 5f)
            lineTo(4f, 13f)
            lineTo(12f, 17.5f)
            lineTo(20f, 13f)
            lineTo(17f, 5f)

            // --- 2. INNER OPEN PAGES ---
            // Center spine crease
            moveTo(12f, 6f)
            lineTo(12f, 15.5f)

            // Left Page
            moveTo(12f, 6f)
            lineTo(6f, 4.5f)
            lineTo(5.5f, 12.5f)
            lineTo(12f, 15.5f)

            // Right Page
            moveTo(12f, 6f)
            lineTo(18f, 4.5f)
            lineTo(18.5f, 12.5f)
            lineTo(12f, 15.5f)

            // --- 3. PAGE TEXT LINES ---
            // Left page lines (4 distinct lines matching image)
            moveTo(7.5f, 7f); lineTo(10.5f, 8f)
            moveTo(7.2f, 9f); lineTo(10.5f, 10f)
            moveTo(7.0f, 11f); lineTo(10.5f, 12f)
            moveTo(7.3f, 13f); lineTo(10.5f, 14f)

            // Right page lines (4 distinct lines matching image)
            moveTo(16.5f, 7f); lineTo(13.5f, 8f)
            moveTo(16.8f, 9f); lineTo(13.5f, 10f)
            moveTo(17.0f, 11f); lineTo(13.5f, 12f)
            moveTo(16.7f, 13f); lineTo(13.5f, 14f)

            // --- 4. SCALLOPED/WAVY RIHAL STAND ---
            // Left descending stand leg with top scalloped bumps
            moveTo(10f, 16.5f)
            curveTo(8.5f, 16f, 7.5f, 16.8f, 6.5f, 17.5f)
            curveTo(5.5f, 18.2f, 4.5f, 18f, 3.5f, 19f)
            lineTo(3.5f, 20.5f)
            lineTo(10.5f, 16.2f)

            // Right descending stand leg with top scalloped bumps
            moveTo(14f, 16.5f)
            curveTo(15.5f, 16f, 16.5f, 16.8f, 17.5f, 17.5f)
            curveTo(18.5f, 18.2f, 19.5f, 18f, 20.5f, 19f)
            lineTo(20.5f, 20.5f)
            lineTo(13.5f, 16.2f)
        }.build()
    }

    val GridViewIcon: ImageVector by lazy {
        ImageVector.Builder(
            name = "GridViewIcon",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).path(
            stroke = SolidColor(DefaultColor),
            strokeLineWidth = 2.5f, // Thicker stroke weight to match your image exactly
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            // Top-Left Card Item
            moveTo(6.5f, 4f)
            lineTo(8.5f, 4f)
            curveTo(10f, 4f, 10f, 4f, 10f, 5.5f)
            lineTo(10f, 7.5f)
            curveTo(10f, 9f, 10f, 9f, 8.5f, 9f)
            lineTo(6.5f, 9f)
            curveTo(5f, 9f, 5f, 9f, 5f, 7.5f)
            lineTo(5f, 5.5f)
            curveTo(5f, 4f, 5f, 4f, 6.5f, 4f)
            close()

            // Top-Right Card Item
            moveTo(15.5f, 4f)
            lineTo(17.5f, 4f)
            curveTo(19f, 4f, 19f, 4f, 19f, 5.5f)
            lineTo(19f, 7.5f)
            curveTo(19f, 9f, 19f, 9f, 17.5f, 9f)
            lineTo(15.5f, 9f)
            curveTo(14f, 9f, 14f, 9f, 14f, 7.5f)
            lineTo(14f, 5.5f)
            curveTo(14f, 4f, 14f, 4f, 15.5f, 4f)
            close()

            // Bottom-Left Card Item
            moveTo(6.5f, 15f)
            lineTo(8.5f, 15f)
            curveTo(10f, 15f, 10f, 15f, 10f, 16.5f)
            lineTo(10f, 18.5f)
            curveTo(10f, 20f, 10f, 20f, 8.5f, 20f)
            lineTo(6.5f, 20f)
            curveTo(5f, 20f, 5f, 20f, 5f, 18.5f)
            lineTo(5f, 16.5f)
            curveTo(5f, 15f, 5f, 15f, 6.5f, 15f)
            close()

            // Bottom-Right Card Item
            moveTo(15.5f, 15f)
            lineTo(17.5f, 15f)
            curveTo(19f, 15f, 19f, 15f, 19f, 16.5f)
            lineTo(19f, 18.5f)
            curveTo(19f, 20f, 19f, 20f, 17.5f, 20f)
            lineTo(15.5f, 20f)
            curveTo(14f, 20f, 14f, 20f, 14f, 18.5f)
            lineTo(14f, 16.5f)
            curveTo(14f, 15f, 14f, 15f, 15.5f, 15f)
            close()
        }.build()
    }

    val ListViewIcon: ImageVector by lazy {
        ImageVector.Builder(
            name = "ListViewIcon",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).path(
            stroke = SolidColor(DefaultColor),
            strokeLineWidth = 2.5f, // Matching the exact stroke thickness of the Grid icon
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            // --- Top Item Row ---
            // Bullet Point Square Indicator
            moveTo(6.5f, 4f)
            lineTo(7.5f, 4f)
            curveTo(9f, 4f, 9f, 4f, 9f, 5.5f)
            lineTo(9f, 6.5f)
            curveTo(9f, 8f, 9f, 8f, 7.5f, 8f)
            lineTo(6.5f, 8f)
            curveTo(5f, 8f, 5f, 8f, 5f, 6.5f)
            lineTo(5f, 5.5f)
            curveTo(5f, 4f, 5f, 4f, 6.5f, 4f)
            close()
            // Text Content Placeholder Bar
            moveTo(13f, 6f)
            lineTo(19f, 6f)

            // --- Middle Item Row ---
            // Bullet Point Square Indicator
            moveTo(6.5f, 10.5f)
            lineTo(7.5f, 10.5f)
            curveTo(9f, 10.5f, 9f, 10.5f, 9f, 12f)
            lineTo(9f, 13f)
            curveTo(9f, 14.5f, 9f, 14.5f, 7.5f, 14.5f)
            lineTo(6.5f, 14.5f)
            curveTo(5f, 14.5f, 5f, 14.5f, 5f, 13f)
            lineTo(5f, 12f)
            curveTo(5f, 10.5f, 5f, 10.5f, 6.5f, 10.5f)
            close()
            // Text Content Placeholder Bar
            moveTo(13f, 12.5f)
            lineTo(19f, 12.5f)

            // --- Bottom Item Row ---
            // Bullet Point Square Indicator
            moveTo(6.5f, 17f)
            lineTo(7.5f, 17f)
            curveTo(9f, 17f, 9f, 17f, 9f, 18.5f)
            lineTo(9f, 19.5f)
            curveTo(9f, 21f, 9f, 21f, 7.5f, 21f)
            lineTo(6.5f, 21f)
            curveTo(5f, 21f, 5f, 21f, 5f, 19.5f)
            lineTo(5f, 18.5f)
            curveTo(5f, 17f, 5f, 17f, 6.5f, 17f)
            close()
            // Text Content Placeholder Bar
            moveTo(13f, 19.5f)
            lineTo(19f, 19.5f)
        }.build()
    }
}


@Composable
fun RamadanIconsShowcase() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = "Ramadan 2026 Icon Set",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Grid Rows showcasing each generated vector element
        IconRowItem(label = "Holy Quran", icon = HolyQuran)
        IconRowItem(label = "Qibla Compass", icon = QiblaCompass)
       // IconRowItem(label = "Praying Hands (Dua)", icon = PrayingHandsDua)
        IconRowItem(label = "Nearby Mosque", icon = NearbyMosque)
        IconRowItem(label = "Zakat Donation", icon = ZakatDonation)
    }
}

@Composable
private fun IconRowItem(label: String, icon: ImageVector) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                shape = MaterialTheme.shapes.medium
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        // Icon drawing dynamically linked with the main primary theme color
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(32.dp)
        )
    }
}

@Preview(name = "Light Theme Showcase", showBackground = true)
@Preview(
    name = "Dark Theme Showcase",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
fun RamadanIconsPreview() {
    // Wrap with your exact app theme class if available
    MaterialTheme {
        Surface {
            RamadanIconsShowcase()
        }
    }
}




