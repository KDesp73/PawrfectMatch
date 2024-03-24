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


class ProfileInfo(
    private var firstName: String,
    private var lastName: String,
    private var email: String,
    private var phone: String,
    private var location: String,
    private var gender: Gender
) {
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

