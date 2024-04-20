package io.github.kdesp73.petadoption.routes

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import io.github.kdesp73.petadoption.R
import io.github.kdesp73.petadoption.Route
import io.github.kdesp73.petadoption.enums.CustomAlignment
import io.github.kdesp73.petadoption.firestore.ImageManager
import io.github.kdesp73.petadoption.navigateTo
import io.github.kdesp73.petadoption.room.AppDatabase
import io.github.kdesp73.petadoption.room.LocalUser
import io.github.kdesp73.petadoption.ui.components.AccountPreview
import io.github.kdesp73.petadoption.ui.components.HalfButton
import io.github.kdesp73.petadoption.ui.components.VerticalScaffold
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking


private const val TAG = "Account"

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(DelicateCoroutinesApi::class)
@Composable
fun Account(navController: NavController?, roomDatabase: AppDatabase?){
    val userDao = roomDatabase?.userDao()
    val userList = userDao?.getUsers()
    var user: LocalUser? = null

    if(userList?.isNotEmpty() == true){
        user = userList[0]
    }


    VerticalScaffold(
        modifier = Modifier.padding(6.dp),
        top = {
            Column (
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ){
                userDao?.let { Log.d(TAG, it.getImageUrl() ?: "") }
                AccountPreview(user = user, navController = navController)

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
                            text = stringResource(R.string.add_a_pet)
                        ) {
                            if (navController != null) {
                                navigateTo(
                                    Route.AddPet.route,
                                    navController,
                                    popUpToStartDestination = false,
                                    launchAsSingleTop = false
                                )
                            }
                        }
                        HalfButton(
                            height = buttonHeight,
                            icon = Icons.Filled.Add,
                            text = stringResource(R.string.add_a_toy)
                        ) {
                            if (navController != null) {
                                navigateTo(
                                    Route.AddToy.route,
                                    navController,
                                    popUpToStartDestination = false,
                                    launchAsSingleTop = false
                                )
                            }
                        }
                    }
                }
            }
        },
        bottomAlignment = CustomAlignment.END,
        bottom = {
            if(user?.loggedIn == true) {
                Button(onClick = {
                    if (navController != null) {
                        navigateTo(
                            Route.MyPets.route,
                            navController = navController,
                            popUpToStartDestination = false,
                            launchAsSingleTop = false
                        )
                    }
                }) {
                    Text(text = stringResource(R.string.my_pets))
                }
            }
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
