package io.github.kdesp73.petadoption.ui.components

import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import coil.compose.rememberAsyncImagePainter
import io.github.kdesp73.petadoption.R
import io.github.kdesp73.petadoption.Route
import io.github.kdesp73.petadoption.firestore.ImageManager
import io.github.kdesp73.petadoption.firestore.User
import io.github.kdesp73.petadoption.navigateTo
import io.github.kdesp73.petadoption.room.LocalUser
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

@Composable
fun AccountPreview(pic: Int, user: LocalUser?, navController: NavController?){
    val imageSize = 135.dp
    val containerHeight = imageSize + 100.dp

    Surface(
        color = MaterialTheme.colorScheme.secondaryContainer,
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(containerHeight)
            .clickable(onClick = {
                if (navController != null) {
                    navigateTo(
                        if (user == null || !user.loggedIn) Route.SignIn.route else Route.AccountSettings.route,
                        navController
                    )
                }
            })
            .padding(8.dp)
    ) {
        if (user == null || !user.loggedIn){
            Center(modifier = Modifier) {
                Row (
                    horizontalArrangement = Arrangement.spacedBy(20.dp)
                ){
                    Icon(
                        imageVector = Icons.Filled.Person,
                        contentDescription = "Person Icon",
                        modifier = Modifier.size(40.dp)
                    )
                    Text(
                        fontSize = 6.em,
                        text = "Sign Up / Log In"
                    )
                }
            }
        } else {
            Row (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                CircularImage(pic = pic, size = imageSize)
                user.ToComposable(containerHeight)
            }
        }
    }
}


@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(DelicateCoroutinesApi::class)
@Composable
fun AccountPreview(user: LocalUser?, navController: NavController?){
    var imageUri by remember { mutableStateOf(Uri.EMPTY) }
    val imageManager = ImageManager()

    if(user != null && user.loggedIn){
        LaunchedEffect(imageUri) {
            val imageDeferredResult: Deferred<Uri?> = GlobalScope.async {
                imageManager.getImageUrl(ImageManager.users + user.email + ".jpg")
            }

            imageUri = imageDeferredResult.await()
        }
    }

    val imageSize = 135.dp
    val containerHeight = imageSize + 100.dp
    if ((imageUri == Uri.EMPTY || imageUri == null) && (user?.imageUrl == null || user.imageUrl == "null" || user.imageUrl!!.isEmpty())) {
        AccountPreview(
            pic = R.drawable.profile_pic_placeholder,
            user = user,
            navController = navController
        )
    } else {
        Surface(
            color = MaterialTheme.colorScheme.secondaryContainer,
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(containerHeight)
                .clickable(onClick = {
                    if (user != null) {
                        if (navController != null) {
                            navigateTo(
                                if (!user.loggedIn) Route.SignIn.route else Route.AccountSettings.route,
                                navController,
                            )
                        }
                    }
                })
                .padding(8.dp)
        ) {
            if (user != null) {
                if (!user.loggedIn) {
                    Center(modifier = Modifier) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(20.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Person,
                                contentDescription = "Person Icon",
                                modifier = Modifier.size(40.dp)
                            )
                            Text(
                                fontSize = 6.em,
                                text = stringResource(R.string.sign_up_log_in)
                            )
                        }
                    }
                } else {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(20.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val imagePainter = rememberAsyncImagePainter(imageUri)
                        CircularImage(
                            painter = imagePainter,
                            contentDescription = "Profile image",
                            size = imageSize,
                        )
                        user.ToComposable(containerHeight)
                    }
                }
            }
        }
    }
}
@Preview
@Composable
private fun AccountPreviewPreview(){
    val info = LocalUser.example
    AccountPreview(R.drawable.profile_pic_placeholder, info, null)
}
