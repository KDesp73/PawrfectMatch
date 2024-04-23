package io.github.kdesp73.petadoption.routes

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.navigation.NavController
import io.github.kdesp73.petadoption.firebase.FirestorePet
import io.github.kdesp73.petadoption.firebase.LikedManager
import io.github.kdesp73.petadoption.firebase.PetManager
import io.github.kdesp73.petadoption.room.AppDatabase
import io.github.kdesp73.petadoption.ui.components.Center
import io.github.kdesp73.petadoption.ui.components.LoadingAnimation
import io.github.kdesp73.petadoption.ui.components.PetCard
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async


private const val TAG = "Favourites"

@SuppressLint("MutableCollectionMutableState", "UnrememberedMutableState")
@OptIn(DelicateCoroutinesApi::class)
@Composable
fun Favourites(room: AppDatabase, navController: NavController){
    val likedManager = LikedManager()
    val petManager = PetManager()
    val email = room.userDao().getEmail() ?: return
    var pets by remember { mutableStateOf<List<FirestorePet?>?>(null) }
    var ids by remember { mutableStateOf<List<String?>>(emptyList()) }

    LaunchedEffect(null) {
        val deferredResult = GlobalScope.async {
            likedManager.getLikedPetIds(email)
        }

        ids = deferredResult.await()
    }

    LaunchedEffect(key1 = ids, pets) {
        val deferredResult = GlobalScope.async {
            petManager.getPetsByIds(ids)
        }

        pets = deferredResult.await()
    }



    if(pets == null){
        Center(modifier = Modifier.fillMaxSize()) {
            LoadingAnimation(64.dp)
        }
    } else if(pets!!.isEmpty()){
        Center(modifier = Modifier.fillMaxSize()) {
            Text(text = "No favourite pets yet", fontSize = 6.em)
        }
    } else {
        LazyColumn (
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            item {
                Text(text = "Favourites", fontSize = 6.em)
                Spacer(modifier = Modifier.height(15.dp))
            }
            items(pets!!) { item ->
                if (item != null) {
                    PetCard(pet = item, id = item.id, navController = navController)
                }
            }
        }
    }
}