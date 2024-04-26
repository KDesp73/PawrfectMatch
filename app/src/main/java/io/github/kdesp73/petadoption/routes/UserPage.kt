package io.github.kdesp73.petadoption.routes

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import io.github.kdesp73.petadoption.R
import io.github.kdesp73.petadoption.enums.genderFromValue
import io.github.kdesp73.petadoption.firebase.ImageManager
import io.github.kdesp73.petadoption.firebase.User
import io.github.kdesp73.petadoption.firebase.UserManager
import io.github.kdesp73.petadoption.ui.components.Center
import io.github.kdesp73.petadoption.ui.components.CircularImage
import io.github.kdesp73.petadoption.ui.components.InfoBox
import io.github.kdesp73.petadoption.ui.components.LoadingAnimation
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun UserPage(email: String, navController: NavController){
    val userManager = UserManager()
    val imageManager = ImageManager()

    var user by remember { mutableStateOf<User?>(null) }
    var uri by remember { mutableStateOf<Uri?>(null) }

    LaunchedEffect(key1 = user) {
        val deferredResult = GlobalScope.async {
            userManager.getUserByEmail(email)
        }

        user = deferredResult.await()
    }

    LaunchedEffect(key1 = uri) {
        val deferredResult = GlobalScope.async {
            imageManager.getImageUrl(ImageManager.users + email + ".jpg")
        }

        uri = deferredResult.await()
    }

    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        @Composable
        fun InfoBoxRow(content: @Composable () -> Unit){
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ){
                content()
            }
        }


        if(user != null){
            CircularImage(uri = uri, contentDescription = "Profile Image", size = 200.dp)
            InfoBoxRow {
                InfoBox(label = stringResource(id = R.string.first_name), info = user?.info?.firstName)
                InfoBox(label = stringResource(id = R.string.last_name), info = user?.info?.lastName)
            }
            InfoBoxRow {
                InfoBox(label = stringResource(id = R.string.gender), info = genderFromValue[user?.info?.gender]?.label)
                InfoBox(label = stringResource(id = R.string.location), info = user?.info?.location)
            }
            InfoBox(width = 280.dp, label = stringResource(id = R.string.email), info = email)
            InfoBox(width = 280.dp, label = stringResource(id = R.string.phone), info = user?.info?.phone)
        } else {
            Center(modifier = Modifier.fillMaxSize()) {
                LoadingAnimation(64.dp)
            }
        }
    }
}