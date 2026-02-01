package com.hathway.ramadankareem2026.ui.settings

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
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SettingsSystemDaydream
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.hathway.ramadankareem2026.ui.components.RamadanToolbar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavController,
    onBack: () -> Unit = { navController.popBackStack() }
) {
    val context = LocalContext.current
    
    Scaffold(
        topBar = {
            RamadanToolbar(
                title = "Settings",
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
                SettingsSection(title = "🔔 Preferences") {
                    SettingsItem(
                        icon = Icons.Default.Notifications,
                        title = "Notification Settings",
                        subtitle = "Manage prayer reminders and notifications",
                        onClick = { /* Navigate to notification settings */ }
                    )
                    
                    SettingsItem(
                        icon = Icons.Default.VolumeUp,
                        title = "Prayer Reminders",
                        subtitle = "Configure Azan and prayer time alerts",
                        onClick = { /* Navigate to prayer reminders */ }
                    )
                    
                    SettingsItem(
                        icon = Icons.Default.VolumeUp,
                        title = "Azan Sound",
                        subtitle = "Choose Azan audio for each prayer",
                        onClick = { /* Navigate to Azan sound settings */ }
                    )
                }
            }
            
            // Appearance & Language Section
            item {
                SettingsSection(title = "🌍 Appearance & Language") {
                    SettingsItem(
                        icon = Icons.Default.Person,
                        title = "Language",
                        subtitle = "English",
                        onClick = { /* Navigate to language settings */ }
                    )
                    
                    var selectedTheme by remember { mutableStateOf("System") }
                    ThemeSelector(
                        currentTheme = selectedTheme,
                        onThemeChanged = { selectedTheme = it }
                    )
                }
            }
            
            //  About Section
            item {
                SettingsSection(title = "ℹ️ About") {
                    SettingsItem(
                        icon = Icons.Default.Settings,
                        title = "App Version",
                        subtitle = "Version 1.0.0",
                        onClick = { /* Show version info */ }
                    )
                    
                    SettingsItem(
                        icon = Icons.Default.Person,
                        title = "About App",
                        subtitle = "Learn more about Ramadan Kareem 2026",
                        onClick = { /* Navigate to about */ }
                    )
                    
                    SettingsItem(
                        icon = Icons.Default.Settings,
                        title = "Privacy Policy",
                        subtitle = "Read our privacy policy",
                        onClick = { /* Navigate to privacy policy */ }
                    )
                    
                    SettingsItem(
                        icon = Icons.Default.Settings,
                        title = "Terms of Use",
                        subtitle = "Read our terms and conditions",
                        onClick = { /* Navigate to terms */ }
                    )
                }
            }
            
            // Support Section
            item {
                SettingsSection(title = "💬 Support") {
                    SettingsItem(
                        icon = Icons.Default.Person,
                        title = "Feedback",
                        subtitle = "Share your feedback with us",
                        onClick = { /* Navigate to feedback */ }
                    )
                    
                    SettingsItem(
                        icon = Icons.Default.Settings,
                        title = "Rate App",
                        subtitle = "Rate us on Google Play Store",
                        onClick = { /* Navigate to rate app */ }
                    )
                    
                    SettingsItem(
                        icon = Icons.Default.Person,
                        title = "Contact Support",
                        subtitle = "Get help from our support team",
                        onClick = { /* Navigate to support */ }
                    )
                }
            }
            
            // Credits Section
            item {
                SettingsSection(title = "🙏 Credits") {
                    SettingsItem(
                        icon = Icons.Default.Person,
                        title = "Developer",
                        subtitle = "Mohammad Asif Zafar",
                        onClick = { /* Navigate to developer info */ }
                    )
                    
                    SettingsItem(
                        icon = Icons.Default.Settings,
                        title = "Design & UI/UX",
                        subtitle = "Ramadan Kareem Team",
                        onClick = { /* Navigate to design credits */ }
                    )
                    
                    SettingsItem(
                        icon = Icons.Default.Person,
                        title = "Special Thanks",
                        subtitle = "Islamic scholars and community contributors",
                        onClick = { /* Navigate to special thanks */ }
                    )
                    
                    SettingsItem(
                        icon = Icons.Default.Settings,
                        title = "Data Sources",
                        subtitle = "Prayer times from Aladhan.com API",
                        onClick = { /* Navigate to data sources */ }
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
        thickness = 0.5.dp
    )
}

@Composable
private fun ThemeSelector(
    currentTheme: String,
    onThemeChanged: (String) -> Unit
) {
    val themes = listOf("Light", "Dark", "System")
    
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
                    text = "Theme",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "Choose your preferred theme",
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
                    .clickable { onThemeChanged(theme) }
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = currentTheme == theme,
                    onClick = { onThemeChanged(theme) }
                )
                
                Spacer(modifier = Modifier.width(12.dp))
                
                Text(
                    text = theme,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
    
    HorizontalDivider(
        modifier = Modifier.padding(horizontal = 16.dp),
        thickness = 0.5.dp
    )
}
