package com.quickspeak.mobile.ui.screens.speakers

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * SPEAKER HOME SCREEN
 * Main speakers screen with saved speakers and recent chats
 */
@Composable
fun SpeakerHome(
    onMenuClick: () -> Unit,
    onSpeakerCatalogClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // TOP BAR SECTION
        SpeakerTopAppBar(
            title = "Speakers",
            onMenuClick = onMenuClick
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
                onClick = onSpeakerCatalogClick
            )

            Spacer(modifier = Modifier.height(24.dp))

            // YOUR SAVED SPEAKERS SECTION
            SavedSpeakersSection()

            Spacer(modifier = Modifier.height(24.dp))

            // RECENTS SECTION
            RecentsSection()
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
    onMenuClick: () -> Unit
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
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.size(48.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        // TITLE
        Text(
            text = title,
            color = MaterialTheme.colorScheme.secondary,
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
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(25.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Browse Speaker Catalog",
                color = BlueDarkMode,
                fontSize = 16.sp
            )

            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add speakers",
                tint = BlueDarkMode,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

/**
 * YOUR SAVED SPEAKERS SECTION
 * Section title and horizontal scrollable row of speaker circles
 */
@Composable
private fun SavedSpeakersSection() {
    Column {
        // SECTION TITLE WITH BACKGROUND
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = BlueDarkMode,
                    shape = RoundedCornerShape(25.dp)
                )
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Text(
                text = "Your Saved Speakers",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // HORIZONTAL SCROLLABLE ROW OF SPEAKER CIRCLES
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 8.dp)
        ) {
            items(getSavedSpeakers()) { speaker ->
                SpeakerCircle(
                    speaker = speaker,
                    size = 84.dp
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
private fun RecentsSection() {
    Column {
        // SECTION TITLE WITH BACKGROUND
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = RedDarkMode,
                    shape = RoundedCornerShape(25.dp)
                )
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Text(
                text = "Recents",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // VERTICAL SCROLLABLE LIST OF CHATS
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            itemsIndexed(getRecentChats()) { index, chat ->
                ChatListItem(
                    chat = chat,
                    cardColor = getChatCardColor(index)
                )
            }
        }
    }
}

/**
 * SPEAKER CIRCLE COMPONENT
 * Individual speaker circle with different colors
 */
@Composable
private fun SpeakerCircle(
    speaker: Speaker,
    size: androidx.compose.ui.unit.Dp
) {
    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(speaker.color)
            .clickable { /* TODO: Handle speaker selection */ },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = speaker.initial,
            color = Color.White,
            fontSize = (size.value / 3).sp,
            fontWeight = FontWeight.Bold
        )
    }
}

/**
 * CHAT LIST ITEM COMPONENT
 * Individual chat item with speaker circle, name, message, and time
 */
@Composable
private fun ChatListItem(
    chat: ChatItem,
    cardColor: Color = RedDarkMode
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* TODO: Handle chat selection */ },
        colors = CardDefaults.cardColors(
            containerColor = cardColor.copy(alpha = 0.1f)
        ),
        shape = RoundedCornerShape(22.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // SPEAKER CIRCLE
            SpeakerCircle(
                speaker = chat.speaker,
                size = 84.dp
            )

            Spacer(modifier = Modifier.width(16.dp))

            // CHAT CONTENT
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = chat.chatName,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = chat.latestMessage,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                    fontSize = 14.sp,
                    maxLines = 2
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            // DOT FOR UNANSWERED CHATS
            Text(
                text = if (chat.isAnswered) chat.time else "•",
                color = if (chat.isAnswered) MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f) else cardColor,
                fontSize = if (chat.isAnswered) 12.sp else 16.sp,
                fontWeight = if (chat.isAnswered) FontWeight.Normal else FontWeight.Bold
            )
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
        2 -> CyanDarkMode
        3 -> BlueDarkMode
        else -> RedDarkMode
    }
}

/**
 * DATA CLASSES
 */
data class Speaker(
    val id: String,
    val name: String,
    val initial: String,
    val color: Color
)

data class ChatItem(
    val id: String,
    val chatName: String,
    val latestMessage: String,
    val time: String,
    val speaker: Speaker,
    val isAnswered: Boolean = true
)

/**
 * SAMPLE DATA FUNCTIONS
 */
private fun getSavedSpeakers(): List<Speaker> {
    return listOf(
        Speaker("1", "Alex", "A", Color(0xFF6366F1)),
        Speaker("2", "Sarah", "S", Color(0xFF10B981)),
        Speaker("3", "Mike", "M", Color(0xFFF59E0B)),
        Speaker("4", "Emma", "E", Color(0xFFEF4444)),
        Speaker("5", "David", "D", Color(0xFF8B5CF6)),
        Speaker("6", "Lisa", "L", Color(0xFF06B6D4)),
        Speaker("7", "John", "J", Color(0xFFEC4899)),
        Speaker("8", "Anna", "A", Color(0xFF84CC16)),
        Speaker("9", "Tom", "T", Color(0xFFF97316)),
        Speaker("10", "Kate", "K", Color(0xFF14B8A6))
    )
}

private fun getRecentChats(): List<ChatItem> {
    val speakers = getSavedSpeakers()
    return listOf(
        ChatItem(
            id = "1",
            chatName = "English Practice",
            latestMessage = "Great job on your pronunciation today! Let's work on the 'th' sound next time.",
            time = "2:30 PM",
            speaker = speakers[0],
            isAnswered = false
        ),
        ChatItem(
            id = "2",
            chatName = "Spanish Conversation",
            latestMessage = "¡Hola! ¿Cómo estás hoy? Let's practice some basic greetings.",
            time = "1:45 PM",
            speaker = speakers[1],
            isAnswered = true
        ),
        ChatItem(
            id = "3",
            chatName = "Business English",
            latestMessage = "We covered presentation skills today. Remember to practice your opening statements.",
            time = "12:15 PM",
            speaker = speakers[2],
            isAnswered = false
        ),
        ChatItem(
            id = "4",
            chatName = "French Basics",
            latestMessage = "Bonjour! Today we learned about French pronunciation. Keep practicing those vowels!",
            time = "11:30 AM",
            speaker = speakers[3],
            isAnswered = true
        ),
        ChatItem(
            id = "5",
            chatName = "IELTS Preparation",
            latestMessage = "Your speaking score is improving! Let's focus on task 2 writing next.",
            time = "10:20 AM",
            speaker = speakers[4],
            isAnswered = false
        )
    )
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