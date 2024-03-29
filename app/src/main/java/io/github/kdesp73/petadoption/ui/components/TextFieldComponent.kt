package io.github.kdesp73.petadoption.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import io.github.kdesp73.petadoption.enums.TextFieldType

@Composable
fun TextFieldComponent(
    labelValue: String,
    icon: ImageVector,
    iconDescriptor: String = "Text field icon",
    type: TextFieldType = TextFieldType.NORMAL
) {
    var text by remember { mutableStateOf("") }

    when (type) {
        TextFieldType.NORMAL -> {
            TextField(
                value = text,
                onValueChange = { text = it },
                label = { Text(labelValue) },
                leadingIcon = { Icon(imageVector = icon, contentDescription = iconDescriptor) }
            )
        }
        TextFieldType.OUTLINED -> {
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                label = { Text(labelValue) },
                leadingIcon = { Icon(imageVector = icon, contentDescription = iconDescriptor) }
            )
        }
    }
}

@Preview
@Composable
private fun TextFieldPreview(){
    TextFieldComponent(labelValue = "Text", Icons.Filled.Place)
}