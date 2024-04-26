package io.github.kdesp73.petadoption.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import io.github.kdesp73.petadoption.Toy
import io.github.kdesp73.petadoption.firebase.FirestoreToy

@Entity
data class LocalToy (
    @PrimaryKey(autoGenerate = true)  var id: Int = 0,
    @ColumnInfo(name = "name") override  var name: String,
    @ColumnInfo(name = "location") override  var location: String,
    @ColumnInfo(name = "price") override var price: Float,
    @ColumnInfo(name = "owner_email") override var ownerEmail: String
): Toy(name, location, price, ownerEmail){

    constructor(toy: FirestoreToy) : this (
        name = toy.name,
        location = toy.location,
        price = toy.price,
        ownerEmail = toy.ownerEmail
    )
}