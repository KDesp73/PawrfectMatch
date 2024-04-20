package io.github.kdesp73.petadoption.enums

import io.github.kdesp73.petadoption.MainActivity
import io.github.kdesp73.petadoption.R

enum class PetSize(val label: String, val value: String) {
    SMALL(MainActivity.appContext.getString(R.string.petsize_small), "small"),
    MEDIUM(MainActivity.appContext.getString(R.string.petsize_medium), "medium"),
    LARGE(MainActivity.appContext.getString(R.string.petsize_large), "large"),
    XLARGE(MainActivity.appContext.getString(R.string.petsize_extra_large), "xlarge")
}

val petSizeFromLabel = PetSize.entries.associateBy { it.label }
val petSizeFromValue = PetSize.entries.associateBy { it.value }
