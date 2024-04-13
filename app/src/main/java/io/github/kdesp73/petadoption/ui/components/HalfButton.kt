package io.github.kdesp73.petadoption.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em

@Composable
fun HalfButton(modifier: Modifier = Modifier, colors: ButtonColors = ButtonDefaults.buttonColors(), icon: ImageVector? = Icons.Filled.Place, text: String = "Button", height: Dp = 60.dp, action: () -> Unit){
    val configuration = LocalConfiguration.current

    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

    Button(
        colors = colors,
        modifier = modifier
            .width(screenWidth / 2 - 2.dp)
            .height(height)
            .padding(5.dp),
        onClick = {
            action()
        }
    ) {
        Row (
            horizontalArrangement = Arrangement.spacedBy(15.dp)
        ){
            if(icon != null){
                Icon(imageVector = icon, contentDescription = text)
            }
            Text(text = text, fontSize = 4.em)
        }
    }

}

@Preview
@Composable
fun HalfButtonPreview(){
    HalfButton (icon = Icons.Filled.Add, text = "Add a Pet"){

    }
}