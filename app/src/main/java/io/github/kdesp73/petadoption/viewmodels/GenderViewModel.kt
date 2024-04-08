package io.github.kdesp73.petadoption.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class GenderViewModel(initialValue: String = "") : ViewModel(){
    var genderState = MutableStateFlow(initialValue)

    fun reset(){
        genderState.value = ""
    }
}