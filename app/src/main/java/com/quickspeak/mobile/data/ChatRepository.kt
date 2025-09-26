package com.quickspeak.mobile.data

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.quickspeak.mobile.domain.model.*

/**
 * CHAT REPOSITORY
 * Manages all chat-related data including messages, saved speakers, and chat sessions
 * This acts as a single source of truth for chat functionality
 */
object ChatRepository {

    // SAVED SPEAKERS STATE
    private var _savedSpeakers = mutableStateOf<List<SavedSpeaker>>(getMockSavedSpeakers())
    val savedSpeakers: List<SavedSpeaker> by _savedSpeakers

    // ACTIVE CHATS STATE
    private var _activeChats = mutableStateOf<List<Chat>>(getMockChats())
    val activeChats: List<Chat> by _activeChats

    // FUNCTIONS FOR SAVED SPEAKERS
    fun addSavedSpeaker(speaker: Speaker) {
        val savedSpeaker = SavedSpeaker.fromSpeaker(speaker)
        if (_savedSpeakers.value.none { it.speakerId == speaker.id }) {
            _savedSpeakers.value = _savedSpeakers.value + savedSpeaker
        }
    }

    fun removeSavedSpeaker(speakerId: Int) {
        _savedSpeakers.value = _savedSpeakers.value.filter { it.speakerId != speakerId }
    }

    fun isSpeakerSaved(speakerId: Int): Boolean {
        return _savedSpeakers.value.any { it.speakerId == speakerId }
    }

    // FUNCTIONS FOR CHATS
    fun startNewChat(speaker: Speaker): Chat {
        val chatId = "chat_${speaker.id}_${System.currentTimeMillis()}"
        val newChat = Chat(
            id = chatId,
            speakerId = speaker.id,
            speakerName = speaker.name,
            messages = getInitialMessagesForSpeaker(speaker),
            lastMessageTime = System.currentTimeMillis(),
            hasUnreadMessages = false
        )

        // Remove any existing chat with this speaker and add the new one
        _activeChats.value = _activeChats.value.filter { it.speakerId != speaker.id } + newChat

        // Also save the speaker if not already saved
        addSavedSpeaker(speaker)

        return newChat
    }

    fun getChatBySpeakerId(speakerId: Int): Chat? {
        return _activeChats.value.find { it.speakerId == speakerId }
    }

    fun addMessageToChat(chatId: String, content: String, fromUser: Boolean = true): Boolean {
        val chatIndex = _activeChats.value.indexOfFirst { it.id == chatId }
        if (chatIndex == -1) return false

        val chat = _activeChats.value[chatIndex]
        val newMessage = Message(
            id = chat.messages.size + 1,
            senderId = if (fromUser) "user" else chat.speakerId.toString(),
            content = content,
            timestamp = System.currentTimeMillis(),
            isFromUser = fromUser
        )

        val updatedChat = chat.copy(
            messages = chat.messages + newMessage,
            lastMessageTime = newMessage.timestamp,
            hasUnreadMessages = !fromUser
        )

        _activeChats.value = _activeChats.value.toMutableList().apply {
            set(chatIndex, updatedChat)
        }

        return true
    }

    fun markChatAsRead(chatId: String) {
        val chatIndex = _activeChats.value.indexOfFirst { it.id == chatId }
        if (chatIndex != -1) {
            val chat = _activeChats.value[chatIndex]
            val updatedChat = chat.copy(hasUnreadMessages = false)
            _activeChats.value = _activeChats.value.toMutableList().apply {
                set(chatIndex, updatedChat)
            }
        }
    }

    // MOCK DATA FUNCTIONS
    private fun getMockSavedSpeakers(): List<SavedSpeaker> {
        return listOf(
            SavedSpeaker(speakerId = 12, name = "Hao", language = "Chinese", flagEmoji = "🇨🇳", avatarSeed = "Hao"),
            SavedSpeaker(speakerId = 6, name = "Sofia", language = "Portuguese", flagEmoji = "🇧🇷", avatarSeed = "Sofia"),
            SavedSpeaker(speakerId = 1, name = "Burkhart", language = "German", flagEmoji = "🇩🇪", avatarSeed = "Burkhart"),
            SavedSpeaker(speakerId = 3, name = "Marta", language = "Spanish", flagEmoji = "🇪🇸", avatarSeed = "Marta"),
            SavedSpeaker(speakerId = 5, name = "Marco", language = "Italian", flagEmoji = "🇮🇹", avatarSeed = "Marco")
        )
    }

