package io.github.kdesp73.petadoption.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import io.github.kdesp73.petadoption.enums.TextFieldType
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun TextFieldComponent(
    state : MutableStateFlow<String> = MutableStateFlow(""),
    labelValue: String,
    icon: ImageVector,
    iconDescriptor: String = "Text field icon",
    type: TextFieldType = TextFieldType.NORMAL,
    isError: Boolean = false,
) {
    val text by state.collectAsState()

    when (type) {
        TextFieldType.NORMAL -> {
            TextField(
                isError = isError,
                value = text,
                onValueChange = { state.value = it },
                label = { Text(labelValue) },
                leadingIcon = { Icon(imageVector = icon, contentDescription = iconDescriptor) }
            )
        }
        TextFieldType.OUTLINED -> {
            OutlinedTextField(
                isError = isError,
                value = text,
                onValueChange = { state.value = it },
                label = { Text(labelValue) },
                leadingIcon = { Icon(imageVector = icon, contentDescription = iconDescriptor) }
            )
        }
    }
}

@Preview
@Composable
private fun TextFieldPreview(){
    TextFieldComponent(labelValue = "Text", icon = Icons.Filled.Place)
}