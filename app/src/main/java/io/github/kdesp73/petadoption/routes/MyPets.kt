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
import androidx.compose.runtime.mutableStateListOf
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
import io.github.kdesp73.petadoption.R
import io.github.kdesp73.petadoption.enums.Orientation
import io.github.kdesp73.petadoption.firebase.ImageManager
import io.github.kdesp73.petadoption.firebase.PetManager
import io.github.kdesp73.petadoption.isLandscape
import io.github.kdesp73.petadoption.room.AppDatabase
import io.github.kdesp73.petadoption.room.LocalPet
import io.github.kdesp73.petadoption.ui.components.Center
import io.github.kdesp73.petadoption.ui.components.LoadingAnimation
import io.github.kdesp73.petadoption.ui.components.OptionPicker
import io.github.kdesp73.petadoption.ui.components.PetCard
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow


private const val TAG = "MyPets"

@OptIn(DelicateCoroutinesApi::class)
@Composable
private fun PetList(pets: List<LocalPet>?, navController: NavController){
    val imageManager = ImageManager()
    if(pets != null){
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


@OptIn(DelicateCoroutinesApi::class)
@SuppressLint("CoroutineCreationDuringComposition", "StateFlowValueCalledInComposition")
@Composable
fun MyPets(room: AppDatabase, navController: NavController){
    val scrollState = rememberScrollState()
    val petManager = PetManager()
    var pets by remember { mutableStateOf<List<LocalPet>?>(null) }

    val options = listOf(
        stringResource(R.string.alphabetically),
        stringResource(R.string.oldest),
        stringResource(R.string.newest)
    )
    var sortBy by remember { mutableStateOf(options[0]) }
    petManager.syncPets(room)

    LaunchedEffect(key1 = sortBy) {
        pets = when(sortBy){
            options[0] -> room.userDao().getEmail()?.let { room.petDao().selectPetsAlphabetically(it) }
            options[1] -> room.userDao().getEmail()?.let { room.petDao().selectPets(it) }
            options[2] -> room.userDao().getEmail()?.let { room.petDao().selectPets(it) }?.reversed()
            else -> null
        }
    }


    Column (
        modifier = Modifier.padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        val landscape = isLandscape(LocalConfiguration.current)
        if(pets == null){
            Center(modifier = Modifier.fillMaxSize()) {
                LoadingAnimation(64.dp)
            }
        } else if(pets!!.isEmpty()){
            Center(modifier = Modifier.fillMaxSize()) {
                Text(text = stringResource(R.string.no_pets_added_yet), fontSize = 6.em)
            }
        } else{
            OptionPicker(value = sortBy, options = options, orientation = Orientation.HORIZONTAL, width = 200.dp){ option ->
                sortBy = option
            }
            if(!landscape){
                Column (
                    modifier = Modifier
                        .padding(8.dp)
                        .verticalScroll(scrollState)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(15.dp)
                ){
                    PetList(pets, navController)
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
                    PetList(pets, navController)
                }
            }
        }
    }
}
