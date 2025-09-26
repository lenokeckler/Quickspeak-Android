package com.quickspeak.mobile.ui.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.quickspeak.mobile.data.UserRepository
import com.quickspeak.mobile.domain.model.*
import com.quickspeak.mobile.ui.theme.*

/**
 * SETTINGS SCREEN
 * Comprehensive settings screen with account and app preferences
 * Features dual dark mode switches (system + manual override)
 * Based on the web version adapted for mobile
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBackClick: () -> Unit = {}
) {
    val isDarkTheme = isSystemInDarkTheme()
    val appSettings = UserRepository.appSettings
    val userProfile = UserRepository.userProfile

    // Calculate effective dark mode based on settings
    val effectiveDarkMode = UserRepository.getEffectiveDarkMode(isDarkTheme)

    // MAIN CONTAINER WITH GRADIENT BACKGROUND
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = if (effectiveDarkMode) {
                    Brush.verticalGradient(
                        colors = listOf(
                            BlackGeneral,
                            Color(0xFF2C006E)
                        )
                    )
                } else {
                    Brush.verticalGradient(
                        colors = listOf(
                            WhiteGeneral,
                            Color(0x7C7C01F6)
                        )
                    )
                }
            )
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                // TOP BAR
                SettingsTopBar(
                    onBackClick = onBackClick,
                    isDarkTheme = effectiveDarkMode
                )
            }

            item {
                // ACCOUNT SETTINGS SECTION
                AccountSettingsSection(
                    userProfile = userProfile,
                    isDarkTheme = effectiveDarkMode
                )
            }

            item {
                // APPEARANCE SETTINGS SECTION
                AppearanceSettingsSection(
                    appSettings = appSettings,
                    systemDarkMode = isDarkTheme,
                    isDarkTheme = effectiveDarkMode
                )
            }

            item {
                // NOTIFICATIONS SETTINGS SECTION
                NotificationSettingsSection(
                    appSettings = appSettings,
                    isDarkTheme = effectiveDarkMode
                )
            }

            item {
                // LEARNING SETTINGS SECTION
                LearningSettingsSection(
                    appSettings = appSettings,
                    isDarkTheme = effectiveDarkMode
                )
            }

            item {
                // ABOUT SECTION
                AboutSection(
                    isDarkTheme = effectiveDarkMode
                )
            }

            item {
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

/**
 * TOP BAR COMPONENT
 */
