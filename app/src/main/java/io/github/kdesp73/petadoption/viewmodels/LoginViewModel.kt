package io.github.kdesp73.petadoption.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class LoginViewModel : ViewModel() {
    val emailState = MutableStateFlow("")
    val passwordState = MutableStateFlow("")

    fun reset(){
        emailState.value = ""
        passwordState.value = ""
    }

    fun clean(){
        emailState.value = emailState.value.trim()
        passwordState.value = passwordState.value.trim()
    }

    fun log(tag: String){
        Log.d(tag, "email: '${emailState.value}'")
        Log.d(tag, "password: '${passwordState.value}'")
    }
}

