package io.github.kdesp73.petadoption.routes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.navigation.NavController
import io.github.kdesp73.petadoption.NotificationService
import io.github.kdesp73.petadoption.R
import io.github.kdesp73.petadoption.Route

@Composable
fun AuthActionButton(textModifier: Modifier, iconButtonModifier: Modifier, contentDesciption: String, label: String, icon: ImageVector, action: () -> Unit){
    IconButton(
        modifier = iconButtonModifier,
        onClick = action
    ) {
        Row (
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ){
            Icon(imageVector = icon, contentDescription = contentDesciption)
            Text(modifier = textModifier, text = label)
        }
    }
}

@Composable
fun AuthActionButton(textModifier: Modifier, iconButtonModifier: Modifier, contentDesciption: String, label: String, painterRes: Int, action: () -> Unit){
    IconButton(
        modifier = iconButtonModifier,
        onClick = action
    ) {
        Row (
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ){
            Icon(
                modifier = Modifier.size(20.dp),
                painter = painterResource(id = painterRes),
                contentDescription = contentDesciption
            )
            Text(modifier = textModifier, text = label)
        }
    }
}

/*
private fun firebaseAuthWithGoogle(idToken: String, auth: FirebaseAuth) {
    val credential = GoogleAuthProvider.getCredential(idToken, null)
    auth.signInWithCredential(credential)
        .addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Log.d("GoogleSignIn", "signInWithCredential:success")
                val user = auth.currentUser
                // updateUI(user)
            } else {
                // If sign in fails, display a message to the user.
                Log.w("GoogleSignIn", "signInWithCredential:failure", task.exception)
                // updateUI(null)
            }
        }
}
*/

@Composable
fun SignIn(navController: NavController?){
    val textModifier: Modifier = Modifier
    val iconButtonModifier: Modifier = Modifier.fillMaxWidth()
    val cardModifier: Modifier = Modifier.padding(12.dp)
    val notificationService = NotificationService(context = LocalContext.current)

    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
    {
        Card (modifier = cardModifier){
            Column (
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(8.dp)
            )
            {
                Text(
                    modifier = textModifier,
                    text = "Create an Account using",
                    fontSize = 5.em
                )
                AuthActionButton(
                    textModifier = textModifier,
                    iconButtonModifier = iconButtonModifier,
                    contentDesciption = "Sign Up with Google",
                    label = "Google",
                    painterRes = R.drawable.google
                ) {
                    // TODO: Sign Up with google action
                }
                AuthActionButton(
                    textModifier = textModifier,
                    iconButtonModifier = iconButtonModifier,
                    contentDesciption = "Sign Up with Email",
                    label = "Email",
                    icon = Icons.Filled.Email
                ) {
                    navController?.navigate(Route.CreateAccount.route) {
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }
        }
        Card (modifier = cardModifier){
            Column (
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(8.dp)
            )
            {
                Text(
                    modifier = textModifier,
                    text = "Log In using",
                    fontSize = 5.em
                )
                AuthActionButton(
                    textModifier = textModifier,
                    iconButtonModifier = iconButtonModifier,
                    contentDesciption = "Log In with Google",
                    label = "Google",
                    painterRes = R.drawable.google
                ) {
                    // TODO: Log in with google action
                }
                AuthActionButton(
                    textModifier = textModifier,
                    iconButtonModifier = iconButtonModifier,
                    contentDesciption = "Log In with Email",
                    label = "Email",
                    icon = Icons.Filled.Email
                ) {
                    navController?.navigate(Route.Login.route) {
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            } // Column
        } // Card
    } // Column
}

@Preview
@Composable
fun SignInPreview(){
    SignIn(null)
}