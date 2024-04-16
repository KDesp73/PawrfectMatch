package io.github.kdesp73.petadoption.viewmodels

import android.util.Log
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import io.github.kdesp73.petadoption.R
import io.github.kdesp73.petadoption.resToString
import kotlinx.coroutines.flow.MutableStateFlow

class SettingsViewModel : ViewModel (){
    var language = MutableStateFlow(resToString(R.string.lang_english))
    var theme = MutableStateFlow(resToString(R.string.theme_light))

    fun log(TAG: String) {
        Log.d(TAG, "language: ${language.value}")
        Log.d(TAG, "theme: ${theme.value}")
    }
}