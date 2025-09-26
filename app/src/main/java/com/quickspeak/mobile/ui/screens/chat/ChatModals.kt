package com.quickspeak.mobile.ui.screens.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.quickspeak.mobile.domain.model.*
import com.quickspeak.mobile.ui.theme.QuickSpeakTheme

/**
 * SPEAKER PROFILE MODAL
 * Shows detailed speaker information with color selection
 * Based on the web version SpeakerProfileModal
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpeakerProfileModal(
    speaker: Speaker,
    chatTheme: ChatTheme,
    isDarkTheme: Boolean,
    onDismiss: () -> Unit
) {
    BasicAlertDialog(
        onDismissRequest = onDismiss
    ) {
        Surface(
            shape = RoundedCornerShape(28.dp),
            color = if (isDarkTheme) chatTheme.speakerBubbleBackground else chatTheme.speakerBubbleBackground.copy(alpha = 0.95f),
            shadowElevation = 16.dp,
            modifier = Modifier
                .widthIn(min = 320.dp, max = 400.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                // MAIN CONTENT SECTION
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = if (isDarkTheme) chatTheme.speakerBubbleBackground else chatTheme.speakerBubbleBackground,
                            shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)
                        )
                ) {
                    // LEFT SECTION: Avatar and basic info
                    Column(
                        modifier = Modifier
                            .weight(0.4f)
                            .background(
                                color = if (isDarkTheme) chatTheme.speakerBubbleBackground.copy(alpha = 0.8f) else Color.White,
                                shape = RoundedCornerShape(28.dp)
                            )
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = speaker.name,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isDarkTheme) Color.Black else Color.Black,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = speaker.language,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = if (isDarkTheme) Color.Black else Color.Black
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = speaker.flagEmoji,
                                fontSize = 16.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // AVATAR
                        Box(
                            modifier = Modifier
                                .size(128.dp)
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
                    }

                    // RIGHT SECTION: Personality and interests
                    Column(
                        modifier = Modifier
                            .weight(0.6f)
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // PERSONALITY SECTION
                        Column {
                            Text(
                                text = "Personality",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isDarkTheme) chatTheme.textColor else Color.Black
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            speaker.personality.forEach { trait ->
                                Text(
                                    text = trait,
                                    fontSize = 14.sp,
                                    color = if (isDarkTheme) chatTheme.textColor else Color.Black,
                                    modifier = Modifier.padding(vertical = 2.dp)
                                )
                            }
                        }

                        // INTERESTS SECTION
                        Column {
                            Text(
                                text = "Interests",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isDarkTheme) chatTheme.textColor else Color.Black
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            speaker.interests.forEach { interest ->
                                Text(
                                    text = interest,
                                    fontSize = 14.sp,
                                    color = if (isDarkTheme) chatTheme.textColor else Color.Black,
                                    modifier = Modifier.padding(vertical = 2.dp)
                                )
                            }
                        }

                        // CLOSE BUTTON
                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .border(
                                    width = 2.dp,
                                    color = if (isDarkTheme) Color.Black.copy(alpha = 0.5f) else Color.Gray,
                                    shape = RoundedCornerShape(20.dp)
                                )
                                .clickable { onDismiss() }
                                .padding(horizontal = 12.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Close,
                                contentDescription = "Close",
                                tint = if (isDarkTheme) Color.Black else Color.Gray,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Close",
                                color = if (isDarkTheme) Color.Black else Color.Gray,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }

                // COLOR SELECTION SECTION (BOTTOM)
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = if (isDarkTheme) Color(0xFF232323) else Color.White,
                            shape = RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp)
                        )
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    val colorOptions = listOf(
                        chatTheme.speakerBubbleBackground,
                        Color(0xFF0EA5E9), // Sky blue
                        Color(0xFFEF4444), // Red
                        Color(0xFFF59E0B)  // Yellow
                    )

                    items(colorOptions) { color ->
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(color)
                                .border(
                                    width = if (color == chatTheme.speakerBubbleBackground) 2.dp else 0.dp,
                                    color = Color.White,
                                    shape = CircleShape
                                )
                                .clickable { /* TODO: Change theme color */ }
                        )
                    }
                }
            }
        }
    }
}

