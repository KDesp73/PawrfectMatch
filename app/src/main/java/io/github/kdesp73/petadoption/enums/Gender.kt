package io.github.kdesp73.petadoption.enums

import android.content.res.Resources
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat.getString
import io.github.kdesp73.petadoption.MainActivity
import io.github.kdesp73.petadoption.R
import io.github.kdesp73.petadoption.enums.Gender


enum class Gender(val label: String, val value: String) {
    MALE(MainActivity.appContext.getString( R.string.gender_male), "male"),
    FEMALE(MainActivity.appContext.getString(R.string.gender_female), "female"),
    OTHER(MainActivity.appContext.getString(R.string.gender_other), "other")
}

val genderFromLabel = Gender.entries.associateBy { it.label }
val genderFromValue = Gender.entries.associateBy { it.value }

