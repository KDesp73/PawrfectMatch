package io.github.kdesp73.petadoption.routes

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import io.github.kdesp73.petadoption.LocaleManager
import io.github.kdesp73.petadoption.MainActivity
import io.github.kdesp73.petadoption.R
import io.github.kdesp73.petadoption.Route
import io.github.kdesp73.petadoption.changeLocale
import io.github.kdesp73.petadoption.enums.CustomAlignment
import io.github.kdesp73.petadoption.enums.Language
import io.github.kdesp73.petadoption.enums.ThemeName
import io.github.kdesp73.petadoption.enums.languageFromLabel
import io.github.kdesp73.petadoption.enums.languageFromValue
import io.github.kdesp73.petadoption.enums.locales
import io.github.kdesp73.petadoption.enums.themeNameFromLabel
import io.github.kdesp73.petadoption.enums.themeNameFromValue
import io.github.kdesp73.petadoption.room.AppDatabase
import io.github.kdesp73.petadoption.ui.components.Dropdown
import io.github.kdesp73.petadoption.ui.components.HalfButton
import io.github.kdesp73.petadoption.ui.components.VerticalScaffold
import io.github.kdesp73.petadoption.viewmodels.SettingsViewModel

private const val TAG = "Settings"

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun Settings(room: AppDatabase?){
    val context = LocalContext.current as Activity
    val settingsDao = room?.settingsDao()
    Log.d(TAG, settingsDao?.getSettings().toString())

    val viewModel = SettingsViewModel()

    viewModel.theme.value = themeNameFromValue[settingsDao?.getTheme()]?.label ?: stringResource(id = R.string.theme_light)
    viewModel.language.value = languageFromValue[settingsDao?.getLanguage()]?.label ?: stringResource(id = R.string.lang_english)

    VerticalScaffold(
        modifier = Modifier
            .padding(6.dp),
        center = {
            Column (
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth()
            ){
                Dropdown(viewModel.theme, title = stringResource(R.string.theme), items = listOf(ThemeName.LIGHT.label, ThemeName.DARK.label, ThemeName.AUTO.label))
                Dropdown(viewModel.language, title = stringResource(R.string.language), items = listOf(Language.ENGLISH.label, Language.GREEK.label))
            }
        },
        top = {
            Spacer(modifier = Modifier.height(10.dp))
            Text(fontSize = 6.em, text = stringResource(id = R.string.route_settings))
        },
        bottom = {
            HalfButton (
                text = stringResource(R.string.apply),
                icon = Icons.Filled.Check
            ){
                viewModel.log(TAG)
                Log.d(TAG, "Selected locale: ${locales[viewModel.language.value] ?: "null"}")
                Log.d(TAG, "Current locale: ${LocaleManager.getLocale()}")

                if(settingsDao?.getLanguage() != viewModel.language.value){
                    // TODO: doesn't change (fix)
                    locales[viewModel.language.value]?.let { LocaleManager.setLocale(MainActivity.appContext, it) }
                }

                if(settingsDao?.getTheme() != themeNameFromLabel[viewModel.theme.value]?.value){
                    context.recreate()
                }

                settingsDao?.insert(
                    io.github.kdesp73.petadoption.room.Settings(
                        theme = themeNameFromLabel[viewModel.theme.value]?.value
                            ?: ThemeName.LIGHT.value,
                        language = languageFromLabel[viewModel.language.value]?.value
                            ?: Language.ENGLISH.value,
                    )
                )

                Log.d(TAG, "Settings applied")
                Toast.makeText(context,
                    context.getString(R.string.settings_applied), Toast.LENGTH_LONG).show()
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