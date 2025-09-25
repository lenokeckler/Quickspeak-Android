package com.quickspeak.mobile.ui.screens.languages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.quickspeak.mobile.domain.model.Language
import com.quickspeak.mobile.ui.theme.BlackGeneral
import com.quickspeak.mobile.ui.theme.BlueDarkMode

@Composable
fun LanguagesScreen(
    onMenuClick: () -> Unit,
    initialLanguages: List<Language> = listOf(
        Language("English", "US", true),
        Language("Português", "ES"),
        Language("Deutsch", "DE"),
        Language("Español", "CR"),
        Language("Hindi", "IN"),
        Language("Français", "FR")
    )
) {
    var languages by remember { mutableStateOf(initialLanguages) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BlackGeneral)
            .padding(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onMenuClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = BlueDarkMode,
                    modifier = Modifier.size(48.dp)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Languages",
                color = BlueDarkMode,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold)
        }

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
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .clip(CircleShape)
                            .background(Color.DarkGray),
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
                        color = Color.White,
                        fontSize = 14.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp)) // espacio entre grid y botón

        Button(
            onClick = {
                val newLanguages = listOf(
                    Language("漢語", "CN"),
                    Language("Русский", "RU"),
                    Language("العربية", "AE")
                )

                val languagesToAdd = newLanguages.filter { newLang ->
                    languages.none { it.countryCode == newLang.countryCode }
                }

                languages = languages + languagesToAdd
            }
            ,
            colors = ButtonDefaults.buttonColors(containerColor = BlueDarkMode),
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier
                .width(300.dp)
                .height(80.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add Language",
                tint = Color.White,
                modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.width(20.dp))
            Column{
                Text(
                    text = "More Languages",
                    color = Color.White,
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Tap here to add another language",
                    color = Color.White,
                    fontSize = 10.sp
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF1C1C1E)
@Composable
fun LanguagesScreenPreview() {
    LanguagesScreen(onMenuClick = {})
}
