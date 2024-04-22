package io.github.kdesp73.petadoption.routes

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.em
import androidx.navigation.NavController
import io.github.kdesp73.petadoption.Pet
import io.github.kdesp73.petadoption.firebase.FirestorePet
import io.github.kdesp73.petadoption.firebase.LikedManager
import io.github.kdesp73.petadoption.firebase.PetManager
import io.github.kdesp73.petadoption.room.AppDatabase
import io.github.kdesp73.petadoption.ui.components.PetCard
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext


private const val TAG = "Favourites"

@SuppressLint("MutableCollectionMutableState", "UnrememberedMutableState")
@OptIn(DelicateCoroutinesApi::class)
@Composable
fun Favourites(room: AppDatabase, navController: NavController){
    val likedManager = LikedManager()
    val petManager = PetManager()
    var ids: List<String>
    val email = room.userDao().getEmail() ?: return
    val pets = mutableStateListOf<FirestorePet>()

    LaunchedEffect(pets) {
        val deferredResult = GlobalScope.async {
            likedManager.getLikedPetIds(email)
        }

        val petIds = deferredResult.await()

        if(deferredResult.isCompleted){
            petIds.forEach { id->
                Log.d(TAG, "id = $id")
                val pet = withContext(Dispatchers.IO){
                    petManager.getPetById(id)
                }
                if (pet != null) {
                    pets.add(pet)
                }
            }
        }
    }



    LazyColumn (
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ){
        item {
            Text(text = "Favourites", fontSize = 6.em)
        }

        items(pets) { item ->
            PetCard(pet = item, id = item.id, navController = navController)
        }
    }
}