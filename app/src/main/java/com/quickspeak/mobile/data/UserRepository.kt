package com.quickspeak.mobile.data

import androidx.compose.runtime.mutableStateOf
import com.quickspeak.mobile.domain.model.*

/**
 * USER REPOSITORY
 * Manages user profile, settings, and achievements data
 * Single source of truth for user-related information
 */
object UserRepository {

    // USER PROFILE STATE
    private var _userProfile = mutableStateOf(createMockUserProfile())
    val userProfile get() = _userProfile.value

    // APP SETTINGS STATE
    private var _appSettings = mutableStateOf(AppSettings())
    val appSettings get() = _appSettings.value

    // LEARNING STATS STATE
    private var _learningStats = mutableStateOf(createMockLearningStats())
    val learningStats get() = _learningStats.value

    // ACHIEVEMENTS STATE
    private var _achievements = mutableStateOf(createMockAchievements())
    val achievements get() = _achievements.value

    /**
     * UPDATE USER PROFILE
     */
    fun updateUserProfile(profile: UserProfile) {
        _userProfile.value = profile
    }

    fun updateUserName(name: String) {
        _userProfile.value = _userProfile.value.copy(name = name)
    }

    fun updateUserEmail(email: String) {
        _userProfile.value = _userProfile.value.copy(email = email)
    }

    fun updateAvatarSeed(seed: String) {
        _userProfile.value = _userProfile.value.copy(avatarSeed = seed)
    }

    /**
     * UPDATE APP SETTINGS
     */
    fun updateAppSettings(settings: AppSettings) {
        _appSettings.value = settings
    }

    fun updateThemeMode(themeMode: ThemeMode) {
        _appSettings.value = _appSettings.value.copy(themeMode = themeMode)
    }

    fun updateManualDarkMode(isDark: Boolean) {
        _appSettings.value = _appSettings.value.copy(manualDarkMode = isDark)
    }

    fun updateNotificationsEnabled(enabled: Boolean) {
        _appSettings.value = _appSettings.value.copy(notificationsEnabled = enabled)
    }

    fun updateDailyReminder(enabled: Boolean, time: String? = null) {
        _appSettings.value = _appSettings.value.copy(
            dailyReminderEnabled = enabled,
            dailyReminderTime = time ?: _appSettings.value.dailyReminderTime
        )
    }

    fun updateSoundSettings(soundEnabled: Boolean, autoplay: Boolean? = null) {
        _appSettings.value = _appSettings.value.copy(
            soundEnabled = soundEnabled,
            autoplayAudio = autoplay ?: _appSettings.value.autoplayAudio
        )
    }

    fun updateSpeechSpeed(speed: Float) {
        _appSettings.value = _appSettings.value.copy(speechSpeed = speed)
    }

    fun updateFontSize(fontSize: FontSize) {
        _appSettings.value = _appSettings.value.copy(fontSize = fontSize)
    }

    fun updateHapticFeedback(enabled: Boolean) {
        _appSettings.value = _appSettings.value.copy(hapticFeedbackEnabled = enabled)
    }

    /**
     * UPDATE LEARNING STATS
     */
    fun updateLearningStats(stats: LearningStats) {
        _learningStats.value = stats
    }

    fun incrementWordsLearned(count: Int = 1) {
        _learningStats.value = _learningStats.value.copy(
            totalWordsLearned = _learningStats.value.totalWordsLearned + count
        )
    }

    fun updateStreak(currentStreak: Int) {
        val longestStreak = maxOf(_learningStats.value.longestStreak, currentStreak)
        _learningStats.value = _learningStats.value.copy(
            currentStreak = currentStreak,
            longestStreak = longestStreak
        )
    }

    fun incrementConversations() {
        _learningStats.value = _learningStats.value.copy(
            totalConversations = _learningStats.value.totalConversations + 1
        )
    }

    fun addLearningTime(minutes: Int) {
        _learningStats.value = _learningStats.value.copy(
            totalMinutesLearned = _learningStats.value.totalMinutesLearned + minutes,
            weeklyProgress = _learningStats.value.weeklyProgress + minutes
        )
    }

