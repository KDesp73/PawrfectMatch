package io.github.kdesp73.petadoption.ui.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import io.github.kdesp73.petadoption.enums.TextFieldType
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun PasswordTextFieldComponent(
    state: MutableStateFlow<String> = MutableStateFlow(""),
    labelValue: String,
    icon: ImageVector,
    iconDescriptor: String = "Text field icon",
    type: TextFieldType = TextFieldType.NORMAL,
    isError: MutableStateFlow<Boolean> = MutableStateFlow(false),
    onValueChanged: () -> Unit = {}
) {
    val password by state.collectAsState()
    val error by isError.collectAsState()
    val iconModifier = Modifier.size(20.dp)

    when (type) {
        TextFieldType.NORMAL -> {
            TextField(
                modifier = Modifier.horizontalScroll(rememberScrollState()),
                isError = error,
                value = password,
                onValueChange = {
                    state.value = it
                    onValueChanged()
                },
                label = { Text(labelValue) },
                leadingIcon = { Icon(modifier = iconModifier, imageVector = icon, contentDescription = iconDescriptor) },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
        }

        TextFieldType.OUTLINED -> {
            OutlinedTextField(
                modifier = Modifier.horizontalScroll(rememberScrollState()),
                isError = error,
                value = password,
                onValueChange = {
                    state.value = it
                    onValueChanged()
                                },
                label = { Text(labelValue) },
                leadingIcon = { Icon(modifier = iconModifier, imageVector = icon, contentDescription = iconDescriptor) },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
        }
    }
}