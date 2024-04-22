package io.github.kdesp73.petadoption.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.MutableStateFlow


@Composable
fun CircularIconButton(
    state: MutableStateFlow<ImageVector>,
    description: String,
    bg: Color,
    size: Dp,
    action: () -> Unit
) {
    val icon by state.collectAsState()
    IconButton(
        modifier = Modifier
            .size(size),
        onClick = { action() }
    ) {
        Surface (
            shape = CircleShape,
            modifier = Modifier
                .background(bg, CircleShape)
                .size(size),
        ){
            Icon(
                modifier = Modifier
                    .background(bg)
                    .padding(5.dp),
                imageVector = icon,
                contentDescription = description
            )
        }

    }
}

@Composable
fun CircularIconButton(icon: ImageVector, description: String, bg: Color, size: Dp, action: () -> Unit){
    IconButton(
        modifier = Modifier
            .size(size),
        onClick = { action() }
    ) {
        Surface (
            shape = CircleShape,
            modifier = Modifier
                .background(bg, CircleShape)
                .size(size),
        ){
            Icon(
                modifier = Modifier
                    .background(bg)
                    .padding(5.dp),
                imageVector = icon,
                contentDescription = description
            )
        }

    }
}

@Preview
@Composable
fun CirularIconButtonPreview(){
    CircularIconButton(icon = Icons.Filled.Settings, description = "Settings button", bg = Color.Green, size = 30.dp){

    }
}