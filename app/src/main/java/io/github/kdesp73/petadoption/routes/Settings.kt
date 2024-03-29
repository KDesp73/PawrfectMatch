package io.github.kdesp73.petadoption.routes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.kdesp73.petadoption.Theme
import io.github.kdesp73.petadoption.ThemeName
import io.github.kdesp73.petadoption.enums.CustomAlignment
import io.github.kdesp73.petadoption.ui.components.Dropdown
import io.github.kdesp73.petadoption.ui.components.DropdownItem
import io.github.kdesp73.petadoption.ui.components.HalfButton
import io.github.kdesp73.petadoption.ui.utils.VerticalScaffold

@Composable
fun Settings(){
    var theme: Theme
    var language: String

    val themes = listOf<DropdownItem>(
        DropdownItem("Light") {
            theme = Theme.getTheme(ThemeName.LIGHT)!!
        },
        DropdownItem("Dark") {
            theme = Theme.getTheme(ThemeName.DARK)!!
        },
        DropdownItem("Auto") {
            theme = Theme.getTheme(ThemeName.AUTO)!!
        },
    )

    val languages = listOf<DropdownItem>(
        DropdownItem("Greek"){
            language = "gr"
        },
        DropdownItem("English"){
            language = "en"
        }
    )


    VerticalScaffold(
        modifier = Modifier
            .padding(6.dp),
        top = {
            Row (
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            ){
                Dropdown(title = "Theme", items = themes)
                Dropdown(title = "Language", items = languages)
            }
        },
        center = {},
        bottom = {
            HalfButton (
                text = "Apply",
                icon = Icons.Filled.Check
            ){
                // TODO: apply query
            }
        },
        bottomAlignment = CustomAlignment.END
    )
}

@Preview
@Composable
fun SettingsPreview(){
    Settings()
}