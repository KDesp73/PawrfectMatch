package io.github.kdesp73.petadoption.enums

import io.github.kdesp73.petadoption.MainActivity
import io.github.kdesp73.petadoption.R

enum class PetAge (val label: String, val value: String){
    BABY(MainActivity.appContext.getString(R.string.petage_baby), "baby"),
    YOUNG(MainActivity.appContext.getString(R.string.petage_young), "young"),
    ADULT(MainActivity.appContext.getString(R.string.petage_adult), "adult"),
    SENIOR(MainActivity.appContext.getString(R.string.petage_senior), "senior")
}

val petAgeFromLabel = PetAge.entries.associateBy { it.label }
val petAgeFromValue = PetAge.entries.associateBy { it.value}
