package io.github.kdesp73.petadoption

import androidx.compose.runtime.Composable

open class Toy (
    open val name: String,
    open val location: String,
    open val price: Float,
    open val ownerEmail: String,
){
    @Composable
    fun ToComposable(){

    }

    fun generateId(): String{
        return hash(
            name + location + price.toString() + ownerEmail
        )
    }
}