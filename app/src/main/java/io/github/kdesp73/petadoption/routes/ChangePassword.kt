package io.github.kdesp73.petadoption.routes

import android.app.NotificationManager
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.navigation.NavController
import io.github.kdesp73.petadoption.NotificationService
import io.github.kdesp73.petadoption.R
import io.github.kdesp73.petadoption.Route
import io.github.kdesp73.petadoption.enums.TextFieldType
import io.github.kdesp73.petadoption.firestore.User
import io.github.kdesp73.petadoption.firestore.UserManager
import io.github.kdesp73.petadoption.hash
import io.github.kdesp73.petadoption.navigateTo
import io.github.kdesp73.petadoption.room.AppDatabase
import io.github.kdesp73.petadoption.room.LocalUser
import io.github.kdesp73.petadoption.ui.components.HalfButton
import io.github.kdesp73.petadoption.ui.components.PasswordTextFieldComponent
import io.github.kdesp73.petadoption.viewmodels.ChangePasswordViewModel

private const val TAG = "ChangePassword"

@Composable
fun ChangePassword(room: AppDatabase, navController: NavController){
    val viewModel = ChangePasswordViewModel()
    val userManager = UserManager()
    val context = LocalContext.current
    val notificationService = NotificationService(context)
    val scrollState = rememberScrollState()

    Column (
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ){
        Text(fontSize = 6.em, text = stringResource(R.string.change_your_password))
        Spacer(modifier = Modifier.height(20.dp))

        PasswordTextFieldComponent(viewModel.oldPasswordState, labelValue = stringResource(R.string.old_password), icon = Icons.Filled.Lock, type = TextFieldType.OUTLINED)
        PasswordTextFieldComponent(viewModel.confirmOldPasswordState, labelValue = stringResource(R.string.confirm_old_password), icon = Icons.Filled.Lock, type = TextFieldType.OUTLINED)
        PasswordTextFieldComponent(viewModel.newPasswordState, labelValue = stringResource(R.string.new_password), icon = Icons.Filled.Lock, type = TextFieldType.OUTLINED)
        PasswordTextFieldComponent(viewModel.confirmNewPasswordState, labelValue = stringResource(R.string.confirm_new_password), icon = Icons.Filled.Lock, type = TextFieldType.OUTLINED)

        Spacer(modifier = Modifier.height(40.dp))
        HalfButton (
            icon = Icons.Filled.Check,
            text = stringResource(R.string.submit)
        ){
            var password: String?
            val userDao = room.userDao()

            userDao.getEmail()?.let {
                userManager.getUserByEmail(it){ users ->
                    if(users.isNotEmpty()) {
                        password = users[0].password

                        val checkForm = viewModel.validate()
                        if(password != null && password == hash(viewModel.oldPasswordState.value)){
                            if(checkForm.isSuccess){
                                if(viewModel.newPasswordState.value != viewModel.oldPasswordState.value){
                                    val email = room.userDao().getEmail()
                                    if(email != null){
                                        userManager.updateUser(User(email, hash(viewModel.newPasswordState.value))) { updated ->
                                            if(updated){
                                                // SUCCESS
                                                notificationService.showBasicNotification(
                                                    context.getString(
                                                        R.string.notif_channel_main
                                                    ),
                                                    context.getString(R.string.success),
                                                    context.getString(R.string.your_password_has_changed_successfully),
                                                    NotificationManager.IMPORTANCE_HIGH
                                                )

                                                userDao.insert(LocalUser()) // Log out
                                                navigateTo(Route.Login.route, navController = navController)
                                            } else {
                                                Toast.makeText(
                                                    context,
                                                    context.getString(R.string.something_went_wrong),
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        }

                                    }
                                } else {
                                    Toast.makeText(
                                        context,
                                        context.getString(R.string.new_password_cannot_be_the_same_as_old_password),
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            } else {
                                Toast.makeText(
                                    context,
                                    checkForm.exceptionOrNull()?.message,
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        } else {
                            Toast.makeText(
                                context,
                                context.getString(R.string.old_password_is_incorrect),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }
        }
    }
}