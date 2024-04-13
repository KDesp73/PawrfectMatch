package io.github.kdesp73.petadoption.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.kdesp73.petadoption.enums.Orientation
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun OptionPicker(
    modifier: Modifier = Modifier,
    state: MutableStateFlow<String>,
    options: List<String>,
    orientation: Orientation,
    width: Dp?,
) {
    val opt by state.collectAsState()
    @Composable
    fun Options(width: Dp?){
        val optionModifier = if(width != null) Modifier.width(width) else Modifier
        options.forEach { option ->
            Button(
                modifier = optionModifier,
                shape = RectangleShape,
                colors =
                if(opt == option)
                    ButtonDefaults.buttonColors()
                else
                    ButtonDefaults.buttonColors(
                        containerColor = Color.Gray,
                        contentColor = Color.Black
                    ),
                onClick = {
                    state.value = option
                }
            ) {
                Text(text = option)
            }
        }
    }

    when(orientation){
        Orientation.HORIZONTAL -> {
            Row (modifier = modifier){
                Options(null)
            }
        }
        Orientation.VERTICAL -> {
            Column (
                modifier = modifier,
                verticalArrangement = Arrangement.spacedBy(0.dp)
            ){
                Options(width)
            }
        }
    }

}