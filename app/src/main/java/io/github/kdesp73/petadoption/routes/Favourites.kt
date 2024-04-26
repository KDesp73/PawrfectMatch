package io.github.kdesp73.petadoption.routes

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.navigation.NavController
import io.github.kdesp73.petadoption.R
import io.github.kdesp73.petadoption.Route
import io.github.kdesp73.petadoption.firebase.FirestorePet
import io.github.kdesp73.petadoption.firebase.FirestoreToy
import io.github.kdesp73.petadoption.firebase.LikedManager
import io.github.kdesp73.petadoption.firebase.PetManager
import io.github.kdesp73.petadoption.firebase.ToyManager
import io.github.kdesp73.petadoption.navigateTo
import io.github.kdesp73.petadoption.room.AppDatabase
import io.github.kdesp73.petadoption.ui.components.Center
import io.github.kdesp73.petadoption.ui.components.IconButton
import io.github.kdesp73.petadoption.ui.components.LoadingAnimation
import io.github.kdesp73.petadoption.ui.components.PetCard
import io.github.kdesp73.petadoption.ui.components.PleaseLogin
import io.github.kdesp73.petadoption.ui.components.ToyCard
import io.github.kdesp73.petadoption.ui.theme.AppTheme
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async


private const val TAG = "Favourites"

@Composable
fun Favourites(room: AppDatabase, navController: NavController) {
    var index by remember { mutableIntStateOf(0) }

    val tabs = listOf(stringResource(R.string.pets), stringResource(R.string.toys))

    Column(modifier = Modifier.fillMaxWidth()) {
        TabRow(selectedTabIndex = index) {
            tabs.forEachIndexed { i, title ->
                Tab(text = { Text(title) },
                    selected = index == i,
                    onClick = { index = i }
                )
            }
        }
        when (index) {
            0 ->FavouritePets(room, navController)
            1 -> FavouriteToys(room, navController)
            else -> Text("No index")
        }
    }
}

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun FavouriteToys(room: AppDatabase, navController: NavController){
    val likedManager = LikedManager()
    val toyManager = ToyManager()
    var toys by remember { mutableStateOf<List<FirestoreToy?>?>(null) }
    var ids by remember { mutableStateOf<List<String?>>(emptyList()) }

    val email: String? = room.userDao().getEmail()
    if(email?.isEmpty() == true){
        PleaseLogin(email = email, navController = navController)
        return
    }

    LaunchedEffect(null) {
        if (email != null) {
            val deferredResult = GlobalScope.async {
                likedManager.getLikedIds(LikedManager.toys, email)
            }

            ids = deferredResult.await()
        }
    }

    LaunchedEffect(key1 = ids, toys) {
        val deferredResult = GlobalScope.async {
            toyManager.getToysByIds(ids)
        }

        toys = deferredResult.await()
    }

    if(toys == null){
        Center(modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
        ) {
            LoadingAnimation(64.dp)
        }
    } else if(toys!!.isEmpty()){
        Center(modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)) {
            Text(text = "No favourite pets yet", fontSize = 6.em)
        }
    } else {
        LazyColumn (
            modifier = Modifier
                .background(colorScheme.surface)
                .padding(8.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            item {
                Text(
                    color = colorScheme.onSurface,
                    modifier = Modifier.padding(vertical = 10.dp),
                    text = stringResource(id = R.string.route_favourites), fontSize = 6.em
                )
            }
            items(toys!!) { item ->
                if (item != null) {
                    ToyCard(toy = item, id = item.id, navController = navController)
                }
            }
            item { Spacer(modifier = Modifier.height(20.dp)) }
        }
    }
}

@SuppressLint("MutableCollectionMutableState", "UnrememberedMutableState")
@OptIn(DelicateCoroutinesApi::class)
@Composable
fun FavouritePets(room: AppDatabase, navController: NavController){
    val likedManager = LikedManager()
    val petManager = PetManager()
    var pets by remember { mutableStateOf<List<FirestorePet?>?>(null) }
    var ids by remember { mutableStateOf<List<String?>>(emptyList()) }

    val email: String? = room.userDao().getEmail()
    if(email?.isEmpty() == true){
        PleaseLogin(email = email, navController = navController)
        return
    }

    LaunchedEffect(null) {
        if (email != null) {
            val deferredResult = GlobalScope.async {
                likedManager.getLikedIds(LikedManager.pets, email)
            }

            ids = deferredResult.await()
        }
    }

    LaunchedEffect(key1 = ids, pets) {
        val deferredResult = GlobalScope.async {
            petManager.getPetsByIds(ids)
        }

        pets = deferredResult.await()
    }

    if(pets == null){
        Center(modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
        ) {
            LoadingAnimation(64.dp)
        }
    } else if(pets!!.isEmpty()){
        Center(modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)) {
            Text(text = "No favourite pets yet", fontSize = 6.em)
        }
    } else {
        LazyColumn (
            modifier = Modifier
                .background(colorScheme.surface)
                .padding(8.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            item {
                Text(
                    color = colorScheme.onSurface,
                    modifier = Modifier.padding(vertical = 10.dp),
                    text = stringResource(id = R.string.route_favourites), fontSize = 6.em
                )
            }
            items(pets!!) { item ->
                if (item != null) {
                    PetCard(pet = item, id = item.id, navController = navController)
                }
            }
            item { Spacer(modifier = Modifier.height(20.dp)) }
        }
    }
}