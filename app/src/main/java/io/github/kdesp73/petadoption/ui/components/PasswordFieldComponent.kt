package io.github.kdesp73.petadoption.ui.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import io.github.kdesp73.petadoption.enums.TextFieldType
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun PasswordTextFieldComponent(
    state: MutableStateFlow<String> = MutableStateFlow(""),
    labelValue: String,
    icon: ImageVector,
    iconDescriptor: String = "Text field icon",
    type: TextFieldType = TextFieldType.NORMAL,
    isError: Boolean = false,
) {
    val password by state.collectAsState()

    when(type){
        TextFieldType.NORMAL -> {
            TextField(
                isError = isError,
                value = password,
                onValueChange = { state.value = it },
                label = { Text(labelValue) },
                leadingIcon = { Icon(imageVector = icon, contentDescription = iconDescriptor) },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
        }
        TextFieldType.OUTLINED -> {
            OutlinedTextField(
                isError = isError,
                value = password,
                onValueChange = { state.value = it },
                label = { Text(labelValue) },
                leadingIcon = { Icon(imageVector = icon, contentDescription = iconDescriptor) },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
        }
    }
}