package io.github.kdesp73.petadoption

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.em
import io.github.kdesp73.petadoption.enums.genderFromValue
import io.github.kdesp73.petadoption.enums.petAgeFromValue
import io.github.kdesp73.petadoption.enums.petSizeFromValue
import io.github.kdesp73.petadoption.enums.petTypeFromValue

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
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start
        ) {
            Text(text = name, fontSize = 5.em, color = MaterialTheme.colorScheme.primary)
            Text(text = petTypeFromValue[type]?.label.toString())
            Text(text = location)
            Text(text = genderFromValue[gender]?.label.toString())
            Text(text = petAgeFromValue[age]?.label.toString())
            Text(text = petSizeFromValue[size]?.label.toString())
        }
    }
}
