
package io.github.kdesp73.petadoption.routes

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.rounded.Face
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import io.github.kdesp73.petadoption.NotificationService
import io.github.kdesp73.petadoption.R
import io.github.kdesp73.petadoption.enums.Gender
import io.github.kdesp73.petadoption.enums.Orientation
import io.github.kdesp73.petadoption.enums.PetAge
import io.github.kdesp73.petadoption.enums.PetSize
import io.github.kdesp73.petadoption.enums.PetType
import io.github.kdesp73.petadoption.enums.TextFieldType
import io.github.kdesp73.petadoption.firestore.Pet
import io.github.kdesp73.petadoption.firestore.PetManager
import io.github.kdesp73.petadoption.room.AppDatabase
import io.github.kdesp73.petadoption.ui.components.CircularIconButton
import io.github.kdesp73.petadoption.ui.components.CircularImage
import io.github.kdesp73.petadoption.ui.components.Dropdown
import io.github.kdesp73.petadoption.ui.components.OptionPicker
import io.github.kdesp73.petadoption.ui.components.SelectImage
import io.github.kdesp73.petadoption.ui.components.TextFieldComponent
import io.github.kdesp73.petadoption.viewmodels.AddPetViewModel

private const val TAG = "AddPet"

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun AddPet(room: AppDatabase?){
    val scrollState = rememberScrollState()
    val viewModel = AddPetViewModel()
    val petManager = PetManager()
    val userDao = room?.userDao()
    val context = LocalContext.current
    val notificationService = NotificationService(context)

    Column (
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ){
        val imageModifier = Modifier.fillMaxSize()
        SelectImage (null){ action, uri ->
            val imagePainter = rememberAsyncImagePainter(uri)
            CircularImage(
                modifier = imageModifier.clickable { action() },
                painter = imagePainter,
                contentDescription = "Pet image",
                size = 200.dp
            )
            viewModel.imageState.value = uri
        }
        // TODO: change icon
        TextFieldComponent(viewModel.nameState, labelValue = "Name", icon = Icons.Rounded.Face, type = TextFieldType.OUTLINED)
        Row {
            Dropdown(
                state = viewModel.typeState,
                title = "Type",
                items = listOf(
                    PetType.DOG.label,
                    PetType.CAT.label,
                    PetType.BIRD.label,
                    PetType.FISH.label,
                    PetType.BUNNY.label,
                    PetType.SNAKE.label
                )
            )
            Dropdown(
                state = viewModel.ageState,
                title = "Age",
                items = listOf(
                    PetAge.BABY.label,
                    PetAge.YOUNG.label,
                    PetAge.ADULT.label,
                    PetAge.SENIOR.label
                )
            )
        }
        Dropdown(
            state = viewModel.sizeState,
            title = "Size",
            items = listOf(
                PetSize.SMALL.label,
                PetSize.MEDIUM.label,
                PetSize.LARGE.label
            )
        )

        OptionPicker(
            state = viewModel.genderState,
            options = listOf(Gender.MALE.label, Gender.FEMALE.label),
            orientation = Orientation.HORIZONTAL,
            width = null
        )
        
        CircularIconButton(icon = Icons.Filled.Check, description = "Add Pet", bg = MaterialTheme.colorScheme.surface, size = 60.dp) {
            viewModel.log(TAG)

            val check = viewModel.validate()
            if(check.isSuccess){
                petManager.addPet(Pet(
                    name = viewModel.nameState.value,
                    age = viewModel.ageState.value,
                    type = viewModel.typeState.value,
                    gender = viewModel.genderState.value,
                    size = viewModel.sizeState.value,
                    location = userDao?.getUser()?.location ?: "",
                    ownerEmail = userDao?.getEmail() ?: ""
                )) { added ->
                    if(added){
                        Log.i(TAG, "Your pet is added to the database")
                        notificationService.showExpandableImageNotification(
                            R.string.MAIN.toString(),
                            "Success",
                            "${viewModel.nameState.value} added!",
                            imageUri = viewModel.imageState.value,
                            NotificationManager.IMPORTANCE_HIGH
                        )

                    } else {
                        Log.i(TAG, "Failed to add pet")
                        Toast.makeText(context, "Pet already exists", Toast.LENGTH_LONG).show()
                    }
                }
            } else {
                Toast.makeText(context, check.exceptionOrNull()?.message, Toast.LENGTH_LONG).show()
            }


        }

    }
}

@Preview
@Composable
fun AddPetPreview(){
    AddPet(null)
}