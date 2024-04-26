package io.github.kdesp73.petadoption.firebase

import com.google.firebase.firestore.DocumentSnapshot
import io.github.kdesp73.petadoption.Toy
import io.github.kdesp73.petadoption.hash

data class FirestoreToy (
    var id: String,
    override var name: String,
    override var location: String,
    override var price: Float,
    override var ownerEmail: String,
) : Toy(name, location, price, ownerEmail){

    constructor(
        name: String,
        location: String,
        price: Float,
        ownerEmail: String
    ) : this(
        id = hash(name + location + price + ownerEmail),
        name, location, price, ownerEmail
    )

    constructor(documentSnapshot: DocumentSnapshot) : this(
        id = documentSnapshot["id"].toString(),
        name = documentSnapshot["name"].toString(),
        location = documentSnapshot["location"].toString(),
        price = documentSnapshot["price"].toString().toFloat(),
        ownerEmail = documentSnapshot["ownerEmail"].toString()
    )

}