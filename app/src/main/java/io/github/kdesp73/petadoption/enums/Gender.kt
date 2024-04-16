package io.github.kdesp73.petadoption.enums

import android.content.res.Resources
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat.getString
import io.github.kdesp73.petadoption.R
import io.github.kdesp73.petadoption.resToString
import io.github.kdesp73.petadoption.enums.Gender


enum class Gender(val label: String, val value: String) {
    MALE(resToString( R.string.gender_male), "male"),
    FEMALE(resToString(R.string.gender_female), "female"),
    OTHER(resToString(R.string.gender_other), "other")
}

val genderFromLabel = Gender.entries.associateBy { it.label }
val genderFromValue = Gender.entries.associateBy { it.value }

