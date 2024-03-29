package io.github.kdesp73.petadoption.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Card(modifier: Modifier = Modifier, content: @Composable () -> Unit, width: Dp = 240.dp, height: Dp = 100.dp, elevation: Dp = 6.dp) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = elevation
        ),
        modifier = modifier
            .size(width = width, height = height)
    ) {
        content()
    }
}