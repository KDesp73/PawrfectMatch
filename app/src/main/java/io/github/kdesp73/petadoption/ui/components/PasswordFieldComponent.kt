package io.github.kdesp73.petadoption.ui.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import io.github.kdesp73.petadoption.enums.TextFieldType

@Composable
fun PasswordTextFieldComponent(
    labelValue: String,
    icon: ImageVector,
    iconDescriptor: String = "Text field icon",
    type: TextFieldType = TextFieldType.NORMAL
) {
    var password by rememberSaveable { mutableStateOf("") }

    when(type){
        TextFieldType.NORMAL -> {
            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(labelValue) },
                leadingIcon = { Icon(imageVector = icon, contentDescription = iconDescriptor) },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
        }
        TextFieldType.OUTLINED -> {
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(labelValue) },
                leadingIcon = { Icon(imageVector = icon, contentDescription = iconDescriptor) },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
        }
    }
}