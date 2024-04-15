package io.github.kdesp73.petadoption.firestore

import androidx.compose.runtime.Composable
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
    var imageUrl: String
){
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
    @Composable
    fun ToComposable(){
        TODO()
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
        imageUrl = ""
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
        imageUrl = documentSnapshot["imageUrl"].toString()
    )
}