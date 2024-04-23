package io.github.kdesp73.petadoption.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import io.github.kdesp73.petadoption.Theme
import io.github.kdesp73.petadoption.enums.ThemeName


@Composable
fun AppTheme(
    theme: String = ThemeName.DARK.value,
    darkColorScheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable() () -> Unit
) {
    val colorScheme = when {
        theme == ThemeName.DYNAMIC.value && dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S ->{
            val context = LocalContext.current
            if (darkColorScheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        theme == ThemeName.DARK.value -> Theme.Dark.colors
        theme == ThemeName.LIGHT.value -> Theme.Light.colors
        theme == ThemeName.AUTO.value -> {
            if (darkColorScheme){
                Theme.Dark.colors
            } else {
                Theme.Light.colors
            }
        }
        else -> Theme.Dark.colors
    }

    val textColor = determineTextColor(colorScheme?.background ?: Color.Black)

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            if (colorScheme != null) {
                window.statusBarColor = colorScheme.primary.toArgb()
            }
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkColorScheme
        }
    }

    if (colorScheme != null) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            
            content = {
                CompositionLocalProvider(LocalContentColor provides textColor) {
                    content()
                }
            }
        )
    }
}

fun determineTextColor(backgroundColor: Color): Color {
    // Calculate relative luminance
    val luminance = 0.2126 * backgroundColor.red + 0.7152 * backgroundColor.green + 0.0722 * backgroundColor.blue

    // Calculate contrast ratio against black and white
    val contrastBlack = calculateContrastRatio(luminance, 0.0)
    val contrastWhite = calculateContrastRatio(luminance, 1.0)

    // Determine which contrast ratio is closer to the recommended value
    return if (contrastBlack > contrastWhite) Color.Black else Color.White
}

// Calculate contrast ratio between background and text
fun calculateContrastRatio(luminance1: Double, luminance2: Double): Double {
    val l1 = if (luminance1 > luminance2) luminance1 else luminance2
    val l2 = if (luminance1 > luminance2) luminance2 else luminance1
    return (l1 + 0.05) / (l2 + 0.05)
}