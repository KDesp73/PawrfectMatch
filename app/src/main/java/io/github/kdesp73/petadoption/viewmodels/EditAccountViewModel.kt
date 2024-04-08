package io.github.kdesp73.petadoption.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class EditAccountViewModel : ViewModel(){
    var fnameState = MutableStateFlow("")
    var lnameState = MutableStateFlow("")
    var emailState = MutableStateFlow("")
    var phoneState = MutableStateFlow("")
    var genderState = MutableStateFlow("")
    var locationState = MutableStateFlow("")
    var profileTypeState = MutableStateFlow(1)

    fun reset(){
        fnameState.value = ""
        lnameState.value = ""
        emailState.value = ""
    }

    fun log(TAG: String){
        Log.d(TAG, "fname: ${fnameState.value}")
        Log.d(TAG, "lname: ${lnameState.value}")
        Log.d(TAG, "email: ${emailState.value}")
        Log.d(TAG, "phone: ${phoneState.value}")
        Log.d(TAG, "location: ${locationState.value}")
        Log.d(TAG, "gender: ${genderState.value}")
        Log.d(TAG, "profileType: ${profileTypeState.value}")
    }
}