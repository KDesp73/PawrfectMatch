package io.github.kdesp73.petadoption.routes

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.kdesp73.petadoption.enums.CustomAlignment
import io.github.kdesp73.petadoption.enums.Language
import io.github.kdesp73.petadoption.enums.ThemeName
import io.github.kdesp73.petadoption.room.AppDatabase
import io.github.kdesp73.petadoption.ui.components.Dropdown
import io.github.kdesp73.petadoption.ui.components.HalfButton
import io.github.kdesp73.petadoption.ui.utils.VerticalScaffold
import io.github.kdesp73.petadoption.viewmodels.SettingsViewModel

private const val TAG = "Settings"

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun Settings(room: AppDatabase?){
    val context = LocalContext.current as Activity
    val settingsDao = room?.settingsDao()

    val viewModel = SettingsViewModel()

    viewModel.theme.value = settingsDao?.getTheme() ?: "Light"
    viewModel.language.value = settingsDao?.getLanguage() ?: "English"

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
                Dropdown(viewModel.theme, title = "Theme", items = listOf(ThemeName.LIGHT.label, ThemeName.DARK.label, ThemeName.AUTO.label))
                Dropdown(viewModel.language, title = "Language", items = listOf(Language.ENGLISH.label, Language.GREEK.label))
            }
        },
        center = {},
        bottom = {
            HalfButton (
                text = "Apply",
                icon = Icons.Filled.Check
            ){

                if(settingsDao?.getTheme() != viewModel.theme.value){
                    context.recreate()
                }

                settingsDao?.insert(
                    io.github.kdesp73.petadoption.room.Settings(
                        theme = viewModel.theme.value,
                        language = viewModel.language.value,
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