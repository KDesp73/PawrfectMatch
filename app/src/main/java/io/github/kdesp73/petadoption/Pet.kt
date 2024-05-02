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
    open val size: String,
    open val ownerEmail: String
) {

    open fun toMap(): HashMap<String, Any>{
        return hashMapOf(
            "name" to name,
            "age" to age,
            "gender" to gender,
            "location" to location,
            "type" to type,
            "size" to size,
            "ownerEmail" to ownerEmail
        )
    }

    @Composable
    fun ToComposable() {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start
        ) {
            val color = MaterialTheme.colorScheme.onSecondaryContainer
            Text(text = name, fontSize = 5.em, color = MaterialTheme.colorScheme.primary)
            Text(text = petTypeFromValue[type]?.label.toString(), color = color)
            Text(text = location, color = color)
            Text(text = genderFromValue[gender]?.label.toString(), color = color)
            Text(text = petAgeFromValue[age]?.label.toString(), color = color)
            Text(text = petSizeFromValue[size]?.label.toString(), color = color)
        }
    }

    fun generateId(): String {
        return hash(
            name +
                    age +
                    gender +
                    location +
                    type +
                    size +
                    ownerEmail
        )
    }
}
