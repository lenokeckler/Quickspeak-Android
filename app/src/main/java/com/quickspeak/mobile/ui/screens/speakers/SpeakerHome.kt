package com.quickspeak.mobile.ui.screens.speakers

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import com.quickspeak.mobile.ui.theme.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.quickspeak.mobile.data.ChatRepository
import com.quickspeak.mobile.data.UserRepository
import com.quickspeak.mobile.domain.model.*
import kotlinx.coroutines.delay
import com.quickspeak.mobile.data.LanguageRepository

/**
 * SPEAKER HOME SCREEN
 * Main speakers screen with saved speakers and recent chats
 */
@Composable
fun SpeakerHome(
    onMenuClick: () -> Unit,
    onSpeakerCatalogClick: () -> Unit = {},
    onChatClick: (Speaker) -> Unit = {}
) {
    val systemDarkTheme = isSystemInDarkTheme()
    val isDarkTheme = UserRepository.getEffectiveDarkMode(systemDarkTheme)

    // Loading state for async operations
    var isLoading by remember { mutableStateOf(true) }

    // Get data from ChatRepository and filter by enabled languages
    val enabledLanguages = remember { LanguageRepository.learningLanguages.map { it.name }.toSet() }
    val savedSpeakers = remember(enabledLanguages) {
        ChatRepository.savedSpeakers.filter { savedSpeaker ->
            enabledLanguages.contains(savedSpeaker.language)
        }
    }
    val activeChats = remember(enabledLanguages) {
        ChatRepository.activeChats.filter { chat ->
            // Find the speaker for this chat and check if their language is enabled
            val speaker = SpeakerData.getAllSpeakers().find { it.id == chat.speakerId }
            speaker?.let { enabledLanguages.contains(it.language) } ?: false
        }
    }

    // Simulate loading for async operations (avatars, flags)
    LaunchedEffect(Unit) {
        delay(1500) // Give time for images to load
        isLoading = false
    }

    // MAIN CONTAINER WITH GRADIENT BACKGROUND
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = if (isDarkTheme) {
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
                            Color(0xFFE9D5FF) // Purple-200 equivalent
                        )
                    )
                }
            )
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        if (isLoading) {
            // LOADING SCREEN
            LoadingScreen(isDarkTheme = isDarkTheme)
        } else {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
            // TOP BAR SECTION
            SpeakerTopAppBar(
                title = "Speakers",
                onMenuClick = onMenuClick,
                isDarkTheme = isDarkTheme
            )

            // MAIN CONTENT
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                // SEARCH BAR STYLE BUTTON FOR SPEAKER CATALOG
                SpeakerCatalogButton(
                    onClick = onSpeakerCatalogClick,
                    isDarkTheme = isDarkTheme
                )

                Spacer(modifier = Modifier.height(24.dp))

                // YOUR SAVED SPEAKERS SECTION
                SavedSpeakersSection(
                    savedSpeakers = savedSpeakers,
                    isDarkTheme = isDarkTheme,
                    onSpeakerClick = onChatClick,
                    onAddClick = onSpeakerCatalogClick
                )

                Spacer(modifier = Modifier.height(24.dp))

                // RECENTS SECTION
                RecentsSection(
                    activeChats = activeChats,
                    isDarkTheme = isDarkTheme,
                    onChatClick = onChatClick
                )
            }
        }
        }
    }
}

/**
 * LOADING SCREEN COMPONENT
 * Shows a spinning loader while avatars and flags are loading
 */
