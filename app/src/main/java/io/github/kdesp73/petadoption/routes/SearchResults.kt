package io.github.kdesp73.petadoption.routes

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.navigation.NavController
import io.github.kdesp73.petadoption.Route
import io.github.kdesp73.petadoption.enums.genderLabelList
import io.github.kdesp73.petadoption.enums.petAgeLabelList
import io.github.kdesp73.petadoption.enums.petSizeLabelList
import io.github.kdesp73.petadoption.enums.petTypeLabelList
import io.github.kdesp73.petadoption.firestore.FirestorePet
import io.github.kdesp73.petadoption.firestore.PetManager
import io.github.kdesp73.petadoption.ui.components.LoadingAnimation
import io.github.kdesp73.petadoption.ui.components.PetCard
import io.github.kdesp73.petadoption.viewmodels.SearchFiltersViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

@OptIn(DelicateCoroutinesApi::class)
@SuppressLint("MutableCollectionMutableState")
@Composable
fun SearchResults(json: String, navController: NavController){
    val viewModel = SearchFiltersViewModel()
    val petManager = PetManager()
    val options = viewModel.deserialize(json)

    var list by remember { mutableStateOf<MutableList<FirestorePet>>(mutableListOf<FirestorePet>()) }

    LaunchedEffect(key1 = list) {
        val deferredResult = GlobalScope.async {
            petManager.filterPets(options)
        }

        list = deferredResult.await()
    }

    if(list.isEmpty()){
        LoadingAnimation()
    } else {
        LazyColumn (
            modifier = Modifier.padding(8.dp).fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            items(list){ item ->
                PetCard(pet = item, id = item.id, navController = navController)
            }
        }
    }


}