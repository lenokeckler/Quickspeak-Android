package com.quickspeak.mobile.domain.model

import androidx.compose.ui.graphics.Color

/**
 * SPEAKER DATA MODEL
 * Represents a language speaker with their details, personality traits, and interests
 * Based on the web version structure but adapted for Android/Kotlin
 */
data class Speaker(
    val id: Int,
    val name: String,
    val language: String,
    val flagEmoji: String,
    val avatarSeed: String,
    val personality: List<String>,
    val interests: List<String>,
    val colorClasses: SpeakerColors
)

/**
 * SPEAKER COLORS DATA CLASS
 * Color scheme for each speaker following the web version design
 */
data class SpeakerColors(
    val background: Color,
    val cardBackground: Color,
    val textColor: Color,
    val borderColor: Color
)

/**
 * SAMPLE SPEAKER DATA
 * Direct conversion from the web version with Android Color objects
 */
object SpeakerData {
    fun getAllSpeakers(): List<Speaker> {
        return listOf(
            Speaker(
                id = 1,
                name = "Burkhart",
                language = "German",
                flagEmoji = "🇩🇪",
                avatarSeed = "Burkhart",
                personality = listOf("😐 Neutral", "😇 Polite"),
                interests = listOf("🎸 Metal Music", "🎭 Theater", "🏋️‍♂️ Weightlifting"),
                colorClasses = SpeakerColors(
                    background = Color(0xFF06D6A0),
                    cardBackground = Color(0xFF50F9C2),
                    textColor = Color(0xFF374151),
                    borderColor = Color(0xFF06D6A0)
                )
            ),
            Speaker(
                id = 2,
                name = "Leonie",
                language = "French",
                flagEmoji = "🇫🇷",
                avatarSeed = "Leonie",
                personality = listOf("🤝 Friendly", "🤪 Goofy"),
                interests = listOf("🗻 Hiking", "🎵 Pop Music", "🐘 Elephants"),
                colorClasses = SpeakerColors(
                    background = Color(0xFFEF476F),
                    cardBackground = Color(0xFFFE6788),
                    textColor = Color.White,
                    borderColor = Color(0xFFEF476F)
                )
            ),
            Speaker(
                id = 3,
                name = "Marta",
                language = "Spanish",
                flagEmoji = "🇪🇸",
                avatarSeed = "Marta",
                personality = listOf("🧐 Curious", "☺ Kind"),
                interests = listOf("🍳 Gourmet Food", "🎞 Old Cinema", "🎓 Education"),
                colorClasses = SpeakerColors(
                    background = Color(0xFFFFD166),
                    cardBackground = Color(0xFFFFE08A),
                    textColor = Color(0xFF374151),
                    borderColor = Color(0xFFFFD166)
                )
            ),
            Speaker(
                id = 4,
                name = "Aarav",
                language = "Hindi",
                flagEmoji = "🇮🇳",
                avatarSeed = "Aarav",
                personality = listOf("🧘‍♂️ Calm", "💡 Insightful"),
                interests = listOf("🏏 Cricket", "🌶️ Spicy Food", "🎬 Bollywood"),
                colorClasses = SpeakerColors(
                    background = Color(0xFFF97316),
                    cardBackground = Color(0xFFFF9933),
                    textColor = Color.White,
                    borderColor = Color(0xFFF97316)
                )
            ),
            Speaker(
                id = 5,
                name = "Marco",
                language = "Italian",
                flagEmoji = "🇮🇹",
                avatarSeed = "Marco",
                personality = listOf("😎 Confident", "🍝 Passionate"),
                interests = listOf("⚽ Soccer", "🏛️ History", "🍷 Fine Wine"),
                colorClasses = SpeakerColors(
                    background = Color(0xFF3B82F6),
                    cardBackground = Color(0xFF60A5FA),
                    textColor = Color.White,
                    borderColor = Color(0xFF3B82F6)
                )
            ),
            Speaker(
                id = 6,
                name = "Sofia",
                language = "Portuguese",
                flagEmoji = "🇧🇷",
                avatarSeed = "Sofia",
                personality = listOf("🎉 Energetic", "🎨 Creative"),
                interests = listOf("💃 Samba", "🏖️ Beaches", "📸 Photography"),
                colorClasses = SpeakerColors(
                    background = Color(0xFF10B981),
                    cardBackground = Color(0xFF34D399),
                    textColor = Color.White,
                    borderColor = Color(0xFF10B981)
                )
            ),
            Speaker(
                id = 7,
                name = "Kenji",
                language = "Japanese",
                flagEmoji = "🇯🇵",
                avatarSeed = "Kenji",
                personality = listOf("🙏 Respectful", "💻 Tech-savvy"),
                interests = listOf("🍣 Sushi", "🌸 Anime", "🕹️ Video Games"),
                colorClasses = SpeakerColors(
                    background = Color(0xFFF87171),
                    cardBackground = Color(0xFFFCA5A5),
                    textColor = Color(0xFF374151),
                    borderColor = Color(0xFFF87171)
                )
            ),
            Speaker(
                id = 8,
                name = "Fatima",
                language = "Arabic",
                flagEmoji = "🇦🇪",
                avatarSeed = "Fatima",
                personality = listOf("👑 Gracious", "🏠 Hospitable"),
                interests = listOf("☕ Coffee", "🖋️ Poetry", "🏜️ Desert Camping"),
                colorClasses = SpeakerColors(
                    background = Color(0xFF8B5CF6),
                    cardBackground = Color(0xFFA78BFA),
                    textColor = Color.White,
                    borderColor = Color(0xFF8B5CF6)
                )
            ),
            Speaker(
                id = 9,
                name = "Dmitri",
                language = "Russian",
                flagEmoji = "🇷🇺",
                avatarSeed = "Dmitri",
                personality = listOf("💪 Stoic", "🤔 Philosophical"),
                interests = listOf("📚 Literature", "♟️ Chess", "❄️ Winter Sports"),
                colorClasses = SpeakerColors(
                    background = Color(0xFF0284C7),
                    cardBackground = Color(0xFF0EA5E9),
                    textColor = Color.White,
                    borderColor = Color(0xFF0284C7)
                )
            ),
            Speaker(
                id = 10,
                name = "Chloe",
                language = "English",
                flagEmoji = "🇬🇧",
                avatarSeed = "Chloe",
                personality = listOf("🔥 Witty", "😏 Sarcastic"),
                interests = listOf("🎸 Indie Rock", "🌧️ Rainy Days", "☕ Tea"),
                colorClasses = SpeakerColors(
                    background = Color(0xFF6366F1),
                    cardBackground = Color(0xFF818CF8),
                    textColor = Color.White,
                    borderColor = Color(0xFF6366F1)
                )
            ),
            Speaker(
                id = 11,
                name = "Liam",
                language = "Irish",
                flagEmoji = "🇮🇪",
                avatarSeed = "Liam",
                personality = listOf("😂 Humorous", "📖 Storytelling"),
                interests = listOf("🎻 Folk Music", "🍻 Pubs", "🍀 Mythology"),
                colorClasses = SpeakerColors(
                    background = Color(0xFF059669),
                    cardBackground = Color(0xFF34D399),
                    textColor = Color.White,
                    borderColor = Color(0xFF059669)
                )
            ),
            Speaker(
                id = 12,
                name = "Hao",
                language = "Chinese",
                flagEmoji = "🇨🇳",
                avatarSeed = "Hao",
                personality = listOf("⚡ Diligent", "🎯 Ambitious"),
                interests = listOf("🏓 Table Tennis", "🍵 Tea Ceremony", "📈 Business"),
                colorClasses = SpeakerColors(
                    background = Color(0xFFE11D48),
                    cardBackground = Color(0xFFF43F5E),
                    textColor = Color.White,
                    borderColor = Color(0xFFE11D48)
                )
            ),
            Speaker(
                id = 13,
                name = "Astrid",
                language = "Swedish",
                flagEmoji = "🇸🇪",
                avatarSeed = "Astrid",
                personality = listOf("❄️ Cool-headed", "🌿 Nature-loving"),
                interests = listOf("🛶 Canoeing", "🔮 Folklore", "☕ Fika"),
                colorClasses = SpeakerColors(
                    background = Color(0xFF06B6D4),
                    cardBackground = Color(0xFF22D3EE),
                    textColor = Color.White,
                    borderColor = Color(0xFF06B6D4)
                )
            ),
            Speaker(
                id = 14,
                name = "Ji-hoon",
                language = "Korean",
                flagEmoji = "🇰🇷",
                avatarSeed = "Ji-hoon",
                personality = listOf("🥋 Disciplined", "🎪 Innovative"),
                interests = listOf("🥋 Taekwondo", "🎵 K-pop", "🍜 Street Food"),
                colorClasses = SpeakerColors(
                    background = Color(0xFF7C3AED),
                    cardBackground = Color(0xFFA78BEA),
                    textColor = Color.White,
                    borderColor = Color(0xFF7C3AED)
                )
            ),
            Speaker(
                id = 15,
                name = "Aisha",
                language = "Swahili",
                flagEmoji = "🇰🇪",
                avatarSeed = "Aisha",
                personality = listOf("🌞 Warm-hearted", "🕊️ Peaceful"),
                interests = listOf("🦒 Safari", "🎶 Afrobeat", "🌴 Palm Trees"),
                colorClasses = SpeakerColors(
                    background = Color(0xFF84CC16),
                    cardBackground = Color(0xFFBEF264),
                    textColor = Color(0xFF374151),
                    borderColor = Color(0xFF84CC16)
                )
            )
        )
    }

    /**
     * Get speakers filtered by language
     */
    fun getSpeakersByLanguage(language: String): List<Speaker> {
        return getAllSpeakers().filter { it.language == language }
    }

    /**
     * Get all available languages
     */
    fun getAvailableLanguages(): List<String> {
        return getAllSpeakers().map { it.language }.distinct().sorted()
    }
}