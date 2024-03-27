package io.github.kdesp73.petadoption.routes

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import io.github.kdesp73.petadoption.Theme
import io.github.kdesp73.petadoption.ThemeName
import io.github.kdesp73.petadoption.ui.components.Dropdown
import io.github.kdesp73.petadoption.ui.components.DropdownItem
import org.intellij.lang.annotations.Language

@Composable
fun Settings(){
    var theme: Theme
    var language: Language

    val themes = listOf<DropdownItem>(
        DropdownItem("Light") {
            theme = Theme.getTheme(ThemeName.LIGHT)!!
        },
        DropdownItem("Dark") {
            theme = Theme.getTheme(ThemeName.DARK)!!
        },
        DropdownItem("Example") {
            theme = Theme.getTheme(ThemeName.EXAMPLE)!!
        },
    )

    val languages = listOf<DropdownItem>(
        DropdownItem("Greek"){

        },
        DropdownItem("English"){

        }
    )


    Row {
        Dropdown(title = "Theme", items = themes)
        Dropdown(title = "Language", items = languages)
    }
}

@Preview
@Composable
fun SettingsPreview(){
    Settings()
}