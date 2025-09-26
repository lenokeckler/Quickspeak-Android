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
import androidx.compose.ui.window.DialogProperties
import coil3.compose.AsyncImage
import com.quickspeak.mobile.data.ChatRepository
import com.quickspeak.mobile.domain.model.*
import com.quickspeak.mobile.ui.components.SpeakerCard
import com.quickspeak.mobile.ui.theme.QuickSpeakTheme

/**
 * SPEAKER PROFILE MODAL USING SPEAKERCARD
 * Shows speaker information using the existing SpeakerCard component
 * with color selection below and close button outside
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpeakerProfileModal(
    speaker: Speaker,
    chatTheme: ChatTheme,
    isDarkTheme: Boolean,
    onDismiss: () -> Unit,
    onColorChange: (Color) -> Unit = {}
) {
    // Track the current selected color for real-time updates
    var currentSelectedColor by remember { mutableStateOf(chatTheme.speakerBubbleBackground) }
    BasicAlertDialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // CLOSE BUTTON (OUTSIDE AND ABOVE)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(
                                color = if (isDarkTheme) Color(0xFF232323) else Color.White
                            )
                            .border(
                                width = 2.dp,
                                color = if (isDarkTheme) Color.White else Color.Black,
                                shape = RoundedCornerShape(20.dp)
                            )
                            .clickable { onDismiss() }
                            .padding(horizontal = 12.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "Close",
                            tint = if (isDarkTheme) Color.White else Color.Black,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Close",
                            color = if (isDarkTheme) Color.White else Color.Black,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                // SPEAKER CARD (ALWAYS SELECTED STATE TO SHOW BUTTONS)
                // Create a temporary speaker with updated colors for preview
                val updatedSpeaker = speaker.copy(
                    colorClasses = speaker.colorClasses.copy(
                        background = currentSelectedColor,
                        cardBackground = currentSelectedColor,
                        borderColor = currentSelectedColor
                    )
                )

                SpeakerCard(
                    speaker = updatedSpeaker,
                    isSelected = true,
                    modifier = Modifier.widthIn(max = 400.dp),
                    onStartChat = { /* No action needed in modal */ },
                    onSaveSpeaker = {
                        // Handle save/unsave
                        if (ChatRepository.isSpeakerSaved(speaker.id)) {
                            ChatRepository.removeSavedSpeaker(speaker.id)
                        } else {
                            ChatRepository.addSavedSpeaker(speaker)
                        }
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // COLOR SELECTION COMPONENT
                SpeakerColorSelector(
                    currentColor = currentSelectedColor,
                    onColorSelected = { color ->
                        currentSelectedColor = color
                        onColorChange(color)
                    },
                    isDarkTheme = isDarkTheme
                )
            }
        }
    }
}

/**
 * SPEAKER COLOR SELECTOR COMPONENT
 * Shows 4 color options for changing speaker theme
 */
@Composable
private fun SpeakerColorSelector(
    currentColor: Color,
    onColorSelected: (Color) -> Unit,
    isDarkTheme: Boolean
) {
    val colorOptions = listOf(
        Color(0xFF06D6A0), // Teal (default from web)
        Color(0xFF0EA5E9), // Sky blue
        Color(0xFFEF4444), // Red
        Color(0xFFF59E0B)  // Yellow
    )

    Surface(
        shape = RoundedCornerShape(20.dp),
        color = if (isDarkTheme) Color(0xFF232323) else Color.White,
        shadowElevation = 8.dp,
        modifier = Modifier.widthIn(max = 400.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Choose Speaker Color",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = if (isDarkTheme) Color.White else Color.Black
            )

            Spacer(modifier = Modifier.height(12.dp))

            LazyRow(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                items(colorOptions) { color ->
                    val isSelected = color == currentColor

                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(color)
                            .border(
                                width = if (isSelected) 3.dp else 0.dp,
                                color = if (isDarkTheme) Color.White else Color.Black,
                                shape = CircleShape
                            )
                            .clickable { onColorSelected(color) }
                    )
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

@Preview(showBackground = true, backgroundColor = 0xFF232323)
@Composable
fun SpeakerColorSelectorPreview() {
    QuickSpeakTheme(darkTheme = true) {
        SpeakerColorSelector(
            currentColor = Color(0xFF06D6A0),
            onColorSelected = {},
            isDarkTheme = true
        )
    }
}