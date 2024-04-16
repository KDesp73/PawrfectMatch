package io.github.kdesp73.petadoption.enums

import io.github.kdesp73.petadoption.R
import io.github.kdesp73.petadoption.resToString

enum class PetType(val label: String, val value: String) {
    DOG(resToString(R.string.pettype_dog), "dog"),
    CAT(resToString(R.string.pettype_cat), "cat"),
    BUNNY(resToString(R.string.pettype_bunny), "bunny"),
    BIRD(resToString(R.string.pettype_bird), "bird"),
    SNAKE(resToString(R.string.pettype_snake), "snake"),
    FISH(resToString(R.string.pettype_fish), "fish")
}

val petTypeFromLabel = PetType.entries.associateBy { it.label }
val petTypeFromValue = PetType.entries.associateBy { it.value}
