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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.navigation.NavController
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import io.github.kdesp73.petadoption.NotificationService
import io.github.kdesp73.petadoption.R
import io.github.kdesp73.petadoption.Route
import io.github.kdesp73.petadoption.User
import io.github.kdesp73.petadoption.UserManager
import io.github.kdesp73.petadoption.enums.Gender
import io.github.kdesp73.petadoption.enums.ProfileType
import io.github.kdesp73.petadoption.enums.TextFieldType
import io.github.kdesp73.petadoption.ui.components.CheckboxComponent
import io.github.kdesp73.petadoption.ui.components.EmailFieldComponent
import io.github.kdesp73.petadoption.ui.components.PasswordTextFieldComponent
import io.github.kdesp73.petadoption.ui.components.TextFieldComponent
import io.github.kdesp73.petadoption.ui.utils.checkEmail
import io.github.kdesp73.petadoption.ui.utils.checkName
import io.github.kdesp73.petadoption.ui.utils.hash
import io.github.kdesp73.petadoption.ui.utils.validatePassword


private const val TAG = "CreateAccount"
@Composable
fun CreateAccount(navController: NavController?){
    val dbRef = Firebase.firestore
    val notificationService = NotificationService(context = LocalContext.current)

    var conditionsAccepted = false

    val fnameState = remember { mutableStateOf("") }
    val lnameState = remember { mutableStateOf("") }
    val emailState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }
    val repeatPasswordState = remember { mutableStateOf("") }

    var fnameError = false
    var lnameError = false
    var emailError = false
    var passwordError = false
    var repeatPasswordError = false



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

                TextFieldComponent(isError = fnameError, value = fnameState, labelValue = "First Name", icon = Icons.Filled.AccountCircle, type = TextFieldType.OUTLINED)
                TextFieldComponent(isError = lnameError, value = lnameState, labelValue = "Last Name", icon = Icons.Filled.AccountCircle, type = TextFieldType.OUTLINED)
                EmailFieldComponent(isError = emailError, value = emailState, labelValue = "Email", icon = Icons.Filled.Email, type = TextFieldType.OUTLINED)
                PasswordTextFieldComponent(isError = passwordError, value = passwordState, labelValue = "Password", icon = Icons.Filled.Lock, type = TextFieldType.OUTLINED)
                PasswordTextFieldComponent(isError = repeatPasswordError, value = repeatPasswordState, labelValue = "Repeat Password", icon = Icons.Filled.Lock, type = TextFieldType.OUTLINED)
                CheckboxComponent(
                    value = "",
                    onTextSelected = { clicked ->
                        notificationService.showBasicNotification(channel = R.string.MAIN.toString(), title = "Clicked", content = "$clicked got clicked",
                            importance = NotificationManager.IMPORTANCE_HIGH
                        )
                    },
                    onCheckedChange = {
                        conditionsAccepted = true
                    }
                )

                val context = LocalContext.current
                Spacer(modifier = Modifier.height(30.dp))
                ElevatedButton(
                    onClick = {
                        Log.i(TAG, "Submit Pressed")
                        val validFirstName = checkName(fnameState.value)
                        val validLastName = checkName(lnameState.value)
                        val validEmail = checkEmail(emailState.value)
                        val validPassword = validatePassword(passwordState.value)
                        val confirmedPassword = passwordState.value.compareTo(repeatPasswordState.value) == 0

                        fnameError = !validFirstName
                        lnameError = !validLastName
                        emailError = !validEmail
                        passwordError = validPassword.isFailure
                        repeatPasswordError = !confirmedPassword

                        val message = when{
                            !validFirstName -> "Invalid First Name"
                            !validLastName -> "Invalid First Name"
                            !validEmail -> "Invalid Email"
                            validPassword.isFailure -> validPassword.exceptionOrNull()?.message
                            !confirmedPassword -> "Passwords don't match"
                            !conditionsAccepted -> "Please accept our Terms and Conditions"
                            else -> "Success"
                        }

                        if (message != "Success"){
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                        } else {
                            val newUser = User(
                                firstName = fnameState.value,
                                lastName = lnameState.value,
                                email = emailState.value,
                                password = hash(passwordState.value),
                                gender = Gender.OTHER.label,
                                phone = "",
                                location = "",
                                profileType = ProfileType.INDIVIDUAL.id
                            )

                            val userManager = UserManager()
                            userManager.addUser(newUser){ success, message ->
                                notificationService.showBasicNotification(R.string.MAIN.toString(), if(success) "Success" else "Failure", message, NotificationManager.IMPORTANCE_HIGH)

                                if(success){
                                    navController?.navigate(Route.Login.route+ "?email=${newUser.email}")
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
