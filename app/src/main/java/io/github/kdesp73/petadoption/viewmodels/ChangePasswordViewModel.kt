package io.github.kdesp73.petadoption.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import io.github.kdesp73.petadoption.firestore.UserManager
import io.github.kdesp73.petadoption.hash
import io.github.kdesp73.petadoption.room.AppDatabase
import io.github.kdesp73.petadoption.validatePassword
import kotlinx.coroutines.flow.MutableStateFlow

class ChangePasswordViewModel : ViewModel() {
    private val TAG = "ChangePasswordViewModel"

    var oldPasswordState = MutableStateFlow("")
    var confirmOldPasswordState = MutableStateFlow("")
    var newPasswordState = MutableStateFlow("")
    var confirmNewPasswordState = MutableStateFlow("")

    fun validate() = runCatching{
        val checkOldPassword = validatePassword(oldPasswordState.value)
        val checkNewPassword = validatePassword(newPasswordState.value)

        require(checkOldPassword.isSuccess) { checkOldPassword.exceptionOrNull()?.message ?: "" }
        require(oldPasswordState.value == confirmOldPasswordState.value) { "Old Password was not confirmed" }
        require(checkNewPassword.isSuccess) { checkNewPassword.exceptionOrNull()?.message ?: "" }
        require(newPasswordState.value == confirmNewPasswordState.value) { "New Password was not confirmed" }
    }

    fun log(TAG: String){
        Log.d(TAG, "oldPasswordState: ${oldPasswordState.value}")
        Log.d(TAG, "confirmOldPasswordState: ${confirmOldPasswordState.value}")
        Log.d(TAG, "newPasswordState: ${newPasswordState.value}")
        Log.d(TAG, "confirmNewPasswordState: ${confirmNewPasswordState.value}")
    }
}