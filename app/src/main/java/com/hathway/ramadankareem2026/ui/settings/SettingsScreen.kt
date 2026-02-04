package com.hathway.ramadankareem2026.ui.settings

import android.content.Intent
import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.automirrored.filled.ContactSupport
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SettingsSystemDaydream
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.hathway.ramadankareem2026.R
import com.hathway.ramadankareem2026.core.localization.LocalizationManager
import com.hathway.ramadankareem2026.ui.components.RamadanToolbar
import com.hathway.ramadankareem2026.ui.settings.components.LanguageSettingsSection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavController,
    onBack: () -> Unit = { navController.popBackStack() }
) {
    val context = LocalContext.current
    val localizationManager = remember { LocalizationManager(context) }
    var selectedLanguage by remember { mutableStateOf(localizationManager.getCurrentLanguage()) }
    
    // Update selectedLanguage when it changes in preferences
    LaunchedEffect(Unit) {
        selectedLanguage = localizationManager.getCurrentLanguage()
    }
    
    Scaffold(
        topBar = {
            RamadanToolbar(
                title = stringResource(R.string.settings),
                showBack = true,
                onBackClick = onBack
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Preferences Section
            item {
                SettingsSection(title = "🔔 ${stringResource(R.string.preferences)}") {
                    SettingsItem(
                        icon = Icons.Default.Notifications,
                        title = stringResource(R.string.notification_settings),
                        subtitle = stringResource(R.string.manage_prayer_reminders),
                        onClick = { /* Navigate to notification settings */ }
                    )
                    
                    SettingsItem(
                        icon = Icons.AutoMirrored.Filled.VolumeUp,
                        title = stringResource(R.string.prayer_reminders),
                        subtitle = stringResource(R.string.configure_azan_prayer_alerts),
                        onClick = { /* Navigate to prayer reminders */ }
                    )
                    
                    SettingsItem(
                        icon = Icons.AutoMirrored.Filled.VolumeUp,
                        title = stringResource(R.string.azan_sound),
                        subtitle = stringResource(R.string.choose_azan_audio_prayer),
                        onClick = { /* Navigate to Azan sound settings */ }
                    )
                }
            }
            // Language & Region Section
            item {
                LanguageSettingsSection(
                    currentLanguage = selectedLanguage,
                    onLanguageChanged = { language ->
                        selectedLanguage = language
                        localizationManager.setLanguage(language)
                        // Force activity recreation to apply language changes
                        (context as? ComponentActivity)?.recreate()
                    }
                )
            }
            
            // Appearance Section
            item {
                SettingsSection(title = "🎨 ${stringResource(R.string.appearance)}") {
                    var selectedTheme by remember { mutableStateOf("System") }
                    ThemeSelector(
                        currentTheme = selectedTheme,
                        onThemeChanged = { selectedTheme = it }
                    )
                }
            }
            
            //  About Section
            item {
                SettingsSection(title = "ℹ️ ${stringResource(R.string.about)}") {
                    SettingsItem(
                        icon = Icons.Default.Settings,
                        title = stringResource(R.string.version),
                        subtitle = "Version 1.0.0",
                        onClick = { /* Show version info */ },
                        showArrow = false
                    )
                    
                    SettingsItem(
                        icon = Icons.Default.Person,
                        title = stringResource(R.string.about_app),
                        subtitle = "Learn more about Ramadan Kareem 2026",
                        onClick = { /* Navigate to about */ }
                    )
                    
                    SettingsItem(
                        icon = Icons.Default.PrivacyTip,
                        title = stringResource(R.string.privacy_policy),
                        subtitle = stringResource(R.string.read_privacy_policy),
                        onClick = { 
                            openPrivacyPolicy(context)
                        }
                    )
                    
                    SettingsItem(
                        icon = Icons.Default.Settings,
                        title = stringResource(R.string.terms_use),
                        subtitle = stringResource(R.string.read_terms_conditions),
                        onClick = { /* Navigate to terms */ }
                    )
                }
            }
            
            // Support Section
            item {
                SettingsSection(title = "💬 ${stringResource(R.string.support)}") {
                    SettingsItem(
                        icon = Icons.Default.Help,
                        title = stringResource(R.string.feedback),
                        subtitle = stringResource(R.string.share_feedback_us),
                        onClick = { /* Navigate to feedback */ }
                    )
                    
                    SettingsItem(
                        icon = Icons.Default.Star,
                        title = stringResource(R.string.rate_app),
                        subtitle = stringResource(R.string.rate_us_google_play),
                        onClick = { /* Navigate to rate app */ }
                    )
                    
                    SettingsItem(
                        icon = Icons.AutoMirrored.Filled.ContactSupport,
                        title = stringResource(R.string.get_help_support_team),
                        subtitle = stringResource(R.string.support),
                        onClick = { /* Navigate to support */ }
                    )
                }
            }
            
            // Credits Section
            item {
                SettingsSection(title = "🙏 ${stringResource(R.string.special_thanks)}") {
                    SettingsItem(
                        icon = Icons.Default.Person,
                        title = stringResource(R.string.developer),
                        subtitle = stringResource(R.string.mohammad_asif_zafar),
                        onClick = { /* Navigate to developer info */ }
                    )
                    
                    SettingsItem(
                        icon = Icons.Default.Settings,
                        title = stringResource(R.string.design_ui_ux),
                        subtitle = stringResource(R.string.ramadan_kareem_team),
                        onClick = { /* Navigate to design credits */ }
                    )
                    
                    SettingsItem(
                        icon = Icons.Default.Person,
                        title = stringResource(R.string.islamic_scholars_community_contributors),
                        subtitle = stringResource(R.string.data_sources),
                        onClick = { /* Navigate to special thanks */ }
                    )
                }
            }
            
            // API Credits Section
            item {
                SettingsSection(title = "📡 ${stringResource(R.string.data_api_credits)}") {
                    SettingsItem(
                        icon = Icons.Default.Settings,
                        title = "Quran API",
                        subtitle = "AlQuran Cloud API - Quran text and audio",
                        onClick = { /* Navigate to API info */ },
                        showArrow = false
                    )
                    
                    SettingsItem(
                        icon = Icons.Default.Settings,
                        title = "Prayer Calculations",
                        subtitle = "Adhan library by Batoul Apps",
                        onClick = { /* Navigate to library info */ },
                        showArrow = false
                    )
                    
                    SettingsItem(
                        icon = Icons.Default.Settings,
                        title = "Location Services",
                        subtitle = "Google Play Services",
                        onClick = { /* Navigate to location info */ },
                        showArrow = false
                    )
                    
                    SettingsItem(
                        icon = Icons.Default.Settings,
                        title = "Maps & Places",
                        subtitle = "Google Maps and Google Places APIs",
                        onClick = { /* Navigate to maps info */ },
                        showArrow = false
                    )
                }
            }
        }
    }
}

@Composable
private fun SettingsSection(
    title: String,
    content: @Composable () -> Unit
) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column {
                content()
            }
        }
    }
}

