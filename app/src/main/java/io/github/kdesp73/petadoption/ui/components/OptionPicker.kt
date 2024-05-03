package io.github.kdesp73.petadoption.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.kdesp73.petadoption.enums.Orientation
import kotlinx.coroutines.flow.MutableStateFlow

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun OptionPicker(
    modifier: Modifier = Modifier,
    value: String,
    options: List<String>,
    orientation: Orientation,
    width: Dp?,
    onChange: (String) -> Unit = {}
) {
    @Composable
    fun Options(width: Dp?){
        val optionModifier = if(width != null) Modifier.width(width) else Modifier
        options.forEach { option ->
            Button(
                modifier = optionModifier,
                shape = RectangleShape,
                colors =
                if(value == option)
                    ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                else
                    ButtonDefaults.buttonColors(
                        containerColor = Color.Gray,
                        contentColor = Color.Black
                    ),
                onClick = {
                    onChange(option)
                }
            ) {
                Text(text = option)
            }
        }
    }

    when(orientation){
        Orientation.HORIZONTAL -> {
            FlowRow (modifier = modifier){
                Options(null)
            }
        }
        Orientation.VERTICAL -> {
            FlowColumn (
                modifier = modifier,
                verticalArrangement = Arrangement.spacedBy(0.dp)
            ){
                Options(width)
            }
        }
    }

}
@OptIn(ExperimentalLayoutApi::class)
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
                    ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                    )
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
            FlowRow (modifier = modifier){
                Options(null)
            }
        }
        Orientation.VERTICAL -> {
            FlowColumn (
                modifier = modifier,
                verticalArrangement = Arrangement.spacedBy(0.dp)
            ){
                Options(width)
            }
        }
    }

}