package io.github.kdesp73.petadoption.routes

import android.app.NotificationManager
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
import io.github.kdesp73.petadoption.NotificationService
import io.github.kdesp73.petadoption.R
import io.github.kdesp73.petadoption.enums.TextFieldType
import io.github.kdesp73.petadoption.ui.components.CheckboxComponent
import io.github.kdesp73.petadoption.ui.components.EmailFieldComponent
import io.github.kdesp73.petadoption.ui.components.PasswordTextFieldComponent
import io.github.kdesp73.petadoption.ui.components.TextFieldComponent

@Composable
fun CreateAccount(){
    val notificationService = NotificationService(context = LocalContext.current)

    val enabled: Boolean = false
    var conditionsAccepted: Boolean = false

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

                TextFieldComponent(labelValue = "First Name", icon = Icons.Filled.AccountCircle, type = TextFieldType.OUTLINED)
                TextFieldComponent(labelValue = "Last Name", icon = Icons.Filled.AccountCircle, type = TextFieldType.OUTLINED)
                EmailFieldComponent(labelValue = "Email", icon = Icons.Filled.Email, type = TextFieldType.OUTLINED)
                PasswordTextFieldComponent(labelValue = "Password", icon = Icons.Filled.Lock, type = TextFieldType.OUTLINED)
                PasswordTextFieldComponent(labelValue = "Repeat Password", icon = Icons.Filled.Lock, type = TextFieldType.OUTLINED)
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

                Spacer(modifier = Modifier.height(30.dp))
                ElevatedButton(
                    enabled = enabled,
                    onClick = {
                        // TODO: Submit
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