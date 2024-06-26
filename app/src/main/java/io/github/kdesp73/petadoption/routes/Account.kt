package io.github.kdesp73.petadoption.routes

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import io.github.kdesp73.petadoption.R
import io.github.kdesp73.petadoption.Route
import io.github.kdesp73.petadoption.enums.CustomAlignment
import io.github.kdesp73.petadoption.isLoggedIn
import io.github.kdesp73.petadoption.navigateTo
import io.github.kdesp73.petadoption.room.AppDatabase
import io.github.kdesp73.petadoption.ui.components.AccountPreview
import io.github.kdesp73.petadoption.ui.components.HalfButton
import io.github.kdesp73.petadoption.ui.components.VerticalScaffold
import kotlinx.coroutines.DelicateCoroutinesApi


private const val TAG = "Account"

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun Account(navController: NavController, roomDatabase: AppDatabase){
    val userDao = roomDatabase.userDao()
    val user = userDao.getUser()
    val scrollState = rememberScrollState()

    VerticalScaffold(
        modifier = Modifier
            .padding(6.dp)
            .fillMaxSize()
            .verticalScroll(scrollState),
        top = {
            Column (
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ){
                AccountPreview(user = user, navController = navController)

                if(isLoggedIn(roomDatabase)) {
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
                            navigateTo(
                                Route.AddPet.route,
                                navController,
                            )
                        }
                        HalfButton(
                            height = buttonHeight,
                            icon = Icons.Filled.Add,
                            text = stringResource(R.string.add_a_toy)
                        ) {
                            navigateTo(
                                Route.AddToy.route,
                                navController,
                            )
                        }
                    }
                }
            }
        },
        bottomAlignment = CustomAlignment.END,
        bottom = {
            if(isLoggedIn(roomDatabase)) {
                Button(onClick = {
                    navigateTo(
                        Route.MyAdditions.route + "?index=0",
                        navController = navController,
                        popUpToStartDestination = false,
                    )
                }) {
                    Text(text = stringResource(R.string.route_my_additions))
                }
            }
        },
        center = {
        }
    )

}