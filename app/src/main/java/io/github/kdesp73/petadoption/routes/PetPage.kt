package io.github.kdesp73.petadoption.routes

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import io.github.kdesp73.petadoption.R
import io.github.kdesp73.petadoption.enums.genderFromValue
import io.github.kdesp73.petadoption.enums.petAgeFromValue
import io.github.kdesp73.petadoption.enums.petSizeFromValue
import io.github.kdesp73.petadoption.enums.petTypeFromValue
import io.github.kdesp73.petadoption.firestore.ImageManager
import io.github.kdesp73.petadoption.firestore.Pet
import io.github.kdesp73.petadoption.firestore.PetManager
import io.github.kdesp73.petadoption.firestore.User
import io.github.kdesp73.petadoption.firestore.UserManager
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
        Text(text = pet.name, fontSize = 5.em)
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
    if(id.all { it.isDigit() }) {
        // Room Pet
        val pet = room.petDao().selectPetFromId(id.toInt())

        Log.d(TAG, pet.imageUri)
        Showcase(pet = pet, pet.imageUri)
    } else {
        // Firebase Pet
        val petManager = PetManager()
        val imageManager = ImageManager()
        val deferredResult: Deferred<Pet?> = GlobalScope.async {
            petManager.getPetByEmailAndId(room.userDao().getEmail(), id)
        }

        val pet: Pet?
        runBlocking {
            pet = deferredResult.await()
        }

        if (pet != null) {
            val imageDeferredResult: Deferred<Uri?> = GlobalScope.async {
                imageManager.getImageUrl(ImageManager.pets + pet.getImageFile())
            }

            val uri: Uri?
            runBlocking {
                uri = imageDeferredResult.await()
            }

            Showcase(pet = LocalPet(pet), uri.toString())
        }
    }
}