    /**
     * ACHIEVEMENTS
     */
    fun unlockAchievement(achievementId: String) {
        _achievements.value = _achievements.value.map { achievement ->
            if (achievement.id == achievementId && !achievement.isUnlocked) {
                achievement.copy(
                    isUnlocked = true,
                    unlockedDate = System.currentTimeMillis()
                )
            } else {
                achievement
            }
        }
    }

    fun getUnlockedAchievements(): List<Achievement> {
        return _achievements.value.filter { it.isUnlocked }
    }

    /**
     * COMPUTED PROPERTIES
     */
    fun getEffectiveDarkMode(systemDarkMode: Boolean): Boolean {
        return when (_appSettings.value.themeMode) {
            ThemeMode.SYSTEM -> systemDarkMode
            ThemeMode.MANUAL -> _appSettings.value.manualDarkMode
        }
    }

    fun getLearningLanguagesCount(): Int {
        return _userProfile.value.learningLanguages.size
    }

    fun getTotalSavedSpeakers(): Int {
        return ChatRepository.savedSpeakers.size
    }

    /**
     * MOCK DATA CREATION
     */
    private fun createMockUserProfile(): UserProfile {
        return UserProfile(
            id = "user_001",
            name = "Alex Johnson",
            email = "alex.johnson@example.com",
            avatarSeed = "alexjohnson",
            nativeLanguage = "English",
            learningLanguages = LanguageRepository.learningLanguages
                .filter { !it.isNative }
                .map { it.name },
            joinedDate = System.currentTimeMillis() - (30L * 24 * 60 * 60 * 1000), // 30 days ago
            totalWordsLearned = 247,
            currentStreak = 7,
            totalConversations = 34,
            preferredDifficulty = "Intermediate"
        )
    }

    private fun createMockLearningStats(): LearningStats {
        return LearningStats(
            totalWordsLearned = 247,
            currentStreak = 7,
            longestStreak = 12,
            totalConversations = 34,
            totalMinutesLearned = 420,
            weeklyGoal = 120,
            weeklyProgress = 87
        )
    }

    private fun createMockAchievements(): List<Achievement> {
        return listOf(
            Achievement(
                id = "first_chat",
                title = "First Chat",
                description = "Complete your first conversation",
                iconEmoji = "💬",
                isUnlocked = true,
                unlockedDate = System.currentTimeMillis() - (25L * 24 * 60 * 60 * 1000)
            ),
            Achievement(
                id = "week_streak",
                title = "Week Warrior",
                description = "Maintain a 7-day learning streak",
                iconEmoji = "🔥",
                isUnlocked = true,
                unlockedDate = System.currentTimeMillis() - (2L * 24 * 60 * 60 * 1000)
            ),
            Achievement(
                id = "hundred_words",
                title = "Word Master",
                description = "Learn 100 new words",
                iconEmoji = "📚",
                isUnlocked = true,
                unlockedDate = System.currentTimeMillis() - (10L * 24 * 60 * 60 * 1000)
            ),
            Achievement(
                id = "polyglot",
                title = "Polyglot",
                description = "Chat with speakers from 3 different languages",
                iconEmoji = "🌍",
                isUnlocked = true,
                unlockedDate = System.currentTimeMillis() - (15L * 24 * 60 * 60 * 1000)
            ),
            Achievement(
                id = "early_bird",
                title = "Early Bird",
                description = "Complete morning lessons for 5 days",
                iconEmoji = "🌅",
                isUnlocked = false
            ),
            Achievement(
                id = "social_butterfly",
                title = "Social Butterfly",
                description = "Save 10 different speakers",
                iconEmoji = "🦋",
                isUnlocked = false
            ),
            Achievement(
                id = "month_streak",
                title = "Monthly Master",
                description = "Maintain a 30-day learning streak",
                iconEmoji = "🏆",
                isUnlocked = false
            ),
            Achievement(
                id = "thousand_words",
                title = "Vocabulary Virtuoso",
                description = "Learn 1000 new words",
                iconEmoji = "🎓",
                isUnlocked = false
            )
        )
    }
}