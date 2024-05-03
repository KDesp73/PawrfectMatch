package io.github.kdesp73.petadoption.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun Modal(
    state: MutableStateFlow<Boolean> = MutableStateFlow(false),
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val open by state.collectAsState()
    if(open){
        Surface (
            modifier = modifier
                .clickable { state.value = false }
                .fillMaxSize(),
            color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.8f)
        ){
            content()
        }
    }
}