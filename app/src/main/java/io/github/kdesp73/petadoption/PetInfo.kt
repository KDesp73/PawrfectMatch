package io.github.kdesp73.petadoption

import io.github.kdesp73.petadoption.enums.Gender
import io.github.kdesp73.petadoption.enums.PetAge
import io.github.kdesp73.petadoption.enums.PetSize
import io.github.kdesp73.petadoption.enums.PetType

class PetInfo (
    private var name: String = "Rex",
    private var age: PetAge = PetAge.YOUNG,
    private var gender: Gender = Gender.MALE,
    private var location: String = "Tatooine",
    private var type: PetType = PetType.DOG,
    private var size: PetSize = PetSize.LARGE,
){
}