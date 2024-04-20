package io.github.kdesp73.petadoption.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import io.github.kdesp73.petadoption.MainActivity
import io.github.kdesp73.petadoption.R
import kotlinx.coroutines.flow.MutableStateFlow

class SettingsViewModel : ViewModel (){
    var language = MutableStateFlow(MainActivity.appContext.getString(R.string.lang_english))
    var theme = MutableStateFlow(MainActivity.appContext.getString(R.string.theme_light))

    fun log(TAG: String) {
        Log.d(TAG, "language: ${language.value}")
        Log.d(TAG, "theme: ${theme.value}")
    }
}