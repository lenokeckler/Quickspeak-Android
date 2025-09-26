package com.quickspeak.mobile.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = RedDarkMode,
    secondary = BlueDarkMode,
    tertiary = YellowDarkMode,
    background = BlackGeneral,
    surface = GrayDarkMode,
    onPrimary = WhiteGeneral,
    onSecondary = BlackGeneral,
    onTertiary = BlackGeneral,
    onBackground = WhiteGeneral,
    onSurface = WhiteGeneral,
    primaryContainer = PurpleDarkMode,
    onPrimaryContainer = WhiteGeneral,
    secondaryContainer = CyanDarkMode,
    onSecondaryContainer = BlackGeneral
)

private val LightColorScheme = lightColorScheme(
    primary = RedLightMode,
    secondary = BlueLightMode,
    tertiary = YellowLightMode,
    background = WhiteGeneral,
    surface = GrayLightMode,
    onPrimary = WhiteGeneral,
    onSecondary = WhiteGeneral,
    onTertiary = BlackGeneral,
    onBackground = BlackGeneral,
    onSurface = BlackGeneral,
    primaryContainer = PurpleLightMode,
    onPrimaryContainer = WhiteGeneral,
    secondaryContainer = CyanLightMode,
    onSecondaryContainer = BlackGeneral
)

@Composable
fun QuickSpeakTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}