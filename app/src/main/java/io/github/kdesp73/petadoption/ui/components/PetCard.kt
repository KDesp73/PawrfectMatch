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
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
private fun Contents(modifier: Modifier, pet: LocalPet, uri: String?, navController: NavController?){
    val size by remember { mutableStateOf(IntSize.Zero) }
    Card(
        modifier = modifier
            .clickable {
                if (navController != null) {
                    navigateTo(Route.PetPage.route + "?id=${pet.id}", navController = navController)
                }
            }
            .height(200.dp)
            .fillMaxWidth(),
        content = {
            Row (
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxSize()
            ){
                val painter = rememberAsyncImagePainter(model = uri)
                Image(
                    modifier = Modifier.size(width = size.width.dp / 2, size.height.dp),
                    painter = painter,
                    contentDescription = "Pet Image",
                    contentScale = ContentScale.Crop
                )
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
    Contents(modifier = modifier, pet = LocalPet(pet), uri = uri.toString(), navController)
}
@Composable
fun PetCard(
    modifier: Modifier = Modifier,
    pet: LocalPet,
    navController: NavController?
){
    Contents(modifier = modifier, pet = pet, uri = pet.imageUri, navController)
}
