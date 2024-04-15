package io.github.kdesp73.petadoption.viewmodels

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import io.github.kdesp73.petadoption.enums.Gender
import io.github.kdesp73.petadoption.enums.PetAge
import io.github.kdesp73.petadoption.enums.PetSize
import io.github.kdesp73.petadoption.enums.PetType
import kotlinx.coroutines.flow.MutableStateFlow

class AddPetViewModel : ViewModel(){
    var imageState = MutableStateFlow<Uri?>(null)
    var nameState = MutableStateFlow("")
    var ageState = MutableStateFlow(PetAge.BABY.label)
    var sizeState = MutableStateFlow(PetSize.SMALL.label)
    var typeState = MutableStateFlow(PetType.DOG.label)
    var genderState = MutableStateFlow(Gender.MALE.label)

    fun reset(){
        imageState.value = null
        nameState.value = ""
        ageState.value = PetAge.BABY.label
        sizeState.value = PetSize.SMALL.label
        typeState.value = PetType.DOG.label
        genderState.value = Gender.MALE.label
    }

    fun validate() = runCatching {
        require(nameState.value.isNotEmpty()) { "Name cannot be empty" }
        require(imageState.value != null) { "You need to select an image" }
    }

    fun log(TAG: String){
        Log.d(TAG, "image_uri: ${imageState.value}")
        Log.d(TAG, "name: ${nameState.value}")
        Log.d(TAG, "age: ${ageState.value}")
        Log.d(TAG, "size ${sizeState.value}")
        Log.d(TAG, "type: ${typeState.value}")
        Log.d(TAG, "gender: ${genderState.value}")
    }
}