package io.github.kdesp73.petadoption.ui.components

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import coil.compose.rememberAsyncImagePainter
import io.github.kdesp73.petadoption.firestore.ImageManager
import io.github.kdesp73.petadoption.firestore.Pet
import io.github.kdesp73.petadoption.imageBitmapFromBitmap
import java.io.File

private const val TAG = "PetCard"

@SuppressLint("RememberReturnType")
@Composable
fun PetCard(
    modifier: Modifier = Modifier,
    pet: Pet,
    uri: Uri?,
    onClick: () -> Unit
){
    val size by remember { mutableStateOf(IntSize.Zero) }
    Card(
        modifier = modifier
            .height(100.dp)
            .fillMaxWidth(),
        content = {
            Row (
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxSize()
            ){
                val painter = rememberAsyncImagePainter(model = uri)
                Image(
                    modifier = Modifier.width(size.width.dp / 2),
                    painter = painter,
                    contentDescription = "Pet Image",
                    contentScale = ContentScale.Crop
                )
                pet.ToComposable()
            }
        }
    )

}