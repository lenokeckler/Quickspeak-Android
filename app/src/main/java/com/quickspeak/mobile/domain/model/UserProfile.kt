package com.quickspeak.mobile.domain.model

/**
 * USER PROFILE DATA MODEL
 * Represents user profile information and preferences
 * Based on the web version adapted for Android/Kotlin
 */
data class UserProfile(
    val id: String = "user_001",
    val name: String = "John Doe",
    val email: String = "john.doe@example.com",
    val avatarSeed: String = "johndoe",
    val nativeLanguage: String = "English",
    val learningLanguages: List<String> = listOf("German", "French"),
    val joinedDate: Long = System.currentTimeMillis(),
    val totalWordsLearned: Int = 127,
    val currentStreak: Int = 5,
    val totalConversations: Int = 23,
    val preferredDifficulty: String = "Intermediate" // Beginner, Intermediate, Advanced
)

/**
 * APP SETTINGS DATA MODEL
 * Represents user app settings and preferences
 */
data class AppSettings(
    val themeMode: ThemeMode = ThemeMode.SYSTEM,
    val manualDarkMode: Boolean = false, // Only used when themeMode is MANUAL
    val notificationsEnabled: Boolean = true,
    val dailyReminderEnabled: Boolean = true,
    val dailyReminderTime: String = "18:00", // 24h format
    val soundEnabled: Boolean = true,
    val autoplayAudio: Boolean = true,
    val speechSpeed: Float = 1.0f, // 0.5 to 2.0
    val fontSize: FontSize = FontSize.MEDIUM,
    val hapticFeedbackEnabled: Boolean = true
)

/**
 * THEME MODE OPTIONS
 * Dual theme system: System + Manual override
 */
enum class ThemeMode {
    SYSTEM,  // Follow system dark mode setting
    MANUAL   // Use manual dark mode setting
}

/**
 * FONT SIZE OPTIONS
 */
enum class FontSize(val displayName: String, val scale: Float) {
    SMALL("Small", 0.9f),
    MEDIUM("Medium", 1.0f),
    LARGE("Large", 1.1f),
    EXTRA_LARGE("Extra Large", 1.2f)
}

/**
 * NOTIFICATION SETTINGS
 */
data class NotificationSettings(
    val dailyReminder: Boolean = true,
    val weeklyProgress: Boolean = true,
    val newFeatures: Boolean = true,
    val streakReminder: Boolean = true
)

/**
 * LEARNING STATS
 */
data class LearningStats(
    val totalWordsLearned: Int = 0,
    val currentStreak: Int = 0,
    val longestStreak: Int = 0,
    val totalConversations: Int = 0,
    val totalMinutesLearned: Int = 0,
    val weeklyGoal: Int = 30, // minutes per week
    val weeklyProgress: Int = 0 // minutes this week
)

/**
 * ACHIEVEMENT DATA
 */
data class Achievement(
    val id: String,
    val title: String,
    val description: String,
    val iconEmoji: String,
    val isUnlocked: Boolean = false,
    val unlockedDate: Long? = null
)