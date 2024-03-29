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
import io.github.kdesp73.petadoption.NotificationService
import io.github.kdesp73.petadoption.R
import io.github.kdesp73.petadoption.enums.TextFieldType
import io.github.kdesp73.petadoption.ui.components.CheckboxComponent
import io.github.kdesp73.petadoption.ui.components.EmailFieldComponent
import io.github.kdesp73.petadoption.ui.components.PasswordTextFieldComponent
import io.github.kdesp73.petadoption.ui.components.TextFieldComponent
import java.security.MessageDigest

fun checkName(name: String): Boolean {
    return name.all { it.isLetter() } && name.isNotBlank() && name.isNotEmpty()
}

fun checkEmail(email: String): Boolean {
    val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"
    return email.matches(emailRegex.toRegex())
}

const val ERR_LEN = "Password must have at least eight characters!"
const val ERR_WHITESPACE = "Password must not contain whitespace!"
const val ERR_DIGIT = "Password must contain at least one digit!"
const val ERR_UPPER = "Password must have at least one uppercase letter!"
const val ERR_SPECIAL = "Password must have at least one special character, such as: _%-=+#@"

fun validatePassword(pwd: String) = runCatching {
    require(pwd.length >= 8) { ERR_LEN }
    require(pwd.none { it.isWhitespace() }) { ERR_WHITESPACE }
    require(pwd.any { it.isDigit() }) { ERR_DIGIT }
    require(pwd.any { it.isUpperCase() }) { ERR_UPPER }
    require(pwd.any { !it.isLetterOrDigit() }) { ERR_SPECIAL }
}

fun hash(pass: String): String {
    val bytes = pass.toByteArray()
    val md = MessageDigest.getInstance("SHA-256")
    val digest = md.digest(bytes)
    return digest.fold("") { str, it -> str + "%02x".format(it) }
}

private const val TAG = "CreateAccount"
@Composable
fun CreateAccount(){
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

                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

                        if(message == "Success"){
                            // TODO: Create account
                            notificationService.showBasicNotification(R.string.MAIN.toString(), "Success", "Account created successfully", NotificationManager.IMPORTANCE_HIGH)
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