    private fun getMockChats(): List<Chat> {
        return listOf(
            Chat(
                id = "chat_hao_1",
                speakerId = 12,
                speakerName = "Hao",
                messages = listOf(
                    Message(1, "12", "你好！我是郝。你好吗？", System.currentTimeMillis() - 240000, false),
                    Message(2, "user", "你好郝！我很好，谢谢。你呢？", System.currentTimeMillis() - 210000, true),
                    Message(3, "12", "我也很好！你学中文多长时间了？", System.currentTimeMillis() - 180000, false),
                    Message(4, "user", "我学了六个月。还在学习中。", System.currentTimeMillis() - 150000, true),
                    Message(5, "12", "很棒！继续加油！你想聊什么？", System.currentTimeMillis() - 120000, false)
                ),
                lastMessageTime = System.currentTimeMillis() - 120000,
                hasUnreadMessages = true
            ),
            Chat(
                id = "chat_sofia_1",
                speakerId = 6,
                speakerName = "Sofia",
                messages = listOf(
                    Message(1, "6", "Oi! Como vai? Sou a Sofia!", System.currentTimeMillis() - 2100000, false),
                    Message(2, "user", "Oi Sofia! Tudo bem e você?", System.currentTimeMillis() - 2070000, true),
                    Message(3, "6", "Tudo ótimo! Que legal conhecer você. That sounds cool. What...", System.currentTimeMillis() - 2040000, false)
                ),
                lastMessageTime = System.currentTimeMillis() - 2040000,
                hasUnreadMessages = true
            ),
            Chat(
                id = "chat_burkhart_1",
                speakerId = 1,
                speakerName = "Burkhart",
                messages = listOf(
                    Message(1, "1", "Hallo! Ich bin Burkhart. Wie geht's?", System.currentTimeMillis() - 2340000, false),
                    Message(2, "user", "Hallo Burkhart! Mir geht's gut, und dir?", System.currentTimeMillis() - 2310000, true),
                    Message(3, "1", "Mir geht es gut, aber du hast einen kleinen Fehler gemacht. I like to do a lot of different...", System.currentTimeMillis() - 2280000, false)
                ),
                lastMessageTime = System.currentTimeMillis() - 2280000,
                hasUnreadMessages = true
            ),
            Chat(
                id = "chat_marta_1",
                speakerId = 3,
                speakerName = "Marta",
                messages = listOf(
                    Message(1, "3", "¡Hola! Soy Marta. ¿Cómo estás?", System.currentTimeMillis() - 86400000, false),
                    Message(2, "user", "¡Hola Marta! Muy bien, gracias.", System.currentTimeMillis() - 86370000, true),
                    Message(3, "3", "That's awesome.", System.currentTimeMillis() - 86340000, false)
                ),
                lastMessageTime = System.currentTimeMillis() - 86340000,
                hasUnreadMessages = false
            ),
            Chat(
                id = "chat_marco_1",
                speakerId = 5,
                speakerName = "Marco",
                messages = listOf(
                    Message(1, "5", "Ciao! Sono Marco. Come stai?", System.currentTimeMillis() - 86400000, false),
                    Message(2, "user", "Ciao Marco! Sto bene, grazie!", System.currentTimeMillis() - 86370000, true),
                    Message(3, "5", "Okay, sounds good!", System.currentTimeMillis() - 86340000, false)
                ),
                lastMessageTime = System.currentTimeMillis() - 86340000,
                hasUnreadMessages = false
            )
        )
    }

    private fun getInitialMessagesForSpeaker(speaker: Speaker): List<Message> {
        return when (speaker.name) {
            "Hao" -> listOf(
                Message(1, speaker.id.toString(), "你好！我是郝。很高兴认识你！", System.currentTimeMillis(), false)
            )
            "Sofia" -> listOf(
                Message(1, speaker.id.toString(), "Oi! Tudo bem? Sou a Sofia. Prazer em conhecê-lo!", System.currentTimeMillis(), false)
            )
            "Burkhart" -> listOf(
                Message(1, speaker.id.toString(), "Guten Tag! Ich bin Burkhart. Freut mich.", System.currentTimeMillis(), false)
            )
            "Marta" -> listOf(
                Message(1, speaker.id.toString(), "¡Hola! Soy Marta. ¡Qué alegría conocerte!", System.currentTimeMillis(), false)
            )
            "Marco" -> listOf(
                Message(1, speaker.id.toString(), "Ciao! Sono Marco. Piacere di conoscerti!", System.currentTimeMillis(), false)
            )
            "Leonie" -> listOf(
                Message(1, speaker.id.toString(), "Salut! Je suis Léonie. Enchantée de faire ta connaissance!", System.currentTimeMillis(), false)
            )
            "Kenji" -> listOf(
                Message(1, speaker.id.toString(), "こんにちは！私は健二です。よろしくお願いします！", System.currentTimeMillis(), false)
            )
            "Fatima" -> listOf(
                Message(1, speaker.id.toString(), "مرحبا! أنا فاطمة. سعيدة بلقائك!", System.currentTimeMillis(), false)
            )
            "Aarav" -> listOf(
                Message(1, speaker.id.toString(), "नमस्ते! मैं आरव हूँ। आपसे मिलकर खुशी हुई!", System.currentTimeMillis(), false)
            )
            else -> listOf(
                Message(1, speaker.id.toString(), "Hello! I'm ${speaker.name}. Nice to meet you!", System.currentTimeMillis(), false)
            )
        }
    }
}