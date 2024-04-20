package io.github.kdesp73.petadoption.routes

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.navigation.NavController
import io.github.kdesp73.petadoption.Route
import io.github.kdesp73.petadoption.room.AppDatabase
import io.github.kdesp73.petadoption.room.LocalPet
import io.github.kdesp73.petadoption.ui.components.PetCard
import kotlinx.coroutines.DelicateCoroutinesApi


private const val TAG = "MyPets"

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun MyPets(room: AppDatabase, navController: NavController){
    val pets: List<LocalPet> = room.petDao().selectPets(room.userDao().getEmail())
    val scrollState = rememberScrollState()

    Column (
        modifier = Modifier.padding(8.dp).verticalScroll(scrollState),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,

    ){
        Text(text = stringResource(id = Route.MyPets.resId), fontSize = 6.em)
        Spacer(modifier = Modifier.height(10.dp))
        Column (
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ){
            for(pet in pets){
                PetCard(
                    pet = pet,
                    navController = navController
                )
            }
        }
    }
}
