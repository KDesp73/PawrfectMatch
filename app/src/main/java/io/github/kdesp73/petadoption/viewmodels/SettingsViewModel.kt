package io.github.kdesp73.petadoption.viewmodels

import kotlinx.coroutines.flow.MutableStateFlow

class SettingsViewModel (){
    var language = MutableStateFlow("English")
    var theme = MutableStateFlow("Light")
}