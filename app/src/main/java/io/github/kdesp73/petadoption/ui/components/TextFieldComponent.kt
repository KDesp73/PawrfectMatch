package io.github.kdesp73.petadoption.ui.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.kdesp73.petadoption.enums.TextFieldType
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun TextFieldComponent(
    state : MutableStateFlow<String> = MutableStateFlow(""),
    labelValue: String,
    icon: ImageVector,
    iconDescriptor: String = "Text field icon",
    type: TextFieldType = TextFieldType.NORMAL,
    isError: MutableStateFlow<Boolean> = MutableStateFlow(false),
    onValueChanged: () -> Unit = {}
) {
    val text by state.collectAsState()
    val error by isError.collectAsState()
    val iconModifier = Modifier.size(20.dp)

    when (type) {
        TextFieldType.NORMAL -> {
            TextField(
                modifier = Modifier.horizontalScroll(rememberScrollState()),
                isError = error,
                value = text,
                onValueChange = {
                    state.value = it
                    onValueChanged()
                },
                label = { Text(labelValue) },
                leadingIcon = { Icon(modifier = iconModifier, imageVector = icon, contentDescription = iconDescriptor) }
            )
        }
        TextFieldType.OUTLINED -> {
            OutlinedTextField(
                modifier = Modifier.horizontalScroll(rememberScrollState()),
                isError = error,
                value = text,
                onValueChange = {
                    state.value = it
                    onValueChanged()
                },
                label = { Text(labelValue) },
                leadingIcon = { Icon(modifier = iconModifier, imageVector = icon, contentDescription = iconDescriptor) }
            )
        }
    }
}

@Preview
@Composable
private fun TextFieldPreview(){
    TextFieldComponent(labelValue = "Text", icon = Icons.Filled.Place)
}