package io.github.kdesp73.petadoption.routes

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import io.github.kdesp73.petadoption.Route
import io.github.kdesp73.petadoption.enums.CustomAlignment
import io.github.kdesp73.petadoption.enums.TextFieldType
import io.github.kdesp73.petadoption.room.AppDatabase
import io.github.kdesp73.petadoption.room.LocalUser
import io.github.kdesp73.petadoption.ui.components.EmailFieldComponent
import io.github.kdesp73.petadoption.ui.components.HalfButton
import io.github.kdesp73.petadoption.ui.components.NumberFieldComponent
import io.github.kdesp73.petadoption.ui.components.OptionPicker
import io.github.kdesp73.petadoption.ui.components.RadioButtonGroup
import io.github.kdesp73.petadoption.ui.components.TextFieldComponent
import io.github.kdesp73.petadoption.ui.utils.VerticalScaffold
import io.github.kdesp73.petadoption.viewmodels.EditAccountViewModel
import io.github.kdesp73.petadoption.viewmodels.GenderViewModel
import kotlinx.coroutines.flow.MutableStateFlow

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun EditAccount(navController: NavController, room: AppDatabase){
    val userDao = room.userDao()
    val user = userDao.getUsers()[0]

    val viewModel = EditAccountViewModel()
    viewModel.fnameState.value = user.firstName
    viewModel.lnameState.value = user.lastName
    viewModel.emailState.value = user.email
    viewModel.phoneState.value = user.phone
    viewModel.genderState.value = user.gender
    viewModel.locationState.value = user.location

    val genders = listOf("Male", "Female", "Other")
    val genderViewModel = GenderViewModel(userDao.getGender())
    val gender by remember {
        mutableStateOf(userDao.getGender())
    }

    VerticalScaffold(
        top = {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                TextFieldComponent(state = viewModel.fnameState, labelValue = "First Name", icon = Icons.Filled.AccountCircle, type = TextFieldType.OUTLINED)
                TextFieldComponent(state = viewModel.lnameState, labelValue = "Last Name", icon = Icons.Filled.AccountCircle, type = TextFieldType.OUTLINED)
                EmailFieldComponent(state = viewModel.emailState, labelValue = "Email", icon = Icons.Filled.Email, type = TextFieldType.OUTLINED)
                NumberFieldComponent(state = viewModel.phoneState, labelValue = "Phone", icon = Icons.Filled.Phone, type = TextFieldType.OUTLINED)
                OptionPicker(state = genderViewModel.genderState, options = genders){ gender ->
                    genderViewModel.genderState = gender
                }
            }
        },
        bottom = {
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
        },
        bottomAlignment = CustomAlignment.END,
        center = {

        }
    )
}