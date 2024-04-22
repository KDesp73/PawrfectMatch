package io.github.kdesp73.petadoption.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em

@Composable
fun IconButton(
    modifier: Modifier = Modifier,
    text: String?,
    fontSize: TextUnit = 4.em,
    imageVector: ImageVector? = null,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    action: () -> Unit
){
    Button(
        colors = colors,
        modifier = modifier,
        onClick = {
            action()
        }
    ) {
        Row (
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            if(imageVector != null){
                Icon(imageVector = imageVector, contentDescription = text)
            }
            if(text != null){
                Text(text = text, fontSize = fontSize)
            }
        }
    }


}