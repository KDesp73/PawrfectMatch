package io.github.kdesp73.petadoption.routes

import android.annotation.SuppressLint
import android.app.NotificationManager
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import io.github.kdesp73.petadoption.NotificationService
import io.github.kdesp73.petadoption.R
import io.github.kdesp73.petadoption.Route
import io.github.kdesp73.petadoption.clearBackTracks
import io.github.kdesp73.petadoption.firebase.FirestorePet
import io.github.kdesp73.petadoption.firebase.FirestoreToy
import io.github.kdesp73.petadoption.firebase.ImageManager
import io.github.kdesp73.petadoption.firebase.LikedManager
import io.github.kdesp73.petadoption.firebase.ToyManager
import io.github.kdesp73.petadoption.firebase.User
import io.github.kdesp73.petadoption.firebase.UserManager
import io.github.kdesp73.petadoption.navigateTo
import io.github.kdesp73.petadoption.room.AppDatabase
import io.github.kdesp73.petadoption.room.LocalPet
import io.github.kdesp73.petadoption.room.LocalToy
import io.github.kdesp73.petadoption.ui.components.Center
import io.github.kdesp73.petadoption.ui.components.CircularIconButton
import io.github.kdesp73.petadoption.ui.components.CircularImage
import io.github.kdesp73.petadoption.ui.components.InfoBox
import io.github.kdesp73.petadoption.ui.components.InfoBoxClickable
import io.github.kdesp73.petadoption.ui.components.LoadingAnimation
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow

private const val TAG = "PetPage"