/**
 * ANALYZE WORDS MODAL
 * Shows message words for selection and analysis
 * Based on the web version AnalyzeWordsModal
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnalyzeWordsModal(
    message: Message,
    speaker: Speaker,
    chatTheme: ChatTheme,
    isDarkTheme: Boolean,
    onDismiss: () -> Unit
) {
    val words = remember { message.content.split("\\s+".toRegex()) }
    var selectedWords by remember { mutableStateOf(setOf<String>()) }

    BasicAlertDialog(
        onDismissRequest = onDismiss
    ) {
        Surface(
            shape = RoundedCornerShape(28.dp),
            color = if (isDarkTheme) Color(0xFF232323) else Color.White,
            shadowElevation = 16.dp,
            modifier = Modifier
                .widthIn(min = 300.dp, max = 400.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                // HEADER SECTION
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = if (isDarkTheme) chatTheme.speakerBubbleBackground else chatTheme.speakerBubbleBackground,
                            shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)
                        )
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // SPEAKER AVATAR
                    Box(
                        modifier = Modifier
                            .size(64.dp)
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

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = speaker.name,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isDarkTheme) Color.White else Color.Black
                    )
                    Text(
                        text = "${speaker.language} Speaker",
                        fontSize = 14.sp,
                        color = if (isDarkTheme) Color.Gray else Color.Gray
                    )
                }

                // WORDS SELECTION SECTION
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    // WORDS GRID
                    val chunkedWords = words.chunked(3)
                    chunkedWords.forEach { rowWords ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            rowWords.forEach { word ->
                                val isSelected = selectedWords.contains(word)
                                Surface(
                                    modifier = Modifier
                                        .clickable {
                                            selectedWords = if (isSelected) {
                                                selectedWords - word
                                            } else {
                                                selectedWords + word
                                            }
                                        }
                                        .weight(1f),
                                    shape = RoundedCornerShape(20.dp),
                                    color = if (isSelected) {
                                        Color.White
                                    } else {
                                        if (isDarkTheme) chatTheme.speakerBubbleBackground else chatTheme.speakerBubbleBackground.copy(alpha = 0.3f)
                                    },
                                    border = if (isSelected) {
                                        androidx.compose.foundation.BorderStroke(4.dp, Color(0xFF0EA5E9))
                                    } else null
                                ) {
                                    Text(
                                        text = word,
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                                        color = if (isSelected) Color(0xFF0EA5E9) else {
                                            if (isDarkTheme) Color.Black else Color.Black
                                        },
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // ACTION BUTTONS
                    Button(
                        onClick = { /* TODO: Ask about selected words */ },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White
                        ),
                        shape = RoundedCornerShape(24.dp),
                        border = androidx.compose.foundation.BorderStroke(4.dp, Color(0xFF0EA5E9))
                    ) {
                        Text(
                            text = "Ask About Selected Words",
                            color = Color(0xFF0EA5E9),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // CLOSE BUTTON
                        OutlinedButton(
                            onClick = onDismiss,
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = if (isDarkTheme) Color(0xFF0EA5E9) else Color(0xFF06B6D4),
                                containerColor = if (isDarkTheme) Color(0xFF232323) else Color.Transparent
                            ),
                            border = androidx.compose.foundation.BorderStroke(
                                2.dp,
                                if (isDarkTheme) Color(0xFF0EA5E9) else Color(0xFF06B6D4)
                            ),
                            shape = RoundedCornerShape(24.dp)
                        ) {
                            Text(
                                text = "Close",
                                fontWeight = FontWeight.Bold
                            )
                        }

                        // SAVE TO DICTIONARY BUTTON
                        Button(
                            onClick = { /* TODO: Save to dictionary */ },
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isDarkTheme) Color(0xFF0EA5E9) else Color(0xFF06B6D4)
                            ),
                            shape = RoundedCornerShape(24.dp)
                        ) {
                            Text(
                                text = "Save to Dictionary",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
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
fun SpeakerProfileModalPreview() {
    QuickSpeakTheme {
        val sampleSpeaker = SpeakerData.getAllSpeakers().first()
        val chatTheme = ChatTheme.fromSpeaker(sampleSpeaker)
        SpeakerProfileModal(
            speaker = sampleSpeaker,
            chatTheme = chatTheme,
            isDarkTheme = false,
            onDismiss = {}
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF232323)
@Composable
fun AnalyzeWordsModalPreview() {
    QuickSpeakTheme(darkTheme = true) {
        val sampleSpeaker = SpeakerData.getAllSpeakers()[1]
        val chatTheme = ChatTheme.fromSpeaker(sampleSpeaker)
        val sampleMessage = Message(
            id = 1,
            senderId = "1",
            content = "Hallo, wie geht's? Mir geht es gut!",
            isFromUser = false
        )
        AnalyzeWordsModal(
            message = sampleMessage,
            speaker = sampleSpeaker,
            chatTheme = chatTheme,
            isDarkTheme = true,
            onDismiss = {}
        )
    }
}