@Composable
private fun SettingsItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit,
    showArrow: Boolean = true
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        )
        
        Spacer(modifier = Modifier.width(16.dp))
        
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        
        if (showArrow) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(16.dp)
            )
        }
    }
    
    HorizontalDivider(
        modifier = Modifier.padding(horizontal = 16.dp),
        thickness = 0.5.dp,
        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
    )
}

@Composable
private fun ThemeSelector(
    currentTheme: String,
    onThemeChanged: (String) -> Unit
) {
    val themes = listOf(
        ThemeOption("Light", Icons.Default.LightMode, stringResource(R.string.light)),
        ThemeOption("Dark", Icons.Default.DarkMode, stringResource(R.string.dark)),
        ThemeOption("System", Icons.Default.SettingsSystemDaydream, stringResource(R.string.system))
    )
    
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = when (currentTheme) {
                    "Light" -> Icons.Default.LightMode
                    "Dark" -> Icons.Default.DarkMode
                    else -> Icons.Default.SettingsSystemDaydream
                },
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(24.dp)
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = stringResource(R.string.theme),
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = stringResource(R.string.choose_your_preferred_theme),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        
        Spacer(modifier = Modifier.height(12.dp))
        
        themes.forEach { theme ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .clickable { onThemeChanged(theme.value) }
                    .padding(vertical = 8.dp, horizontal = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = currentTheme == theme.value,
                    onClick = { onThemeChanged(theme.value) }
                )
                
                Spacer(modifier = Modifier.width(12.dp))
                
                Icon(
                    imageVector = theme.icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(20.dp)
                )
                
                Spacer(modifier = Modifier.width(12.dp))
                
                Text(
                    text = theme.label,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
    
    HorizontalDivider(
        modifier = Modifier.padding(horizontal = 16.dp),
        thickness = 0.5.dp,
        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
    )
}

private data class ThemeOption(
    val value: String,
    val icon: ImageVector,
    val label: String
)

private fun openPrivacyPolicy(context: android.content.Context) {
    val privacyPolicyUrl = "https://mohammad-asif-zafar.github.io/Ramadan-Kareem-2026/privacy-policy.html"
    
    try {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(privacyPolicyUrl))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    } catch (e: Exception) {
        // Handle error - could show a toast or dialog
        e.printStackTrace()
    }
}