@Composable
private fun InfoBoxRow(content: @Composable () -> Unit){
    Row (
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ){
        content()
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(DelicateCoroutinesApi::class)
@Composable
private fun Showcase(toy: LocalToy, uri: String?, navController: NavController, room: AppDatabase) {
    val context = LocalContext.current
    val toyManager = ToyManager()
    val userManager = UserManager()
    val imageManager = ImageManager()
    val likedManager = LikedManager()
    var owner by remember { mutableStateOf<User?>(null) }
    var liked by remember { mutableStateOf(false) }
    val email = room.userDao().getEmail() ?: return
    val toyId = toy.generateId()

    LaunchedEffect(key1 = null) {
        val deferredResult = GlobalScope.async {
            likedManager.isLiked(LikedManager.toys, email, toyId)
        }

        liked = deferredResult.await() == true
        Log.d(TAG, "isLiked: $liked")
    }

    LaunchedEffect(key1 = owner) {
        val userDeferredResult: Deferred<User?> = GlobalScope.async {
            userManager.getUserByEmail(toy.ownerEmail)
        }

        owner = userDeferredResult.await()
    }

    Surface {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {

            if(owner == null){
                Center(modifier = Modifier.fillMaxSize()) {
                    LoadingAnimation(64.dp)
                }
            } else {
                val painter = rememberAsyncImagePainter(model = uri)
                val heart = MutableStateFlow<ImageVector>(if (liked) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder)
                CircularImage(painter = painter, contentDescription = "Toy image", size = 200.dp)
                Row (
                    horizontalArrangement = Arrangement.spacedBy(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(text = toy.name, fontSize = 6.em, color = MaterialTheme.colorScheme.primary)
                    if(toy.ownerEmail != email){
                        CircularIconButton(
                            state = heart,
                            description = "",
                            bg = MaterialTheme.colorScheme.background,
                            size = 40.dp
                        ) {
                            liked = !liked
                            heart.value = if(liked) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder

                            Log.d(TAG, "Updated like: $liked")

                            if(liked){
                                likedManager.like(LikedManager.toys, email, toyId){ completed ->
                                    Log.d(TAG, "Liked toy: $completed")
                                }
                            } else {
                                likedManager.unlike(LikedManager.toys, email, toyId) { completed ->
                                    Log.d(TAG, "Unliked toy: $completed")
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(5.dp))

                InfoBoxRow {
                    InfoBox(label = "Location", info = toy.location)
                    InfoBox(label = "Price", info = toy.price.toString() + "$")
                }
                Spacer(modifier = Modifier.height(5.dp))
                if(toy.ownerEmail == email){
                    Column (
                        verticalArrangement = Arrangement.spacedBy(15.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        Button(
                            colors = ButtonColors(
                                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                                contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                                disabledContainerColor = ButtonDefaults.buttonColors().disabledContainerColor,
                                disabledContentColor = ButtonDefaults.buttonColors().disabledContentColor
                            ),
                            onClick = {
                                navigateTo(
                                    Route.EditToy.route + "?id=${toyId}",
                                    navController,
                                )
                            }
                        ) {
                            Row (
                                horizontalArrangement = Arrangement.SpaceEvenly,
                                verticalAlignment = Alignment.CenterVertically
                            ){
                                Icon(imageVector = Icons.Filled.Edit, contentDescription = "Edit Icon")
                                Text(text = stringResource(R.string.edit))
                            }
                        }

                    }
                    Button(
                        colors = ButtonColors(
                            containerColor = Color.Red,
                            contentColor = Color.White,
                            disabledContainerColor = ButtonDefaults.buttonColors().disabledContainerColor,
                            disabledContentColor = ButtonDefaults.buttonColors().disabledContentColor
                        ),
                        onClick = {
                            var deleted: Boolean = true
                            toyManager.deleteToyById(toy.generateId()) { completed ->
                                deleted = deleted and completed
                            }

                            imageManager.deleteImage(ImageManager.toys + toy.generateId() + ".jpg") { completed ->
                                deleted = deleted and completed
                            }

                            likedManager.removeAllLikes(LikedManager.toys, toy.generateId()){ completed ->
                                deleted = deleted and completed
                            }

                            room.toyDao().delete(toy)

                            val notificationService = NotificationService(context)
                            if (deleted){
                                navigateTo(
                                    route = Route.Home.route,
                                    navController = navController,
                                    popUpToStartDestination = true,
                                    saveStateOnPopUpTo = false,
                                    restore = false
                                )
                                notificationService.showBasicNotification(
                                    context.getString(R.string.notif_channel_main),
                                    context.getString(R.string.success),
                                    content = context.getString(R.string.deleted_successfully, toy.name),
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
                } else {
                    if(owner?.info?.firstName != null){
                        InfoBoxClickable(
                            label = stringResource(R.string.owner),
                            width = 200.dp,
                            height = 100.dp,
                            infoFontSize = 4.em.value,
                            info = (owner?.info?.firstName)
                        ) {
                            navigateTo(
                                Route.UserPage.route + "?email=${owner?.email}",
                                navController = navController,
                                popUpToStartDestination = false
                            )
                        }
                    } else {
                        LoadingAnimation()
                    }
                }

            }
        }
    }

}

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(DelicateCoroutinesApi::class)
@Composable
fun ShowToy(id: String = "", room: AppDatabase, navController: NavController){
    val toyManager = ToyManager()
    val imageManager = ImageManager()

    var toy by remember { mutableStateOf<LocalToy?>(null) }
    var uri by remember { mutableStateOf<Uri?>(null) }

    if(id.all { it.isDigit() }) {
        // Room Pet
        toy = room.toyDao().selectToyFromId(id.toInt())
        LaunchedEffect(toy) {
            if(toy != null){
                val deferredResult: Deferred<Uri?> = GlobalScope.async {
                    imageManager.getImageUrl(ImageManager.toys+ toy!!.generateId() + ".jpg")
                }

                uri = deferredResult.await()
            }
        }
    } else {
        // Firebase Pet
        var firebaseToy by remember{ mutableStateOf<FirestoreToy?>(null) }

        LaunchedEffect(firebaseToy) {
            val deferredResult: Deferred<FirestoreToy?> = GlobalScope.async {
                toyManager.getToyById(id)
            }

            firebaseToy = deferredResult.await()
        }

        var firebaseUri by remember { mutableStateOf<Uri?>(null) }
        LaunchedEffect(firebaseUri, firebaseToy) {
            if (firebaseToy != null) {
                val imageDeferredResult: Deferred<Uri?> = GlobalScope.async {
                    imageManager.getImageUrl(ImageManager.toys+ id + ".jpg")
                }

                firebaseUri = imageDeferredResult.await()
            }
        }


        toy = if (firebaseToy == null) null else LocalToy(firebaseToy!!)
        uri = firebaseUri
    }

    if (toy != null && uri != null) {
        val scrollState = rememberScrollState()
        Column (
            modifier = Modifier.verticalScroll(scrollState),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Showcase(toy = toy!!, uri.toString(), navController, room)
        }
    } else {
        Center(modifier = Modifier.fillMaxSize()) {
            LoadingAnimation(64.dp)
        }
    }
}