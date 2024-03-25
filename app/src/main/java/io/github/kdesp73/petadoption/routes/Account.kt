package io.github.kdesp73.petadoption.routes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import io.github.kdesp73.petadoption.ProfileInfo
import io.github.kdesp73.petadoption.R
import io.github.kdesp73.petadoption.ui.components.AccountPreview
import io.github.kdesp73.petadoption.ui.components.HalfButton

@Composable
fun Account(navController: NavController?){
    val info = ProfileInfo()
    // val user: FirebaseUser? = FirebaseAuth.getInstance().currentUser

    // TODO: Get info from user

    Column (
        modifier = Modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp)

    ){
        AccountPreview(pic = R.drawable.profile_pic_placeholder, info = info, navController = navController)
        Row (
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
        ){
            HalfButton (icon = Icons.Filled.Add, text = "Add a Pet"){
                // TODO: Action
            }
            HalfButton (icon = Icons.Filled.Add, text = "Add a Toy"){
                // TODO: Action
            }
        }
    }
}

@Preview
@Composable
fun AccountPagePreview(){
    Account(null)
}
