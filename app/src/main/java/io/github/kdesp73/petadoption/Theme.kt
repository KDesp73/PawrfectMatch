package io.github.kdesp73.petadoption

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import io.github.kdesp73.petadoption.enums.ThemeName
import io.github.kdesp73.petadoption.ui.theme.Pink40
import io.github.kdesp73.petadoption.ui.theme.Pink80
import io.github.kdesp73.petadoption.ui.theme.Purple40
import io.github.kdesp73.petadoption.ui.theme.Purple80
import io.github.kdesp73.petadoption.ui.theme.PurpleGrey40
import io.github.kdesp73.petadoption.ui.theme.PurpleGrey80
import io.github.kdesp73.petadoption.ui.theme.backgroundDark
import io.github.kdesp73.petadoption.ui.theme.backgroundDarkHighContrast
import io.github.kdesp73.petadoption.ui.theme.backgroundDarkMediumContrast
import io.github.kdesp73.petadoption.ui.theme.backgroundLight
import io.github.kdesp73.petadoption.ui.theme.backgroundLightHighContrast
import io.github.kdesp73.petadoption.ui.theme.backgroundLightMediumContrast
import io.github.kdesp73.petadoption.ui.theme.errorContainerDark
import io.github.kdesp73.petadoption.ui.theme.errorContainerDarkHighContrast
import io.github.kdesp73.petadoption.ui.theme.errorContainerDarkMediumContrast
import io.github.kdesp73.petadoption.ui.theme.errorContainerLight
import io.github.kdesp73.petadoption.ui.theme.errorContainerLightHighContrast
import io.github.kdesp73.petadoption.ui.theme.errorContainerLightMediumContrast
import io.github.kdesp73.petadoption.ui.theme.errorDark
import io.github.kdesp73.petadoption.ui.theme.errorDarkHighContrast
import io.github.kdesp73.petadoption.ui.theme.errorDarkMediumContrast
import io.github.kdesp73.petadoption.ui.theme.errorLight
import io.github.kdesp73.petadoption.ui.theme.errorLightHighContrast
import io.github.kdesp73.petadoption.ui.theme.errorLightMediumContrast
import io.github.kdesp73.petadoption.ui.theme.inverseOnSurfaceDark
import io.github.kdesp73.petadoption.ui.theme.inverseOnSurfaceDarkHighContrast
import io.github.kdesp73.petadoption.ui.theme.inverseOnSurfaceDarkMediumContrast
import io.github.kdesp73.petadoption.ui.theme.inverseOnSurfaceLight
import io.github.kdesp73.petadoption.ui.theme.inverseOnSurfaceLightHighContrast
import io.github.kdesp73.petadoption.ui.theme.inverseOnSurfaceLightMediumContrast
import io.github.kdesp73.petadoption.ui.theme.inversePrimaryDark
import io.github.kdesp73.petadoption.ui.theme.inversePrimaryDarkHighContrast
import io.github.kdesp73.petadoption.ui.theme.inversePrimaryDarkMediumContrast
import io.github.kdesp73.petadoption.ui.theme.inversePrimaryLight
import io.github.kdesp73.petadoption.ui.theme.inversePrimaryLightHighContrast
import io.github.kdesp73.petadoption.ui.theme.inversePrimaryLightMediumContrast
import io.github.kdesp73.petadoption.ui.theme.inverseSurfaceDark
import io.github.kdesp73.petadoption.ui.theme.inverseSurfaceDarkHighContrast
import io.github.kdesp73.petadoption.ui.theme.inverseSurfaceDarkMediumContrast
import io.github.kdesp73.petadoption.ui.theme.inverseSurfaceLight
import io.github.kdesp73.petadoption.ui.theme.inverseSurfaceLightHighContrast
import io.github.kdesp73.petadoption.ui.theme.inverseSurfaceLightMediumContrast
import io.github.kdesp73.petadoption.ui.theme.onBackgroundDark
import io.github.kdesp73.petadoption.ui.theme.onBackgroundDarkHighContrast
import io.github.kdesp73.petadoption.ui.theme.onBackgroundDarkMediumContrast
import io.github.kdesp73.petadoption.ui.theme.onBackgroundLight
import io.github.kdesp73.petadoption.ui.theme.onBackgroundLightHighContrast
import io.github.kdesp73.petadoption.ui.theme.onBackgroundLightMediumContrast
import io.github.kdesp73.petadoption.ui.theme.onErrorContainerDark
import io.github.kdesp73.petadoption.ui.theme.onErrorContainerDarkHighContrast
import io.github.kdesp73.petadoption.ui.theme.onErrorContainerDarkMediumContrast
import io.github.kdesp73.petadoption.ui.theme.onErrorContainerLight
import io.github.kdesp73.petadoption.ui.theme.onErrorContainerLightHighContrast
import io.github.kdesp73.petadoption.ui.theme.onErrorContainerLightMediumContrast
import io.github.kdesp73.petadoption.ui.theme.onErrorDark
import io.github.kdesp73.petadoption.ui.theme.onErrorDarkHighContrast
import io.github.kdesp73.petadoption.ui.theme.onErrorDarkMediumContrast
import io.github.kdesp73.petadoption.ui.theme.onErrorLight
import io.github.kdesp73.petadoption.ui.theme.onErrorLightHighContrast
import io.github.kdesp73.petadoption.ui.theme.onErrorLightMediumContrast
import io.github.kdesp73.petadoption.ui.theme.onPrimaryContainerDark
import io.github.kdesp73.petadoption.ui.theme.onPrimaryContainerDarkHighContrast
import io.github.kdesp73.petadoption.ui.theme.onPrimaryContainerDarkMediumContrast
import io.github.kdesp73.petadoption.ui.theme.onPrimaryContainerLight
import io.github.kdesp73.petadoption.ui.theme.onPrimaryContainerLightHighContrast
import io.github.kdesp73.petadoption.ui.theme.onPrimaryContainerLightMediumContrast
import io.github.kdesp73.petadoption.ui.theme.onPrimaryDark
import io.github.kdesp73.petadoption.ui.theme.onPrimaryDarkHighContrast
import io.github.kdesp73.petadoption.ui.theme.onPrimaryDarkMediumContrast
import io.github.kdesp73.petadoption.ui.theme.onPrimaryLight
import io.github.kdesp73.petadoption.ui.theme.onPrimaryLightHighContrast
import io.github.kdesp73.petadoption.ui.theme.onPrimaryLightMediumContrast
import io.github.kdesp73.petadoption.ui.theme.onSecondaryContainerDark
import io.github.kdesp73.petadoption.ui.theme.onSecondaryContainerDarkHighContrast
import io.github.kdesp73.petadoption.ui.theme.onSecondaryContainerDarkMediumContrast
import io.github.kdesp73.petadoption.ui.theme.onSecondaryContainerLight
import io.github.kdesp73.petadoption.ui.theme.onSecondaryContainerLightHighContrast
import io.github.kdesp73.petadoption.ui.theme.onSecondaryContainerLightMediumContrast
import io.github.kdesp73.petadoption.ui.theme.onSecondaryDark
import io.github.kdesp73.petadoption.ui.theme.onSecondaryDarkHighContrast
import io.github.kdesp73.petadoption.ui.theme.onSecondaryDarkMediumContrast
import io.github.kdesp73.petadoption.ui.theme.onSecondaryLight
import io.github.kdesp73.petadoption.ui.theme.onSecondaryLightHighContrast
import io.github.kdesp73.petadoption.ui.theme.onSecondaryLightMediumContrast
import io.github.kdesp73.petadoption.ui.theme.onSurfaceDark
import io.github.kdesp73.petadoption.ui.theme.onSurfaceDarkHighContrast
import io.github.kdesp73.petadoption.ui.theme.onSurfaceDarkMediumContrast
import io.github.kdesp73.petadoption.ui.theme.onSurfaceLight
import io.github.kdesp73.petadoption.ui.theme.onSurfaceLightHighContrast
import io.github.kdesp73.petadoption.ui.theme.onSurfaceLightMediumContrast
import io.github.kdesp73.petadoption.ui.theme.onSurfaceVariantDark
import io.github.kdesp73.petadoption.ui.theme.onSurfaceVariantDarkHighContrast
import io.github.kdesp73.petadoption.ui.theme.onSurfaceVariantDarkMediumContrast
import io.github.kdesp73.petadoption.ui.theme.onSurfaceVariantLight
import io.github.kdesp73.petadoption.ui.theme.onSurfaceVariantLightHighContrast
import io.github.kdesp73.petadoption.ui.theme.onSurfaceVariantLightMediumContrast
import io.github.kdesp73.petadoption.ui.theme.onTertiaryContainerDark
import io.github.kdesp73.petadoption.ui.theme.onTertiaryContainerDarkHighContrast
import io.github.kdesp73.petadoption.ui.theme.onTertiaryContainerDarkMediumContrast
import io.github.kdesp73.petadoption.ui.theme.onTertiaryContainerLight
import io.github.kdesp73.petadoption.ui.theme.onTertiaryContainerLightHighContrast
import io.github.kdesp73.petadoption.ui.theme.onTertiaryContainerLightMediumContrast
import io.github.kdesp73.petadoption.ui.theme.onTertiaryDark
import io.github.kdesp73.petadoption.ui.theme.onTertiaryDarkHighContrast
import io.github.kdesp73.petadoption.ui.theme.onTertiaryDarkMediumContrast
import io.github.kdesp73.petadoption.ui.theme.onTertiaryLight
import io.github.kdesp73.petadoption.ui.theme.onTertiaryLightHighContrast
import io.github.kdesp73.petadoption.ui.theme.onTertiaryLightMediumContrast
import io.github.kdesp73.petadoption.ui.theme.outlineDark
import io.github.kdesp73.petadoption.ui.theme.outlineDarkHighContrast
import io.github.kdesp73.petadoption.ui.theme.outlineDarkMediumContrast
import io.github.kdesp73.petadoption.ui.theme.outlineLight
import io.github.kdesp73.petadoption.ui.theme.outlineLightHighContrast
import io.github.kdesp73.petadoption.ui.theme.outlineLightMediumContrast
import io.github.kdesp73.petadoption.ui.theme.outlineVariantDark
import io.github.kdesp73.petadoption.ui.theme.outlineVariantDarkHighContrast
import io.github.kdesp73.petadoption.ui.theme.outlineVariantDarkMediumContrast
import io.github.kdesp73.petadoption.ui.theme.outlineVariantLight
import io.github.kdesp73.petadoption.ui.theme.outlineVariantLightHighContrast
import io.github.kdesp73.petadoption.ui.theme.outlineVariantLightMediumContrast
import io.github.kdesp73.petadoption.ui.theme.primaryContainerDark
import io.github.kdesp73.petadoption.ui.theme.primaryContainerDarkHighContrast
import io.github.kdesp73.petadoption.ui.theme.primaryContainerDarkMediumContrast
import io.github.kdesp73.petadoption.ui.theme.primaryContainerLight
import io.github.kdesp73.petadoption.ui.theme.primaryContainerLightHighContrast
import io.github.kdesp73.petadoption.ui.theme.primaryContainerLightMediumContrast
import io.github.kdesp73.petadoption.ui.theme.primaryDark
import io.github.kdesp73.petadoption.ui.theme.primaryDarkHighContrast
import io.github.kdesp73.petadoption.ui.theme.primaryDarkMediumContrast
import io.github.kdesp73.petadoption.ui.theme.primaryLight
import io.github.kdesp73.petadoption.ui.theme.primaryLightHighContrast
import io.github.kdesp73.petadoption.ui.theme.primaryLightMediumContrast
import io.github.kdesp73.petadoption.ui.theme.scrimDark
import io.github.kdesp73.petadoption.ui.theme.scrimDarkHighContrast
import io.github.kdesp73.petadoption.ui.theme.scrimDarkMediumContrast
import io.github.kdesp73.petadoption.ui.theme.scrimLight
import io.github.kdesp73.petadoption.ui.theme.scrimLightHighContrast
import io.github.kdesp73.petadoption.ui.theme.scrimLightMediumContrast
import io.github.kdesp73.petadoption.ui.theme.secondaryContainerDark
import io.github.kdesp73.petadoption.ui.theme.secondaryContainerDarkHighContrast
import io.github.kdesp73.petadoption.ui.theme.secondaryContainerDarkMediumContrast
import io.github.kdesp73.petadoption.ui.theme.secondaryContainerLight
import io.github.kdesp73.petadoption.ui.theme.secondaryContainerLightHighContrast
import io.github.kdesp73.petadoption.ui.theme.secondaryContainerLightMediumContrast
import io.github.kdesp73.petadoption.ui.theme.secondaryDark
import io.github.kdesp73.petadoption.ui.theme.secondaryDarkHighContrast
import io.github.kdesp73.petadoption.ui.theme.secondaryDarkMediumContrast
import io.github.kdesp73.petadoption.ui.theme.secondaryLight
import io.github.kdesp73.petadoption.ui.theme.secondaryLightHighContrast
import io.github.kdesp73.petadoption.ui.theme.secondaryLightMediumContrast
import io.github.kdesp73.petadoption.ui.theme.surfaceBrightDark
import io.github.kdesp73.petadoption.ui.theme.surfaceBrightDarkHighContrast
import io.github.kdesp73.petadoption.ui.theme.surfaceBrightDarkMediumContrast
import io.github.kdesp73.petadoption.ui.theme.surfaceBrightLight
import io.github.kdesp73.petadoption.ui.theme.surfaceBrightLightHighContrast
import io.github.kdesp73.petadoption.ui.theme.surfaceBrightLightMediumContrast
import io.github.kdesp73.petadoption.ui.theme.surfaceContainerDark
import io.github.kdesp73.petadoption.ui.theme.surfaceContainerDarkHighContrast
import io.github.kdesp73.petadoption.ui.theme.surfaceContainerDarkMediumContrast
import io.github.kdesp73.petadoption.ui.theme.surfaceContainerHighDark
import io.github.kdesp73.petadoption.ui.theme.surfaceContainerHighDarkHighContrast
import io.github.kdesp73.petadoption.ui.theme.surfaceContainerHighDarkMediumContrast
import io.github.kdesp73.petadoption.ui.theme.surfaceContainerHighLight
import io.github.kdesp73.petadoption.ui.theme.surfaceContainerHighLightHighContrast
import io.github.kdesp73.petadoption.ui.theme.surfaceContainerHighLightMediumContrast
import io.github.kdesp73.petadoption.ui.theme.surfaceContainerHighestDark
import io.github.kdesp73.petadoption.ui.theme.surfaceContainerHighestDarkHighContrast
import io.github.kdesp73.petadoption.ui.theme.surfaceContainerHighestDarkMediumContrast
import io.github.kdesp73.petadoption.ui.theme.surfaceContainerHighestLight
import io.github.kdesp73.petadoption.ui.theme.surfaceContainerHighestLightHighContrast
import io.github.kdesp73.petadoption.ui.theme.surfaceContainerHighestLightMediumContrast
import io.github.kdesp73.petadoption.ui.theme.surfaceContainerLight
import io.github.kdesp73.petadoption.ui.theme.surfaceContainerLightHighContrast
import io.github.kdesp73.petadoption.ui.theme.surfaceContainerLightMediumContrast
import io.github.kdesp73.petadoption.ui.theme.surfaceContainerLowDark
import io.github.kdesp73.petadoption.ui.theme.surfaceContainerLowDarkHighContrast
import io.github.kdesp73.petadoption.ui.theme.surfaceContainerLowDarkMediumContrast
import io.github.kdesp73.petadoption.ui.theme.surfaceContainerLowLight
import io.github.kdesp73.petadoption.ui.theme.surfaceContainerLowLightHighContrast
import io.github.kdesp73.petadoption.ui.theme.surfaceContainerLowLightMediumContrast
import io.github.kdesp73.petadoption.ui.theme.surfaceContainerLowestDark
import io.github.kdesp73.petadoption.ui.theme.surfaceContainerLowestDarkHighContrast
import io.github.kdesp73.petadoption.ui.theme.surfaceContainerLowestDarkMediumContrast
import io.github.kdesp73.petadoption.ui.theme.surfaceContainerLowestLight
import io.github.kdesp73.petadoption.ui.theme.surfaceContainerLowestLightHighContrast
import io.github.kdesp73.petadoption.ui.theme.surfaceContainerLowestLightMediumContrast
import io.github.kdesp73.petadoption.ui.theme.surfaceDark
import io.github.kdesp73.petadoption.ui.theme.surfaceDarkHighContrast
import io.github.kdesp73.petadoption.ui.theme.surfaceDarkMediumContrast
import io.github.kdesp73.petadoption.ui.theme.surfaceDimDark
import io.github.kdesp73.petadoption.ui.theme.surfaceDimDarkHighContrast
import io.github.kdesp73.petadoption.ui.theme.surfaceDimDarkMediumContrast
import io.github.kdesp73.petadoption.ui.theme.surfaceDimLight
import io.github.kdesp73.petadoption.ui.theme.surfaceDimLightHighContrast
import io.github.kdesp73.petadoption.ui.theme.surfaceDimLightMediumContrast
import io.github.kdesp73.petadoption.ui.theme.surfaceLight
import io.github.kdesp73.petadoption.ui.theme.surfaceLightHighContrast
import io.github.kdesp73.petadoption.ui.theme.surfaceLightMediumContrast
import io.github.kdesp73.petadoption.ui.theme.surfaceVariantDark
import io.github.kdesp73.petadoption.ui.theme.surfaceVariantDarkHighContrast
import io.github.kdesp73.petadoption.ui.theme.surfaceVariantDarkMediumContrast
import io.github.kdesp73.petadoption.ui.theme.surfaceVariantLight
import io.github.kdesp73.petadoption.ui.theme.surfaceVariantLightHighContrast
import io.github.kdesp73.petadoption.ui.theme.surfaceVariantLightMediumContrast
import io.github.kdesp73.petadoption.ui.theme.tertiaryContainerDark
import io.github.kdesp73.petadoption.ui.theme.tertiaryContainerDarkHighContrast
import io.github.kdesp73.petadoption.ui.theme.tertiaryContainerDarkMediumContrast
import io.github.kdesp73.petadoption.ui.theme.tertiaryContainerLight
import io.github.kdesp73.petadoption.ui.theme.tertiaryContainerLightHighContrast
import io.github.kdesp73.petadoption.ui.theme.tertiaryContainerLightMediumContrast
import io.github.kdesp73.petadoption.ui.theme.tertiaryDark
import io.github.kdesp73.petadoption.ui.theme.tertiaryDarkHighContrast
import io.github.kdesp73.petadoption.ui.theme.tertiaryDarkMediumContrast
import io.github.kdesp73.petadoption.ui.theme.tertiaryLight
import io.github.kdesp73.petadoption.ui.theme.tertiaryLightHighContrast
import io.github.kdesp73.petadoption.ui.theme.tertiaryLightMediumContrast

