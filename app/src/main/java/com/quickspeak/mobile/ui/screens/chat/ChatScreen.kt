package com.quickspeak.mobile.ui.screens.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.quickspeak.mobile.data.ChatRepository
import com.quickspeak.mobile.domain.model.*
import com.quickspeak.mobile.ui.theme.*

/**
 * CHAT SCREEN
 * Main chat interface for conversation with a specific speaker
 * Based on the web version but with popup-style modals for additional functionality
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    speaker: Speaker,
    onMenuClick: () -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    val isDarkTheme = isSystemInDarkTheme()
    val chatTheme = ChatTheme.fromSpeaker(speaker)

    // Get or create chat
    var currentChat by remember {
        mutableStateOf(
            ChatRepository.getChatBySpeakerId(speaker.id)
                ?: ChatRepository.startNewChat(speaker)
        )
    }

    // Modal states
    var showSpeakerProfileModal by remember { mutableStateOf(false) }
    var showAnalyzeWordsModal by remember { mutableStateOf(false) }
    var selectedMessageForAnalysis by remember { mutableStateOf<Message?>(null) }

    // Input state
    var messageText by remember { mutableStateOf("") }

    // Auto-scroll to bottom
    val listState = rememberLazyListState()
    LaunchedEffect(currentChat.messages.size) {
        if (currentChat.messages.isNotEmpty()) {
            listState.animateScrollToItem(currentChat.messages.size - 1)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = if (isDarkTheme) {
                    Brush.verticalGradient(
                        colors = listOf(
                            chatTheme.gradientStart,
                            chatTheme.gradientEnd
                        )
                    )
                } else {
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.White,
                            chatTheme.speakerBubbleBackground.copy(alpha = 0.1f)
                        )
                    )
                }
            )
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // CHAT HEADER
            ChatHeader(
                speaker = speaker,
                chatTheme = chatTheme,
                isDarkTheme = isDarkTheme,
                onMenuClick = onMenuClick,
                onAvatarClick = { showSpeakerProfileModal = true }
            )

            // MESSAGES LIST
            LazyColumn(
                state = listState,
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                reverseLayout = false
            ) {
                items(currentChat.messages) { message ->
                    ChatMessageBubble(
                        message = message,
                        speaker = speaker,
                        chatTheme = chatTheme,
                        isDarkTheme = isDarkTheme,
                        onAnalyzeClick = {
                            selectedMessageForAnalysis = message
                            showAnalyzeWordsModal = true
                        }
                    )
                }
            }

            // CHAT INPUT
            ChatInputBar(
                messageText = messageText,
                onMessageChange = { messageText = it },
                onSendClick = {
                    if (messageText.isNotBlank()) {
                        ChatRepository.addMessageToChat(currentChat.id, messageText.trim(), true)
                        currentChat = ChatRepository.getChatBySpeakerId(speaker.id)!!
                        messageText = ""
                    }
                },
                chatTheme = chatTheme,
                isDarkTheme = isDarkTheme
            )
        }
    }

    // SPEAKER PROFILE MODAL
    if (showSpeakerProfileModal) {
        SpeakerProfileModal(
            speaker = speaker,
            chatTheme = chatTheme,
            isDarkTheme = isDarkTheme,
            onDismiss = { showSpeakerProfileModal = false }
        )
    }

    // ANALYZE WORDS MODAL
    if (showAnalyzeWordsModal && selectedMessageForAnalysis != null) {
        AnalyzeWordsModal(
            message = selectedMessageForAnalysis!!,
            speaker = speaker,
            chatTheme = chatTheme,
            isDarkTheme = isDarkTheme,
            onDismiss = {
                showAnalyzeWordsModal = false
                selectedMessageForAnalysis = null
            }
        )
    }
}

/**
 * CHAT HEADER COMPONENT
 * Top section with menu button, speaker info, and avatar
 */
@Composable
private fun ChatHeader(
    speaker: Speaker,
    chatTheme: ChatTheme,
    isDarkTheme: Boolean,
    onMenuClick: () -> Unit,
    onAvatarClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(24.dp),
        color = if (isDarkTheme) chatTheme.headerBackground else chatTheme.headerBackground,
        shadowElevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // MENU BUTTON
            IconButton(onClick = onMenuClick) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menu",
                    tint = if (isDarkTheme) chatTheme.textColor else Color.Black,
                    modifier = Modifier.size(32.dp)
                )
            }

            // SPEAKER INFO (CENTER)
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = speaker.name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isDarkTheme) chatTheme.textColor else Color.Black
                )
                Text(
                    text = "${speaker.language} Speaker",
                    fontSize = 14.sp,
                    color = if (isDarkTheme) chatTheme.textColor.copy(alpha = 0.7f) else Color.Black.copy(alpha = 0.7f)
                )
            }

            // SPEAKER AVATAR (CLICKABLE)
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .clickable { onAvatarClick() }
            ) {
                // Flag background
                AsyncImage(
                    model = "https://unpkg.com/circle-flags/flags/${getFlagCode(speaker.language)}.svg",
                    contentDescription = "${speaker.name}'s flag",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                // Avatar overlay
                AsyncImage(
                    model = "https://api.dicebear.com/9.x/avataaars/svg?seed=${speaker.avatarSeed}",
                    contentDescription = "Avatar of ${speaker.name}",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            }
        }
    }
}

/**
 * CHAT MESSAGE BUBBLE COMPONENT
 * Individual message with sender-appropriate styling
 */
