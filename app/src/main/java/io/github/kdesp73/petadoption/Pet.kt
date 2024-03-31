package io.github.kdesp73.petadoption

import androidx.compose.runtime.Composable
import io.github.kdesp73.petadoption.enums.Gender
import io.github.kdesp73.petadoption.enums.PetAge
import io.github.kdesp73.petadoption.enums.PetSize
import io.github.kdesp73.petadoption.enums.PetType

data class Pet (
    var name: String = "Rex",
    var age: PetAge = PetAge.YOUNG,
    var gender: Gender = Gender.MALE,
    var location: String = "Tatooine",
    var type: PetType = PetType.DOG,
    var size: PetSize = PetSize.LARGE,
){
    @Composable
    fun ToComposable(){
        TODO()
    }
}