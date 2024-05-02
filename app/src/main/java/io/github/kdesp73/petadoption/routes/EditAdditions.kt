package io.github.kdesp73.petadoption.routes

import android.app.NotificationManager
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import io.github.kdesp73.petadoption.NotificationService
import io.github.kdesp73.petadoption.Pet
import io.github.kdesp73.petadoption.R
import io.github.kdesp73.petadoption.Route
import io.github.kdesp73.petadoption.Toy
import io.github.kdesp73.petadoption.enums.genderFromLabel
import io.github.kdesp73.petadoption.enums.petAgeFromLabel
import io.github.kdesp73.petadoption.enums.petSizeFromLabel
import io.github.kdesp73.petadoption.enums.petTypeFromLabel
import io.github.kdesp73.petadoption.firebase.FirestorePet
import io.github.kdesp73.petadoption.firebase.FirestoreToy
import io.github.kdesp73.petadoption.firebase.ImageManager
import io.github.kdesp73.petadoption.firebase.LikedManager
import io.github.kdesp73.petadoption.firebase.PetManager
import io.github.kdesp73.petadoption.firebase.ToyManager
import io.github.kdesp73.petadoption.hash
import io.github.kdesp73.petadoption.navigateTo
import io.github.kdesp73.petadoption.room.AppDatabase
import io.github.kdesp73.petadoption.room.LocalPet
import io.github.kdesp73.petadoption.room.LocalToy
import io.github.kdesp73.petadoption.ui.components.Center
import io.github.kdesp73.petadoption.ui.components.LoadingAnimation
import io.github.kdesp73.petadoption.ui.components.PetInfoForm
import io.github.kdesp73.petadoption.ui.components.ToyForm
import io.github.kdesp73.petadoption.viewmodels.PetFormViewModel
import io.github.kdesp73.petadoption.viewmodels.ToyFormViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun EditPet(id: String, room: AppDatabase, navController: NavController){
    val TAG = "EditPet"
    val viewModel = PetFormViewModel()
    val petManager = PetManager()
    var pet by remember {
        mutableStateOf<Pet?>(null)
    }
    val context = LocalContext.current
    val notificationService = NotificationService(context)

    LaunchedEffect(key1 = id) {
        val deferredResult = GlobalScope.async {
            petManager.getPetById(id)
        }

        pet = deferredResult.await()
    }

    if(pet == null){
        Center(modifier = Modifier.fillMaxSize()) {
            LoadingAnimation(64.dp)
        }
    } else {
        viewModel.init(pet!!)
        PetInfoForm(viewModel = viewModel) {
            viewModel.log(TAG)
            
            if(viewModel.imageState.value == null){
                viewModel.imageState.value = Uri.EMPTY
            }

            if(viewModel.validate().isSuccess){
                val updatedPet = FirestorePet(
                    name = viewModel.nameState.value,
                    location = viewModel.locationState.value,
                    size = petSizeFromLabel[viewModel.sizeState.value]?.value.toString(),
                    age = petAgeFromLabel[viewModel.ageState.value]?.value.toString(),
                    gender = genderFromLabel[viewModel.genderState.value]?.value.toString(),
                    type = petTypeFromLabel[viewModel.typeState.value]?.value.toString(),
                    ownerEmail = pet!!.ownerEmail
                )

                Log.d(TAG, "oldId: $id")
                Log.d(TAG, "newId: ${updatedPet.id}")

                if(id == updatedPet.id && viewModel.imageState.value == Uri.EMPTY){
                    Log.d(TAG, "No changes")
                    Toast.makeText(context, "No changes", Toast.LENGTH_SHORT).show()
                } else {
                    var error = false
                    petManager.updatePet(id, updatedPet){ completed ->
                        if(completed){
                            Log.i(TAG, "Updated pet info")
                        } else {
                            error = true
                            Log.i(TAG, "Failed to update pet info")
                        }
                    }

                    if(!error){
                        room.petDao().update(LocalPet(updatedPet))
                    }

                    val imageManager = ImageManager()

                    if(!error){
                        if(viewModel.imageState.value == Uri.EMPTY){
                            imageManager.renameImage("${ImageManager.pets}$id.jpg", "${ImageManager.pets}${updatedPet.id}.jpg"){ completed ->
                                if (completed){
                                    Log.d(TAG, "Image renamed successfully")
                                } else {
                                    error = true
                                    Log.d(TAG, "Failed to rename image")
                                }
                            }
                        } else {
                            imageManager.deleteImage(ImageManager.pets + id + ".jpg"){ completed ->
                                if(completed){
                                    Log.d(TAG, "${ImageManager.pets}$id.jpg deleted successfully")
                                } else {
                                    error = true
                                    Log.d(TAG, "Failed to delete image")
                                }
                            }
                            viewModel.imageState.value?.let {
                                imageManager.uploadPetImage(it, updatedPet.id){ url ->
                                    if(url != null){
                                        Log.d(TAG, "${ImageManager.pets}${updatedPet.id}.jpg uploaded successfully at $url")
                                    } else {
                                        error = true
                                        Log.d(TAG, "Failed to upload image")
                                    }
                                }
                            }
                        }
                    }

                    if(!error){
                        LikedManager().updateLikes(LikedManager.pets, id, updatedPet.id){ completed ->
                            if(completed){
                                Log.d(TAG, "Pets likes updated")
                            } else {
                                error = true
                                Log.d(TAG, "Failed to update likes")
                            }
                        }
                    }

                    if(!error){
                        petManager.syncPets(room)
                        notificationService.showBasicNotification(
                            context.getString(R.string.notif_channel_main),
                            context.getString(R.string.success),
                            context.getString(R.string.pet_updated_successfully),
                            NotificationManager.IMPORTANCE_HIGH
                        )

//                    navigateTo(Route.PetPage.route + "?id=$updatedId", navController)
                        navigateTo(Route.Home.route, navController)
                    }

                    if(error){
                        Toast.makeText(context, "An error occurred", Toast.LENGTH_SHORT).show()
                    }
                }

            } else {
                Toast.makeText(context, viewModel.validate().exceptionOrNull()?.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun EditToy(id: String, room: AppDatabase, navController: NavController){
    val TAG = "EditToy"
    val viewModel = ToyFormViewModel()
    val toyManager = ToyManager()
    val context = LocalContext.current
    val notificationService = NotificationService(context)
    var toy by remember {
        mutableStateOf<Toy?>(null)
    }

    Log.d(TAG, "id: $id")

    LaunchedEffect(key1 = id) {
        val deferredResult = GlobalScope.async {
            toyManager.getToyById(id)
        }

        toy = deferredResult.await()
    }

    if(toy == null){
        Center(modifier = Modifier.fillMaxSize()) {
            LoadingAnimation(64.dp)
        }
    } else {
        viewModel.init(toy!!)
        ToyForm(viewModel = viewModel) {
            viewModel.log(TAG)

            if(viewModel.imageState.value == null){
                viewModel.imageState.value = Uri.EMPTY
            }

            if(viewModel.validate().isSuccess){
                val updatedToy = FirestoreToy(
                    name = viewModel.nameState.value,
                    location = viewModel.locationState.value,
                    price = viewModel.priceState.value.toFloat(),
                    ownerEmail = toy!!.ownerEmail
                )

                if(id == updatedToy.id && viewModel.imageState.value == Uri.EMPTY){
                    Toast.makeText(context, "No changes", Toast.LENGTH_SHORT).show()
                    Log.d(TAG, "No changes")
                } else {
                    var error = false

                    toyManager.updateToy(id, updatedToy){ completed ->
                        if(completed){
                            Log.i(TAG, "Updated toy info")
                        } else {
                            error = true
                            Log.i(TAG, "Failed to update toy info")
                        }
                    }

                    if(!error){
                        room.toyDao().update(LocalToy(updatedToy))
                    }

                    val imageManager = ImageManager()
                    if(!error){
                        if(viewModel.imageState.value == Uri.EMPTY){
                            imageManager.renameImage("${ImageManager.toys}$id.jpg", "${ImageManager.toys}${updatedToy.id}.jpg"){ completed ->
                                if(completed){
                                    Log.d(TAG, "Renamed image successfully")
                                } else {
                                    error = true
                                    Log.d(TAG, "Failed to rename image")
                                }
                            }
                        } else {
                            imageManager.deleteImage(ImageManager.toys + id + ".jpg"){ completed ->
                                if(completed){
                                    Log.d(TAG, "${ImageManager.toys}$id.jpg deleted successfully")
                                } else {
                                    error = true
                                    Log.d(TAG, "Failed to delete image")
                                }
                            }
                            viewModel.imageState.value?.let {
                                imageManager.uploadToyImage(it, updatedToy.id){ url ->
                                    if(url != null){
                                        Log.d(TAG, "${ImageManager.toys}${updatedToy.id}.jpg uploaded successfully at $url")
                                    } else {
                                        error = true
                                        Log.d(TAG, "Failed to upload image")
                                    }
                                }
                            }
                        }
                    }

                    if(!error){
                        LikedManager().updateLikes(LikedManager.toys, id, updatedToy.id){ completed ->
                            if(completed){
                                Log.d(TAG, "Toy likes updated")
                            } else {
                                error = true
                                Log.d(TAG, "Failed to update likes")
                            }
                        }
                    }

                    if(!error){
                        toyManager.syncToys(room)
                        notificationService.showBasicNotification(
                            context.getString(R.string.notif_channel_main),
                            context.getString(R.string.success),
                            context.getString(R.string.toy_updated_successfully),
                            NotificationManager.IMPORTANCE_HIGH
                        )
//                    navigateTo(Route.ToyPage.route + "?id=$updatedId", navController)
                        navigateTo(Route.Home.route, navController)
                    }

                    if(error){
                        Toast.makeText(context, "An error occurred", Toast.LENGTH_SHORT).show()
                    }
                }

            } else {
                Toast.makeText(context, viewModel.validate().exceptionOrNull()?.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}