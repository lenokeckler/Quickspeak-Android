// MainActivity.kt
package com.quickspeak.mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import com.quickspeak.mobile.data.ChatRepository
import com.quickspeak.mobile.domain.model.Speaker
import com.quickspeak.mobile.ui.components.navigation.NavigationDrawerContent
import com.quickspeak.mobile.ui.screens.chat.ChatScreen
import com.quickspeak.mobile.ui.screens.languages.LanguagesNavigation
import com.quickspeak.mobile.ui.screens.test.AvatarScreen
import com.quickspeak.mobile.ui.screens.speakers.SpeakerHome
import com.quickspeak.mobile.ui.screens.speakers.SpeakerCatalogScreen
import com.quickspeak.mobile.ui.screens.profile.ProfileScreen
import com.quickspeak.mobile.ui.screens.settings.SettingsScreen
import com.quickspeak.mobile.ui.theme.QuickSpeakTheme
import kotlinx.coroutines.launch

/**
 * MAIN ACTIVITY
 * This is the entry point of your Android app.
 * Every Android app needs one Activity to start.
 * ComponentActivity is the modern version that supports Jetpack Compose.
 *
 * Now clean and focused only on app initialization!
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // setContent{} tells Android: "Use Jetpack Compose instead of XML layouts"
        setContent {
            // Apply our custom theme (colors, fonts, etc.)
            QuickSpeakTheme {
                // Start the main app UI
                QuickSpeakApp()
            }
        }
    }
}

/**
 * MAIN APP CONTAINER
 * This function manages the overall app structure:
 * - Navigation drawer (sidebar)
 * - Main content area
 * - Handles opening/closing the drawer
 * - Navigation between different screens
 *
 * @OptIn tells Kotlin: "Yes, I know this is experimental API"
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuickSpeakApp() {
    // STATE MANAGEMENT
    // drawerState: Controls whether sidebar is open or closed
    // the remember keeps this state alive even when UI redraws (recomposes)
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    // currentScreen: Tracks which screen is currently displayed
    var currentScreen by remember { mutableStateOf("Speakers") }

    // speakerScreen: Tracks which speaker screen is currently displayed (Home, Catalog, or Chat)
    var speakerScreen by remember { mutableStateOf("Home") }

    // currentSpeaker: Tracks the currently selected speaker for chat
    var currentSpeaker by remember { mutableStateOf<Speaker?>(null) }

    // scope: Needed to launch animations (like drawer sliding)
    // Coroutines handle animations without blocking the UI thread
    val scope = rememberCoroutineScope()

    // NAVIGATION DRAWER SETUP
    // ModalNavigationDrawer creates the sliding sidebar behavior
    // "Modal" means it appears on top with a dark overlay behind it
    ModalNavigationDrawer(
        drawerState = drawerState,           // Connect our state to the drawer
        drawerContent = {                    // What shows inside the sidebar
            NavigationDrawerContent(
                currentScreen = currentScreen,
                onNavigate = { screen ->
                    currentScreen = screen
                    // Reset speaker screen to Home when navigating away
                    if (screen != "Speakers") {
                        speakerScreen = "Home"
                        currentSpeaker = null
                    }
                    scope.launch {
                        drawerState.close()
                    }
                }
            )
        },
        content = {                          // Main app content
            // BACK NAVIGATION HANDLING
            BackHandler(
                enabled = when (currentScreen) {
                    "Speakers" -> speakerScreen != "Home"
                    else -> false
                }
            ) {
                when (currentScreen) {
                    "Speakers" -> {
                        when (speakerScreen) {
                            "Catalog" -> speakerScreen = "Home"
                            "Chat" -> {
                                speakerScreen = "Home"
                                currentSpeaker = null
                            }
                        }
                    }
                }
            }

            when (currentScreen) {
                "Speakers" -> {
                    when (speakerScreen) {
                        "Home" -> {
                            SpeakerHome(
                                onMenuClick = {
                                    scope.launch {
                                        drawerState.open()
                                    }
                                },
                                onSpeakerCatalogClick = {
                                    speakerScreen = "Catalog"
                                },
                                onChatClick = { speaker ->
                                    currentSpeaker = speaker
                                    speakerScreen = "Chat"
                                }
                            )
                        }
                        "Catalog" -> {
                            SpeakerCatalogScreen(
                                onBackClick = {
                                    speakerScreen = "Home"
                                },
                                onSpeakerClick = { speaker ->
                                    currentSpeaker = speaker
                                    speakerScreen = "Chat"
                                }
                            )
                        }
                        "Chat" -> {
                            currentSpeaker?.let { speaker ->
                                ChatScreen(
                                    speaker = speaker,
                                    onMenuClick = {
                                        scope.launch {
                                            drawerState.open()
                                        }
                                    },
                                    onBackClick = {
                                        speakerScreen = "Home"
                                        currentSpeaker = null
                                    }
                                )
                            }
                        }
                    }
                }
                "Languages" -> {
                    LanguagesNavigation(
                        onMenuClick = {
                            scope.launch {
                                drawerState.open()
                            }
                        }
                    )
                }
                "Profile" -> {
                    ProfileScreen(
                        onBackClick = {
                            currentScreen = "Speakers"
                        },
                        onSettingsClick = {
                            currentScreen = "Settings"
                        }
                    )
                }
                "Settings" -> {
                    SettingsScreen(
                        onBackClick = {
                            currentScreen = "Speakers"
                        }
                    )
                }
                else -> {
                    AvatarScreen(
                        onMenuClick = {              // When hamburger menu is clicked...
                            scope.launch {           // Start an animation...
                                drawerState.open()   // ...to open the drawer
                            }
                        }
                    )
                }
            }
        }
    )
}


/**
 * PREVIEW FUNCTION
 * For testing the main app in Android Studio's design preview
 */
@Preview(showBackground = true, backgroundColor = 0xFF1C1C1E)
@Composable
fun QuickSpeakAppPreview() {
    QuickSpeakTheme {
        QuickSpeakApp()
    }
}