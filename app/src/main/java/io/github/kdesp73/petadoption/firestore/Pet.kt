package io.github.kdesp73.petadoption.firestore

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.google.firebase.firestore.DocumentSnapshot
import io.github.kdesp73.petadoption.enums.Gender
import io.github.kdesp73.petadoption.enums.PetAge
import io.github.kdesp73.petadoption.enums.PetSize
import io.github.kdesp73.petadoption.enums.PetType
import io.github.kdesp73.petadoption.hash

data class Pet (
    var id: String,
    var name: String = "Rex",
    var age: String = PetAge.YOUNG.label,
    var gender: String = Gender.MALE.label,
    var location: String = "Tatooine",
    var type: String = PetType.DOG.label,
    var size: String = PetSize.LARGE.label,
    var ownerEmail: String,
){
    @Composable
    fun ToComposable(){
        Column (
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ){
            Text(text = name)
            Text(text = age)
            Text(text = gender)
            Text(text = location)
            Text(text = type)
            Text(text = size)
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

    fun getImageFile() : String {
        return this.id + ".jpg"
    }

    constructor(
        name: String = "Rex",
        age: String = PetAge.YOUNG.label,
        gender: String = Gender.MALE.label,
        location: String = "Tatooine",
        type: String = PetType.DOG.label,
        size: String = PetSize.LARGE.label,
        ownerEmail: String
    ) : this (
        id = hash(str = name +
                age +
                gender +
                location +
                type +
                size +
                ownerEmail
        ),
        name = name,
        age = age,
        gender = gender,
        location = location,
        type = type,
        size = size,
        ownerEmail = ownerEmail,
    )

    constructor(documentSnapshot: DocumentSnapshot) : this(
        id = hash(str =
        documentSnapshot["name"].toString() +
                documentSnapshot["age"].toString() +
                documentSnapshot["gender"].toString() +
                documentSnapshot["location"].toString() +
                documentSnapshot["type"].toString() +
                documentSnapshot["size"].toString() +
                documentSnapshot["ownerEmail"].toString()
        ),
        name = documentSnapshot["name"].toString(),
        age = documentSnapshot["age"].toString(),
        gender = documentSnapshot["gender"].toString(),
        location = documentSnapshot["location"].toString(),
        type = documentSnapshot["type"].toString(),
        size = documentSnapshot["size"].toString(),
        ownerEmail = documentSnapshot["ownerEmail"].toString(),
    )
}