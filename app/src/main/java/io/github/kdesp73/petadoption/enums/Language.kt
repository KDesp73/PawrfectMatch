package io.github.kdesp73.petadoption.enums

import io.github.kdesp73.petadoption.R
import io.github.kdesp73.petadoption.resToString

enum class Language (val label: String, val value: String){
    ENGLISH(resToString(R.string.lang_english), "english"),
    GREEK(resToString(R.string.lang_greek), "greek")
}

val locales = hashMapOf(
    Language.ENGLISH.label to "en_US",
    Language.GREEK.label to "el_GR"
)

val languageFromLabel = Language.entries.associateBy { it.label }
val languageFromValue = Language.entries.associateBy { it.value }