@Composable
private fun SettingsTopBar(
    onBackClick: () -> Unit,
    isDarkTheme: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 28.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBackClick) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = if (isDarkTheme) CyanDarkMode else CyanLightMode,
                modifier = Modifier.size(48.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = "Settings",
            color = if (isDarkTheme) CyanDarkMode else CyanLightMode,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

/**
 * ACCOUNT SETTINGS SECTION
 */
@Composable
private fun AccountSettingsSection(
    userProfile: UserProfile,
    isDarkTheme: Boolean
) {
    SettingsSection(
        title = "Account",
        isDarkTheme = isDarkTheme
    ) {
        SettingsItem(
            icon = Icons.Default.Person,
            title = "Edit Profile",
            subtitle = userProfile.name,
            isDarkTheme = isDarkTheme,
            onClick = { /* TODO: Navigate to edit profile */ }
        )

        SettingsItem(
            icon = Icons.Default.Email,
            title = "Email",
            subtitle = userProfile.email,
            isDarkTheme = isDarkTheme,
            onClick = { /* TODO: Edit email */ }
        )

        SettingsItem(
            icon = Icons.Default.Language,
            title = "Languages",
            subtitle = "${userProfile.learningLanguages.size} learning",
            isDarkTheme = isDarkTheme,
            onClick = { /* TODO: Navigate to languages */ }
        )

        SettingsItem(
            icon = Icons.Default.Security,
            title = "Privacy & Security",
            subtitle = "Manage your data",
            isDarkTheme = isDarkTheme,
            onClick = { /* TODO: Privacy settings */ }
        )
    }
}

/**
 * APPEARANCE SETTINGS SECTION
 * Includes dual dark mode switches
 */
@Composable
private fun AppearanceSettingsSection(
    appSettings: AppSettings,
    systemDarkMode: Boolean,
    isDarkTheme: Boolean
) {
    SettingsSection(
        title = "Appearance",
        isDarkTheme = isDarkTheme
    ) {
        // THEME MODE SETTING
        SettingsItemWithDropdown(
            icon = Icons.Default.Palette,
            title = "Theme Mode",
            subtitle = when (appSettings.themeMode) {
                ThemeMode.SYSTEM -> "Follow System"
                ThemeMode.MANUAL -> "Manual"
            },
            options = listOf("Follow System", "Manual"),
            selectedOption = when (appSettings.themeMode) {
                ThemeMode.SYSTEM -> "Follow System"
                ThemeMode.MANUAL -> "Manual"
            },
            onOptionSelected = { option ->
                val themeMode = when (option) {
                    "Follow System" -> ThemeMode.SYSTEM
                    else -> ThemeMode.MANUAL
                }
                UserRepository.updateThemeMode(themeMode)
            },
            isDarkTheme = isDarkTheme
        )

        // MANUAL DARK MODE SWITCH (only shown when theme mode is manual)
        if (appSettings.themeMode == ThemeMode.MANUAL) {
            SettingsItemWithSwitch(
                icon = Icons.Default.DarkMode,
                title = "Dark Mode",
                subtitle = "Manual dark mode override",
                isChecked = appSettings.manualDarkMode,
                onCheckedChange = { UserRepository.updateManualDarkMode(it) },
                isDarkTheme = isDarkTheme
            )
        }

        // SYSTEM DARK MODE INFO (read-only, shown when following system)
        if (appSettings.themeMode == ThemeMode.SYSTEM) {
            SettingsItem(
                icon = Icons.Default.PhoneAndroid,
                title = "System Dark Mode",
                subtitle = if (systemDarkMode) "Currently Dark" else "Currently Light",
                isDarkTheme = isDarkTheme,
                onClick = { /* Read-only */ },
                showArrow = false
            )
        }

        SettingsItemWithDropdown(
            icon = Icons.Default.FormatSize,
            title = "Font Size",
            subtitle = appSettings.fontSize.displayName,
            options = FontSize.values().map { it.displayName },
            selectedOption = appSettings.fontSize.displayName,
            onOptionSelected = { option ->
                val fontSize = FontSize.values().find { it.displayName == option } ?: FontSize.MEDIUM
                UserRepository.updateFontSize(fontSize)
            },
            isDarkTheme = isDarkTheme
        )
    }
}

/**
 * NOTIFICATIONS SETTINGS SECTION
 */
@Composable
private fun NotificationSettingsSection(
    appSettings: AppSettings,
    isDarkTheme: Boolean
) {
    SettingsSection(
        title = "Notifications",
        isDarkTheme = isDarkTheme
    ) {
        SettingsItemWithSwitch(
            icon = Icons.Default.Notifications,
            title = "Push Notifications",
            subtitle = "Receive app notifications",
            isChecked = appSettings.notificationsEnabled,
            onCheckedChange = { UserRepository.updateNotificationsEnabled(it) },
            isDarkTheme = isDarkTheme
        )

        SettingsItemWithSwitch(
            icon = Icons.Default.Schedule,
            title = "Daily Reminder",
            subtitle = "Remind me to practice at ${appSettings.dailyReminderTime}",
            isChecked = appSettings.dailyReminderEnabled,
            onCheckedChange = { UserRepository.updateDailyReminder(it) },
            isDarkTheme = isDarkTheme,
            enabled = appSettings.notificationsEnabled
        )

        SettingsItemWithSwitch(
            icon = Icons.Default.Vibration,
            title = "Haptic Feedback",
            subtitle = "Vibration for interactions",
            isChecked = appSettings.hapticFeedbackEnabled,
            onCheckedChange = { UserRepository.updateHapticFeedback(it) },
            isDarkTheme = isDarkTheme
        )
    }
}

/**
 * LEARNING SETTINGS SECTION
 */
@Composable
private fun LearningSettingsSection(
    appSettings: AppSettings,
    isDarkTheme: Boolean
) {
    SettingsSection(
        title = "Learning",
        isDarkTheme = isDarkTheme
    ) {
        SettingsItemWithSwitch(
            icon = Icons.Default.VolumeUp,
            title = "Sound Effects",
            subtitle = "Audio feedback for actions",
            isChecked = appSettings.soundEnabled,
            onCheckedChange = { UserRepository.updateSoundSettings(it) },
            isDarkTheme = isDarkTheme
        )

        SettingsItemWithSwitch(
            icon = Icons.Default.PlayArrow,
            title = "Autoplay Audio",
            subtitle = "Automatically play speaker messages",
            isChecked = appSettings.autoplayAudio,
            onCheckedChange = { UserRepository.updateSoundSettings(appSettings.soundEnabled, it) },
            isDarkTheme = isDarkTheme,
            enabled = appSettings.soundEnabled
        )

        SettingsItemWithSlider(
            icon = Icons.Default.Speed,
            title = "Speech Speed",
            subtitle = "${(appSettings.speechSpeed * 100).toInt()}%",
            value = appSettings.speechSpeed,
            onValueChange = { UserRepository.updateSpeechSpeed(it) },
            valueRange = 0.5f..2.0f,
            isDarkTheme = isDarkTheme
        )
    }
}

/**
 * ABOUT SECTION
 */
@Composable
private fun AboutSection(
    isDarkTheme: Boolean
) {
    SettingsSection(
        title = "About",
        isDarkTheme = isDarkTheme
    ) {
        SettingsItem(
            icon = Icons.Default.Info,
            title = "App Version",
            subtitle = "1.0.0 (Build 1)",
            isDarkTheme = isDarkTheme,
            onClick = { /* Show version info */ }
        )

        SettingsItem(
            icon = Icons.Default.Help,
            title = "Help & Support",
            subtitle = "Get help and contact us",
            isDarkTheme = isDarkTheme,
            onClick = { /* Open help */ }
        )

        SettingsItem(
            icon = Icons.Default.Description,
            title = "Terms & Privacy",
            subtitle = "Legal information",
            isDarkTheme = isDarkTheme,
            onClick = { /* Open legal */ }
        )

        SettingsItem(
            icon = Icons.Default.Logout,
            title = "Sign Out",
            subtitle = "Log out of your account",
            isDarkTheme = isDarkTheme,
            onClick = { /* Sign out */ },
            textColor = if (isDarkTheme) RedDarkMode else RedLightMode
        )
    }
}

/**
 * SETTINGS SECTION WRAPPER
 */
@Composable
private fun SettingsSection(
    title: String,
    isDarkTheme: Boolean,
    content: @Composable ColumnScope.() -> Unit
) {
    Column {
        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = if (isDarkTheme) Color.White else Color.Black,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            color = if (isDarkTheme) Color(0xFF232323) else Color.White,
            shadowElevation = 8.dp
        ) {
            Column(
                modifier = Modifier.padding(4.dp)
            ) {
                content()
            }
        }
    }
}

