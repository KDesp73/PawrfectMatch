package io.github.kdesp73.petadoption.ui.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import io.github.kdesp73.petadoption.enums.TextFieldType

@Composable
fun EmailFieldComponent(
    value: MutableState<String> = mutableStateOf(""),
    labelValue: String,
    icon: ImageVector,
    iconDescriptor: String = "Text field icon",
    type: TextFieldType = TextFieldType.NORMAL,
    isError: Boolean = false,
) {
    var text by remember { value }

    when (type) {
        TextFieldType.NORMAL -> {
            TextField(
                isError = isError,
                value = text,
                onValueChange = { text = it },
                label = { Text(labelValue) },
                leadingIcon = { Icon(imageVector = icon, contentDescription = iconDescriptor) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )
        }
        TextFieldType.OUTLINED -> {
            OutlinedTextField(
                isError = isError,
                value = text,
                onValueChange = { text = it },
                label = { Text(labelValue) },
                leadingIcon = { Icon(imageVector = icon, contentDescription = iconDescriptor) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )
        }
    }
}