private val lightScheme = lightColorScheme(
    primary = primaryLight,
    onPrimary = onPrimaryLight,
    primaryContainer = primaryContainerLight,
    onPrimaryContainer = onPrimaryContainerLight,
    secondary = secondaryLight,
    onSecondary = onSecondaryLight,
    secondaryContainer = secondaryContainerLight,
    onSecondaryContainer = onSecondaryContainerLight,
    tertiary = tertiaryLight,
    onTertiary = onTertiaryLight,
    tertiaryContainer = tertiaryContainerLight,
    onTertiaryContainer = onTertiaryContainerLight,
    error = errorLight,
    onError = onErrorLight,
    errorContainer = errorContainerLight,
    onErrorContainer = onErrorContainerLight,
    background = backgroundLight,
    onBackground = onBackgroundLight,
    surface = surfaceLight,
    onSurface = onSurfaceLight,
    surfaceVariant = surfaceVariantLight,
    onSurfaceVariant = onSurfaceVariantLight,
    outline = outlineLight,
    outlineVariant = outlineVariantLight,
    scrim = scrimLight,
    inverseSurface = inverseSurfaceLight,
    inverseOnSurface = inverseOnSurfaceLight,
    inversePrimary = inversePrimaryLight,
    surfaceDim = surfaceDimLight,
    surfaceBright = surfaceBrightLight,
    surfaceContainerLowest = surfaceContainerLowestLight,
    surfaceContainerLow = surfaceContainerLowLight,
    surfaceContainer = surfaceContainerLight,
    surfaceContainerHigh = surfaceContainerHighLight,
    surfaceContainerHighest = surfaceContainerHighestLight,
)

