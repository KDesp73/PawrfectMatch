package io.github.kdesp73.petadoption.viewmodels

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import io.github.kdesp73.petadoption.ERR_DIGIT
import io.github.kdesp73.petadoption.ERR_LEN
import io.github.kdesp73.petadoption.ERR_LOWER
import io.github.kdesp73.petadoption.ERR_SPECIAL
import io.github.kdesp73.petadoption.ERR_UPPER
import io.github.kdesp73.petadoption.ERR_WHITESPACE
import kotlinx.coroutines.flow.MutableStateFlow

class EditAccountViewModel : ViewModel(){
    var imageState = MutableStateFlow<Uri?>(null)
    var fnameState = MutableStateFlow("")
    var lnameState = MutableStateFlow("")
    var phoneState = MutableStateFlow("")
    var genderState = MutableStateFlow("")
    var locationState = MutableStateFlow("")
    var profileTypeState = MutableStateFlow(1)

    fun reset(){
        imageState.value = null
        fnameState.value = ""
        lnameState.value = ""
        phoneState.value = ""
        genderState.value = ""
        locationState.value = ""
        profileTypeState.value = 1
    }

    private fun validateName(name: String) = runCatching {
        require(name.isNotBlank() && name.isNotEmpty()) { "cannot be empty" }
        require(name.all { it.isLetter() }) { "cannot contain special characters or numbers" }
    }

    fun validate() = runCatching {
        val checkFirstName = validateName(fnameState.value)
        val checkLastName = validateName(lnameState.value)
        require(checkFirstName.isSuccess) { "First Name " + checkFirstName.exceptionOrNull()?.message }
        require(checkLastName.isSuccess) { "Last Name " + checkLastName.exceptionOrNull()?.message }
    }

    fun log(TAG: String){
        Log.d(TAG, "image: ${imageState.value}")
        Log.d(TAG, "fname: ${fnameState.value}")
        Log.d(TAG, "lname: ${lnameState.value}")
        Log.d(TAG, "phone: ${phoneState.value}")
        Log.d(TAG, "location: ${locationState.value}")
        Log.d(TAG, "gender: ${genderState.value}")
        Log.d(TAG, "profileType: ${profileTypeState.value}")
    }
}