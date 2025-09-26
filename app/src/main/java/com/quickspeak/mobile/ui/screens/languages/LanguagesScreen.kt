package com.quickspeak.mobile.ui.screens.languages

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
fun LanguagesScreen(
    onMenuClick: () -> Unit,
    onAddLanguagesClick: () -> Unit
) {
    val languages by remember { derivedStateOf { LanguageRepository.learningLanguages } }
    var selectedLanguage by remember { mutableStateOf<Language?>(null) }
    var showLanguageOptionsDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        LanguagesTopAppBar(
            title = "Languages",
            onMenuClick = onMenuClick
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Grid de banderas (3 por fila)
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(24.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(languages) { language ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                selectedLanguage = language
                                showLanguageOptionsDialog = true
                            }
                    ) {
                        Box(
                            modifier = Modifier
                                .size(72.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.surface)
                                .then(
                                    if (language.isNative) {
                                        Modifier.border(
                                            width = 3.dp,
                                            color = MaterialTheme.colorScheme.primary,
                                            shape = CircleShape
                                        )
                                    } else Modifier
                                ),
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

                        if (language.isNative) {
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "Native",
                                color = MaterialTheme.colorScheme.primary,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = onAddLanguagesClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                shape = RoundedCornerShape(28.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
                    .height(56.dp),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 8.dp
                )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Language",
                        tint = MaterialTheme.colorScheme.onTertiary,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Add More Languages",
                        color = MaterialTheme.colorScheme.onTertiary,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }

    if (showLanguageOptionsDialog && selectedLanguage != null) {
        LanguageOptionsDialog(
            language = selectedLanguage!!,
            onSetAsNative = {
                LanguageRepository.setAsNativeLanguage(selectedLanguage!!)
                showLanguageOptionsDialog = false
                selectedLanguage = null
            },
            onRemove = {
                LanguageRepository.removeLanguageFromLearning(selectedLanguage!!)
                showLanguageOptionsDialog = false
                selectedLanguage = null
            },
            onDismiss = {
                showLanguageOptionsDialog = false
                selectedLanguage = null
            }
        )
    }
}

@Composable
private fun LanguagesTopAppBar(
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
private fun LanguageOptionsDialog(
    language: Language,
    onSetAsNative: () -> Unit,
    onRemove: () -> Unit,
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
                        .border(
                            width = 2.dp,
                            color = MaterialTheme.colorScheme.onSurface,
                            shape = RoundedCornerShape(20.dp)
                        )
                        .clickable { onDismiss() }
                        .padding(horizontal = 12.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Close",
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Close",
                        color = MaterialTheme.colorScheme.onSurface,
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
                                .fillMaxSize()
                                .background(
                                    MaterialTheme.colorScheme.background.copy(
                                        alpha = 0.2f
                                    )
                                )
                                .then(
                                    if (language.isNative) {
                                        Modifier.border(
                                            width = 3.dp,
                                            color = MaterialTheme.colorScheme.primary,
                                            shape = CircleShape
                                        )
                                    } else Modifier
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

                    if (language.isNative) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Native Language",
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Primary CTA - Set as Native or Already Native indicator
                    if (language.isNative) {
                        Button(
                            onClick = { /* Do nothing - already native */ },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                                contentColor = MaterialTheme.colorScheme.primary
                            ),
                            shape = RoundedCornerShape(28.dp),
                            enabled = false,
                            modifier = Modifier
                                .fillMaxWidth(0.84f)
                                .height(56.dp)
                        ) {
                            Text(
                                text = "This is your Native Language",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            )
                        }
                    } else {
                        Button(
                            onClick = onSetAsNative,
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
                                text = "Set as Native Language",
                                color = MaterialTheme.colorScheme.onSecondary,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Remove button
                        OutlinedButton(
                            onClick = onRemove,
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = MaterialTheme.colorScheme.error
                            ),
                            border = BorderStroke(
                                width = 2.dp,
                                color = MaterialTheme.colorScheme.error
                            ),
                            shape = RoundedCornerShape(28.dp),
                            modifier = Modifier
                                .fillMaxWidth(0.84f)
                                .height(48.dp)
                        ) {
                            Text(
                                text = "Remove Language",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Supporting text (centered, subtle)
                    if (language.isNative) {
                        Text(
                            text = "To remove this language, first set another " +
                                    "language as your native language.",
                            color = MaterialTheme.colorScheme.onSurface.copy(
                                alpha = 0.7f
                            ),
                            fontSize = 13.sp,
                            textAlign = TextAlign.Center
                        )
                    } else {
                        Text(
                            text = "Setting this as your native language will " +
                                    "remove native status from your current native language.",
                            color = MaterialTheme.colorScheme.onSurface.copy(
                                alpha = 0.85f
                            ),
                            fontSize = 13.sp,
                            textAlign = TextAlign.Center
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF1C1C1E)
@Composable
fun LanguagesScreenPreview() {
    MaterialTheme {
        LanguagesScreen(
            onMenuClick = {},
            onAddLanguagesClick = {}
        )
    }
}
