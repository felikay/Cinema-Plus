package com.example.cinemaplus.ui.theme

import android.app.Activity
import android.os.Build
import android.view.View
import android.view.WindowInsetsController
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = PrussianBlue, // Using Prussian Blue as primary color for dark mode
    secondary = OxfordBlue,
    tertiary = VividSkyBlue,
    // Add other colors as necessary
)

private val LightColorScheme = lightColorScheme(
    primary = ColumbiaBlue, // Using Columbia Blue as primary color for light mode
    secondary = AliceBlue,
    tertiary = VividSkyBlue
)

@Composable
fun CinemaPlusTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
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

    val context = LocalContext.current
    val window = (context as? Activity)?.window
    val view = LocalView.current

    if (!view.isInEditMode) {
        SideEffect {
            window?.let { win ->
                // Set the status bar color based on the theme
                win.statusBarColor = if (darkTheme) PrussianBlue.toArgb() else ColumbiaBlue.toArgb()

                // Handle status bar icons appearance based on the theme and Android version
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    win.insetsController?.setSystemBarsAppearance(
                        if (darkTheme) 0 else WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                        WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                    )
                } else {
                    @Suppress("DEPRECATION")
                    win.decorView.systemUiVisibility = if (darkTheme) 0 else View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                }
            }
        }
    }
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}