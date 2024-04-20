package io.github.kdesp73.petadoption.ui.components

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import io.github.kdesp73.petadoption.Route
import io.github.kdesp73.petadoption.firestore.ImageManager
import io.github.kdesp73.petadoption.firestore.Pet
import io.github.kdesp73.petadoption.imageBitmapFromBitmap
import io.github.kdesp73.petadoption.navigateTo
import io.github.kdesp73.petadoption.room.LocalPet
import java.io.File

private const val TAG = "PetCard"

@Composable
private fun Contents(modifier: Modifier, pet: LocalPet, uri: String?, id: String, navController: NavController?){
    Card(
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
        content = {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Log.d(TAG, "Uri (${pet.name}): $uri")
                val painter = rememberAsyncImagePainter(model = uri)
                CircularImage(painter = painter, contentDescription = "", size = 150.dp)
                pet.ToComposable()
            }
        }
    )
}

@SuppressLint("RememberReturnType")
@Composable
fun PetCard(
    modifier: Modifier = Modifier,
    pet: Pet,
    uri: Uri?,
    navController: NavController?
){
    Contents(
        modifier = modifier,
        pet = LocalPet(pet),
        uri = uri.toString(),
        id = pet.id,
        navController
    )
}
@Composable
fun PetCard(
    modifier: Modifier = Modifier,
    pet: LocalPet,
    uri: Uri?,
    navController: NavController?
){
    Contents(
        modifier = modifier,
        pet = pet,
        uri = uri.toString() ?: pet.imageUri,
        id = pet.id.toString(),
        navController
    )
}


@Preview
@Composable
fun PetCardPreview(){
    PetCard(pet = LocalPet.example, uri = null, navController = null)
}