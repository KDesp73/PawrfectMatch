package io.github.kdesp73.petadoption.routes

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import io.github.kdesp73.petadoption.NotificationService
import io.github.kdesp73.petadoption.R
import io.github.kdesp73.petadoption.Route
import io.github.kdesp73.petadoption.firebase.UserManager
import io.github.kdesp73.petadoption.checkEmail
import io.github.kdesp73.petadoption.enums.CustomAlignment
import io.github.kdesp73.petadoption.enums.TextFieldType
import io.github.kdesp73.petadoption.hash
import io.github.kdesp73.petadoption.logIn
import io.github.kdesp73.petadoption.navigateTo
import io.github.kdesp73.petadoption.room.AppDatabase
import io.github.kdesp73.petadoption.room.LocalUser
import io.github.kdesp73.petadoption.ui.components.EmailFieldComponent
import io.github.kdesp73.petadoption.ui.components.PasswordTextFieldComponent
import io.github.kdesp73.petadoption.ui.components.VerticalScaffold
import io.github.kdesp73.petadoption.viewmodels.LoginViewModel

private const val TAG = "Login"

@Composable
private fun ClickableTextComponent(onTextSelected: (String) -> Unit) {
    val start = stringResource(R.string.don_t_have_an_account_click)
    val here = stringResource(R.string.here)
    val toRegister = stringResource(R.string.to_register)

    val annotatedString = buildAnnotatedString {
        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.onBackground)) {
            append(start)
        }
        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
            pushStringAnnotation(tag = here, annotation = here)
            append(here)
        }
        append(" ")
        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.onBackground)) {
            append(toRegister)
        }
    }

    ClickableText(text = annotatedString, onClick = { offset ->
        annotatedString.getStringAnnotations(offset, offset)
            .firstOrNull()?.also { span ->
                Log.d("ClickableTextComponent", "{${span.item}}")

                if (span.item == here){
                    onTextSelected(span.item)
                }
            }
    })
}

@SuppressLint("UnrememberedMutableState", "StateFlowValueCalledInComposition")
@Composable
fun Login(navController: NavController, email: String, roomDatabase: AppDatabase){
    val context = LocalContext.current
    val notificationService = NotificationService(context = LocalContext.current)

    val viewModel = LoginViewModel()

    viewModel.emailState.value = email

    VerticalScaffold(
        modifier = Modifier.fillMaxSize(),
        top = {
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


                        viewModel.clean()
                        viewModel.log(TAG)


                        if(checkEmail(viewModel.emailState.value).isSuccess){
                            userManager.getUserByEmail(viewModel.emailState.value){ users ->
                                if(users.isNotEmpty()) {
                                    val hash = users[0].password
                                    if(hash.let { hash(viewModel.passwordState.value).compareTo(it) } == 0){
                                        logIn(roomDatabase, users[0])

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
        },
        bottomAlignment = CustomAlignment.CENTER,
        bottom = {
            Row (
                modifier = Modifier
                    .padding(10.dp)
                    .height(50.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ){
                Icon(imageVector = Icons.Filled.Info, contentDescription = "")
                ClickableTextComponent() { _ ->
                    navigateTo(Route.CreateAccount.route, navController)
                }
            }
        }
    )
}