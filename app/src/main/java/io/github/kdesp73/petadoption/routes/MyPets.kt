package io.github.kdesp73.petadoption.routes

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import io.github.kdesp73.petadoption.Route
import io.github.kdesp73.petadoption.firestore.ImageManager
import io.github.kdesp73.petadoption.firestore.Pet
import io.github.kdesp73.petadoption.firestore.PetManager
import io.github.kdesp73.petadoption.room.AppDatabase
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import io.github.kdesp73.petadoption.ui.components.*


private const val TAG = "MyPets"

@OptIn(DelicateCoroutinesApi::class)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun MyPets(room: AppDatabase){
    val context = LocalContext.current
    val petManager = PetManager()
    val imageManager = ImageManager()

    val pets: List<Pet>

    val petsDeferredResult: Deferred<List<Pet>> = GlobalScope.async {
        petManager.getPetsByEmail(room.userDao().getEmail())
    }

    runBlocking {
        pets = petsDeferredResult.await()
    }
    Column (
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(text = stringResource(id = Route.MyPets.resId), fontSize = 6.em)
        Column (
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ){
            for(pet in pets){
                val urlDeferredResult: Deferred<Uri?> = GlobalScope.async {
                    imageManager.getImageUrl(ImageManager.pets + pets[0].getImageFile())
                }

                val uri: Uri?
                runBlocking {
                    uri = urlDeferredResult.await()
                }

                PetCard(
                    pet = pet,
                    uri = uri,
                ) {

                }
            }
        }
    }
}
