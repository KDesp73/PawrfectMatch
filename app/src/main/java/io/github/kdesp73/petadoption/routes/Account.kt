package io.github.kdesp73.petadoption.routes

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import io.github.kdesp73.petadoption.ProfileInfo
import io.github.kdesp73.petadoption.R
import io.github.kdesp73.petadoption.enums.Gender
import io.github.kdesp73.petadoption.enums.ProfileType
import io.github.kdesp73.petadoption.ui.components.AccountPreview

@Composable
fun Account(navController: NavController?){
    val info = ProfileInfo(
        firstName = "Konstantinos",
        lastName = "Despoinidis",
        email = "kdesp2003@gmail.com",
        phone = "123456789",
        location = "Thessaloniki",
        gender = Gender.MALE,
        profileType = ProfileType.INDIVIDUAL
    )
    val user: FirebaseUser? = FirebaseAuth.getInstance().currentUser


    AccountPreview(user, pic = R.drawable.profile_pic_placeholder, info = info, navController = navController)
}

@Preview
@Composable
fun AccountPagePreview(){
    Account(null)
}
