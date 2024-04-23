package io.github.kdesp73.petadoption.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em

@Composable
fun InfoBox(
    modifier: Modifier = Modifier,
    label: String,
    info: Any?,
    width: Dp = 150.dp,
    height: Dp = 80.dp,
    infoFontSize: Float = 4.em.value
) {
    MyCard(
        modifier = modifier
            .size(width, height),
    ) {
        Column (
            modifier = Modifier
                .padding(2.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(text = label, color = Color.Gray)
            Text(
                text = info.toString(),
                fontSize = infoFontSize.em,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}

@Composable
fun InfoBoxClickable(
    modifier: Modifier = Modifier,
    label: String,
    info: Any?,
    width: Dp = 100.dp,
    height: Dp = 40.dp,
    infoFontSize: Float = 5.em.value,
    action: () -> Unit = {}
) {
    InfoBox(
        label = label,
        info = info,
        width = width,
        height = height,
        infoFontSize = infoFontSize,
        modifier = modifier.clickable {
            action()
        }
    )
}

@Preview
@Composable
fun InfoBoxPreview(){
    InfoBox(label = "Type", info = "Dog", width = 100.dp, height = 80.dp)
}
