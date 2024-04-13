
package io.github.kdesp73.petadoption.routes

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import io.github.kdesp73.petadoption.R
import io.github.kdesp73.petadoption.SelectImage
import io.github.kdesp73.petadoption.ui.components.CircularImage

@Composable
fun AddPet(){
    Column {
        Text(text = "Add Pet")

        val imageModifier = Modifier.fillMaxSize()
        SelectImage { action, uri ->
            val imagePainter = rememberAsyncImagePainter(uri)
            CircularImage(
                modifier = imageModifier.clickable { action() },
                painter = imagePainter,
                contentDescription = "Pet image",
                size = 200.dp
            ){
                Image(
                    painter = painterResource(id = R.drawable.profile_pic_placeholder),
                    contentDescription = "profile image",
                    modifier = imageModifier.clickable { action() },
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

@Preview
@Composable
fun AddPetPreview(){
    AddPet()
}