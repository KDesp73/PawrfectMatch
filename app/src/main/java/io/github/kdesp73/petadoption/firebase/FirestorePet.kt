package io.github.kdesp73.petadoption.firebase

import com.google.firebase.firestore.DocumentSnapshot
import io.github.kdesp73.petadoption.Pet
import io.github.kdesp73.petadoption.enums.Gender
import io.github.kdesp73.petadoption.enums.PetAge
import io.github.kdesp73.petadoption.enums.PetSize
import io.github.kdesp73.petadoption.enums.PetType
import io.github.kdesp73.petadoption.hash
import io.github.kdesp73.petadoption.room.LocalPet

data class FirestorePet (
    var id: String,
    override var name: String = "Rex",
    override var age: String = PetAge.YOUNG.label,
    override var gender: String = Gender.MALE.label,
    override var location: String = "Tatooine",
    override var type: String = PetType.DOG.label,
    override var size: String = PetSize.LARGE.label,
    override var ownerEmail: String,
) : Pet(name, age, gender, location, type, size, ownerEmail){
    override fun toMap() : HashMap<String, Any>{
       return hashMapOf(
           "id" to id,
           "name" to name,
           "age" to age,
           "gender" to gender,
           "location" to location,
           "type" to type,
           "size" to size,
           "ownerEmail" to ownerEmail
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