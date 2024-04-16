package io.github.kdesp73.petadoption.enums

import io.github.kdesp73.petadoption.R
import io.github.kdesp73.petadoption.resToString

enum class ThemeName(val label: String, val value: String) {
    DARK(resToString(R.string.theme_dark), "dark"),
    LIGHT(resToString(R.string.theme_light), "light"),
    AUTO(resToString(R.string.theme_auto), "auto");
}

val themeNameFromLabel = ThemeName.entries.associateBy { it.label }
val themeNameFromValue = ThemeName.entries.associateBy { it.value }
