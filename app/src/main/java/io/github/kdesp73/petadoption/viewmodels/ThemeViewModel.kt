package io.github.kdesp73.petadoption.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ThemeViewModel: ViewModel() {
    lateinit var onRecreate: () -> Unit

    private val _theme = MutableLiveData("Auto")
    val theme: LiveData<String> = _theme

    fun onThemeChanged(newTheme: String) {
        when (newTheme) {
            "Auto" -> _theme.value = "Light"
            "Light" -> _theme.value = "Dark"
            "Dark" -> _theme.value = "Auto"
        }
        onRecreate
    }
}