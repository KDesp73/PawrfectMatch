
package io.github.kdesp73.petadoption.routes

import android.annotation.SuppressLint
import android.app.NotificationManager
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import io.github.kdesp73.petadoption.MainActivity
import io.github.kdesp73.petadoption.NotificationService
import io.github.kdesp73.petadoption.R
import io.github.kdesp73.petadoption.Route
import io.github.kdesp73.petadoption.enums.Gender
import io.github.kdesp73.petadoption.enums.Orientation
import io.github.kdesp73.petadoption.enums.PetAge
import io.github.kdesp73.petadoption.enums.PetSize
import io.github.kdesp73.petadoption.enums.PetType
import io.github.kdesp73.petadoption.enums.TextFieldType
import io.github.kdesp73.petadoption.enums.genderFromLabel
import io.github.kdesp73.petadoption.enums.petAgeFromLabel
import io.github.kdesp73.petadoption.enums.petSizeFromLabel
import io.github.kdesp73.petadoption.enums.petTypeFromLabel
import io.github.kdesp73.petadoption.firestore.ImageManager
import io.github.kdesp73.petadoption.firestore.Pet
import io.github.kdesp73.petadoption.firestore.PetManager
import io.github.kdesp73.petadoption.navigateTo
import io.github.kdesp73.petadoption.room.AppDatabase
import io.github.kdesp73.petadoption.room.LocalPet
import io.github.kdesp73.petadoption.ui.components.CircularIconButton
import io.github.kdesp73.petadoption.ui.components.CircularImage
import io.github.kdesp73.petadoption.ui.components.Dropdown
import io.github.kdesp73.petadoption.ui.components.OptionPicker
import io.github.kdesp73.petadoption.ui.components.PetInfoForm
import io.github.kdesp73.petadoption.ui.components.SelectImage
import io.github.kdesp73.petadoption.ui.components.TextFieldComponent
import io.github.kdesp73.petadoption.viewmodels.PetFormViewModel

private const val TAG = "AddPet"


@Composable
fun AddPet(navController: NavController?, room: AppDatabase?){
    val viewModel = PetFormViewModel()
    val petManager = PetManager()
    val userDao = room?.userDao()
    val context = LocalContext.current
    val notificationService = NotificationService(context)
    val imageManager = ImageManager()

    PetInfoForm(viewModel = viewModel) {
        viewModel.log(TAG)

        val check = viewModel.validate()
        if(check.isSuccess){
            if (navController != null) {
                navigateTo(Route.Home.route, navController)
            }
            val newPet = Pet(
                name = viewModel.nameState.value,
                age = petAgeFromLabel[viewModel.ageState.value]?.value ?: PetAge.BABY.value,
                type = petTypeFromLabel[viewModel.typeState.value]?.value ?: PetType.DOG.value,
                gender = genderFromLabel[viewModel.genderState.value]?.value ?: Gender.OTHER.value,
                size = petSizeFromLabel[viewModel.sizeState.value]?.value ?: PetSize.SMALL.value,
                location = userDao?.getUser()?.location ?: "",
                ownerEmail = userDao?.getEmail() ?: ""
            )

            viewModel.imageState.value?.let {
                imageManager.uploadPetImage(it, newPet.id){}
            }

            petManager.addPet(newPet) { added ->
                if(added){
                    Log.i(TAG, "Your pet is added to the database")
                    notificationService.showExpandableImageNotification(
                        MainActivity.appContext.getString( R.string.notif_channel_main),
                        MainActivity.appContext.getString(R.string.success),
                        context.getString(R.string.added, viewModel.nameState.value),
                        imageUri = viewModel.imageState.value,
                        NotificationManager.IMPORTANCE_HIGH
                    )
                } else {
                    Log.i(TAG, "Failed to add pet")
                    Toast.makeText(context,
                        context.getString(R.string.pet_already_exists), Toast.LENGTH_LONG).show()
                }
            }

            val newLocalPet = LocalPet(newPet)
            newLocalPet.imageUri = viewModel.imageState.value.toString()
            room?.petDao()?.insert(newLocalPet)

        } else {
            Toast.makeText(context, check.exceptionOrNull()?.message, Toast.LENGTH_LONG).show()
        }
    }

}

@Preview
@Composable
fun AddPetPreview(){
    AddPet(null, null)
}