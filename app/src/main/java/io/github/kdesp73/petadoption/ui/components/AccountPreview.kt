package io.github.kdesp73.petadoption.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import io.github.kdesp73.petadoption.R
import io.github.kdesp73.petadoption.Route
import io.github.kdesp73.petadoption.User
import io.github.kdesp73.petadoption.ui.utils.Center


@Composable
private fun ProfileImage(modifier: Modifier = Modifier, pic: Int, size: Dp) {
    Surface(
        modifier = Modifier
            .size(154.dp)
            .padding(5.dp),
        shape = CircleShape,
        border = BorderStroke(0.5.dp, Color.LightGray),
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
    ) {
        Image(
            painter = painterResource(id = pic),
            contentDescription = "profile image",
            modifier = modifier
                .size(size),
            contentScale = ContentScale.Crop
        )

    }
}

@Composable
fun AccountPreview(pic: Int, info: User?, navController: NavController?){
    val imageSize = 135.dp
    val containerHeight = imageSize + 50.dp

    Surface(
        color = MaterialTheme.colorScheme.primary,
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(containerHeight)
            .clickable(onClick = {
                navController?.navigate(if (info == null) Route.SignIn.route else Route.EditAccount.route) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true

                }
            })
            .padding(8.dp)
    ) {
        if (info == null){
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
                ProfileImage(pic = pic, size = imageSize)
                info.ToComposable(containerHeight)
            }
        }
    }
}

@Preview
@Composable
private fun AccountPreviewPreview(){
    val info = null
    AccountPreview(R.drawable.profile_pic_placeholder, info, null)
}