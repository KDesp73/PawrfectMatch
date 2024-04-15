package io.github.kdesp73.petadoption.routes

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.google.firebase.firestore.FirebaseFirestore
import io.github.kdesp73.petadoption.R
import io.github.kdesp73.petadoption.Route
import io.github.kdesp73.petadoption.enums.CustomAlignment
import io.github.kdesp73.petadoption.room.AppDatabase
import io.github.kdesp73.petadoption.room.LocalUser
import io.github.kdesp73.petadoption.ui.components.AccountPreview
import io.github.kdesp73.petadoption.ui.components.HalfButton
import io.github.kdesp73.petadoption.ui.utils.VerticalScaffold


private const val TAG = "Account"

@Composable
fun Account(navController: NavController?, roomDatabase: AppDatabase?){
    val userDao = roomDatabase?.userDao()
    val userList = userDao?.getUsers()
    var user: LocalUser? = null

    if(userList?.isNotEmpty() == true){
        user = userList[0]
    }

    VerticalScaffold(
        top = {
            Column (
                modifier = Modifier.padding(6.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)

            ){
                AccountPreview(pic = R.drawable.profile_pic_placeholder, user = user, navController = navController)

                if(user?.loggedIn == true) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        val buttonHeight: Dp = 80.dp
                        HalfButton(
                            height = buttonHeight,
                            icon = Icons.Filled.Add,
                            text = "Add a Pet"
                        ) {
                            navController?.navigate(Route.AddPet.route){
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                        HalfButton(
                            height = buttonHeight,
                            icon = Icons.Filled.Add,
                            text = "Add a Toy"
                        ) {
                            navController?.navigate(Route.AddToy.route){
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
                }
            }
        },
        bottom = {
        },
        center = {

        }
    )

}

@Preview
@Composable
fun AccountPagePreview(){
    Account(
        null,
        null,
    )
}
