package io.github.kdesp73.petadoption.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination

@Composable
fun SettingsButton(height: Dp = 60.dp, navController: NavController?){
    val configuration = LocalConfiguration.current

    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

    Button(
        modifier = Modifier
            .width(screenWidth / 2 - 4.dp)
            .height(height)
            .padding(8.dp),
        onClick = {
        navController?.navigate("Settings") {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }) {
        Text(text = "Settings")
    }

}

@Preview
@Composable
fun SettingsButtonPreview(){
    SettingsButton(navController = null)
}