package com.quickspeak.mobile.ui.screens.speakers

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.ui.graphics.graphicsLayer
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
import com.quickspeak.mobile.data.UserRepository
import com.quickspeak.mobile.ui.components.SpeakerCard
import com.quickspeak.mobile.ui.theme.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


data class LanguageFilter(
    val name: String,
    val flag: String,
    val isActive: Boolean = false
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpeakerCatalogScreen(
    onBackClick: () -> Unit = {},
    onSpeakerClick: (Speaker) -> Unit = {}
) {
    val systemDarkTheme = isSystemInDarkTheme()
    val isDarkTheme = UserRepository.getEffectiveDarkMode(systemDarkTheme)
    val scope = rememberCoroutineScope()

    var languageFilters by remember { mutableStateOf(getLanguageFiltersFromRepository()) }
    var selectedSpeakerId by remember { mutableIntStateOf(-1) }

    var displayedSpeakers by remember { mutableStateOf<List<Speaker>>(emptyList()) }
    var fadingOutSpeakerIds by remember { mutableStateOf<Set<Int>>(emptySet()) }

    val sourceSpeakers = remember(languageFilters) {
        val activeLanguages = languageFilters.filter { it.isActive }.map { it.name }
        val savedSpeakerIds = ChatRepository.savedSpeakers.map { it.speakerId }.toSet()

        val availableSpeakers = SpeakerData.getAllSpeakers().filter { speaker ->
            LanguageRepository.learningLanguages.any { userLang -> userLang.name == speaker.language } &&
            !savedSpeakerIds.contains(speaker.id)
        }

        if (activeLanguages.isEmpty()) {
            availableSpeakers
        } else {
            availableSpeakers.filter { speaker -> activeLanguages.contains(speaker.language) }
        }
    }

    LaunchedEffect(sourceSpeakers) {
        val fadingSpeakers = displayedSpeakers.filter { it.id in fadingOutSpeakerIds }
        val newSpeakers = sourceSpeakers.filter { it.id !in fadingOutSpeakerIds }
        displayedSpeakers = (fadingSpeakers + newSpeakers).distinctBy { it.id }
    }

    fun handleDeselection(deselectedId: Int) {
        if (deselectedId == -1) return

        val speakerToAnimate = displayedSpeakers.find { it.id == deselectedId }
        if (speakerToAnimate != null && ChatRepository.isSpeakerSaved(speakerToAnimate.id)) {
            fadingOutSpeakerIds = fadingOutSpeakerIds + speakerToAnimate.id
            scope.launch {
                delay(400) // Animation duration
                displayedSpeakers = displayedSpeakers.filter { it.id != speakerToAnimate.id }
                fadingOutSpeakerIds = fadingOutSpeakerIds - speakerToAnimate.id
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = if (isDarkTheme) {
                    Brush.verticalGradient(colors = listOf(BlackGeneral, Color(0xFF2C006E)))
                } else {
                    Brush.verticalGradient(colors = listOf(WhiteGeneral, Color(0x7C7C01F6)))
                }
            )
            .clickable( // Clickable background to handle deselection
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                handleDeselection(selectedSpeakerId)
                selectedSpeakerId = -1
            }
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            SpeakerCatalogTopBar(onBackClick = onBackClick, isDarkTheme = isDarkTheme)
            Spacer(modifier = Modifier.height(24.dp))
            LanguageFilters(
                languageFilters = languageFilters,
                isDarkTheme = isDarkTheme,
                onToggle = { languageName ->
                    languageFilters = languageFilters.map { 
                        if (it.name == languageName) it.copy(isActive = !it.isActive) else it
                    }
                }
            )
            Spacer(modifier = Modifier.height(32.dp))

            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 300.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(bottom = 32.dp)
            ) {
                items(
                    items = displayedSpeakers,
                    key = { it.id }
                ) { speaker ->
                    val isFadingOut = speaker.id in fadingOutSpeakerIds
                    val animatedAlpha by animateFloatAsState(
                        targetValue = if (isFadingOut) 0f else 1f,
                        animationSpec = tween(400),
                        label = "alpha"
                    )
                    val animatedScale by animateFloatAsState(
                        targetValue = if (isFadingOut) 0.9f else 1f,
                        animationSpec = tween(400),
                        label = "scale"
                    )

                    val isBlurred = selectedSpeakerId != -1 && selectedSpeakerId != speaker.id

                    SpeakerCard(
                        modifier = Modifier
                            .graphicsLayer {
                                alpha = if (isBlurred) 0.6f else animatedAlpha
                                scaleX = animatedScale
                                scaleY = animatedScale
                            }
                            .animateContentSize(animationSpec = tween(300)),
                        speaker = speaker,
                        isBlurred = isBlurred,
                        isSelected = selectedSpeakerId == speaker.id,
                        onCardClick = {
                            val previouslySelectedId = selectedSpeakerId
                            val newSelectedId = if (previouslySelectedId == speaker.id) -1 else speaker.id

                            if (previouslySelectedId != newSelectedId) {
                                handleDeselection(previouslySelectedId)
                            }
                            
                            selectedSpeakerId = newSelectedId
                        },
                        onStartChat = { onSpeakerClick(speaker) },
                        onSaveSpeaker = {
                            ChatRepository.addSavedSpeaker(speaker)
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun LanguageFilters(
    languageFilters: List<LanguageFilter>,
    isDarkTheme: Boolean,
    onToggle: (String) -> Unit
) {
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
                onToggle = onToggle
            )
        }
    }
}

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
        IconButton(onClick = onBackClick) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = if (isDarkTheme) BlueDarkMode else BlueLightMode,
                modifier = Modifier.size(48.dp)
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = "Speaker Catalog",
            color = if (isDarkTheme) BlueDarkMode else BlueLightMode,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

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

    Surface(
        modifier = Modifier
            .clip(RoundedCornerShape(25.dp))
            .clickable { onToggle(filter.name) }
            .animateContentSize(animationSpec = tween(300)),
        shape = RoundedCornerShape(25.dp),
        color = backgroundColor,
        border = BorderStroke(1.dp, borderColor)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(
                text = filter.name,
                color = textColor,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        color = if (filter.isActive) {
                            if (isDarkTheme) Color.Black.copy(alpha = 0.2f) else Color.White.copy(alpha = 0.5f)
                        } else {
                            if (isDarkTheme) Color(0xFF374151) else Color(0xFFE5E7EB)
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = filter.flag,
                    fontSize = 12.sp,
                    color = if (filter.isActive) Color.White else Color.Black
                )
            }
        }
    }
}

private fun getLanguageFiltersFromRepository(): List<LanguageFilter> {
    return LanguageRepository.learningLanguages
        .mapNotNull { userLanguage ->
            val flagEmoji = getLanguageFlagEmoji(userLanguage.name)
            if (SpeakerData.getAllSpeakers().any { it.language == userLanguage.name }) {
                LanguageFilter(
                    name = userLanguage.name,
                    flag = flagEmoji,
                    isActive = !userLanguage.isNative
                )
            } else {
                null
            }
        }
}

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