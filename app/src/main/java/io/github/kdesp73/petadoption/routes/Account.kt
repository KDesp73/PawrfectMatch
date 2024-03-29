package io.github.kdesp73.petadoption.routes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import io.github.kdesp73.petadoption.R
import io.github.kdesp73.petadoption.enums.Routes
import io.github.kdesp73.petadoption.ui.components.AccountPreview
import io.github.kdesp73.petadoption.ui.components.HalfButton

@Composable
fun Account(navController: NavController?){
    // val info = ProfileInfo()
    val info = null
    // val user: FirebaseUser? = FirebaseAuth.getInstance().currentUser

    // TODO: Get info from user

    Column (
        modifier = Modifier.padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)

    ){
        AccountPreview(pic = R.drawable.profile_pic_placeholder, info = info, navController = navController)
        Row (
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
        ){
            val buttonHeight: Dp = 80.dp
            HalfButton (height = buttonHeight, icon = Icons.Filled.Add, text = "Add a Pet"){
                navController?.navigate(Routes.ADD_PET.label)
            }
            HalfButton (height = buttonHeight, icon = Icons.Filled.Add, text = "Add a Toy"){
                navController?.navigate(Routes.ADD_TOY.label)
            }
        }
    }
}

@Preview
@Composable
fun AccountPagePreview(){
    Account(null)
}