private val darkScheme = darkColorScheme(
    primary = primaryDark,
    onPrimary = onPrimaryDark,
    primaryContainer = primaryContainerDark,
    onPrimaryContainer = onPrimaryContainerDark,
    secondary = secondaryDark,
    onSecondary = onSecondaryDark,
    secondaryContainer = secondaryContainerDark,
    onSecondaryContainer = onSecondaryContainerDark,
    tertiary = tertiaryDark,
    onTertiary = onTertiaryDark,
    tertiaryContainer = tertiaryContainerDark,
    onTertiaryContainer = onTertiaryContainerDark,
    error = errorDark,
    onError = onErrorDark,
    errorContainer = errorContainerDark,
    onErrorContainer = onErrorContainerDark,
    background = backgroundDark,
    onBackground = onBackgroundDark,
    surface = surfaceDark,
    onSurface = onSurfaceDark,
    surfaceVariant = surfaceVariantDark,
    onSurfaceVariant = onSurfaceVariantDark,
    outline = outlineDark,
    outlineVariant = outlineVariantDark,
    scrim = scrimDark,
    inverseSurface = inverseSurfaceDark,
    inverseOnSurface = inverseOnSurfaceDark,
    inversePrimary = inversePrimaryDark,
    surfaceDim = surfaceDimDark,
    surfaceBright = surfaceBrightDark,
    surfaceContainerLowest = surfaceContainerLowestDark,
    surfaceContainerLow = surfaceContainerLowDark,
    surfaceContainer = surfaceContainerDark,
    surfaceContainerHigh = surfaceContainerHighDark,
    surfaceContainerHighest = surfaceContainerHighestDark,
)

