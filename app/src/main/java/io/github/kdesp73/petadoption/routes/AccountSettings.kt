package io.github.kdesp73.petadoption.routes

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import coil.compose.rememberAsyncImagePainter
import io.github.kdesp73.petadoption.NotificationService
import io.github.kdesp73.petadoption.R
import io.github.kdesp73.petadoption.Route
import io.github.kdesp73.petadoption.enums.Gender
import io.github.kdesp73.petadoption.enums.Orientation
import io.github.kdesp73.petadoption.enums.TextFieldType
import io.github.kdesp73.petadoption.firestore.UserInfo
import io.github.kdesp73.petadoption.firestore.UserManager
import io.github.kdesp73.petadoption.room.AppDatabase
import io.github.kdesp73.petadoption.room.LocalUser
import io.github.kdesp73.petadoption.ui.components.CircularIconButton
import io.github.kdesp73.petadoption.ui.components.CircularImage
import io.github.kdesp73.petadoption.ui.components.CustomButton
import io.github.kdesp73.petadoption.ui.components.NumberFieldComponent
import io.github.kdesp73.petadoption.ui.components.OptionPicker
import io.github.kdesp73.petadoption.ui.components.SelectImage
import io.github.kdesp73.petadoption.ui.components.TextFieldComponent
import io.github.kdesp73.petadoption.viewmodels.EditAccountViewModel
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities.Local

private const val TAG = "AccountSettings"

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun AccountSettings(navController: NavController, room: AppDatabase) {
    val userDao = room.userDao()
    val user = userDao.getUsers()[0]
    val notificationService = NotificationService(LocalContext.current)

    val scrollState = rememberScrollState()

    val viewModel = EditAccountViewModel()
    viewModel.fnameState.value = user.firstName
    viewModel.lnameState.value = user.lastName
    viewModel.phoneState.value = user.phone
    viewModel.genderState.value = user.gender
    viewModel.locationState.value = user.location

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        Column(
            modifier = Modifier.fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ){
            val imageModifier = Modifier.fillMaxSize()
            SelectImage(null) { action, uri ->
                val imagePainter = rememberAsyncImagePainter(uri)
                CircularImage(
                    modifier = imageModifier.clickable { action() },
                    painter = imagePainter,
                    contentDescription = "User image",
                    size = 200.dp
                )
            }
            Spacer(modifier = Modifier.height(15.dp))
            TextFieldComponent(
                state = viewModel.fnameState,
                labelValue = "First Name",
                icon = Icons.Filled.AccountCircle,
                type = TextFieldType.OUTLINED
            )
            Spacer(modifier = Modifier.height(15.dp))
            TextFieldComponent(
                state = viewModel.lnameState,
                labelValue = "Last Name",
                icon = Icons.Filled.AccountCircle,
                type = TextFieldType.OUTLINED
            )
            Spacer(modifier = Modifier.height(15.dp))
            TextFieldComponent(
                state = viewModel.locationState,
                labelValue = "Location",
                icon = Icons.Filled.LocationOn,
                type = TextFieldType.OUTLINED
            )
            Spacer(modifier = Modifier.height(15.dp))
            NumberFieldComponent(
                state = viewModel.phoneState,
                labelValue = "Phone",
                icon = Icons.Filled.Phone,
                type = TextFieldType.OUTLINED
            )
            Spacer(modifier = Modifier.height(15.dp))
            OptionPicker(
                state = viewModel.genderState,
                options = listOf(Gender.MALE.label, Gender.FEMALE.label, Gender.OTHER.label),
                orientation = Orientation.HORIZONTAL,
                width = null
            )
            Spacer(modifier = Modifier.height(10.dp))

            CircularIconButton(
                icon = Icons.Filled.Check,
                description = "Add Pet",
                bg = MaterialTheme.colorScheme.surface,
                size = 60.dp
            ) {
                viewModel.log(TAG)

                // TODO: validate fields

                val manager = UserManager()
                val updatedUserInfo = UserInfo(
                    email = user.email,
                    firstName = viewModel.fnameState.value,
                    lastName = viewModel.lnameState.value,
                    phone = viewModel.phoneState.value,
                    location = viewModel.locationState.value,
                    gender = viewModel.genderState.value,
                    profileType = 1
                )

                manager.updateInfo(updatedUserInfo){ completed ->
                    if(completed){
                        Log.i(TAG, "Updated user info")
                    } else {
                        Log.i(TAG, "Failed to update user info")
                    }
                }
                userDao.update(LocalUser(info = updatedUserInfo))

                notificationService.showBasicNotification(
                    R.string.MAIN.toString(),
                    "Success",
                    "Account Information Updated Successfully",
                    NotificationManager.IMPORTANCE_HIGH
                )

            }
        }
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ){
            Spacer(modifier = Modifier.height(150.dp))
            CustomButton (
                modifier = Modifier
                    .width(200.dp)
                    .padding(4.dp),
                text = "Change Password",
                icon = Icons.Filled.Refresh
            ){

            }
            CustomButton(
                modifier = Modifier
                    .width(200.dp)
                    .padding(4.dp),
                text = "Log Out",
                icon = Icons.AutoMirrored.Filled.ExitToApp
            ) {
                userDao.insert(LocalUser()) // Log out

                navController.navigate(Route.Home.route) {
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