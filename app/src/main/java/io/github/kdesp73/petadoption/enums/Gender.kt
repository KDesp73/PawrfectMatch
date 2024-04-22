package io.github.kdesp73.petadoption.enums

import io.github.kdesp73.petadoption.MainActivity
import io.github.kdesp73.petadoption.R


enum class Gender(val label: String, val value: String) {
    MALE(MainActivity.appContext.getString( R.string.gender_male), "male"),
    FEMALE(MainActivity.appContext.getString(R.string.gender_female), "female"),
    OTHER(MainActivity.appContext.getString(R.string.gender_other), "other")
}

val genderFromLabel = Gender.entries.associateBy { it.label }
val genderFromValue = Gender.entries.associateBy { it.value }
val genderLabelList = Gender.entries.map { it.label }.toList()
val genderValueList = Gender.entries.map { it.value}.toList()