private val mediumContrastLightColorScheme = lightColorScheme(
    primary = primaryLightMediumContrast,
    onPrimary = onPrimaryLightMediumContrast,
    primaryContainer = primaryContainerLightMediumContrast,
    onPrimaryContainer = onPrimaryContainerLightMediumContrast,
    secondary = secondaryLightMediumContrast,
    onSecondary = onSecondaryLightMediumContrast,
    secondaryContainer = secondaryContainerLightMediumContrast,
    onSecondaryContainer = onSecondaryContainerLightMediumContrast,
    tertiary = tertiaryLightMediumContrast,
    onTertiary = onTertiaryLightMediumContrast,
    tertiaryContainer = tertiaryContainerLightMediumContrast,
    onTertiaryContainer = onTertiaryContainerLightMediumContrast,
    error = errorLightMediumContrast,
    onError = onErrorLightMediumContrast,
    errorContainer = errorContainerLightMediumContrast,
    onErrorContainer = onErrorContainerLightMediumContrast,
    background = backgroundLightMediumContrast,
    onBackground = onBackgroundLightMediumContrast,
    surface = surfaceLightMediumContrast,
    onSurface = onSurfaceLightMediumContrast,
    surfaceVariant = surfaceVariantLightMediumContrast,
    onSurfaceVariant = onSurfaceVariantLightMediumContrast,
    outline = outlineLightMediumContrast,
    outlineVariant = outlineVariantLightMediumContrast,
    scrim = scrimLightMediumContrast,
    inverseSurface = inverseSurfaceLightMediumContrast,
    inverseOnSurface = inverseOnSurfaceLightMediumContrast,
    inversePrimary = inversePrimaryLightMediumContrast,
    surfaceDim = surfaceDimLightMediumContrast,
    surfaceBright = surfaceBrightLightMediumContrast,
    surfaceContainerLowest = surfaceContainerLowestLightMediumContrast,
    surfaceContainerLow = surfaceContainerLowLightMediumContrast,
    surfaceContainer = surfaceContainerLightMediumContrast,
    surfaceContainerHigh = surfaceContainerHighLightMediumContrast,
    surfaceContainerHighest = surfaceContainerHighestLightMediumContrast,
)