@Composable
private fun LoadingScreen(isDarkTheme: Boolean) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(64.dp),
                color = if (isDarkTheme) BlueDarkMode else BlueLightMode,
                strokeWidth = 6.dp
            )

            Text(
                text = "Loading Speakers...",
                color = if (isDarkTheme) BlueDarkMode else BlueLightMode,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

/**
 * TOP APP BAR FOR SPEAKER SCREEN
 * Contains the hamburger menu and title
 */
@Composable
private fun SpeakerTopAppBar(
    title: String,
    onMenuClick: () -> Unit,
    isDarkTheme: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 28.dp)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // HAMBURGER MENU BUTTON
        IconButton(
            onClick = onMenuClick
        ) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Open menu",
                tint = if (isDarkTheme) BlueDarkMode else BlueLightMode,
                modifier = Modifier.size(48.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        // TITLE
        Text(
            text = title,
            color = if (isDarkTheme) BlueDarkMode else BlueLightMode,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

/**
 * SEARCH BAR STYLE BUTTON FOR SPEAKER CATALOG
 * Long button with search symbol at the end
 */
@Composable
private fun SpeakerCatalogButton(
    onClick: () -> Unit,
    isDarkTheme: Boolean
) {
    // BUTTON WITH ROUNDED BACKGROUND AND ICON (LIKE WEB VERSION)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(25.dp))
            .background(if (isDarkTheme) BlueDarkMode else BlueLightMode)
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Speaker Catalog",
                color = if (isDarkTheme) WhiteGeneral else BlackGeneral,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            // ICON WITH WHITE CIRCLE BACKGROUND
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(
                        color = WhiteGeneral,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Open speaker catalog",
                    tint = if (isDarkTheme) BlueDarkMode else BlueLightMode,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

/**
 * YOUR SAVED SPEAKERS SECTION
 * Section title and horizontal scrollable row of speaker circles
 */
@Composable
private fun SavedSpeakersSection(
    savedSpeakers: List<SavedSpeaker>,
    isDarkTheme: Boolean,
    onSpeakerClick: (Speaker) -> Unit,
    onAddClick: () -> Unit
) {
    Column {
        // SECTION TITLE WITH BACKGROUND (LIKE WEB VERSION)
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(25.dp))
                .background(if (isDarkTheme) BlueDarkMode else BlueLightMode)
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Your Saved Speakers",
                    color = if (isDarkTheme) WhiteGeneral else BlackGeneral,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                // DOT INDICATOR LIKE WEB VERSION
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .background(
                            color = if (isDarkTheme) Color(0xFF0F766E) else Color(0xFF06B6D4),
                            shape = CircleShape
                        )
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // HORIZONTAL SCROLLABLE ROW OF SPEAKER CIRCLES WITH AVATARS
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 8.dp)
        ) {
            items(savedSpeakers) { savedSpeaker ->
                SavedSpeakerAvatarCircle(
                    savedSpeaker = savedSpeaker,
                    size = 84.dp,
                    onClick = {
                        // Find the full speaker data and start chat
                        val fullSpeaker = SpeakerData.getAllSpeakers().find { it.id == savedSpeaker.speakerId }
                        fullSpeaker?.let { onSpeakerClick(it) }
                    }
                )
            }

            // ADD SPEAKER BUTTON (LIKE WEB VERSION)
            item {
                AddSpeakerButton(
                    size = 84.dp,
                    isDarkTheme = isDarkTheme,
                    onClick = onAddClick
                )
            }
        }
    }
}

/**
 * RECENTS SECTION
 * Section title and vertical scrollable list of chats
 */
@Composable
private fun RecentsSection(
    activeChats: List<Chat>,
    isDarkTheme: Boolean,
    onChatClick: (Speaker) -> Unit
) {
    Column {
        // SECTION TITLE WITH BACKGROUND (LIKE WEB VERSION)
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(25.dp))
                .background(RedDarkMode)
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Recents",
                    color = WhiteGeneral,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Refresh",
                    tint = Color.White.copy(alpha = 0.8f),
                    modifier = Modifier.size(16.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // VERTICAL SCROLLABLE LIST OF CHATS WITH WEB-LIKE STYLING
        if (activeChats.isNotEmpty()) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                itemsIndexed(activeChats.sortedByDescending { it.lastMessageTime }) { index, chat ->
                    val speaker = SpeakerData.getAllSpeakers().find { it.id == chat.speakerId }
                    // Get custom color for this speaker if set
                    val customColor = ChatRepository.getSpeakerColor(chat.speakerId)
                    // Use custom color, or speaker's default background, or fallback to index-based color
                    val cardColor = customColor ?: speaker?.colorClasses?.cardBackground ?: getChatCardColor(index)

                    RecentChatCard(
                        chat = chat,
                        cardColor = cardColor,
                        isDarkTheme = isDarkTheme,
                        onClick = {
                            // Find the full speaker data and open chat
                            speaker?.let { onChatClick(it) }
                        }
                    )
                }
            }
        } else {
            // NO CHATS PLACEHOLDER
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "No recent chats",
                    color = if (isDarkTheme) Color.Gray else Color.Gray,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Start a conversation from the Speaker Catalog",
                    color = if (isDarkTheme) Color.Gray else Color.Gray,
                    fontSize = 14.sp
                )
            }
        }
    }
}

/**
 * SAVED SPEAKER AVATAR CIRCLE COMPONENT
 * Individual saved speaker circle with avatar and flag overlay (like web version)
 */
@Composable
private fun SavedSpeakerAvatarCircle(
    savedSpeaker: SavedSpeaker,
    size: androidx.compose.ui.unit.Dp,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(size)
                .clickable { onClick() }
        ) {
            // FLAG BACKGROUND
            AsyncImage(
                model = "https://unpkg.com/circle-flags/flags/${getFlagCode(savedSpeaker.language)}.svg",
                contentDescription = "${savedSpeaker.name}'s country flag",
                modifier = Modifier
                    .size(size)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            // AVATAR OVERLAY
            AsyncImage(
                model = "https://api.dicebear.com/9.x/avataaars/svg?seed=${savedSpeaker.avatarSeed}",
                contentDescription = "Avatar of ${savedSpeaker.name}",
                modifier = Modifier
                    .size(size)
                    .clip(CircleShape),
                contentScale = ContentScale.Fit
            )
        }

        // SPEAKER NAME
        Text(
            text = savedSpeaker.name,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = if (isSystemInDarkTheme()) Color(0xFFE5E7EB) else Color(0xFF374151)
        )
    }
}

