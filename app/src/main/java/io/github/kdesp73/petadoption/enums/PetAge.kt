package io.github.kdesp73.petadoption.enums

import io.github.kdesp73.petadoption.R
import io.github.kdesp73.petadoption.resToString

enum class PetAge (val label: String, val value: String){
    BABY(resToString(R.string.petage_baby), "baby"),
    YOUNG(resToString(R.string.petage_young), "young"),
    ADULT(resToString(R.string.petage_adult), "adult"),
    SENIOR(resToString(R.string.petage_senior), "senior")
}

val petAgeFromLabel = PetAge.entries.associateBy { it.label }
val petAgeFromValue = PetAge.entries.associateBy { it.value}
