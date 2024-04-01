package io.github.kdesp73.petadoption.routes

import android.util.Log
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
import com.google.firebase.firestore.FirebaseFirestore
import io.github.kdesp73.petadoption.R
import io.github.kdesp73.petadoption.Route
import io.github.kdesp73.petadoption.User
import io.github.kdesp73.petadoption.UserManager
import io.github.kdesp73.petadoption.room.AppDatabase
import io.github.kdesp73.petadoption.ui.components.AccountPreview
import io.github.kdesp73.petadoption.ui.components.HalfButton


private const val TAG = "Account"

@Composable
fun Account(navController: NavController?, firestore: FirebaseFirestore?, roomDatabase: AppDatabase?){
    val userManager = UserManager()

    val email = roomDatabase!!.userDao().getEmail()
    val user: User = User.example // TODO: get from firestore using room api email
    userManager.getUserByEmail(email){ list ->
        if (list.size == 1){
            firestore!!.collection("Users")
                .document(list[0])
                .get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                    } else {
                        Log.d(TAG, "No such document")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, exception.toString())
                }
        } else {
            Log.e(TAG, "Multiple accounts with the same email found. Fix your database")
        }
    }

    Column (
        modifier = Modifier.padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)

    ){
        AccountPreview(pic = R.drawable.profile_pic_placeholder, user = user, navController = navController)
        Row (
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
        ){
            val buttonHeight: Dp = 80.dp
            HalfButton (height = buttonHeight, icon = Icons.Filled.Add, text = "Add a Pet"){
                navController?.navigate(Route.AddPet.route)
            }
            HalfButton (height = buttonHeight, icon = Icons.Filled.Add, text = "Add a Toy"){
                navController?.navigate(Route.AddToy.route)
            }
        }
    }
}

@Preview
@Composable
fun AccountPagePreview(){
    Account(
        null,
        null,
        null
    )
}
