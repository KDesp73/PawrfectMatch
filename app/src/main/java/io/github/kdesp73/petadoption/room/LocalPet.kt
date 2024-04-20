package io.github.kdesp73.petadoption.room

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import io.github.kdesp73.petadoption.enums.Gender
import io.github.kdesp73.petadoption.enums.PetAge
import io.github.kdesp73.petadoption.enums.PetSize
import io.github.kdesp73.petadoption.enums.PetType
import io.github.kdesp73.petadoption.firestore.Pet

@Entity
data class LocalPet(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "age") var age: String,
    @ColumnInfo(name = "gender") var gender: String,
    @ColumnInfo(name = "location") var location: String,
    @ColumnInfo(name = "owner_email") var ownerEmail: String,
    @ColumnInfo(name = "size") var size: String,
    @ColumnInfo(name = "type") var type: String,
    @ColumnInfo(name = "image_uri") var imageUri: String
){
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

    @Composable
    fun ToComposable(){
        Column (
//            verticalArrangement = Arrangement.Center,
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

    constructor(pet: Pet) : this (
        name = pet.name,
        age = pet.age,
        gender = pet.gender,
        location = pet.location,
        ownerEmail = pet.ownerEmail,
        size = pet.size,
        type = pet.type,
        imageUri = ""
    )
}
