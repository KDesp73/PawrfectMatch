package io.github.kdesp73.petadoption.ui.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import io.github.kdesp73.petadoption.DecimalFormatter
import io.github.kdesp73.petadoption.DecimalInputVisualTransformation
import io.github.kdesp73.petadoption.enums.TextFieldType
import kotlinx.coroutines.flow.MutableStateFlow
import java.text.DecimalFormatSymbols
import java.util.Locale

@Composable
fun DecimalFieldComponent(
    state : MutableStateFlow<String> = MutableStateFlow(""),
    labelValue: String,
    icon: ImageVector,
    iconDescriptor: String = "Text field icon",
    type: TextFieldType = TextFieldType.NORMAL,
    isError: Boolean = false,
){
    val decimalFormatter = DecimalFormatter(symbols = DecimalFormatSymbols(Locale.US))
    val text by state.collectAsState()

    when (type) {
        TextFieldType.NORMAL -> {
            TextField(
                isError = isError,
                value = text.toString(),
                onValueChange = { state.value = decimalFormatter.cleanup(it) },
                label = { Text(labelValue) },
                leadingIcon = { Icon(imageVector = icon, contentDescription = iconDescriptor) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                visualTransformation = DecimalInputVisualTransformation(decimalFormatter)
            )
        }
        TextFieldType.OUTLINED -> {
            OutlinedTextField(
                isError = isError,
                value = text.toString(),
                onValueChange = { state.value = decimalFormatter.cleanup(it) },
                label = { Text(labelValue) },
                leadingIcon = { Icon(imageVector = icon, contentDescription = iconDescriptor) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                visualTransformation = DecimalInputVisualTransformation(decimalFormatter)
            )
        }
    }
}
