package com.quickspeak.mobile.ui.screens.speakers

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.quickspeak.mobile.data.ChatRepository
import com.quickspeak.mobile.domain.model.Speaker
import com.quickspeak.mobile.domain.model.SpeakerData
import com.quickspeak.mobile.domain.model.Language
import com.quickspeak.mobile.data.LanguageRepository
import com.quickspeak.mobile.ui.components.SpeakerCard
import com.quickspeak.mobile.ui.theme.*

/**
 * LANGUAGE FILTER DATA CLASS
 * Represents a language filter option with its active state and styling
 */
data class LanguageFilter(
    val name: String,
    val flag: String,
    val isActive: Boolean = false
)

/**
 * SPEAKER CATALOG SCREEN
 * Main catalog screen showing all available speakers with filtering capabilities
 * Based on the web version but adapted for Android/Jetpack Compose
 *
 * Features:
 * - Gradient background (dark/light theme aware)
 * - Large title with cyan accent color
 * - Language filter chips
 * - Grid layout of speaker cards
 * - Hover effect simulation for cards
 * - Back navigation
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpeakerCatalogScreen(
    onBackClick: () -> Unit = {},
    onSpeakerClick: (Speaker) -> Unit = {}
) {
    val isDarkTheme = isSystemInDarkTheme()

    // STATE MANAGEMENT - Get filters from user's learning languages
    var languageFilters by remember {
        mutableStateOf(getLanguageFiltersFromRepository())
    }

    var hoveredSpeakerId by remember {
        mutableIntStateOf(-1)
    }

    var selectedSpeakerId by remember {
        mutableIntStateOf(-1)
    }

    // COMPUTED VALUES - Show speakers for languages the user has, filtered by active selections and excluding saved speakers
    val filteredSpeakers = remember(languageFilters) {
        val activeLanguages = languageFilters.filter { it.isActive }.map { it.name }
        val savedSpeakerIds = ChatRepository.savedSpeakers.map { it.speakerId }.toSet()

        val availableSpeakers = SpeakerData.getAllSpeakers().filter { speaker ->
            // Include speakers for any language the user is learning (including native)
            // BUT exclude speakers that are already saved
            LanguageRepository.learningLanguages.any { userLang ->
                userLang.name == speaker.language
            } && !savedSpeakerIds.contains(speaker.id)
        }

        if (activeLanguages.isEmpty()) {
            availableSpeakers
        } else {
            availableSpeakers.filter { speaker ->
                activeLanguages.contains(speaker.language)
            }
        }
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
                            Color(0x7C7C01F6)
                        )
                    )
                }
            )
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            // TOP BAR WITH BACK BUTTON AND TITLE
            SpeakerCatalogTopBar(
                onBackClick = onBackClick,
                isDarkTheme = isDarkTheme
            )

            Spacer(modifier = Modifier.height(24.dp))

            // LANGUAGE FILTER CHIPS - WRAPPING LAYOUT
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                languageFilters.forEach { filter ->
                    LanguageFilterChip(
                        filter = filter,
                        isDarkTheme = isDarkTheme,
                        onToggle = { languageName ->
                            languageFilters = languageFilters.map { lang ->
                                if (lang.name == languageName) {
                                    lang.copy(isActive = !lang.isActive)
                                } else {
                                    lang
                                }
                            }
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // SPEAKERS GRID
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 300.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(bottom = 32.dp)
            ) {
                items(filteredSpeakers) { speaker ->
                    SpeakerCard(
                        speaker = speaker,
                        isBlurred = selectedSpeakerId != -1 && selectedSpeakerId != speaker.id,
                        isSelected = selectedSpeakerId == speaker.id,
                        onCardHover = { isHovering ->
                            if (selectedSpeakerId == -1) { // Only hover if no card is selected
                                hoveredSpeakerId = if (isHovering) speaker.id else -1
                            }
                        },
                        onCardClick = {
                            selectedSpeakerId = if (selectedSpeakerId == speaker.id) -1 else speaker.id
                        },
                        onStartChat = { onSpeakerClick(speaker) },
                        onSaveSpeaker = {
                            ChatRepository.addSavedSpeaker(speaker)
                            selectedSpeakerId = -1 // Reset selection after save
                        }
                    )
                }
            }
        }
    }
}

/**
 * TOP BAR COMPONENT
 * Contains back button and title aligned like other screens
 */