/**
 * BASIC SETTINGS ITEM
 */
@Composable
private fun SettingsItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    isDarkTheme: Boolean,
    onClick: () -> Unit,
    showArrow: Boolean = true,
    textColor: Color? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = textColor ?: if (isDarkTheme) Color.White else Color.Black,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = textColor ?: if (isDarkTheme) Color.White else Color.Black
            )
            Text(
                text = subtitle,
                fontSize = 14.sp,
                color = if (isDarkTheme) Color(0xFF9CA3AF) else Color(0xFF6B7280)
            )
        }

        if (showArrow) {
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = if (isDarkTheme) Color(0xFF9CA3AF) else Color(0xFF6B7280),
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

/**
 * SETTINGS ITEM WITH SWITCH
 */
@Composable
private fun SettingsItemWithSwitch(
    icon: ImageVector,
    title: String,
    subtitle: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    isDarkTheme: Boolean,
    enabled: Boolean = true
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = if (enabled) {
                if (isDarkTheme) Color.White else Color.Black
            } else {
                if (isDarkTheme) Color(0xFF6B7280) else Color(0xFF9CA3AF)
            },
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = if (enabled) {
                    if (isDarkTheme) Color.White else Color.Black
                } else {
                    if (isDarkTheme) Color(0xFF6B7280) else Color(0xFF9CA3AF)
                }
            )
            Text(
                text = subtitle,
                fontSize = 14.sp,
                color = if (isDarkTheme) Color(0xFF9CA3AF) else Color(0xFF6B7280)
            )
        }

        Switch(
            checked = isChecked,
            onCheckedChange = onCheckedChange,
            enabled = enabled,
            colors = SwitchDefaults.colors(
                checkedThumbColor = if (isDarkTheme) CyanDarkMode else CyanLightMode,
                checkedTrackColor = if (isDarkTheme) CyanDarkMode.copy(alpha = 0.5f) else CyanLightMode.copy(alpha = 0.5f)
            )
        )
    }
}

/**
 * SETTINGS ITEM WITH DROPDOWN
 */
@Composable
private fun SettingsItemWithDropdown(
    icon: ImageVector,
    title: String,
    subtitle: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    isDarkTheme: Boolean
) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true }
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (isDarkTheme) Color.White else Color.Black,
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = if (isDarkTheme) Color.White else Color.Black
                )
                Text(
                    text = subtitle,
                    fontSize = 14.sp,
                    color = if (isDarkTheme) Color(0xFF9CA3AF) else Color(0xFF6B7280)
                )
            }

            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = null,
                tint = if (isDarkTheme) Color(0xFF9CA3AF) else Color(0xFF6B7280),
                modifier = Modifier.size(20.dp)
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(
                if (isDarkTheme) Color(0xFF374151) else Color.White
            )
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = option,
                            color = if (isDarkTheme) Color.White else Color.Black
                        )
                    },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}

/**
 * SETTINGS ITEM WITH SLIDER
 */
@Composable
private fun SettingsItemWithSlider(
    icon: ImageVector,
    title: String,
    subtitle: String,
    value: Float,
    onValueChange: (Float) -> Unit,
    valueRange: ClosedFloatingPointRange<Float>,
    isDarkTheme: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (isDarkTheme) Color.White else Color.Black,
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = if (isDarkTheme) Color.White else Color.Black
                )
                Text(
                    text = subtitle,
                    fontSize = 14.sp,
                    color = if (isDarkTheme) Color(0xFF9CA3AF) else Color(0xFF6B7280)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = valueRange,
            colors = SliderDefaults.colors(
                thumbColor = if (isDarkTheme) CyanDarkMode else CyanLightMode,
                activeTrackColor = if (isDarkTheme) CyanDarkMode else CyanLightMode
            )
        )
    }
}

/**
 * PREVIEW FUNCTIONS
 */
@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    QuickSpeakTheme {
        SettingsScreen()
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF232323)
@Composable
fun SettingsScreenDarkPreview() {
    QuickSpeakTheme(darkTheme = true) {
        SettingsScreen()
    }
}