package io.github.kdesp73.petadoption.routes

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import io.github.kdesp73.petadoption.MainActivity
import io.github.kdesp73.petadoption.NotificationService
import io.github.kdesp73.petadoption.R
import io.github.kdesp73.petadoption.Route
import io.github.kdesp73.petadoption.firebase.FirestoreToy
import io.github.kdesp73.petadoption.firebase.ImageManager
import io.github.kdesp73.petadoption.firebase.ToyManager
import io.github.kdesp73.petadoption.navigateTo
import io.github.kdesp73.petadoption.room.AppDatabase
import io.github.kdesp73.petadoption.room.LocalToy
import io.github.kdesp73.petadoption.ui.components.ToyForm
import io.github.kdesp73.petadoption.viewmodels.ToyFormViewModel

private const val TAG = "AddPet"


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun AddToy(navController: NavController, room: AppDatabase){
    val context = LocalContext.current
    val viewModel = ToyFormViewModel()
    val toyManager = ToyManager()
    val imageManager = ImageManager()
    val userDao = room.userDao()
    val notificationService = NotificationService(context)

    viewModel.locationState.value = room.userDao().getUser()?.location ?: return

    ToyForm(viewModel = viewModel) {
        viewModel.log(TAG)

        val check = viewModel.validate()
        if(check.isSuccess){
            navigateTo(Route.Home.route, navController)

            val newToy = FirestoreToy(
                name = viewModel.nameState.value.trim(),
                location = viewModel.locationState.value.trim(),
                price = viewModel.priceState.value.toFloat(),
                ownerEmail = userDao.getEmail() ?: ""
            )

            viewModel.imageState.value?.let {
                imageManager.uploadToyImage(it, newToy.id){}
            }

            toyManager.addToy(newToy) { added ->
                if(added){
                    Log.i(TAG, "Your toy is added to the database")
                    notificationService.showExpandableImageNotification(
                        MainActivity.appContext.getString( R.string.notif_channel_main),
                        MainActivity.appContext.getString(R.string.success),
                        context.getString(R.string.added, viewModel.nameState.value),
                        imageUri = viewModel.imageState.value,
                        NotificationManager.IMPORTANCE_HIGH
                    )
                } else {
                    Log.i(TAG, "Failed to add toy")
                    Toast.makeText(context,
                        "Toy already exists", Toast.LENGTH_LONG).show()
                }
            }

            val newLocalToy = LocalToy(newToy)
            room.toyDao().insert(newLocalToy)
        } else {
            Toast.makeText(context, check.exceptionOrNull()?.message, Toast.LENGTH_LONG).show()
        }
    }
}
