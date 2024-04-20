package io.github.kdesp73.petadoption.firestore

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
import com.google.firebase.firestore.DocumentSnapshot
import io.github.kdesp73.petadoption.enums.ProfileType


data class User(
    val email: String,
    val password: String,
    val info: UserInfo?
) {
    constructor(
        email: String,
        password: String
    ): this(
        email,
        password,
        null
    )

    fun toMap(): HashMap<String, Any?> {
        return hashMapOf(
            "email" to this.email,
            "password" to this.password
        )
    }

    companion object{
        val example = User(
            email = "example@gmail.com",
            password = "03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4",
            info = UserInfo(
                "example@gmail.com",
                "John",
                "Doe",
                "1234567890",
                "Tatooine",
                "Male",
                profileType = ProfileType.INDIVIDUAL.id,
            )
        )

        fun documentToObject(userDocument: DocumentSnapshot, infoDocument: DocumentSnapshot): User {
            return User(
                email = userDocument["email"].toString(),
                password = userDocument["password"].toString(),
                info = UserInfo(
                    userDocument["email"].toString(),
                    infoDocument["firstName"].toString(),
                    infoDocument["lastName"].toString(),
                    infoDocument["phone"].toString(),
                    infoDocument["location"].toString(),
                    infoDocument["gender"].toString(),
                    infoDocument["profileType"].toString().toInt(),
                )
            )
        }
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
            Text(modifier = textModifier, text = info?.firstName!!)
            Text(modifier = textModifier, text = info.lastName)
            Text(modifier = textModifier, text = email)
            Text(modifier = textModifier, text = info.phone)
            Text(modifier = textModifier, text = info.location)
            Text(modifier = textModifier, text = info.gender.lowercase().replaceFirstChar { it.uppercase() })
        }
    }
}

