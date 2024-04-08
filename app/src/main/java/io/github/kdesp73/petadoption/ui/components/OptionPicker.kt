package io.github.kdesp73.petadoption.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun OptionPicker(modifier: Modifier = Modifier, state: MutableStateFlow<String>, options: List<String>, onClick: (MutableStateFlow<String>) -> Unit){
    Row (
        modifier = modifier
    ){
        options.forEach { option ->
            Button(
                shape = RectangleShape,
                colors =
                if(state.value == option)
                    ButtonDefaults.buttonColors()
                else
                    ButtonDefaults.buttonColors(
                        containerColor = Color.Gray,
                        contentColor = Color.Black
                    ),
                onClick = {
                    state.value = option
                    onClick(state)
                }
            ) {
                Text(text = option)
            }
        }
    }

}