
package io.github.kdesp73.petadoption.routes

import android.app.NotificationManager
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import io.github.kdesp73.petadoption.MainActivity
import io.github.kdesp73.petadoption.NotificationService
import io.github.kdesp73.petadoption.R
import io.github.kdesp73.petadoption.Route
import io.github.kdesp73.petadoption.enums.Gender
import io.github.kdesp73.petadoption.enums.PetAge
import io.github.kdesp73.petadoption.enums.PetSize
import io.github.kdesp73.petadoption.enums.PetType
import io.github.kdesp73.petadoption.enums.genderFromLabel
import io.github.kdesp73.petadoption.enums.petAgeFromLabel
import io.github.kdesp73.petadoption.enums.petSizeFromLabel
import io.github.kdesp73.petadoption.enums.petTypeFromLabel
import io.github.kdesp73.petadoption.firestore.ImageManager
import io.github.kdesp73.petadoption.firestore.FirestorePet
import io.github.kdesp73.petadoption.firestore.PetManager
import io.github.kdesp73.petadoption.navigateTo
import io.github.kdesp73.petadoption.room.AppDatabase
import io.github.kdesp73.petadoption.room.LocalPet
import io.github.kdesp73.petadoption.ui.components.PetInfoForm
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

    if(userDao?.getUser()?.location?.isEmpty()!!) {
        if (navController != null) {
            navigateTo(Route.AccountSettings.route, navController)
        }

        Toast.makeText(
            context,
            stringResource(R.string.please_specify_your_location_first),
            Toast.LENGTH_LONG
        ).show()
        return
    }

    PetInfoForm(viewModel = viewModel) {
        viewModel.log(TAG)

        val check = viewModel.validate()
        if(check.isSuccess){
            if (navController != null) {
                navigateTo(Route.Home.route, navController)
            }
            val newPet = FirestorePet(
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