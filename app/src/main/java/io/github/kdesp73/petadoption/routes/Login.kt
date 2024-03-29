package io.github.kdesp73.petadoption.routes

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
import androidx.compose.ui.unit.dp
import io.github.kdesp73.petadoption.NotificationService
import io.github.kdesp73.petadoption.enums.TextFieldType
import io.github.kdesp73.petadoption.ui.components.EmailFieldComponent
import io.github.kdesp73.petadoption.ui.components.PasswordTextFieldComponent

@Composable
fun Login(){
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
                Spacer(modifier = Modifier.height(20.dp))

                EmailFieldComponent(labelValue = "Email", icon = Icons.Filled.Email, type = TextFieldType.OUTLINED)
                PasswordTextFieldComponent(labelValue = "Password", icon = Icons.Filled.Lock, type = TextFieldType.OUTLINED)

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