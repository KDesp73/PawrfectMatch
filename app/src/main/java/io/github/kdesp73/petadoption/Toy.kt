package io.github.kdesp73.petadoption

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.em
import io.github.kdesp73.petadoption.enums.genderFromValue
import io.github.kdesp73.petadoption.enums.petAgeFromValue
import io.github.kdesp73.petadoption.enums.petSizeFromValue
import io.github.kdesp73.petadoption.enums.petTypeFromValue

open class Toy (
    open val name: String,
    open val location: String,
    open val price: Float,
    open val ownerEmail: String,
){
    @Composable
    fun ToComposable(){
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start
        ) {
            val color = MaterialTheme.colorScheme.onSecondaryContainer
            Text(text = name, fontSize = 5.em, color = MaterialTheme.colorScheme.primary)
            Text(text = location, color = color)
            Text(text = price.toString() + "$", color = color)
        }
    }

    fun generateId(): String{
        return hash(
            name + location + price.toString() + ownerEmail
        )
    }
}