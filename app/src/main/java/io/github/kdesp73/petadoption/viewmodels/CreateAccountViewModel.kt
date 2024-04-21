package io.github.kdesp73.petadoption.viewmodels

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import io.github.kdesp73.petadoption.ERR_DIGIT
import io.github.kdesp73.petadoption.ERR_LEN
import io.github.kdesp73.petadoption.ERR_LOWER
import io.github.kdesp73.petadoption.ERR_SPECIAL
import io.github.kdesp73.petadoption.ERR_UPPER
import io.github.kdesp73.petadoption.ERR_WHITESPACE
import kotlinx.coroutines.flow.MutableStateFlow

class CreateAccountViewModel : ViewModel(){
    var fnameState = MutableStateFlow("")
    var lnameState = MutableStateFlow("")
    var emailState = MutableStateFlow("")
    var passwordState = MutableStateFlow("")
    var repeatPasswordState = MutableStateFlow("")
    var termsAndConditionsAcceptedState = MutableStateFlow(false)

    var fnameError = MutableStateFlow(false)
    var lnameError = MutableStateFlow(false)
    var emailError = MutableStateFlow(false)
    var passwordError = MutableStateFlow(false)
    var repeatPasswordError = MutableStateFlow(false)

    var lowercaseLettersIconState = MutableStateFlow<ImageVector>(Icons.Filled.Clear)
    var uppercaseLettersIconState = MutableStateFlow<ImageVector>(Icons.Filled.Clear)
    var digitIconState = MutableStateFlow<ImageVector>(Icons.Filled.Clear)
    var eightCharactersIconState = MutableStateFlow<ImageVector>(Icons.Filled.Clear)
    var noWhitespaceIconState = MutableStateFlow<ImageVector>(Icons.Filled.Clear)
    var containsSpecialCharactersIconState = MutableStateFlow<ImageVector>(Icons.Filled.Clear)

    fun updateIcons(){
        val pwd = passwordState.value
        val clear = Icons.Filled.Clear
        val check = Icons.Filled.Check

        noWhitespaceIconState.value = if(pwd.none { it.isWhitespace() }) check else clear
        lowercaseLettersIconState.value = if(pwd.any { it.isLowerCase() }) check else clear
        uppercaseLettersIconState.value = if(pwd.any { it.isUpperCase() }) check else clear
        digitIconState.value = if(pwd.any { it.isDigit() }) check else clear
        eightCharactersIconState.value = if(pwd.length >= 8) check else clear
        containsSpecialCharactersIconState.value = if (pwd.any { !it.isLetterOrDigit() }) check else clear
    }

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