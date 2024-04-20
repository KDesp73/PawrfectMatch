package io.github.kdesp73.petadoption.firestore

import io.github.kdesp73.petadoption.enums.Gender

data class UserInfo(
    val email: String,
    val firstName: String,
    val lastName: String,
    val phone: String = "",
    val location: String = "",
    val gender: String = Gender.OTHER.label,
    val profileType: Int,
){
    fun toMap(): HashMap<String, Any?> {
        return hashMapOf(
            "email" to this.email,
            "firstName" to this.firstName,
            "lastName" to this.lastName,
            "phone" to this.phone,
            "location" to this.location,
            "gender" to this.gender,
            "profileType" to this.profileType,
        )
    }

}
