package io.github.kdesp73.petadoption.viewmodels

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import io.github.kdesp73.petadoption.MainActivity
import io.github.kdesp73.petadoption.Pet
import io.github.kdesp73.petadoption.R
import io.github.kdesp73.petadoption.Toy
import io.github.kdesp73.petadoption.firebase.ImageManager
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking

class ToyFormViewModel () : ViewModel(){
    val imageState = MutableStateFlow<Uri?>(null)
    val nameState = MutableStateFlow("")
    val locationState = MutableStateFlow("")
    val priceState = MutableStateFlow("")
    var saveImageUri: Uri? = null

    fun compare(viewModel: ToyFormViewModel) = runCatching {
        require(imageState.value == viewModel.imageState.value)
        require(nameState.value == viewModel.nameState.value)
        require(locationState.value == viewModel.locationState.value)
        require(priceState.value == viewModel.priceState.value)
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun init(toy: Toy){
        val imageManager = ImageManager()
        val deferredResult = GlobalScope.async {
            imageManager.getImageUrl(ImageManager.toys + toy.generateId() + ".jpg")
        }
        runBlocking {
            imageState.value = deferredResult.await()
            saveImageUri = imageState.value
        }

        nameState.value = toy.name
        locationState.value = toy.location
        priceState.value = toy.price.toString()
    }

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