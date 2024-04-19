package io.github.kdesp73.petadoption.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
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
