package io.github.kdesp73.petadoption.routes

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import io.github.kdesp73.petadoption.NotificationService
import io.github.kdesp73.petadoption.R
import io.github.kdesp73.petadoption.Route
import io.github.kdesp73.petadoption.enums.genderFromValue
import io.github.kdesp73.petadoption.enums.petAgeFromValue
import io.github.kdesp73.petadoption.enums.petSizeFromValue
import io.github.kdesp73.petadoption.enums.petTypeFromValue
import io.github.kdesp73.petadoption.firestore.ImageManager
import io.github.kdesp73.petadoption.firestore.FirestorePet
import io.github.kdesp73.petadoption.firestore.PetManager
import io.github.kdesp73.petadoption.firestore.User
import io.github.kdesp73.petadoption.firestore.UserManager
import io.github.kdesp73.petadoption.navigateTo
import io.github.kdesp73.petadoption.room.AppDatabase
import io.github.kdesp73.petadoption.room.LocalPet
import io.github.kdesp73.petadoption.ui.components.CircularImage
import io.github.kdesp73.petadoption.ui.components.InfoBox
import io.github.kdesp73.petadoption.ui.components.InfoBoxClickable
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

private const val TAG = "PetPage"

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(DelicateCoroutinesApi::class)
@Composable
private fun Showcase(pet: LocalPet, uri: String?) {
    val userManager = UserManager()

    val userDeferredResult: Deferred<User?> = GlobalScope.async {
        userManager.getUserByEmail(pet.ownerEmail)
    }

    val owner: User?
    runBlocking {
        owner = userDeferredResult.await()
    }


    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        val painter = rememberAsyncImagePainter(model = uri)
        CircularImage(painter = painter, contentDescription = "Pet image", size = 200.dp)
        Text(text = pet.name, fontSize = 6.em, color = MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.height(5.dp))
        @Composable
        fun InfoBoxRow(content: @Composable () -> Unit){
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ){
                content()
            }
        }

        InfoBoxRow {
            InfoBox(label = stringResource(id = R.string.type), info = petTypeFromValue[pet.type]?.label)
            InfoBox(label = stringResource(R.string.gender), info = genderFromValue[pet.gender]?.label)
        }
        InfoBoxRow {
            InfoBox(label = stringResource(id = R.string.age), info = petAgeFromValue[pet.age]?.label)
            InfoBox(label = stringResource(id = R.string.size), info = petSizeFromValue[pet.size]?.label)
        }
        Spacer(modifier = Modifier.height(5.dp))
        InfoBoxClickable(
            label = stringResource(R.string.owner),
            width = 200.dp,
            height = 100.dp,
            infoFontSize = 4.em.value,
            info = (owner?.info?.firstName)
        ) {
            // TODO: Navigate to users profile page
        }
    }

}

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(DelicateCoroutinesApi::class)
@Composable
fun ShowPet(id: String = "", room: AppDatabase, navController: NavController){
    val petManager = PetManager()
    val imageManager = ImageManager()
    val context = LocalContext.current

    var pet by remember { mutableStateOf<LocalPet?>(null) }
    var uri by remember { mutableStateOf<Uri?>(null) }

    if(id.all { it.isDigit() }) {
        // Room Pet
        pet = room.petDao().selectPetFromId(id.toInt())
        LaunchedEffect(pet) {
            if(pet != null){
                val deferredResult: Deferred<Uri?> = GlobalScope.async {
                    imageManager.getImageUrl(ImageManager.pets + pet!!.generateId() + ".jpg")
                }

                uri = deferredResult.await()
            }
        }
    } else {
        // Firebase Pet
        var firebasePet by remember{ mutableStateOf<FirestorePet?>(null) }
        LaunchedEffect(firebasePet) {
            val deferredResult: Deferred<FirestorePet?> = GlobalScope.async {
                petManager.getPetByEmailAndId(room.userDao().getEmail(), id)
            }

            firebasePet = deferredResult.await()
        }

        var firebaseUri by remember { mutableStateOf<Uri?>(null) }
        LaunchedEffect(firebaseUri) {
            if (firebasePet != null) {
                val imageDeferredResult: Deferred<Uri?> = GlobalScope.async {
                    imageManager.getImageUrl(ImageManager.pets + firebasePet!!.getImageFile())
                }

                firebaseUri = imageDeferredResult.await()
            }
        }

        pet = LocalPet(firebasePet!!)
        uri = firebaseUri
    }

    if (pet != null) {
        val scrollState = rememberScrollState()
        Column (
            modifier = Modifier.verticalScroll(scrollState),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Showcase(pet = pet!!, uri.toString())

            if(pet!!.ownerEmail == room.userDao().getEmail()) {
                Button(
                    colors = ButtonColors(
                        containerColor = Color.Red,
                        contentColor = Color.White,
                        disabledContainerColor = ButtonDefaults.buttonColors().disabledContainerColor,
                        disabledContentColor = ButtonDefaults.buttonColors().disabledContentColor

                    ),
                    onClick = {
                        var deleted: Boolean = true
                        petManager.deletePetById(pet!!.generateId()) { completed ->
                            deleted = deleted and completed
                        }

                        imageManager.deleteImage(ImageManager.pets + pet!!.generateId() + ".jpg") { completed ->
                            deleted = deleted and completed
                        }

                        val notificationService = NotificationService(context)
                        if (deleted){
                            room.petDao().delete(pet!!)
                            navigateTo(Route.Home.route, navController)
                            notificationService.showBasicNotification(
                                context.getString(R.string.notif_channel_main),
                                context.getString(R.string.success),
                                content = context.getString(R.string.deleted_successfully, pet!!.name),
                                NotificationManager.IMPORTANCE_DEFAULT
                            )

                        }
                    }
                ) {
                    Row (
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Icon(imageVector = Icons.Filled.Delete, contentDescription = "Delete Icon")
                        Text(text = "Delete")
                    }
                }
            }
        }
    }


}