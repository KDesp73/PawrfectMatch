package io.github.kdesp73.petadoption.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class CreateAccountViewModel : ViewModel(){
    var fnameState = MutableStateFlow("")
    var lnameState = MutableStateFlow("")
    var emailState = MutableStateFlow("")
    var passwordState = MutableStateFlow("")
    var repeatPasswordState = MutableStateFlow("")
    var termsAndConditionsAcceptedState = MutableStateFlow(false)

    fun reset(){
        fnameState.value = ""
        lnameState.value = ""
        emailState.value = ""
        passwordState.value = ""
        repeatPasswordState.value = ""
        termsAndConditionsAcceptedState.value = false
    }

    fun log(TAG: String){
        Log.d(TAG, "fname: ${fnameState.value}")
        Log.d(TAG, "lname: ${lnameState.value}")
        Log.d(TAG, "email: ${emailState.value}")
        Log.d(TAG, "password: ${passwordState.value}")
        Log.d(TAG, "repeatPassword: ${repeatPasswordState.value}")
        Log.d(TAG, "termsAndConditionsAccepted: ${termsAndConditionsAcceptedState.value}")
    }
}