package io.github.kdesp73.petadoption.enums

import io.github.kdesp73.petadoption.MainActivity
import io.github.kdesp73.petadoption.R

enum class PetType(val label: String, val value: String) {
    DOG(MainActivity.appContext.getString(R.string.pettype_dog), "dog"),
    CAT(MainActivity.appContext.getString(R.string.pettype_cat), "cat"),
    BUNNY(MainActivity.appContext.getString(R.string.pettype_bunny), "bunny"),
    BIRD(MainActivity.appContext.getString(R.string.pettype_bird), "bird"),
    SNAKE(MainActivity.appContext.getString(R.string.pettype_snake), "snake"),
    FISH(MainActivity.appContext.getString(R.string.pettype_fish), "fish")
}

val petTypeFromLabel = PetType.entries.associateBy { it.label }
val petTypeFromValue = PetType.entries.associateBy { it.value}
