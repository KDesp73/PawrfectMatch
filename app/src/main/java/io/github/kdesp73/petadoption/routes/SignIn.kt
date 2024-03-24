package io.github.kdesp73.petadoption.routes

import android.app.NotificationManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.kdesp73.petadoption.NotificationService
import io.github.kdesp73.petadoption.R
import io.github.kdesp73.petadoption.ui.utils.Center

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
fun SignIn(){
    val textModifier: Modifier = Modifier
    val iconButtonModifier: Modifier = Modifier.fillMaxWidth()
    val notificationService = NotificationService(context = LocalContext.current)

    BoxWithConstraints(modifier = Modifier
        .background(MaterialTheme.colorScheme.background)
        .fillMaxSize()
    ){
        Center(modifier = Modifier.fillMaxSize()) {
            Column (modifier = Modifier.fillMaxSize()){
                Text(modifier = textModifier, text = "Create an Account")
                AuthActionButton(
                    textModifier = textModifier,
                    iconButtonModifier = iconButtonModifier,
                    contentDesciption = "Sign Up with Google",
                    label = "Using Google",
                    icon = Icons.Filled.AccountCircle // TODO: Replace with Google icon
                ) {
                    // TODO: Sign Up with google action
                    notificationService.showBasicNotification(
                        R.string.DEBUG.toString(),
                        "Email Sign up",
                        "Sign Up with Email pressed",
                        NotificationManager.IMPORTANCE_HIGH
                    )
                }
                AuthActionButton(
                    textModifier = textModifier,
                    iconButtonModifier = iconButtonModifier,
                    contentDesciption = "Sign Up with Email",
                    label = "Using Email",
                    icon = Icons.Filled.Email
                ) {
                    // TODO: Sign Up with email action
                }
                Text(text = "Or Log In")
                AuthActionButton(
                    textModifier = textModifier,
                    iconButtonModifier = iconButtonModifier,
                    contentDesciption = "Log In with Google",
                    label = "Using Google",
                    icon = Icons.Filled.AccountCircle // TODO: Replace with Google icon
                ) {
                    // TODO: Log in with google action
                }
                AuthActionButton(
                    textModifier = textModifier,
                    iconButtonModifier = iconButtonModifier,
                    contentDesciption = "Log In with Email",
                    label = "Using Email",
                    icon = Icons.Filled.Email
                ) {
                    // TODO: Log in with email action
                }
            }

        }
    }
}

@Preview
@Composable
fun SignInPreview(){
    SignIn()
}