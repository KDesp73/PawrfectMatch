package io.github.kdesp73.petadoption.routes

import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.navigation.NavController
import io.github.kdesp73.petadoption.Route
import io.github.kdesp73.petadoption.firestore.ImageManager
import io.github.kdesp73.petadoption.firestore.PetManager
import io.github.kdesp73.petadoption.isLandscape
import io.github.kdesp73.petadoption.room.AppDatabase
import io.github.kdesp73.petadoption.room.LocalPet
import io.github.kdesp73.petadoption.ui.components.PetCard
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async


private const val TAG = "MyPets"

@OptIn(DelicateCoroutinesApi::class)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun MyPets(room: AppDatabase, navController: NavController){
    val pets: List<LocalPet>? = room.userDao().getEmail()?.let { room.petDao().selectPets(it) }
    val scrollState = rememberScrollState()
    val imageManager = ImageManager()
    val petManager = PetManager()

//    petManager.syncPets(room)

    Column (
        modifier = Modifier
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,

    ){
        val landscape = isLandscape(LocalConfiguration.current)
        Text(
            modifier = Modifier.padding(vertical = 20.dp),
            text = stringResource(id = Route.MyPets.resId),
            fontSize = if(landscape) 4.em else 6.em
        )
        @Composable
        fun PetList(){
            if (pets != null) {
                for(pet in pets){
                    var uri by remember { mutableStateOf(Uri.EMPTY) }
                    LaunchedEffect(pet) {
                        val deferredResult: Deferred<Uri?> = GlobalScope.async {
                            imageManager.getImageUrl(ImageManager.pets + pet.generateId() + ".jpg")
                        }
                        uri = deferredResult.await()
                    }

                    PetCard(
                        pet = pet,
                        uri = uri,
                        id = pet.id.toString(),
                        navController = navController
                    )
                }
            }
        }
        if(!landscape){
            Column (
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxSize()
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ){
                PetList()
            }
        } else {
            Row (
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxSize()
                    .horizontalScroll(scrollState),
                horizontalArrangement = Arrangement.spacedBy(15.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                PetList()
            }
        }
    }
}
