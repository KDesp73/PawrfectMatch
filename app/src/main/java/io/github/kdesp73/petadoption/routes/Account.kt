package io.github.kdesp73.petadoption.routes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import io.github.kdesp73.petadoption.ProfileInfo
import io.github.kdesp73.petadoption.R
import io.github.kdesp73.petadoption.ui.components.AccountPreview
import io.github.kdesp73.petadoption.ui.components.SettingsButton

@Composable
fun Account(navController: NavController?){
    val info = ProfileInfo()
    val user: FirebaseUser? = FirebaseAuth.getInstance().currentUser

    // TODO: Get info from user

    Column {
        AccountPreview(pic = R.drawable.profile_pic_placeholder, info = info, navController = navController)
        Row {
            SettingsButton(navController = navController)
            // TODO: Add pet button
        }
    }
}

@Preview
@Composable
fun AccountPagePreview(){
    Account(null)
}
