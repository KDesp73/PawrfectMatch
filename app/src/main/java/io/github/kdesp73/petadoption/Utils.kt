package io.github.kdesp73.petadoption

import android.app.LocaleManager
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.LocaleList
import android.preference.PreferenceManager
import android.view.ContextThemeWrapper
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.core.os.LocaleListCompat
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import io.github.kdesp73.petadoption.enums.locales
import java.security.MessageDigest
import java.util.Locale

fun checkName(name: String): Boolean {
    return name.all { it.isLetter() } && name.isNotBlank() && name.isNotEmpty()
}

fun checkEmail(email: String): Boolean {
    val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"
    return email.matches(emailRegex.toRegex())
}

val ERR_LEN = resToString(R.string.password_must_have_at_least_eight_characters)
val ERR_WHITESPACE = resToString(R.string.password_must_not_contain_whitespace)
val ERR_DIGIT = resToString(R.string.password_must_contain_at_least_one_digit)
val ERR_LOWER = resToString(R.string.password_must_have_at_least_one_lowercase_letter)
val ERR_UPPER = resToString(R.string.password_must_have_at_least_one_uppercase_letter)
val ERR_SPECIAL = resToString(R.string.password_must_have_at_least_one_special_character_such_as)

fun validatePassword(pwd: String) = runCatching {
    require(pwd.length >= 8) { ERR_LEN }
    require(pwd.none { it.isWhitespace() }) { ERR_WHITESPACE }
    require(pwd.any { it.isDigit() }) { ERR_DIGIT }
    require(pwd.any { it.isLowerCase() }) { ERR_LOWER }
    require(pwd.any { it.isUpperCase() }) { ERR_UPPER }
    require(pwd.any { !it.isLetterOrDigit() }) { ERR_SPECIAL }
}

fun hash(str: String): String {
    val bytes = str.toByteArray()
    val md = MessageDigest.getInstance("SHA-256")
    val digest = md.digest(bytes)
    return digest.fold("") { s, it -> s + "%02x".format(it) }
}

fun navigateTo(route: String, navController: NavController){
    navController.navigate(route) {
        popUpTo(navController.graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}

fun resToString(res: Int) : String {
    return MainActivity.appContext.getString(res)
}

fun getLocales() : LocaleList? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        MainActivity.appContext.getSystemService(LocaleManager::class.java).applicationLocales
    } else {
        null
    }
}

fun changeLocale(locale: String) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        MainActivity.appContext.getSystemService(LocaleManager::class.java)
            .applicationLocales = LocaleList.forLanguageTags(locale)
    } else {
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(locale))
    }
}

fun imageBitmapFromBitmap(bitmap: Bitmap, context: Context) : ImageBitmap {
    val option = BitmapFactory.Options()
    option.inPreferredConfig = Bitmap.Config.ARGB_8888
    return BitmapFactory.decodeResource(
        context.resources,
        bitmap.generationId,
        option
    ).asImageBitmap()
}