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
            ),
            // NEW SPEAKERS FOR CATALOG (not saved by default)
            Speaker(
                id = 16,
                name = "Klaus",
                language = "German",
                flagEmoji = "🇩🇪",
                avatarSeed = "Klaus",
                personality = listOf("🎯 Precise", "🧠 Analytical"),
                interests = listOf("⚽ Football", "🍺 Beer Gardens", "🏰 Castles"),
                colorClasses = SpeakerColors(
                    background = Color(0xFF10B981),
                    cardBackground = Color(0xFF34D399),
                    textColor = Color.White,
                    borderColor = Color(0xFF10B981)
                )
            ),
            Speaker(
                id = 17,
                name = "Amélie",
                language = "French",
                flagEmoji = "🇫🇷",
                avatarSeed = "Amelie",
                personality = listOf("🎨 Artistic", "🍷 Sophisticated"),
                interests = listOf("🥐 Pastries", "🎭 Theater", "🗼 Architecture"),
                colorClasses = SpeakerColors(
                    background = Color(0xFF8B5CF6),
                    cardBackground = Color(0xFFA78BFA),
                    textColor = Color.White,
                    borderColor = Color(0xFF8B5CF6)
                )
            ),
            Speaker(
                id = 18,
                name = "Carmen",
                language = "Spanish",
                flagEmoji = "🇪🇸",
                avatarSeed = "Carmen",
                personality = listOf("💃 Passionate", "🎉 Lively"),
                interests = listOf("💃 Flamenco", "🎸 Guitar", "🌶️ Spicy Food"),
                colorClasses = SpeakerColors(
                    background = Color(0xFFEF4444),
                    cardBackground = Color(0xFFF87171),
                    textColor = Color.White,
                    borderColor = Color(0xFFEF4444)
                )
            ),
            Speaker(
                id = 19,
                name = "Giulia",
                language = "Italian",
                flagEmoji = "🇮🇹",
                avatarSeed = "Giulia",
                personality = listOf("😊 Cheerful", "👨‍👩‍👧‍👦 Family-oriented"),
                interests = listOf("🍝 Pasta", "🎨 Renaissance Art", "🏛️ Ancient Rome"),
                colorClasses = SpeakerColors(
                    background = Color(0xFF06B6D4),
                    cardBackground = Color(0xFF22D3EE),
                    textColor = Color.White,
                    borderColor = Color(0xFF06B6D4)
                )
            ),
            Speaker(
                id = 20,
                name = "Beatriz",
                language = "Portuguese",
                flagEmoji = "🇧🇷",
                avatarSeed = "Beatriz",
                personality = listOf("🎵 Musical", "☀️ Optimistic"),
                interests = listOf("🏖️ Carnival", "⚽ Soccer", "🥭 Tropical Fruits"),
                colorClasses = SpeakerColors(
                    background = Color(0xFFF59E0B),
                    cardBackground = Color(0xFFFBBF24),
                    textColor = Color(0xFF374151),
                    borderColor = Color(0xFFF59E0B)
                )
            ),
            Speaker(
                id = 21,
                name = "Wei",
                language = "Chinese",
                flagEmoji = "🇨🇳",
                avatarSeed = "Wei",
                personality = listOf("🧘 Wise", "📚 Scholarly"),
                interests = listOf("🍵 Tea", "🏯 Temples", "🐉 Dragons"),
                colorClasses = SpeakerColors(
                    background = Color(0xFF7C3AED),
                    cardBackground = Color(0xFF8B5CF6),
                    textColor = Color.White,
                    borderColor = Color(0xFF7C3AED)
                )
            ),
            Speaker(
                id = 22,
                name = "Yuki",
                language = "Japanese",
                flagEmoji = "🇯🇵",
                avatarSeed = "Yuki",
                personality = listOf("🌸 Gentle", "🎯 Focused"),
                interests = listOf("🍱 Bento", "🌸 Cherry Blossoms", "🎎 Traditions"),
                colorClasses = SpeakerColors(
                    background = Color(0xFFEC4899),
                    cardBackground = Color(0xFFF472B6),
                    textColor = Color.White,
                    borderColor = Color(0xFFEC4899)
                )
            ),
            Speaker(
                id = 23,
                name = "Omar",
                language = "Arabic",
                flagEmoji = "🇦🇪",
                avatarSeed = "Omar",
                personality = listOf("🤝 Generous", "🌟 Charismatic"),
                interests = listOf("🏜️ Desert", "🐪 Camels", "📖 Calligraphy"),
                colorClasses = SpeakerColors(
                    background = Color(0xFF059669),
                    cardBackground = Color(0xFF10B981),
                    textColor = Color.White,
                    borderColor = Color(0xFF059669)
                )
            ),
            Speaker(
                id = 24,
                name = "Priya",
                language = "Hindi",
                flagEmoji = "🇮🇳",
                avatarSeed = "Priya",
                personality = listOf("🙏 Spiritual", "💫 Vibrant"),
                interests = listOf("🕉️ Yoga", "🎨 Henna", "🍛 Curry"),
                colorClasses = SpeakerColors(
                    background = Color(0xFFDC2626),
                    cardBackground = Color(0xFFEF4444),
                    textColor = Color.White,
                    borderColor = Color(0xFFDC2626)
                )
            ),
            Speaker(
                id = 25,
                name = "Alexei",
                language = "Russian",
                flagEmoji = "🇷🇺",
                avatarSeed = "Alexei",
                personality = listOf("❄️ Resilient", "📖 Intellectual"),
                interests = listOf("🎭 Ballet", "🏰 Kremlin", "🍲 Borscht"),
                colorClasses = SpeakerColors(
                    background = Color(0xFF1D4ED8),
                    cardBackground = Color(0xFF3B82F6),
                    textColor = Color.White,
                    borderColor = Color(0xFF1D4ED8)
                )
            ),
            Speaker(
                id = 26,
                name = "Emma",
                language = "English",
                flagEmoji = "🇬🇧",
                avatarSeed = "Emma",
                personality = listOf("☕ Proper", "🎩 Witty"),
                interests = listOf("🫖 Afternoon Tea", "👑 Royal Family", "🌧️ Weather"),
                colorClasses = SpeakerColors(
                    background = Color(0xFF7C2D12),
                    cardBackground = Color(0xFF9A3412),
                    textColor = Color.White,
                    borderColor = Color(0xFF7C2D12)
                )
            ),
            Speaker(
                id = 27,
                name = "Siobhan",
                language = "Irish",
                flagEmoji = "🇮🇪",
                avatarSeed = "Siobhan",
                personality = listOf("🍀 Lucky", "🎶 Musical"),
                interests = listOf("🎻 Fiddle", "🏰 Castles", "🍻 Craic"),
                colorClasses = SpeakerColors(
                    background = Color(0xFF16A34A),
                    cardBackground = Color(0xFF22C55E),
                    textColor = Color.White,
                    borderColor = Color(0xFF16A34A)
                )
            ),
            Speaker(
                id = 28,
                name = "Min-jun",
                language = "Korean",
                flagEmoji = "🇰🇷",
                avatarSeed = "Minjun",
                personality = listOf("🎯 Focused", "💪 Hardworking"),
                interests = listOf("🥋 Taekwondo", "🎮 Gaming", "🍜 K-BBQ"),
                colorClasses = SpeakerColors(
                    background = Color(0xFF0891B2),
                    cardBackground = Color(0xFF0EA5E9),
                    textColor = Color.White,
                    borderColor = Color(0xFF0891B2)
                )
            ),
            Speaker(
                id = 29,
                name = "Lars",
                language = "Dutch",
                flagEmoji = "🇳🇱",
                avatarSeed = "Lars",
                personality = listOf("🚴 Practical", "🌷 Down-to-earth"),
                interests = listOf("🚲 Cycling", "🧀 Cheese", "🌷 Tulips"),
                colorClasses = SpeakerColors(
                    background = Color(0xFFEA580C),
                    cardBackground = Color(0xFFF97316),
                    textColor = Color.White,
                    borderColor = Color(0xFFEA580C)
                )
            ),
            Speaker(
                id = 30,
                name = "Anna",
                language = "Polish",
                flagEmoji = "🇵🇱",
                avatarSeed = "Anna",
                personality = listOf("💪 Strong", "🏠 Family-oriented"),
                interests = listOf("🥟 Pierogi", "🏰 Castles", "🎼 Chopin"),
                colorClasses = SpeakerColors(
                    background = Color(0xFFBE185D),
                    cardBackground = Color(0xFFEC4899),
                    textColor = Color.White,
                    borderColor = Color(0xFFBE185D)
                )
            ),
            Speaker(
                id = 31,
                name = "Emre",
                language = "Turkish",
                flagEmoji = "🇹🇷",
                avatarSeed = "Emre",
                personality = listOf("🤝 Hospitable", "☕ Energetic"),
                interests = listOf("☕ Turkish Coffee", "🕌 History", "🍖 Kebabs"),
                colorClasses = SpeakerColors(
                    background = Color(0xFF7C2D12),
                    cardBackground = Color(0xFF9A3412),
                    textColor = Color.White,
                    borderColor = Color(0xFF7C2D12)
                )
            ),
            Speaker(
                id = 32,
                name = "Astrid",
                language = "Swedish",
                flagEmoji = "🇸🇪",
                avatarSeed = "Astrid",
                personality = listOf("🌲 Nature-loving", "📚 Intellectual"),
                interests = listOf("🌲 Forests", "🍞 Fika", "🎵 ABBA"),
                colorClasses = SpeakerColors(
                    background = Color(0xFF0F766E),
                    cardBackground = Color(0xFF14B8A6),
                    textColor = Color.White,
                    borderColor = Color(0xFF0F766E)
                )
            ),
            Speaker(
                id = 33,
                name = "Magnus",
                language = "Norwegian",
                flagEmoji = "🇳🇴",
                avatarSeed = "Magnus",
                personality = listOf("⛷️ Adventurous", "🏔️ Outdoor-lover"),
                interests = listOf("⛷️ Skiing", "🐟 Salmon", "🌌 Northern Lights"),
                colorClasses = SpeakerColors(
                    background = Color(0xFF1E40AF),
                    cardBackground = Color(0xFF3B82F6),
                    textColor = Color.White,
                    borderColor = Color(0xFF1E40AF)
                )
            ),
            Speaker(
                id = 34,
                name = "Ingrid",
                language = "Danish",
                flagEmoji = "🇩🇰",
                avatarSeed = "Ingrid",
                personality = listOf("😊 Hygge", "🧘 Peaceful"),
                interests = listOf("🧱 LEGO", "🍰 Pastries", "🕯️ Hygge"),
                colorClasses = SpeakerColors(
                    background = Color(0xFFB91C1C),
                    cardBackground = Color(0xFFEF4444),
                    textColor = Color.White,
                    borderColor = Color(0xFFB91C1C)
                )
            ),
            Speaker(
                id = 35,
                name = "Aino",
                language = "Finnish",
                flagEmoji = "🇫🇮",
                avatarSeed = "Aino",
                personality = listOf("🔥 Resilient", "🌲 Forest-lover"),
                interests = listOf("🔥 Sauna", "🍄 Mushrooms", "❄️ Winter"),
                colorClasses = SpeakerColors(
                    background = Color(0xFF4C1D95),
                    cardBackground = Color(0xFF7C3AED),
                    textColor = Color.White,
                    borderColor = Color(0xFF4C1D95)
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