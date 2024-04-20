package io.github.kdesp73.petadoption

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.em

open class Pet (
    open val name: String,
    open val age: String,
    open val gender: String,
    open val location: String,
    open val type: String,
    open val size: String
) {

    @Composable
    fun ToComposable() {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(text = name, fontSize = 5.em, color = MaterialTheme.colorScheme.primary)
            Text(text = age)
            Text(text = gender)
            Text(text = location)
            Text(text = type)
            Text(text = size)
        }
    }
}
