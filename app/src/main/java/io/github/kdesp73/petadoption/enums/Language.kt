package io.github.kdesp73.petadoption.enums

import io.github.kdesp73.petadoption.MainActivity
import io.github.kdesp73.petadoption.R

enum class Language (val label: String, val value: String){
    ENGLISH(MainActivity.appContext.getString(R.string.lang_english), "english"),
    GREEK(MainActivity.appContext.getString(R.string.lang_greek), "greek")
}

val locales = hashMapOf(
    Language.ENGLISH.value to "en_US",
    Language.GREEK.value to "el_GR"
)

val languageFromLabel = Language.entries.associateBy { it.label }
val languageFromValue = Language.entries.associateBy { it.value }