/**
 * ADD SPEAKER BUTTON COMPONENT
 * Plus button to add new speakers (like web version)
 */
@Composable
private fun AddSpeakerButton(
    size: androidx.compose.ui.unit.Dp,
    isDarkTheme: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(size)
                .clip(CircleShape)
                .background(
                    if (isDarkTheme) Color(0xFF374151).copy(alpha = 0.6f) else Color(0xFFE5E7EB)
                )
                .clickable { onClick() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add speaker",
                tint = if (isDarkTheme) Color(0xFF9CA3AF) else Color(0xFF6B7280),
                modifier = Modifier.size(32.dp)
            )
        }

        Text(
            text = "Add",
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = if (isDarkTheme) Color(0xFFD1D5DB) else Color(0xFF6B7280)
        )
    }
}

/**
 * RECENT CHAT CARD COMPONENT
 * Individual chat card with gradient background and speaker avatar (like web version)
 */
@Composable
private fun RecentChatCard(
    chat: Chat,
    cardColor: Color,
    isDarkTheme: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        cardColor,
                        cardColor.copy(alpha = 0.8f)
                    )
                )
            )
            .clickable { onClick() }
            .padding(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // SPEAKER AVATAR WITH FLAG OVERLAY
            Box(modifier = Modifier.size(56.dp)) {
                // Find the saved speaker or create a temporary one
                val savedSpeaker = ChatRepository.savedSpeakers.find { it.speakerId == chat.speakerId }
                    ?: SavedSpeaker(chat.speakerId, chat.speakerName, "Unknown", "🌍", chat.speakerName)

                AsyncImage(
                    model = "https://unpkg.com/circle-flags/flags/${getFlagCode(savedSpeaker.language)}.svg",
                    contentDescription = "${savedSpeaker.name}'s country flag",
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )

                AsyncImage(
                    model = "https://api.dicebear.com/9.x/avataaars/svg?seed=${savedSpeaker.avatarSeed}",
                    contentDescription = "Avatar of ${savedSpeaker.name}",
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Fit
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // CHAT CONTENT
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = chat.speakerName,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = chat.lastMessage?.content ?: "No messages yet",
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 14.sp,
                    maxLines = 2
                )
            }

            // TIME AND UNREAD DOT
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = formatTimestamp(chat.lastMessageTime),
                    color = Color.White.copy(alpha = 0.9f),
                    fontSize = 12.sp
                )

                if (chat.hasUnreadMessages) {
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .background(
                                color = Color.Black.copy(alpha = 0.5f),
                                shape = CircleShape
                            )
                    )
                }
            }
        }
    }
}

/**
 * GLOBAL STYLE FUNCTIONS
 */

/**
 * Get chat card color based on index following the pattern: Red -> Yellow -> Cyan -> Blue
 */
fun getChatCardColor(index: Int): Color {
    return when (index % 4) {
        0 -> RedDarkMode
        1 -> YellowDarkMode
        2 -> BlueDarkMode
        3 -> BlueDarkMode
        else -> RedDarkMode
    }
}

/**
 * HELPER FUNCTIONS
 */
private fun formatTimestamp(timestamp: Long): String {
    val now = System.currentTimeMillis()
    val diff = now - timestamp

    return when {
        diff < 60 * 1000 -> "now"
        diff < 60 * 60 * 1000 -> "${diff / (60 * 1000)} min"
        diff < 24 * 60 * 60 * 1000 -> "${diff / (60 * 60 * 1000)}h"
        else -> "Today"
    }
}

private fun getFlagCode(language: String): String {
    return when (language) {
        "German" -> "de"
        "French" -> "fr"
        "Spanish" -> "es"
        "Hindi" -> "in"
        "Italian" -> "it"
        "Portuguese" -> "br"
        "Japanese" -> "jp"
        "Arabic" -> "ae"
        "Russian" -> "ru"
        "English" -> "gb"
        "Irish" -> "ie"
        "Chinese" -> "cn"
        else -> "us"
    }
}

/**
 * PREVIEW FUNCTIONS
 */
@Preview(showBackground = true, backgroundColor = 0xFF1C1C1E)
@Composable
fun SpeakerHomePreview() {
    MaterialTheme {
        SpeakerHome(onMenuClick = {})
    }
}