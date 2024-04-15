package io.github.kdesp73.petadoption.ui.components

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import coil.compose.rememberAsyncImagePainter
import io.github.kdesp73.petadoption.R
import io.github.kdesp73.petadoption.Route
import io.github.kdesp73.petadoption.room.LocalUser
import io.github.kdesp73.petadoption.ui.utils.Center

@Composable
fun AccountPreview(pic: Int, user: LocalUser?, navController: NavController?){
    val imageSize = 135.dp
    val containerHeight = imageSize + 100.dp

    Surface(
        color = MaterialTheme.colorScheme.primary,
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(containerHeight)
            .clickable(onClick = {
                navController?.navigate(if (user == null || !user.loggedIn) Route.SignIn.route else Route.AccountSettings.route) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
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

@Composable
fun AccountPreview(user: LocalUser?, navController: NavController?){
    if(user?.imageUrl == null){
        AccountPreview(pic = R.drawable.profile_pic_placeholder, user = user, navController = navController)
        return
    }

    val imageSize = 135.dp
    val containerHeight = imageSize + 100.dp

    Surface(
        color = MaterialTheme.colorScheme.primary,
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(containerHeight)
            .clickable(onClick = {
                navController?.navigate(if (user == null || !user.loggedIn) Route.SignIn.route else Route.AccountSettings.route) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
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
                val imagePainter = rememberAsyncImagePainter(user.imageUrl)
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
@Preview
@Composable
private fun AccountPreviewPreview(){
    val info = LocalUser.example
    AccountPreview(R.drawable.profile_pic_placeholder, info, null)
}