@Composable
private fun ChatMessageBubble(
    message: Message,
    speaker: Speaker,
    chatTheme: ChatTheme,
    isDarkTheme: Boolean,
    onAnalyzeClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (message.isFromUser) Arrangement.End else Arrangement.Start
    ) {
        if (!message.isFromUser) {
            // SPEAKER AVATAR (LEFT SIDE)
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
            ) {
                AsyncImage(
                    model = "https://unpkg.com/circle-flags/flags/${getFlagCode(speaker.language)}.svg",
                    contentDescription = "${speaker.name}'s flag",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                AsyncImage(
                    model = "https://api.dicebear.com/9.x/avataaars/svg?seed=${speaker.avatarSeed}",
                    contentDescription = "Avatar of ${speaker.name}",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
        }

        Column {
            // MESSAGE BUBBLE
            Surface(
                modifier = Modifier.widthIn(max = 280.dp),
                shape = RoundedCornerShape(16.dp),
                color = if (message.isFromUser) {
                    chatTheme.userBubbleBackground
                } else {
                    if (isDarkTheme) chatTheme.speakerBubbleBackground else chatTheme.speakerBubbleBackground.copy(alpha = 0.3f)
                },
                shadowElevation = 2.dp
            ) {
                Text(
                    text = message.content,
                    modifier = Modifier.padding(12.dp),
                    color = if (message.isFromUser) Color.White else {
                        if (isDarkTheme) chatTheme.textColor else Color.Black
                    },
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            // ACTION BUTTONS (ONLY FOR SPEAKER MESSAGES)
            if (!message.isFromUser) {
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    IconButton(
                        onClick = onAnalyzeClick,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.MenuBook,
                            contentDescription = "Analyze words",
                            tint = if (isDarkTheme) Color.Gray else Color.Gray,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    IconButton(
                        onClick = { /* TODO: Play audio */ },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.VolumeUp,
                            contentDescription = "Play audio",
                            tint = if (isDarkTheme) Color.Gray else Color.Gray,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    IconButton(
                        onClick = { /* TODO: Copy text */ },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ContentCopy,
                            contentDescription = "Copy text",
                            tint = if (isDarkTheme) Color.Gray else Color.Gray,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }

        if (message.isFromUser) {
            Spacer(modifier = Modifier.width(12.dp))
        }
    }
}

/**
 * CHAT INPUT BAR COMPONENT
 * Bottom input area with send functionality
 */
@Composable
private fun ChatInputBar(
    messageText: String,
    onMessageChange: (String) -> Unit,
    onSendClick: () -> Unit,
    chatTheme: ChatTheme,
    isDarkTheme: Boolean
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(24.dp),
        color = if (isDarkTheme) chatTheme.inputBackground else chatTheme.inputBackground,
        shadowElevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // BOOKMARK BUTTON
            IconButton(onClick = { /* TODO: Bookmark functionality */ }) {
                Icon(
                    imageVector = Icons.Default.Bookmark,
                    contentDescription = "Bookmark",
                    tint = if (isDarkTheme) chatTheme.textColor else Color.Black,
                    modifier = Modifier.size(32.dp)
                )
            }

            // MIC BUTTON
            IconButton(onClick = { /* TODO: Voice input */ }) {
                Icon(
                    imageVector = Icons.Default.Mic,
                    contentDescription = "Voice input",
                    tint = if (isDarkTheme) chatTheme.textColor else Color.Black,
                    modifier = Modifier.size(32.dp)
                )
            }

            // TEXT INPUT FIELD
            OutlinedTextField(
                value = messageText,
                onValueChange = onMessageChange,
                modifier = Modifier.weight(1f),
                placeholder = {
                    Text(
                        text = "Type your message",
                        color = if (isDarkTheme) Color.Gray else Color.Gray
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    focusedContainerColor = if (isDarkTheme) Color(0xFF232323) else Color.White,
                    unfocusedContainerColor = if (isDarkTheme) Color(0xFF232323) else Color.White,
                    focusedTextColor = if (isDarkTheme) Color.White else Color.Black,
                    unfocusedTextColor = if (isDarkTheme) Color.White else Color.Black
                ),
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )

            // SEND BUTTON
            IconButton(onClick = onSendClick) {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = "Send",
                    tint = if (isDarkTheme) chatTheme.textColor else Color.Black,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}

/**
 * Helper function to get flag code from language
 */
private fun getFlagCode(language: String): String {
    return when (language) {
        "German" -> "de"
        "French" -> "fr"
        "Spanish" -> "es"
        "Italian" -> "it"
        "Portuguese" -> "br"
        "Chinese" -> "cn"
        "Japanese" -> "jp"
        "Korean" -> "kr"
        "Russian" -> "ru"
        "Arabic" -> "ae"
        "Hindi" -> "in"
        "English" -> "gb"
        "Irish" -> "ie"
        else -> "gb"
    }
}

/**
 * PREVIEW FUNCTIONS
 */
@Preview(showBackground = true)
@Composable
fun ChatScreenPreview() {
    QuickSpeakTheme {
        val sampleSpeaker = SpeakerData.getAllSpeakers().first()
        ChatScreen(speaker = sampleSpeaker)
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF232323)
@Composable
fun ChatScreenDarkPreview() {
    QuickSpeakTheme(darkTheme = true) {
        val sampleSpeaker = SpeakerData.getAllSpeakers()[1]
        ChatScreen(speaker = sampleSpeaker)
    }
}