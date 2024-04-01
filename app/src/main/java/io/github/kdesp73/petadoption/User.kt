package io.github.kdesp73.petadoption

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.kdesp73.petadoption.enums.Gender
import io.github.kdesp73.petadoption.enums.ProfileType


data class User(
    var firstName: String,
    var lastName: String,
    var email: String,
    var phone: String = "",
    var location: String = "",
    var gender: String = Gender.OTHER.label,
    var password: String,
    var profileType: Int,
) {
    companion object{
        val example = User(
            "John",
            "Doe",
            "example@gmail.com",
            "",
            "Tatooine",
            "Male",
            "03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4",
            ProfileType.INDIVIDUAL.id
        )
    }
    @Composable
    fun ToComposable(height: Dp){
        val textModifier = Modifier
            .padding(2.dp)

        Column (
            modifier = Modifier
                .height(height)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ){
            Text(modifier = textModifier, text = firstName)
            Text(modifier = textModifier, text = lastName)
            Text(modifier = textModifier, text = email)
            Text(modifier = textModifier, text = phone)
            Text(modifier = textModifier, text = location)
            Text(modifier = textModifier, text = gender.toString().lowercase().replaceFirstChar { it.uppercase() })
        }
    }
}

