package io.github.kdesp73.petadoption.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    gradient: Brush? = null,
    icon: ImageVector?,
    text: String?,
    action: () -> Unit = {}
) {
    Button(
        modifier = modifier,
        colors = colors,
        onClick = action,
        contentPadding = PaddingValues(),
    ) {
        Box(
            modifier = if(gradient != null) Modifier
                .background(gradient)
                .then(modifier) else modifier,
            contentAlignment = Alignment.Center,
        ){
            Row (
                horizontalArrangement = Arrangement.spacedBy(15.dp)
            ){
                if (icon != null) Icon(imageVector = icon, contentDescription = "icon")
                if (text != null) Text(text = text)
            }
        }
    }
}