private val highContrastLightColorScheme = lightColorScheme(
    primary = primaryLightHighContrast,
    onPrimary = onPrimaryLightHighContrast,
    primaryContainer = primaryContainerLightHighContrast,
    onPrimaryContainer = onPrimaryContainerLightHighContrast,
    secondary = secondaryLightHighContrast,
    onSecondary = onSecondaryLightHighContrast,
    secondaryContainer = secondaryContainerLightHighContrast,
    onSecondaryContainer = onSecondaryContainerLightHighContrast,
    tertiary = tertiaryLightHighContrast,
    onTertiary = onTertiaryLightHighContrast,
    tertiaryContainer = tertiaryContainerLightHighContrast,
    onTertiaryContainer = onTertiaryContainerLightHighContrast,
    error = errorLightHighContrast,
    onError = onErrorLightHighContrast,
    errorContainer = errorContainerLightHighContrast,
    onErrorContainer = onErrorContainerLightHighContrast,
    background = backgroundLightHighContrast,
    onBackground = onBackgroundLightHighContrast,
    surface = surfaceLightHighContrast,
    onSurface = onSurfaceLightHighContrast,
    surfaceVariant = surfaceVariantLightHighContrast,
    onSurfaceVariant = onSurfaceVariantLightHighContrast,
    outline = outlineLightHighContrast,
    outlineVariant = outlineVariantLightHighContrast,
    scrim = scrimLightHighContrast,
    inverseSurface = inverseSurfaceLightHighContrast,
    inverseOnSurface = inverseOnSurfaceLightHighContrast,
    inversePrimary = inversePrimaryLightHighContrast,
    surfaceDim = surfaceDimLightHighContrast,
    surfaceBright = surfaceBrightLightHighContrast,
    surfaceContainerLowest = surfaceContainerLowestLightHighContrast,
    surfaceContainerLow = surfaceContainerLowLightHighContrast,
    surfaceContainer = surfaceContainerLightHighContrast,
    surfaceContainerHigh = surfaceContainerHighLightHighContrast,
    surfaceContainerHighest = surfaceContainerHighestLightHighContrast,
)

