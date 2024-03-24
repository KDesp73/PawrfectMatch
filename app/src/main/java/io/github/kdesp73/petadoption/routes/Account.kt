package io.github.kdesp73.petadoption.routes

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import io.github.kdesp73.petadoption.Gender
import io.github.kdesp73.petadoption.ProfileInfo
import io.github.kdesp73.petadoption.R
import io.github.kdesp73.petadoption.ui.components.AccountPreview

@Composable
fun Account(navController: NavController?){
    val info = ProfileInfo("Konstantinos", "Despoinidis", "kdesp2003@gmail.com", "123456789", "Thessaloniki", Gender.MALE)
    AccountPreview(pic = R.drawable.profile_pic_placeholder, info = info, navController = navController)
}

@Preview
@Composable
fun AccountPagePreview(){
    Account(null)
}
