package io.github.kdesp73.petadoption.room

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
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import io.github.kdesp73.petadoption.firestore.User
import io.github.kdesp73.petadoption.enums.ProfileType
import io.github.kdesp73.petadoption.firestore.UserInfo

@Entity
data class LocalUser (
    @PrimaryKey var id: Int = 0,
    @ColumnInfo(name = "email") var email: String,
    @ColumnInfo(name = "firstName") var firstName: String,
    @ColumnInfo(name = "lastName") var lastName: String,
    @ColumnInfo(name = "location") var location: String = "",
    @ColumnInfo(name = "gender") var gender: String,
    @ColumnInfo(name = "profile_type") var profileType: Int = 1,
    @ColumnInfo(name = "phone") var phone: String = "",
    @ColumnInfo(name = "logged_in") var loggedIn: Boolean = false
){
    constructor() : this(
        0,
        "",
        "",
        "",
        "",
        "",
        1,
        "",
        false
    )

    constructor(id: Int = 0, user: User, loggedIn: Boolean = true) : this(
        id,
        if(user.email == "null") "" else user.email,
        if(user.info.firstName == "null") "" else user.info.firstName,
        if(user.info.lastName == "null") "" else user.info.lastName,
        if(user.info.location == "null") "" else user.info.location,
        if(user.info.gender == "null") "" else user.info.gender,
        user.info.profileType,
        if(user.info.phone == "null") "" else user.info.phone,
        loggedIn,
    )

    constructor(id: Int = 0, info: UserInfo, loggedIn: Boolean = true) : this(
        id,
        if(info.email == "null") "" else info.email,
        if(info.firstName == "null") "" else info.firstName,
        if(info.lastName == "null") "" else info.lastName,
        if(info.location == "null") "" else info.location,
        if(info.gender == "null") "" else info.gender,
        info.profileType,
        if(info.phone == "null") "" else info.phone,
        loggedIn,
    )

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
            Text(modifier = textModifier, text = phone ?: "")
            Text(modifier = textModifier, text = location)
            Text(modifier = textModifier, text = gender.toString().lowercase().replaceFirstChar { it.uppercase() })
        }
    }


    companion object {
        val example = LocalUser(
            0,
            "example@gmail.com",
            "John",
            "Doe",
            "Tatooine",
            "Male",
            ProfileType.INDIVIDUAL.id,
            "1234567890",
            true
        )
    }
}