package io.github.kdesp73.petadoption.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import io.github.kdesp73.petadoption.Route
import io.github.kdesp73.petadoption.navigateTo

@Composable
fun PleaseLogin(msg: String? = "Please login first to use this feature", email: String?, navController: NavController){
    Center(modifier = Modifier.fillMaxSize()) {
        if(msg != null){
            Text(text = msg)
        }
        IconButton(text = "Login", imageVector = Icons.Filled.AccountCircle) {
            navigateTo(Route.Login.route, navController)
        }
    }

}