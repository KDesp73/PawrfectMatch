package io.github.kdesp73.petadoption.routes

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.navigation.NavController
import io.github.kdesp73.petadoption.R
import io.github.kdesp73.petadoption.enums.genderFromValue
import io.github.kdesp73.petadoption.enums.petAgeFromValue
import io.github.kdesp73.petadoption.enums.petSizeFromValue
import io.github.kdesp73.petadoption.enums.petTypeFromValue
import io.github.kdesp73.petadoption.firestore.FirestorePet
import io.github.kdesp73.petadoption.firestore.PetManager
import io.github.kdesp73.petadoption.ui.components.Center
import io.github.kdesp73.petadoption.ui.components.LoadingAnimation
import io.github.kdesp73.petadoption.ui.components.PetCard
import io.github.kdesp73.petadoption.viewmodels.SearchFiltersViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

@OptIn(DelicateCoroutinesApi::class, ExperimentalLayoutApi::class)
@SuppressLint("MutableCollectionMutableState")
@Composable
fun SearchResults(json: String, navController: NavController){
    val viewModel = SearchFiltersViewModel()
    val petManager = PetManager()
    val options = viewModel.deserialize(json)

    var list by remember { mutableStateOf<MutableList<FirestorePet>?>(null) }

    LaunchedEffect(key1 = list) {
        val deferredResult = GlobalScope.async {
            petManager.filterPets(options)
        }

        list = deferredResult.await()
    }

    if(list == null){
        Center(modifier = Modifier.fillMaxSize()) {
            LoadingAnimation(64.dp)
        }
    } else if(list?.isEmpty() == true){
        Row (
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            Text(text = stringResource(R.string.no_results), fontSize = 6.em)
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
                FlowRow (
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ){
                    for (type in options.type){
                        if(type.value){
                            AssistChip(
                                onClick = { /*TODO*/ },
                                label = { Text(text = petTypeFromValue[type.key]?.label.toString()) })
                        }
                    }
                    for (age in options.age) {
                        if(age.value) {
                            AssistChip(
                                onClick = { /*TODO*/ },
                                label = { Text(text = petAgeFromValue[age.key]?.label.toString()) })
                        }
                    }

                    for(gender in options.gender) {
                        if(gender.value) {
                            AssistChip(
                                onClick = { /*TODO*/ },
                                label = { Text(text = genderFromValue[gender.key]?.label.toString()) })
                        }
                    }

                    for (size in options.size) {
                        if (size.value) {
                            AssistChip(
                                onClick = { /*TODO*/ },
                                label = { Text(text = petSizeFromValue[size.key]?.label.toString()) })
                        }
                    }
                }
            }
            items(list!!){ item ->
                PetCard(pet = item, id = item.id, navController = navController)
            }
        }
    }


}