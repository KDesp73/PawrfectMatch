package io.github.kdesp73.petadoption.routes

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import io.github.kdesp73.petadoption.NotificationService
import io.github.kdesp73.petadoption.R
import io.github.kdesp73.petadoption.Route
import io.github.kdesp73.petadoption.firestore.UserManager
import io.github.kdesp73.petadoption.checkEmail
import io.github.kdesp73.petadoption.enums.TextFieldType
import io.github.kdesp73.petadoption.hash
import io.github.kdesp73.petadoption.room.AppDatabase
import io.github.kdesp73.petadoption.room.LocalUser
import io.github.kdesp73.petadoption.ui.components.EmailFieldComponent
import io.github.kdesp73.petadoption.ui.components.PasswordTextFieldComponent
import io.github.kdesp73.petadoption.viewmodels.LoginViewModel

private const val TAG = "Login"

@SuppressLint("UnrememberedMutableState", "StateFlowValueCalledInComposition")
@Composable
fun Login(navController: NavController, email: String, roomDatabase: AppDatabase){
    val context = LocalContext.current
    val notificationService = NotificationService(context = LocalContext.current)

    val viewModel = LoginViewModel()

    viewModel.emailState.value = email

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(28.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(fontSize = 6.em, text = stringResource(R.string.log_in))
                Spacer(modifier = Modifier.height(20.dp))

                EmailFieldComponent(viewModel.emailState, labelValue = stringResource(R.string.email), icon = Icons.Filled.Email, type = TextFieldType.OUTLINED)
                PasswordTextFieldComponent(viewModel.passwordState, labelValue = stringResource(R.string.password), icon = Icons.Filled.Lock, type = TextFieldType.OUTLINED)

                Spacer(modifier = Modifier.height(30.dp))
                ElevatedButton(
                    onClick = {
                        val userManager = UserManager()

                        viewModel.log(TAG)

                        if(checkEmail(viewModel.emailState.value)){
                            userManager.getUserByEmail(viewModel.emailState.value){ users ->
                                if(users.isNotEmpty()) {
                                    val hash = users[0].password
                                    if(hash.let { hash(viewModel.passwordState.value).compareTo(it) } == 0){
                                        val userDao = roomDatabase.userDao()

                                        userDao.insert(LocalUser(user = users[0], loggedIn = true))

                                        notificationService.showBasicNotification(
                                            context.getString(R.string.notif_channel_main),
                                            context.getString(R.string.success),
                                            context.getString(R.string.you_are_logged_in),
                                            NotificationManager.IMPORTANCE_HIGH
                                        )

                                        navController.navigate(Route.Account.route) {
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                        viewModel.reset()
                                    } else {
                                        Toast.makeText(
                                            context,
                                            context.getString(R.string.incorrect_password),
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        viewModel.reset()
                                    }
                                } else {
                                    Log.e(TAG, "There is no one with this email in the cloud db")
                                    Toast.makeText(
                                        context,
                                        context.getString(R.string.there_is_no_account_using_this_email),
                                        Toast.LENGTH_LONG
                                    ).show()
                                    viewModel.reset()
                                }
                            }

                        } else {
                            Toast.makeText(
                                context,
                                context.getString(R.string.invalid_email), Toast.LENGTH_SHORT
                            ).show()
                            viewModel.reset()
                        }
                    }
                ) {
                    Row (
                        horizontalArrangement = Arrangement.spacedBy(5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Icon(imageVector = Icons.Filled.Check, contentDescription = "Submit")
                        Text(text = stringResource(id = R.string.submit))
                    }
                }
            }
        }
    }
}