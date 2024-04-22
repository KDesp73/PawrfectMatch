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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.navigation.NavController
import io.github.kdesp73.petadoption.NotificationService
import io.github.kdesp73.petadoption.R
import io.github.kdesp73.petadoption.Route
import io.github.kdesp73.petadoption.checkEmail
import io.github.kdesp73.petadoption.checkName
import io.github.kdesp73.petadoption.enums.Gender
import io.github.kdesp73.petadoption.enums.ProfileType
import io.github.kdesp73.petadoption.enums.TextFieldType
import io.github.kdesp73.petadoption.firestore.User
import io.github.kdesp73.petadoption.firestore.UserInfo
import io.github.kdesp73.petadoption.firestore.UserManager
import io.github.kdesp73.petadoption.hash
import io.github.kdesp73.petadoption.ui.components.CheckboxComponent
import io.github.kdesp73.petadoption.ui.components.EmailFieldComponent
import io.github.kdesp73.petadoption.ui.components.PasswordTextFieldComponent
import io.github.kdesp73.petadoption.ui.components.TextFieldComponent
import io.github.kdesp73.petadoption.validatePassword
import io.github.kdesp73.petadoption.viewmodels.CreateAccountViewModel
import kotlinx.coroutines.flow.MutableStateFlow


private const val TAG = "CreateAccount"
@Composable
fun CreateAccount(navController: NavController?){
    val context = LocalContext.current
    val notificationService = NotificationService(context = context)

    val viewModel = CreateAccountViewModel()

    val scrollState = rememberScrollState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        contentAlignment = Alignment.Center
    ) {

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(28.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(text = stringResource(R.string.hey_there))
                Text(fontSize = 6.em, text = stringResource(R.string.create_an_account))
                Spacer(modifier = Modifier.height(20.dp))

                TextFieldComponent(
                    isError = viewModel.fnameError,
                    state = viewModel.fnameState,
                    labelValue = "First Name",
                    icon = Icons.Filled.AccountCircle,
                    type = TextFieldType.OUTLINED
                ) {
                    viewModel.fnameError.value = checkName(viewModel.fnameState.value).isFailure
                }

                TextFieldComponent(
                    isError = viewModel.lnameError,
                    state = viewModel.lnameState,
                    labelValue = "Last Name",
                    icon = Icons.Filled.AccountCircle,
                    type = TextFieldType.OUTLINED
                ) {
                    viewModel.lnameError.value = checkName(viewModel.lnameState.value).isFailure
                }

                EmailFieldComponent(
                    isError = viewModel.emailError,
                    state = viewModel.emailState,
                    labelValue = "Email",
                    icon = Icons.Filled.Email,
                    type = TextFieldType.OUTLINED
                ){
                    viewModel.emailError.value = checkEmail(viewModel.emailState.value).isFailure
                }

                PasswordTextFieldComponent(
                    isError = viewModel.passwordError,
                    state = viewModel.passwordState,
                    labelValue = "Password",
                    icon = Icons.Filled.Lock,
                    type = TextFieldType.OUTLINED
                ) {
                    val validPassword = validatePassword(viewModel.passwordState.value)
                    viewModel.passwordError.value = validPassword.isFailure
                    viewModel.updateIcons()
                }

                PasswordTextFieldComponent(
                    isError = viewModel.repeatPasswordError,
                    state = viewModel.repeatPasswordState,
                    labelValue = "Repeat Password",
                    icon = Icons.Filled.Lock,
                    type = TextFieldType.OUTLINED
                ){
                    viewModel.repeatPasswordError.value = viewModel.passwordState.value.compareTo(viewModel.repeatPasswordState.value) != 0
                }

                CheckboxComponent(
                    value = "",
                    onTextSelected = { clicked ->
                        notificationService.showBasicNotification(channel = context.getString(R.string.notif_channel_main), title = "Clicked", content = "$clicked got clicked",
                            importance = NotificationManager.IMPORTANCE_HIGH
                        )
                    },
                    onCheckedChange = {
                        viewModel.termsAndConditionsAcceptedState.value = !viewModel.termsAndConditionsAcceptedState.value
                    }
                )


                @SuppressLint("StateFlowValueCalledInComposition")
                @Composable
                fun IconText(imageVectorState: MutableStateFlow<ImageVector>, text: String){
                    val icon by imageVectorState.collectAsState()
                    Row (
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(vertical = 4.dp, horizontal = 2.dp)
                            .fillMaxWidth()
                    ){
                        Icon(imageVector = icon, contentDescription = "")
                        Text(text = text)
                    }
                }

                Spacer(modifier = Modifier.height(30.dp))
                Column {
                    IconText(imageVectorState = viewModel.lowercaseLettersIconState, text = stringResource(id = R.string.password_must_have_at_least_one_lowercase_letter))
                    IconText(imageVectorState = viewModel.uppercaseLettersIconState, text = stringResource(id = R.string.password_must_have_at_least_one_uppercase_letter))
                    IconText(imageVectorState = viewModel.digitIconState, text = stringResource(id = R.string.password_must_contain_at_least_one_digit))
                    IconText(imageVectorState = viewModel.eightCharactersIconState, text = stringResource(id = R.string.password_must_have_at_least_eight_characters))
                    IconText(imageVectorState = viewModel.noWhitespaceIconState, text = stringResource(id = R.string.password_must_not_contain_whitespace))
                    IconText(imageVectorState = viewModel.containsSpecialCharactersIconState, text = stringResource(id = R.string.password_must_have_at_least_one_special_character_such_as))
                }
                Spacer(modifier = Modifier.height(30.dp))

                ElevatedButton(
                    onClick = {
                        Log.i(TAG, "Submit Pressed")
                        viewModel.log(TAG)

                        viewModel.clean()

                        viewModel.fnameError.value = checkName(viewModel.fnameState.value).isFailure
                        viewModel.lnameError.value = checkName(viewModel.lnameState.value).isFailure
                        viewModel.emailError.value = checkEmail(viewModel.emailState.value).isFailure
                        val validPassword = validatePassword(viewModel.passwordState.value)
                        viewModel.passwordError.value = validPassword.isFailure
                        viewModel.repeatPasswordError.value = viewModel.passwordState.value.compareTo(viewModel.repeatPasswordState.value) != 0

                        val message = when{
                            viewModel.fnameError.value -> "Invalid First Name"
                            viewModel.lnameError.value -> "Invalid First Name"
                            viewModel.emailError.value -> "Invalid Email"
                            validPassword.isFailure -> validPassword.exceptionOrNull()?.message
                            viewModel.repeatPasswordError.value -> "Passwords don't match"
                            viewModel.termsAndConditionsAcceptedState.value-> "Please accept our Terms and Conditions"
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
                                notificationService.showBasicNotification(R.string.notif_channel_main.toString(), if(success) "Success" else "Failure", message, NotificationManager.IMPORTANCE_HIGH)

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
