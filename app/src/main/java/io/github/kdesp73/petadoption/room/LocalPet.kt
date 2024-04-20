package io.github.kdesp73.petadoption.room

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.em
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import io.github.kdesp73.petadoption.Pet
import io.github.kdesp73.petadoption.enums.Gender
import io.github.kdesp73.petadoption.enums.PetAge
import io.github.kdesp73.petadoption.enums.PetSize
import io.github.kdesp73.petadoption.enums.PetType
import io.github.kdesp73.petadoption.enums.genderFromValue
import io.github.kdesp73.petadoption.enums.petAgeFromValue
import io.github.kdesp73.petadoption.enums.petSizeFromValue
import io.github.kdesp73.petadoption.enums.petTypeFromValue
import io.github.kdesp73.petadoption.firestore.FirestorePet
import io.github.kdesp73.petadoption.hash

@Entity
data class LocalPet(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "name") override var name: String,
    @ColumnInfo(name = "age") override var age: String,
    @ColumnInfo(name = "gender") override var gender: String,
    @ColumnInfo(name = "location") override var location: String,
    @ColumnInfo(name = "owner_email") var ownerEmail: String,
    @ColumnInfo(name = "size") override var size: String,
    @ColumnInfo(name = "type") override var type: String,
    @ColumnInfo(name = "image_uri") var imageUri: String
) : Pet(name, age, gender, location, type, size){
    companion object {
        val example = LocalPet(
            name = "Kitty",
            age = PetAge.YOUNG.value,
            gender = Gender.FEMALE.value,
            location = "Tattoine",
            ownerEmail = "",
            size = PetSize.MEDIUM.value,
            type = PetType.DOG.value,
            imageUri = "https://firebasestorage.googleapis.com/v0/b/petadoption-f6e9a.appspot.com/o/pets%2Fabde9407e881d6bb7a971ee4895ac4830dae4cdbf13e2c990f5f831c34fedc86.jpg?alt=media&token=273b7744-419e-4b99-8433-5a5fd834629d"
        )
    }

    constructor(pet: FirestorePet) : this (
        name = pet.name,
        age = pet.age,
        gender = pet.gender,
        location = pet.location,
        ownerEmail = pet.ownerEmail,
        size = pet.size,
        type = pet.type,
        imageUri = ""
    )

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
