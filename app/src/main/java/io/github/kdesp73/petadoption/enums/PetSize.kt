package io.github.kdesp73.petadoption.enums

import io.github.kdesp73.petadoption.R
import io.github.kdesp73.petadoption.resToString

enum class PetSize(val label: String, val value: String) {
    SMALL(resToString(R.string.petsize_small), "small"),
    MEDIUM(resToString(R.string.petsize_medium), "medium"),
    LARGE(resToString(R.string.petsize_large), "large"),
    XLARGE(resToString(R.string.petsize_extra_large), "xlarge")
}

val petSizeFromLabel = PetSize.entries.associateBy { it.label }
val petSizeFromValue = PetSize.entries.associateBy { it.value }
