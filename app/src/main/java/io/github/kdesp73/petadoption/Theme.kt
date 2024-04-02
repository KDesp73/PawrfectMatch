package io.github.kdesp73.petadoption

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import io.github.kdesp73.petadoption.ui.theme.Pink40
import io.github.kdesp73.petadoption.ui.theme.Pink80
import io.github.kdesp73.petadoption.ui.theme.Purple40
import io.github.kdesp73.petadoption.ui.theme.Purple80
import io.github.kdesp73.petadoption.ui.theme.PurpleGrey40
import io.github.kdesp73.petadoption.ui.theme.PurpleGrey80

enum class ThemeName(val label: String) {
    DARK(label = "Dark"),
    LIGHT(label = "Light"),
    AUTO(label = "Auto");
}

sealed class Theme(
    var name: ThemeName,
    val colors: ColorScheme?
) {
    data object Dark : Theme(
        ThemeName.DARK,
        colors = darkColorScheme(
            primary = Purple80,
            secondary = PurpleGrey80,
            tertiary = Pink80
        )
    )

    data object Light : Theme(
        name = ThemeName.LIGHT,
        colors = lightColorScheme(
            primary = Purple40,
            secondary = PurpleGrey40,
            tertiary = Pink40

            /* Other default colors to override
            background = Color(0xFFFFFBFE),
            surface = Color(0xFFFFFBFE),
            onPrimary = Color.White,
            onSecondary = Color.White,
            onTertiary = Color.White,
            onBackground = Color(0xFF1C1B1F),
            onSurface = Color(0xFF1C1B1F),
            */
        )
    )
    
    data object Auto: Theme (
        name = ThemeName.AUTO,
        colors = null
    )
    
    
    companion object{
        fun getTheme(name: String): Theme? {
            val themes = hashMapOf<String, Theme>()
            themes[ThemeName.DARK.label] = Theme.Dark
            themes[ThemeName.LIGHT.label] = Theme.Light
            themes[ThemeName.AUTO.label] = Theme.Auto

            return themes[name]
        }
    }
}

