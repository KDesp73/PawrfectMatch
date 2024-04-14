package io.github.kdesp73.petadoption.routes

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import coil.compose.rememberAsyncImagePainter
import io.github.kdesp73.petadoption.Route
import io.github.kdesp73.petadoption.enums.Gender
import io.github.kdesp73.petadoption.enums.Orientation
import io.github.kdesp73.petadoption.enums.TextFieldType
import io.github.kdesp73.petadoption.room.AppDatabase
import io.github.kdesp73.petadoption.room.LocalUser
import io.github.kdesp73.petadoption.ui.components.CircularImage
import io.github.kdesp73.petadoption.ui.components.HalfButton
import io.github.kdesp73.petadoption.ui.components.NumberFieldComponent
import io.github.kdesp73.petadoption.ui.components.OptionPicker
import io.github.kdesp73.petadoption.ui.components.SelectImage
import io.github.kdesp73.petadoption.ui.components.TextFieldComponent
import io.github.kdesp73.petadoption.viewmodels.EditAccountViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun EditAccount(navController: NavController, room: AppDatabase){
    val userDao = room.userDao()
    val user = userDao.getUsers()[0]

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
        val imageModifier = Modifier.fillMaxSize()
        SelectImage (null){ action, uri ->
            val imagePainter = rememberAsyncImagePainter(uri)
            CircularImage(
                modifier = imageModifier.clickable { action() },
                painter = imagePainter,
                contentDescription = "User image",
                size = 200.dp
            )
        }
        TextFieldComponent(state = viewModel.fnameState, labelValue = "First Name", icon = Icons.Filled.AccountCircle, type = TextFieldType.OUTLINED)
        TextFieldComponent(state = viewModel.lnameState, labelValue = "Last Name", icon = Icons.Filled.AccountCircle, type = TextFieldType.OUTLINED)
        TextFieldComponent(state = viewModel.locationState, labelValue = "Location", icon = Icons.Filled.LocationOn, type = TextFieldType.OUTLINED)
        NumberFieldComponent(state = viewModel.phoneState, labelValue = "Phone", icon = Icons.Filled.Phone, type = TextFieldType.OUTLINED)
        OptionPicker(
            state = viewModel.genderState,
            options = listOf(Gender.MALE.label, Gender.FEMALE.label, Gender.OTHER.label),
            orientation = Orientation.HORIZONTAL,
            width = null
        )
        HalfButton (
            modifier = Modifier.padding(8.dp),
            text = "Log Out",
            icon = Icons.AutoMirrored.Filled.ExitToApp
        ){
            userDao.insert(LocalUser()) // Log out

            navController.navigate(Route.Home.route){
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        }
    }
}