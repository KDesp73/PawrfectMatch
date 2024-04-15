package io.github.kdesp73.petadoption.routes

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
import androidx.compose.material.icons.filled.AccountCircle
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.navigation.NavController
import io.github.kdesp73.petadoption.NotificationService
import io.github.kdesp73.petadoption.R
import io.github.kdesp73.petadoption.Route
import io.github.kdesp73.petadoption.firestore.User
import io.github.kdesp73.petadoption.firestore.UserInfo
import io.github.kdesp73.petadoption.firestore.UserManager
import io.github.kdesp73.petadoption.enums.Gender
import io.github.kdesp73.petadoption.enums.ProfileType
import io.github.kdesp73.petadoption.enums.TextFieldType
import io.github.kdesp73.petadoption.ui.components.CheckboxComponent
import io.github.kdesp73.petadoption.ui.components.EmailFieldComponent
import io.github.kdesp73.petadoption.ui.components.PasswordTextFieldComponent
import io.github.kdesp73.petadoption.ui.components.TextFieldComponent
import io.github.kdesp73.petadoption.checkEmail
import io.github.kdesp73.petadoption.checkName
import io.github.kdesp73.petadoption.hash
import io.github.kdesp73.petadoption.validatePassword
import io.github.kdesp73.petadoption.viewmodels.CreateAccountViewModel


private const val TAG = "CreateAccount"
@Composable
fun CreateAccount(navController: NavController?){
    val notificationService = NotificationService(context = LocalContext.current)

    val viewModel = CreateAccountViewModel()

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
                Text(text = "Hey there,")
                Text(fontSize = 6.em, text = "Create an Account")
                Spacer(modifier = Modifier.height(20.dp))

                TextFieldComponent(state = viewModel.fnameState, labelValue = "First Name", icon = Icons.Filled.AccountCircle, type = TextFieldType.OUTLINED)
                TextFieldComponent(state = viewModel.lnameState, labelValue = "Last Name", icon = Icons.Filled.AccountCircle, type = TextFieldType.OUTLINED)
                EmailFieldComponent(state = viewModel.emailState, labelValue = "Email", icon = Icons.Filled.Email, type = TextFieldType.OUTLINED)
                PasswordTextFieldComponent(state = viewModel.passwordState, labelValue = "Password", icon = Icons.Filled.Lock, type = TextFieldType.OUTLINED)
                PasswordTextFieldComponent(state = viewModel.repeatPasswordState, labelValue = "Repeat Password", icon = Icons.Filled.Lock, type = TextFieldType.OUTLINED)
                CheckboxComponent(
                    value = "",
                    onTextSelected = { clicked ->
                        notificationService.showBasicNotification(channel = R.string.MAIN.toString(), title = "Clicked", content = "$clicked got clicked",
                            importance = NotificationManager.IMPORTANCE_HIGH
                        )
                    },
                    onCheckedChange = {
                        viewModel.termsAndConditionsAcceptedState.value = true
                    }
                )

                val context = LocalContext.current
                Spacer(modifier = Modifier.height(30.dp))
                ElevatedButton(
                    onClick = {
                        Log.i(TAG, "Submit Pressed")
                        val validFirstName = checkName(viewModel.fnameState.value)
                        val validLastName = checkName(viewModel.lnameState.value)
                        val validEmail = checkEmail(viewModel.emailState.value)
                        val validPassword = validatePassword(viewModel.passwordState.value)
                        val confirmedPassword = viewModel.passwordState.value.compareTo(viewModel.repeatPasswordState.value) == 0

                        val message = when{
                            !validFirstName -> "Invalid First Name"
                            !validLastName -> "Invalid First Name"
                            !validEmail -> "Invalid Email"
                            validPassword.isFailure -> validPassword.exceptionOrNull()?.message
                            !confirmedPassword -> "Passwords don't match"
                            !viewModel.termsAndConditionsAcceptedState.value-> "Please accept our Terms and Conditions"
                            else -> "Success"
                        }

                        if (message != "Success"){
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                        } else {
                            val newUser = User(
                                email = viewModel.emailState.value,
                                password = hash(viewModel.passwordState.value),
                                info = UserInfo(
                                    email = viewModel.emailState.value,
                                    firstName = viewModel.fnameState.value,
                                    lastName = viewModel.lnameState.value,
                                    gender = Gender.OTHER.label,
                                    phone = "",
                                    location = "",
                                    profileType = ProfileType.INDIVIDUAL.id
                                )
                            )

                            val userManager = UserManager()
                            userManager.addUser(newUser){ success, message ->
                                notificationService.showBasicNotification(R.string.MAIN.toString(), if(success) "Success" else "Failure", message, NotificationManager.IMPORTANCE_HIGH)

                                if(success){
                                    navController?.navigate(Route.Login.route+ "?email=${newUser.email}")
                                    viewModel.reset()
                                }
                            }
                        }
                    }
                ) {
                    Row (
                        horizontalArrangement = Arrangement.spacedBy(5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Icon(imageVector = Icons.Filled.Check, contentDescription = "Submit")
                        Text(text = "Submit")
                    }
                }
            }

        }
    }
}
