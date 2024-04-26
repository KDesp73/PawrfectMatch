package io.github.kdesp73.petadoption.viewmodels

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import io.github.kdesp73.petadoption.MainActivity
import io.github.kdesp73.petadoption.R
import kotlinx.coroutines.flow.MutableStateFlow

class ToyFormViewModel () : ViewModel(){
    val imageState = MutableStateFlow<Uri?>(null)
    val nameState = MutableStateFlow("")
    val locationState = MutableStateFlow("")
    val priceState = MutableStateFlow("")

    fun validate() = runCatching {
        require(imageState.value != null) { MainActivity.appContext.getString(R.string.you_need_to_select_an_image) }
        require(nameState.value.isNotEmpty()) { MainActivity.appContext.getString(R.string.name_cannot_be_empty) }
        require(locationState.value.isNotEmpty()) { MainActivity.appContext.getString(R.string.location_cannot_be_empty) }
        require(priceState.value.toFloat() >= 0.0f) { "Price must be a positive number" }
    }

    fun log(TAG: String){
        Log.d(TAG, "imageState: ${imageState.value}")
        Log.d(TAG, "nameState: ${nameState.value}")
        Log.d(TAG, "locationState: ${locationState.value}")
        Log.d(TAG, "priceState: ${priceState.value}")
    }
}