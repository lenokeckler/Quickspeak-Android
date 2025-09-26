package com.quickspeak.mobile.ui.screens.languages

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.quickspeak.mobile.domain.model.Language
import com.quickspeak.mobile.data.LanguageRepository

@Composable
fun AddLanguagesScreen(
    onBackClick: () -> Unit
) {
    var selectedLanguage by remember { mutableStateOf<Language?>(null) }
    var showConfirmationDialog by remember { mutableStateOf(false) }
    val availableLanguages by remember { derivedStateOf { LanguageRepository.availableLanguages } }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        AddLanguagesTopAppBar(
            title = "Add Languages",
            onBackClick = onBackClick
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(24.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(availableLanguages) { language ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                selectedLanguage = language
                                showConfirmationDialog = true
                            }
                    ) {
                        Box(
                            modifier = Modifier
                                .size(72.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.surface),
                            contentAlignment = Alignment.Center
                        ) {
                            AsyncImage(
                                model = language.flagUrl(style = "flat", size = 64),
                                contentDescription = "${language.name} flag",
                                modifier = Modifier
                                    .size(102.dp)
                                    .clip(CircleShape),
                                contentScale = ContentScale.Crop
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = language.name,
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }

    if (showConfirmationDialog && selectedLanguage != null) {
        ConfirmAddLanguageDialog(
            language = selectedLanguage!!,
            onConfirm = {
                LanguageRepository.addLanguageToLearning(selectedLanguage!!)
                showConfirmationDialog = false
                selectedLanguage = null
            },
            onDismiss = {
                showConfirmationDialog = false
                selectedLanguage = null
            }
        )
    }
}

@Composable
private fun AddLanguagesTopAppBar(
    title: String,
    onBackClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 28.dp)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onBackClick
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.size(48.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = title,
            color = MaterialTheme.colorScheme.secondary,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ConfirmAddLanguageDialog(
    language: Language,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    BasicAlertDialog(
        onDismissRequest = onDismiss
    ) {
        Surface(
            shape = RoundedCornerShape(28.dp),
            color = MaterialTheme.colorScheme.background,
            tonalElevation = 8.dp,
            modifier = Modifier
                .widthIn(min = 280.dp, max = 360.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                // Close pill (top-left)
                Row(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .clip(RoundedCornerShape(20.dp))
                        .border(  // ADD THIS: Outline stroke
                            width = 2.dp,
                            color = MaterialTheme.colorScheme.onSurface,  // White/bright outline for dark theme
                            shape = RoundedCornerShape(20.dp)
                        )
                        .clickable { onDismiss() }  // Keep clickable
                        .padding(horizontal = 12.dp, vertical = 6.dp),  // Slightly tighter padding for outline
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Close",
                        tint = MaterialTheme.colorScheme.onSurface,  // CHANGE: Match outline color (no onSecondaryContainer)
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Close",
                        color = MaterialTheme.colorScheme.onSurface,  // CHANGE: Match outline color
                        style = MaterialTheme.typography.labelSmall
                    )
                }

                // Centered content
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(16.dp))

                    // Flag
                    Surface(
                        modifier = Modifier.size(112.dp),
                        shape = CircleShape,
                        shadowElevation = 8.dp,
                        tonalElevation = 4.dp
                    ) {
                        Box(
                            modifier = Modifier
                                .size(112.dp)
                                .clip(CircleShape)
                                .background(
                                    MaterialTheme.colorScheme.background.copy(
                                        alpha = 0.2f
                                    )
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            AsyncImage(
                                model = language.flagUrl(style = "flat", size = 128),
                                contentDescription = "${language.name} flag",
                                modifier = Modifier
                                    .size(128.dp)
                                    .clip(CircleShape),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Title (large, centered)
                    Text(
                        text = language.name,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 42.sp,
                        lineHeight = 42.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Primary CTA
                    Button(
                        onClick = onConfirm,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondary
                        ),
                        shape = RoundedCornerShape(28.dp),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 6.dp
                        ),
                        modifier = Modifier
                            .fillMaxWidth(0.84f)
                            .height(56.dp)
                    ) {
                        Text(
                            text = "Add to Language List",
                            color = MaterialTheme.colorScheme.onSecondary,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Supporting text (centered, subtle)
                    Text(
                        text = "This will allow you to start learning " +
                                "${language.name}.",
                        color = MaterialTheme.colorScheme.onSurface.copy(
                            alpha = 0.85f
                        ),
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "If you wish to make this your native language " +
                                "you must add it to your language list.",
                        color = MaterialTheme.colorScheme.onSurface.copy(
                            alpha = 0.7f
                        ),
                        fontSize = 13.sp,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF1C1C1E)
@Composable
fun AddLanguagesScreenPreview() {
    MaterialTheme {
        AddLanguagesScreen(onBackClick = {})
    }
}