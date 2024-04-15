package io.github.kdesp73.petadoption.ui.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun SelectImage(
    trigger: @Composable (action: () -> Unit) -> Unit,
    imageContainer: @Composable (image: Uri?) -> Unit
){
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }
    imageContainer(imageUri)
    trigger { launcher.launch("image/*") }
}


@Composable
fun SelectImage(
    defaultUri: Uri?,
    containerButton: @Composable (action: () -> Unit, image: Uri?) -> Unit,
){
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }
    containerButton({ launcher.launch("image/*") }, defaultUri ?: imageUri)
}