private val mediumContrastDarkColorScheme = darkColorScheme(
    primary = primaryDarkMediumContrast,
    onPrimary = onPrimaryDarkMediumContrast,
    primaryContainer = primaryContainerDarkMediumContrast,
    onPrimaryContainer = onPrimaryContainerDarkMediumContrast,
    secondary = secondaryDarkMediumContrast,
    onSecondary = onSecondaryDarkMediumContrast,
    secondaryContainer = secondaryContainerDarkMediumContrast,
    onSecondaryContainer = onSecondaryContainerDarkMediumContrast,
    tertiary = tertiaryDarkMediumContrast,
    onTertiary = onTertiaryDarkMediumContrast,
    tertiaryContainer = tertiaryContainerDarkMediumContrast,
    onTertiaryContainer = onTertiaryContainerDarkMediumContrast,
    error = errorDarkMediumContrast,
    onError = onErrorDarkMediumContrast,
    errorContainer = errorContainerDarkMediumContrast,
    onErrorContainer = onErrorContainerDarkMediumContrast,
    background = backgroundDarkMediumContrast,
    onBackground = onBackgroundDarkMediumContrast,
    surface = surfaceDarkMediumContrast,
    onSurface = onSurfaceDarkMediumContrast,
    surfaceVariant = surfaceVariantDarkMediumContrast,
    onSurfaceVariant = onSurfaceVariantDarkMediumContrast,
    outline = outlineDarkMediumContrast,
    outlineVariant = outlineVariantDarkMediumContrast,
    scrim = scrimDarkMediumContrast,
    inverseSurface = inverseSurfaceDarkMediumContrast,
    inverseOnSurface = inverseOnSurfaceDarkMediumContrast,
    inversePrimary = inversePrimaryDarkMediumContrast,
    surfaceDim = surfaceDimDarkMediumContrast,
    surfaceBright = surfaceBrightDarkMediumContrast,
    surfaceContainerLowest = surfaceContainerLowestDarkMediumContrast,
    surfaceContainerLow = surfaceContainerLowDarkMediumContrast,
    surfaceContainer = surfaceContainerDarkMediumContrast,
    surfaceContainerHigh = surfaceContainerHighDarkMediumContrast,
    surfaceContainerHighest = surfaceContainerHighestDarkMediumContrast,
)

