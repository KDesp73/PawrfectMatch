package io.github.kdesp73.petadoption.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import io.github.kdesp73.petadoption.Gender
import io.github.kdesp73.petadoption.ProfileInfo
import io.github.kdesp73.petadoption.R


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
            painter = painterResource(id = R.drawable.profile_pic_placeholder),
            contentDescription = "profile image",
            modifier = modifier
                .size(size),
            contentScale = ContentScale.Crop
        )

    }
}

@Composable
fun AccountPreview(pic: Int, info: ProfileInfo, navController: NavController?){
    val imageSize = 135.dp
    val containerHeight = imageSize + 40.dp

    Surface(
        color = MaterialTheme.colorScheme.primary,
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .height(containerHeight)
            .clickable(onClick = {
                navController?.navigate("Edit Account"){
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true

                }
            })
            .padding(2.dp)
    ) {
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

@Preview
@Composable
private fun AccountPreviewPreview(){
    val info = ProfileInfo("Konstantinos", "Despoinidis", "kdesp2003@gmail.com", "123456789", "Thessaloniki", Gender.MALE)
    AccountPreview(R.drawable.profile_pic_placeholder, info, null)
}