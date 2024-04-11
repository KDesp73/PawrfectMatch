package io.github.kdesp73.petadoption

import io.github.kdesp73.petadoption.enums.Gender

data class UserInfo(
    val firstName: String,
    val lastName: String,
    val phone: String = "",
    val location: String = "",
    val gender: String = Gender.OTHER.label,
    val profileType: Int
){

}
