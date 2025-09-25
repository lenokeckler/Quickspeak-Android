package com.quickspeak.mobile.ui.screens.languages

import androidx.compose.runtime.*

enum class LanguagesScreenRoute {
    LANGUAGES,
    ADD_LANGUAGES
}

@Composable
fun LanguagesNavigation(
    onMenuClick: () -> Unit
) {
    var currentRoute by remember { mutableStateOf(LanguagesScreenRoute.LANGUAGES) }

    when (currentRoute) {
        LanguagesScreenRoute.LANGUAGES -> {
            LanguagesScreen(
                onMenuClick = onMenuClick,
                onAddLanguagesClick = {
                    currentRoute = LanguagesScreenRoute.ADD_LANGUAGES
                }
            )
        }
        LanguagesScreenRoute.ADD_LANGUAGES -> {
            AddLanguagesScreen(
                onBackClick = {
                    currentRoute = LanguagesScreenRoute.LANGUAGES
                }
            )
        }
    }
}