private val highContrastDarkColorScheme = darkColorScheme(
    primary = primaryDarkHighContrast,
    onPrimary = onPrimaryDarkHighContrast,
    primaryContainer = primaryContainerDarkHighContrast,
    onPrimaryContainer = onPrimaryContainerDarkHighContrast,
    secondary = secondaryDarkHighContrast,
    onSecondary = onSecondaryDarkHighContrast,
    secondaryContainer = secondaryContainerDarkHighContrast,
    onSecondaryContainer = onSecondaryContainerDarkHighContrast,
    tertiary = tertiaryDarkHighContrast,
    onTertiary = onTertiaryDarkHighContrast,
    tertiaryContainer = tertiaryContainerDarkHighContrast,
    onTertiaryContainer = onTertiaryContainerDarkHighContrast,
    error = errorDarkHighContrast,
    onError = onErrorDarkHighContrast,
    errorContainer = errorContainerDarkHighContrast,
    onErrorContainer = onErrorContainerDarkHighContrast,
    background = backgroundDarkHighContrast,
    onBackground = onBackgroundDarkHighContrast,
    surface = surfaceDarkHighContrast,
    onSurface = onSurfaceDarkHighContrast,
    surfaceVariant = surfaceVariantDarkHighContrast,
    onSurfaceVariant = onSurfaceVariantDarkHighContrast,
    outline = outlineDarkHighContrast,
    outlineVariant = outlineVariantDarkHighContrast,
    scrim = scrimDarkHighContrast,
    inverseSurface = inverseSurfaceDarkHighContrast,
    inverseOnSurface = inverseOnSurfaceDarkHighContrast,
    inversePrimary = inversePrimaryDarkHighContrast,
    surfaceDim = surfaceDimDarkHighContrast,
    surfaceBright = surfaceBrightDarkHighContrast,
    surfaceContainerLowest = surfaceContainerLowestDarkHighContrast,
    surfaceContainerLow = surfaceContainerLowDarkHighContrast,
    surfaceContainer = surfaceContainerDarkHighContrast,
    surfaceContainerHigh = surfaceContainerHighDarkHighContrast,
    surfaceContainerHighest = surfaceContainerHighestDarkHighContrast,
)

val defaultDarkScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

val defaultLightScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40,
)


sealed class Theme(
    var name: ThemeName,
    val colors: ColorScheme?
) {
    data object Dark : Theme(
        name = ThemeName.DARK,
        colors = darkScheme,
    )

    data object Light : Theme(
        name = ThemeName.LIGHT,
        colors = lightScheme
    )

    data object Auto: Theme (
        name = ThemeName.AUTO,
        colors = null
    )


    companion object{
        fun getTheme(name: String): Theme? {
            val themes = hashMapOf<String, Theme>()
            themes[ThemeName.DARK.label] = Theme.Dark
            themes[ThemeName.LIGHT.label] = Theme.Light
            themes[ThemeName.AUTO.label] = Theme.Auto

            return themes[name]
        }
    }
}

