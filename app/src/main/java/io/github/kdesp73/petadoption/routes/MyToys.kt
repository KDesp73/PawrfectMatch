package io.github.kdesp73.petadoption.routes

import android.net.Uri
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
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
import io.github.kdesp73.petadoption.R
import io.github.kdesp73.petadoption.firebase.ImageManager
import io.github.kdesp73.petadoption.firebase.PetManager
import io.github.kdesp73.petadoption.firebase.ToyManager
import io.github.kdesp73.petadoption.isLandscape
import io.github.kdesp73.petadoption.room.AppDatabase
import io.github.kdesp73.petadoption.room.LocalPet
import io.github.kdesp73.petadoption.room.LocalToy
import io.github.kdesp73.petadoption.ui.components.Center
import io.github.kdesp73.petadoption.ui.components.LoadingAnimation
import io.github.kdesp73.petadoption.ui.components.PetCard
import io.github.kdesp73.petadoption.ui.components.ToyCard
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun MyToys(room: AppDatabase, navController: NavController){
    val scrollState = rememberScrollState()
    val imageManager = ImageManager()
    val toyManager = ToyManager()
    var toys by remember { mutableStateOf<List<LocalToy>?>(null) }

    LaunchedEffect(key1 = null) {
        toys = room.userDao().getEmail()?.let { room.toyDao().selectToys(it) }!!
    }

    LaunchedEffect(key1 = toys) {
        toyManager.syncToys(room)
    }

    Surface {
        Column (
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ){
            val landscape = isLandscape(LocalConfiguration.current)
            @Composable
            fun PetList(){
                if(toys != null){
                    for(toy in toys!!){
                        var uri by remember { mutableStateOf(Uri.EMPTY) }
                        LaunchedEffect(toy) {
                            val deferredResult: Deferred<Uri?> = GlobalScope.async {
                                imageManager.getImageUrl(ImageManager.toys + toy.generateId() + ".jpg")
                            }
                            uri = deferredResult.await()
                        }

                        ToyCard(
                            toy = toy,
                            uri = uri,
                            id = toy.id.toString(),
                            navController = navController
                        )
                    }
                }
            }
            if(toys == null){
                Center(modifier = Modifier.fillMaxSize()) {
                    LoadingAnimation(64.dp)
                }
            } else if(toys!!.isEmpty()){
                Center(modifier = Modifier.fillMaxSize()) {
                    Text(text = stringResource(R.string.no_pets_added_yet), fontSize = 6.em)
                }
            } else{
                if(!landscape){
                    Column (
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxSize(),
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
    }
}