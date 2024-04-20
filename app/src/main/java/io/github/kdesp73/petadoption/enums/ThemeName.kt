package io.github.kdesp73.petadoption.enums

import io.github.kdesp73.petadoption.MainActivity
import io.github.kdesp73.petadoption.R

enum class ThemeName(val label: String, val value: String) {
    DARK(MainActivity.appContext.getString(R.string.theme_dark), "dark"),
    LIGHT(MainActivity.appContext.getString(R.string.theme_light), "light"),
    AUTO(MainActivity.appContext.getString(R.string.theme_auto), "auto");
}

val themeNameFromLabel = ThemeName.entries.associateBy { it.label }
val themeNameFromValue = ThemeName.entries.associateBy { it.value }
