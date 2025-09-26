package com.quickspeak.mobile.domain.model

/**
 * CHAT DATA MODELS
 * Structures for managing chat functionality, messages, and saved speakers
 * Based on the web version structure but adapted for Android/Kotlin
 */

/**
 * Represents a single message in a chat
 */
data class Message(
    val id: Int,
    val senderId: String, // "user" or speaker's ID
    val content: String,
    val timestamp: Long = System.currentTimeMillis(),
    val isFromUser: Boolean = senderId == "user"
)

/**
 * Represents a chat conversation with a speaker
 */
data class Chat(
    val id: String,
    val speakerId: Int,
    val speakerName: String,
    val messages: List<Message> = emptyList(),
    val lastMessageTime: Long = System.currentTimeMillis(),
    val hasUnreadMessages: Boolean = false
) {
    val lastMessage: Message?
        get() = messages.lastOrNull()
}

/**
 * Represents a saved speaker (simplified from full Speaker model)
 */
data class SavedSpeaker(
    val speakerId: Int,
    val name: String,
    val language: String,
    val flagEmoji: String,
    val avatarSeed: String
) {
    companion object {
        fun fromSpeaker(speaker: Speaker): SavedSpeaker {
            return SavedSpeaker(
                speakerId = speaker.id,
                name = speaker.name,
                language = speaker.language,
                flagEmoji = speaker.flagEmoji,
                avatarSeed = speaker.avatarSeed
            )
        }
    }
}

/**
 * Chat theme data for speaker-specific colors
 */
data class ChatTheme(
    val speakerId: Int,
    val headerBackground: androidx.compose.ui.graphics.Color,
    val inputBackground: androidx.compose.ui.graphics.Color,
    val speakerBubbleBackground: androidx.compose.ui.graphics.Color,
    val userBubbleBackground: androidx.compose.ui.graphics.Color,
    val textColor: androidx.compose.ui.graphics.Color,
    val gradientStart: androidx.compose.ui.graphics.Color,
    val gradientEnd: androidx.compose.ui.graphics.Color
) {
    companion object {
        fun fromSpeaker(speaker: Speaker, customColor: androidx.compose.ui.graphics.Color? = null): ChatTheme {
            val themeColor = customColor ?: speaker.colorClasses.background
            return ChatTheme(
                speakerId = speaker.id,
                headerBackground = themeColor,
                inputBackground = themeColor,
                speakerBubbleBackground = themeColor,
                userBubbleBackground = androidx.compose.ui.graphics.Color(0xFF0EA5E9), // Sky blue for user
                textColor = if (customColor != null) androidx.compose.ui.graphics.Color.White else speaker.colorClasses.textColor,
                gradientStart = androidx.compose.ui.graphics.Color(0xFF232323), // Dark theme gradient
                gradientEnd = themeColor.copy(alpha = 0.3f)
            )
        }
    }
}