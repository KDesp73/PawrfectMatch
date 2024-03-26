package io.github.kdesp73.petadoption.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import io.github.kdesp73.petadoption.Theme
import io.github.kdesp73.petadoption.ThemeName

@Composable
fun PetAdoptionTheme(
    theme: ThemeName = ThemeName.DARK,
    darkColorScheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S ->{
            val context = LocalContext.current
            if (darkColorScheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        theme == ThemeName.DARK && darkColorScheme -> Theme.Dark.colors
        theme == ThemeName.EXAMPLE && darkColorScheme -> Theme.Example.colors
        else -> Theme.Light.colors
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkColorScheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}