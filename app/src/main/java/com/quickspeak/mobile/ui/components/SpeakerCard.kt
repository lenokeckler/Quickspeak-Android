package com.quickspeak.mobile.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.quickspeak.mobile.data.ChatRepository
import com.quickspeak.mobile.domain.model.Speaker
import com.quickspeak.mobile.domain.model.SpeakerData
import com.quickspeak.mobile.ui.theme.QuickSpeakTheme

/**
 * SPEAKER CARD COMPONENT
 * Individual speaker card with avatar, personality, and interests
 * Based on the web version SpeakerCard component but adapted for Jetpack Compose
 *
 * Features:
 * - Adaptive colors for light/dark theme
 * - Blur effect when not focused (hover equivalent)
 * - Two-section layout: avatar section + details section
 * - Responsive design with proper spacing
 */
@Composable
fun SpeakerCard(
    speaker: Speaker,
    isBlurred: Boolean = false,
    isSelected: Boolean = false,
    onCardHover: (Boolean) -> Unit = {},
    onCardClick: () -> Unit = {},
    onStartChat: () -> Unit = {},
    onSaveSpeaker: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val isDarkTheme = isSystemInDarkTheme()

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(250.dp) // Fixed height ensures both sections match
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioLowBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
            .blur(if (isBlurred) 4.dp else 0.dp)
            .clickable { onCardClick() },
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isDarkTheme) {
                speaker.colorClasses.background
            } else {
                Color.White
            }
        ),
        border = if (isDarkTheme) null else androidx.compose.foundation.BorderStroke(
            width = 4.dp,
            color = speaker.colorClasses.cardBackground
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight() // Fill the fixed height
        ) {
            // LEFT SECTION: Avatar and basic info (1/2 width)
            AvatarSection(
                speaker = speaker,
                modifier = Modifier.weight(0.5f)
            )

            // RIGHT SECTION: Personality/interests or action buttons (1/2 width)
            if (isSelected) {
                ActionButtonsSection(
                    speaker = speaker,
                    isDarkTheme = isDarkTheme,
                    onStartChat = onStartChat,
                    onSaveSpeaker = onSaveSpeaker,
                    modifier = Modifier.weight(0.5f)
                )
            } else {
                DetailsSection(
                    speaker = speaker,
                    isDarkTheme = isDarkTheme,
                    modifier = Modifier.weight(0.5f)
                )
            }
        }
    }
}

/**
 * AVATAR SECTION COMPONENT
 * Left side of the card with speaker name, language, flag, and avatar
 * Avatar should touch the bottom of the entire speaker card
 */
@Composable
private fun AvatarSection(
    speaker: Speaker,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .background(
                color = speaker.colorClasses.cardBackground,
                shape = RoundedCornerShape(topEnd = 24.dp, bottomEnd = 24.dp)
            )
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // SPEAKER NAME
        Text(
            text = speaker.name,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF374151),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(5.dp))

        // LANGUAGE AND FLAG
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = speaker.language,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF374151)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = speaker.flagEmoji,
                fontSize = 20.sp
            )
        }

        // SPACER TO PUSH AVATAR TO BOTTOM
        Spacer(modifier = Modifier.weight(1.2f))

        // AVATAR IMAGE - TOUCHES BOTTOM OF CARD
        AsyncImage(
            model = "https://api.dicebear.com/9.x/avataaars/svg?seed=${speaker.avatarSeed}",
            contentDescription = "Avatar of ${speaker.name}",
            contentScale = ContentScale.Fit
        )
    }
}

/**
 * DETAILS SECTION COMPONENT
 * Right side of the card with personality traits and interests
 */
@Composable
private fun DetailsSection(
    speaker: Speaker,
    isDarkTheme: Boolean,
    modifier: Modifier = Modifier
) {
    val textColor = if (isDarkTheme) {
        speaker.colorClasses.textColor
    } else {
        Color(0xFF374151)
    }

    Column(
        modifier = modifier
            .fillMaxHeight()
            .padding(horizontal = 16.dp)
            .padding(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // PERSONALITY SECTION
        DetailSection(
            title = "Personality",
            items = speaker.personality,
            textColor = textColor
        )

        // INTERESTS SECTION
        DetailSection(
            title = "Interests",
            items = speaker.interests,
            textColor = textColor
        )
    }
}

/**
 * DETAIL SECTION COMPONENT
 * Reusable component for personality and interests sections
 */
@Composable
private fun DetailSection(
    title: String,
    items: List<String>,
    textColor: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = textColor
        )

        items.forEach { item ->
            Text(
                text = item,
                fontSize = 14.sp,
                color = textColor,
                modifier = Modifier.padding(start = 4.dp)
            )
        }
    }
}

/**
 * ACTION BUTTONS SECTION COMPONENT
 * Centered column with action buttons when card is selected
 */
@Composable
private fun ActionButtonsSection(
    speaker: Speaker,
    isDarkTheme: Boolean,
    onStartChat: () -> Unit,
    onSaveSpeaker: () -> Unit,
    modifier: Modifier = Modifier
) {
    val textColor = if (isDarkTheme) {
        speaker.colorClasses.textColor
    } else {
        Color(0xFF374151)
    }

    Column(
        modifier = modifier
            .fillMaxHeight()
            .padding(horizontal = 16.dp)
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // START NEW CHAT BUTTON (FILLED)
        Button(
            onClick = onStartChat,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isDarkTheme) speaker.colorClasses.cardBackground else speaker.colorClasses.borderColor
            ),
            shape = RoundedCornerShape(24.dp)
        ) {
            Text(
                text = "Start New Chat",
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // SAVE SPEAKER BUTTON (PILL STYLE LIKE CLOSE PILL)
        val isSaved = ChatRepository.isSpeakerSaved(speaker.id)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(if (isSaved) (if (isDarkTheme) speaker.colorClasses.cardBackground else speaker.colorClasses.borderColor) else Color.Transparent)
                .border(
                    width = 2.dp,
                    color = if (isDarkTheme) speaker.colorClasses.cardBackground else speaker.colorClasses.borderColor,
                    shape = RoundedCornerShape(24.dp)
                )
                .clickable { onSaveSpeaker() }
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (isSaved) "Saved ✓" else "Save Speaker",
                color = if (isSaved) Color.White else (if (isDarkTheme) speaker.colorClasses.cardBackground else speaker.colorClasses.borderColor),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // WARNING TEXT
        Text(
            text = "You won't see this speaker again if you don't save or start a new chat",
            color = textColor.copy(alpha = 0.6f),
            fontSize = 11.sp,
            textAlign = TextAlign.Center,
            lineHeight = 14.sp
        )
    }
}

/**
 * PREVIEW FUNCTIONS
 */
@Preview(showBackground = true)
@Composable
fun SpeakerCardPreview() {
    QuickSpeakTheme {
        val sampleSpeaker = SpeakerData.getAllSpeakers().first()
        SpeakerCard(
            speaker = sampleSpeaker,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF232323)
@Composable
fun SpeakerCardDarkPreview() {
    QuickSpeakTheme(darkTheme = true) {
        val sampleSpeaker = SpeakerData.getAllSpeakers()[1] // Leonie (French)
        SpeakerCard(
            speaker = sampleSpeaker,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SpeakerCardBlurredPreview() {
    QuickSpeakTheme {
        val sampleSpeaker = SpeakerData.getAllSpeakers()[2] // Marta (Spanish)
        SpeakerCard(
            speaker = sampleSpeaker,
            isBlurred = true,
            modifier = Modifier.padding(16.dp)
        )
    }
}