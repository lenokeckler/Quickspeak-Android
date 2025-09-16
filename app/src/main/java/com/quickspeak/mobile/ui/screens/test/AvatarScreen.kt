package com.quickspeak.mobile.ui.screens.test

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.quickspeak.mobile.ui.theme.BlackGeneral
import com.quickspeak.mobile.ui.theme.BlueDarkMode
import com.quickspeak.mobile.ui.theme.GrayDarkMode
import com.quickspeak.mobile.ui.theme.GrayLightMode
import com.quickspeak.mobile.ui.theme.YellowDarkMode
import com.quickspeak.mobile.ui.theme.YellowLightMode
import kotlin.random.Random

/**
 * AVATAR SCREEN
 * This is the main screen with the avatar generator functionality.
 * Now separated into its own file for better organization.
 *
 * @param onMenuClick: A function that gets called when menu button is pressed
 */
@Composable
fun AvatarScreen(
    onMenuClick: () -> Unit
) {
    // AVATAR STATE
    var seedText by remember { mutableStateOf("") }
    var isRandomMode by remember { mutableStateOf(false) }
    var avatarUrl by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    // SCREEN LAYOUT
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BlackGeneral)
    ) {
        // TOP BAR SECTION
        AvatarTopAppBar(
            title = "Avatar Generator",
            onMenuClick = onMenuClick
        )

        // MAIN CONTENT
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // AVATAR DISPLAY SECTION
            AvatarDisplay(
                avatarUrl = avatarUrl,
                isLoading = isLoading,
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // INPUT SECTION
            AvatarInputSection(
                seedText = seedText,
                onSeedTextChange = { seedText = it },
                isRandomMode = isRandomMode,
                onRandomModeChange = { isRandomMode = it }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // GENERATE BUTTON
            GenerateAvatarButton(
                onClick = {
                    isLoading = true
                    val seed = if (isRandomMode) {
                        Random.nextInt(1000000).toString()
                    } else {
                        seedText.ifEmpty { "DefaultUser" }
                    }
                    avatarUrl = "https://api.dicebear.com/9.x/avataaars/svg?seed=$seed"
                    // Set loading to false after a short delay to show the loading state
                    isLoading = false
                }
            )

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

/**
 * TOP APP BAR FOR AVATAR SCREEN
 * Contains the hamburger menu and app title
 */
@Composable
private fun AvatarTopAppBar(
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
                tint = BlueDarkMode,
                modifier = Modifier.size(48.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        // APP TITLE
        Text(
            text = title,
            color = BlueDarkMode,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

/**
 * AVATAR DISPLAY
 * Shows the generated avatar or a placeholder
 */
@Composable
private fun AvatarDisplay(
    avatarUrl: String?,
    isLoading: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier.size(280.dp),
            colors = CardDefaults.cardColors(
                containerColor = GrayDarkMode
            ),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            )
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                when {
                    isLoading -> {
                        CircularProgressIndicator(
                            color = BlueDarkMode,
                            modifier = Modifier.size(48.dp)
                        )
                    }
                    avatarUrl != null -> {
                        AsyncImage(
                            model = avatarUrl,
                            contentDescription = "Generated Avatar",
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(20.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }
                    else -> {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "No avatar",
                                tint = Color.White.copy(alpha = 0.6f),
                                modifier = Modifier.size(64.dp)
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "Generate an avatar!",
                                color = Color.White.copy(alpha = 0.6f),
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * AVATAR INPUT SECTION
 * Contains the seed input field and random mode toggle
 */
@Composable
private fun AvatarInputSection(
    seedText: String,
    onSeedTextChange: (String) -> Unit,
    isRandomMode: Boolean,
    onRandomModeChange: (Boolean) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // RANDOM MODE TOGGLE
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Text(
                text = "Random Mode",
                color = Color.White,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.width(12.dp))
            Switch(
                checked = isRandomMode,
                onCheckedChange = onRandomModeChange,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = BlueDarkMode,
                    checkedTrackColor = BlueDarkMode.copy(alpha = 0.5f)
                )
            )
        }

        // SEED INPUT FIELD
        if (!isRandomMode) {
            OutlinedTextField(
                value = seedText,
                onValueChange = onSeedTextChange,
                label = {
                    Text(
                        text = "Enter a name (optional)",
                        color = Color.White.copy(alpha = 0.7f)
                    )
                },
                placeholder = {
                    Text(
                        text = "e.g., John, Alice, Felix",
                        color = Color.White.copy(alpha = 0.5f)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedBorderColor = BlueDarkMode,
                    unfocusedBorderColor = GrayLightMode,
                    cursorColor = BlueDarkMode
                ),
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )
        }
    }
}

/**
 * GENERATE AVATAR BUTTON
 * Button to trigger avatar generation
 */
@Composable
private fun GenerateAvatarButton(
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp)
            .padding(bottom = 146.dp)
            .height(56.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = YellowDarkMode
        ),
        shape = RoundedCornerShape(28.dp),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 8.dp
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = null,
                tint = BlackGeneral,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "Generate Avatar",
                color = BlackGeneral,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

/**
 * PREVIEW FUNCTIONS
 */
@Preview(showBackground = true, backgroundColor = 0xFF1C1C1E)
@Composable
fun AvatarScreenPreview() {
    MaterialTheme {
        AvatarScreen(onMenuClick = {})
    }
}