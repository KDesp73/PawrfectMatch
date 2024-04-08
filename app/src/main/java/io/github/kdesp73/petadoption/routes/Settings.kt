package io.github.kdesp73.petadoption.routes

import android.app.Activity
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.kdesp73.petadoption.ThemeName
import io.github.kdesp73.petadoption.enums.CustomAlignment
import io.github.kdesp73.petadoption.room.AppDatabase
import io.github.kdesp73.petadoption.ui.components.Dropdown
import io.github.kdesp73.petadoption.ui.components.DropdownItem
import io.github.kdesp73.petadoption.ui.components.HalfButton
import io.github.kdesp73.petadoption.ui.utils.VerticalScaffold

private const val TAG = "Settings"

@Composable
fun Settings(room: AppDatabase?){
    val context = LocalContext.current as Activity
    val settingsDao = room?.settingsDao()

    val theme = remember { mutableStateOf(settingsDao?.getTheme() ?: "Light") }
    val language = remember { mutableStateOf(settingsDao?.getLanguage() ?: "English") }

    val themes = listOf<DropdownItem>(
        DropdownItem("Light") {
            theme.value = ThemeName.LIGHT.label
        },
        DropdownItem("Dark") {
            theme.value = ThemeName.DARK.label
        },
        DropdownItem("Auto") {
            theme.value = ThemeName.AUTO.label
        },
    )

    val languages = listOf<DropdownItem>(
        DropdownItem("English"){
            language.value = "English"
        },
        DropdownItem("Greek"){
            language.value = "Greek"
        }
    )

    val themeSettings = room?.settingsDao()?.getTheme()
    val languageSettings = room?.settingsDao()?.getLanguage()

    VerticalScaffold(
        modifier = Modifier
            .padding(6.dp),
        top = {
            Row (
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            ){
                Dropdown(themeSettings ?: themes[0].name, title = "Theme", items = themes)
                Dropdown(languageSettings ?: languages[0].name, title = "Language", items = languages)
            }
        },
        center = {},
        bottom = {
            HalfButton (
                text = "Apply",
                icon = Icons.Filled.Check
            ){

                if(settingsDao?.getTheme() != theme.value){
                    context.recreate()
                }

                settingsDao?.insert(
                    io.github.kdesp73.petadoption.room.Settings(
                        theme = theme.value,
                        language = language.value,
                    )
                )

                Log.d(TAG, "Settings applied")
            }
        },
        bottomAlignment = CustomAlignment.END
    )
}

@Preview
@Composable
fun SettingsPreview(){
    Settings(null)
}