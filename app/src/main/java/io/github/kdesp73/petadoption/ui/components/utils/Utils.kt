package io.github.kdesp73.petadoption.ui.components.utils

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun Center(modifier: Modifier,content: @Composable () -> Unit){
    Row (
        modifier = modifier
            .fillMaxHeight(),
        verticalAlignment = Alignment.CenterVertically
    ){
        Column (
            modifier = modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            content()
        }
    }
}