@Composable
private fun SpeakerCatalogTopBar(
    onBackClick: () -> Unit,
    isDarkTheme: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 28.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onBackClick
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = if (isDarkTheme) CyanDarkMode else CyanLightMode,
                modifier = Modifier.size(48.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        // TITLE ALIGNED WITH BACK BUTTON
        Text(
            text = "Speaker Catalog",
            color = if (isDarkTheme) CyanDarkMode else CyanLightMode,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

/**
 * LANGUAGE FILTER CHIP COMPONENT
 * Individual filter button for languages
 * Matches the web version design with dashed borders for inactive state
 */
@Composable
private fun LanguageFilterChip(
    filter: LanguageFilter,
    isDarkTheme: Boolean,
    onToggle: (String) -> Unit
) {
    val backgroundColor = if (filter.isActive) {
        if (isDarkTheme) RedDarkMode else RedLightMode
    } else {
        Color.Transparent
    }

    val textColor = if (filter.isActive) {
        if (isDarkTheme) WhiteGeneral else BlackGeneral
    } else {
        if (isDarkTheme) Color(0xFF9CA3AF) else Color(0xFF6B7280)
    }

    val borderColor = if (filter.isActive) {
        backgroundColor
    } else {
        if (isDarkTheme) Color(0xFF6B7280) else Color(0xFF9CA3AF)
    }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(25.dp))
            .background(backgroundColor)
            .clickable { onToggle(filter.name) }
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = filter.name,
                color = textColor,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )

            // FLAG EMOJI IN CIRCLE
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(
                        color = if (filter.isActive) {
                            if (isDarkTheme) Color.Black.copy(alpha = 0.2f) else Color.White.copy(alpha = 0.5f)
                        } else {
                            Color(0xFF6B7280)
                        },
                        shape = RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = filter.flag,
                    fontSize = 12.sp
                )
            }
        }
    }
}

/**
 * DATA HELPER FUNCTIONS
 */
private fun getLanguageFiltersFromRepository(): List<LanguageFilter> {
    return LanguageRepository.learningLanguages
        .mapNotNull { userLanguage ->
            val flagEmoji = getLanguageFlagEmoji(userLanguage.name)

            // Only include if we have speakers for this language
            if (SpeakerData.getAllSpeakers().any { it.language == userLanguage.name }) {
                LanguageFilter(
                    name = userLanguage.name,
                    flag = flagEmoji,
                    isActive = !userLanguage.isNative // Native language starts inactive, others start active
                )
            } else {
                null
            }
        }
}


/**
 * Gets flag emoji for speaker language names
 */
private fun getLanguageFlagEmoji(languageName: String): String {
    return when (languageName) {
        "German" -> "🇩🇪"
        "French" -> "🇫🇷"
        "Spanish" -> "🇪🇸"
        "Hindi" -> "🇮🇳"
        "Italian" -> "🇮🇹"
        "Portuguese" -> "🇧🇷"
        "Japanese" -> "🇯🇵"
        "Arabic" -> "🇦🇪"
        "Russian" -> "🇷🇺"
        "English" -> "🇬🇧"
        "Irish" -> "🇮🇪"
        "Chinese" -> "🇨🇳"
        else -> "🌍"
    }
}

/**
 * PREVIEW FUNCTIONS
 */
@Preview(showBackground = true)
@Composable
fun SpeakerCatalogScreenPreview() {
    QuickSpeakTheme {
        SpeakerCatalogScreen()
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF232323)
@Composable
fun SpeakerCatalogScreenDarkPreview() {
    QuickSpeakTheme(darkTheme = true) {
        SpeakerCatalogScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun LanguageFilterChipPreview() {
    QuickSpeakTheme {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            LanguageFilterChip(
                filter = LanguageFilter("French", "🇫🇷", isActive = true),
                isDarkTheme = false,
                onToggle = {}
            )
            LanguageFilterChip(
                filter = LanguageFilter("Spanish", "🇪🇸", isActive = false),
                isDarkTheme = false,
                onToggle = {}
            )
        }
    }
}