package io.github.kdesp73.petadoption.ui.components

import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import io.github.kdesp73.petadoption.Pet
import io.github.kdesp73.petadoption.Route
import io.github.kdesp73.petadoption.Toy
import io.github.kdesp73.petadoption.firebase.ImageManager
import io.github.kdesp73.petadoption.navigateTo
import io.github.kdesp73.petadoption.room.LocalPet
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

private const val TAG = "PetCard"


@Composable
private fun ToyContents(modifier: Modifier, toy: Toy, uri: String?, id: String, navController: NavController?){
    MyCard (
        modifier = modifier
            .clickable {
                if (navController != null) {
                    navigateTo(
                        Route.ToyPage.route + "?id=$id",
                        navController = navController,
                        popUpToStartDestination = false,
                        launchAsSingleTop = false,
                        restore = false
                    )
                }
            }
            .height(200.dp)
            .fillMaxWidth(),
    ){
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val painter = rememberAsyncImagePainter(model = uri)
            CircularImage(
                contentScale = ContentScale.Crop,
                modifier = Modifier.padding(horizontal = 10.dp),
                painter = painter,
                contentDescription = "",
                size = 150.dp
            )
            toy.ToComposable()
        }
    }
}

@Composable
private fun PetContents(modifier: Modifier, pet: Pet, uri: String?, id: String, navController: NavController?){
    MyCard (
        modifier = modifier
            .clickable {
                if (navController != null) {
                    navigateTo(
                        Route.PetPage.route + "?id=$id",
                        navController = navController,
                        popUpToStartDestination = false,
                        launchAsSingleTop = false,
                        restore = false
                    )
                }
            }
            .height(200.dp)
            .fillMaxWidth(),
    ){
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val painter = rememberAsyncImagePainter(model = uri)
            CircularImage(
                contentScale = ContentScale.Crop,
                modifier = Modifier.padding(horizontal = 10.dp),
                painter = painter,
                contentDescription = "",
                size = 150.dp
            )
            pet.ToComposable()
        }
    }
}


@SuppressLint("RememberReturnType")
@Composable
fun ToyCard(
    modifier: Modifier = Modifier,
    toy: Toy,
    id: String?,
    uri: Uri?,
    navController: NavController?
){
    ToyContents(
        modifier = modifier,
        toy = toy,
        uri = uri.toString(),
        id = id.toString(),
        navController
    )
}

@OptIn(DelicateCoroutinesApi::class)
@SuppressLint("RememberReturnType")
@Composable
fun ToyCard(
    modifier: Modifier = Modifier,
    toy: Toy,
    id: String?,
    navController: NavController?
){
    val imageManager = ImageManager()
    var uri by remember { mutableStateOf<Uri?>(null) }

    LaunchedEffect(key1 = uri) {
        val deferredResult = GlobalScope.async {
            imageManager.getImageUrl(ImageManager.toys+ id + ".jpg")
        }

        uri = deferredResult.await()
    }


    ToyContents(
        modifier = modifier,
        toy = toy,
        uri = uri.toString(),
        id = id.toString(),
        navController
    )
}

@SuppressLint("RememberReturnType")
@Composable
fun PetCard(
    modifier: Modifier = Modifier,
    pet: Pet,
    id: String?,
    uri: Uri?,
    navController: NavController?
){
    PetContents(
        modifier = modifier,
        pet = pet,
        uri = uri.toString(),
        id = id.toString(),
        navController
    )
}

@OptIn(DelicateCoroutinesApi::class)
@SuppressLint("RememberReturnType")
@Composable
fun PetCard(
    modifier: Modifier = Modifier,
    pet: Pet,
    id: String?,
    navController: NavController?
){
    val imageManager = ImageManager()
    var uri by remember { mutableStateOf<Uri?>(null) }

    LaunchedEffect(key1 = uri) {
        val deferredResult = GlobalScope.async {
            imageManager.getImageUrl(ImageManager.pets + id + ".jpg")
        }

        uri = deferredResult.await()
    }


    PetContents(
        modifier = modifier,
        pet = pet,
        uri = uri.toString(),
        id = id.toString(),
        navController
    )
}

@Preview
@Composable
fun PetCardPreview(){
    PetCard(pet = LocalPet.example, uri = null, id = "", navController = null)
}