package io.github.kdesp73.petadoption

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.util.Log
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import io.github.kdesp73.petadoption.firebase.User
import io.github.kdesp73.petadoption.room.AppDatabase
import io.github.kdesp73.petadoption.room.LocalUser
import java.security.MessageDigest
import java.util.Locale

fun checkName(name: String) = runCatching {
    require(name.all { !it.isWhitespace() })
    require(name.all { it.isLetter() })
}

fun checkEmail(email: String) = runCatching {
    val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"
    require(email.matches(emailRegex.toRegex()))
}

val ERR_LEN = MainActivity.appContext.getString(R.string.password_must_have_at_least_eight_characters)
val ERR_WHITESPACE = MainActivity.appContext.getString(R.string.password_must_not_contain_whitespace)
val ERR_DIGIT = MainActivity.appContext.getString(R.string.password_must_contain_at_least_one_digit)
val ERR_LOWER = MainActivity.appContext.getString(R.string.password_must_have_at_least_one_lowercase_letter)
val ERR_UPPER = MainActivity.appContext.getString(R.string.password_must_have_at_least_one_uppercase_letter)
val ERR_SPECIAL = MainActivity.appContext.getString(R.string.password_must_have_at_least_one_special_character_such_as)

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

fun navigateTo(
    route: String,
    navController: NavController,
    popUpToStartDestination: Boolean = true,
    saveStateOnPopUpTo: Boolean = true,
    launchAsSingleTop: Boolean = true,
    restore: Boolean = true
) {
    navController.navigate(route) {
        if (popUpToStartDestination) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = saveStateOnPopUpTo
            }
        }
        launchSingleTop = true
        restoreState = restore
    }
}

fun clearBackTracks(navController: NavController){
    navController.popBackStack(navController.graph.startDestinationId, inclusive = true)
}

fun changeLocale(language: String) {
    val locale = Locale(language)
    Locale.setDefault(locale)

    val resources = MainActivity.appContext.resources

    val configuration = resources.configuration
    configuration.setLocale(locale)
    configuration.setLayoutDirection(locale)

    resources.configuration.updateFrom(configuration)
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

fun isLandscape(configuration: Configuration) : Boolean {
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

    return screenWidth > screenHeight
}

fun openURL(context: Context, url: String) {
    val urlIntent = Intent(
        Intent.ACTION_VIEW,
        Uri.parse(url)
    )
    context.startActivity(urlIntent)
}

fun isLoggedIn(room: AppDatabase): Boolean{
    return room.userDao().getUsers().isNotEmpty()
}

fun isNotLoggedIn(room: AppDatabase): Boolean{
    return room.userDao().getUsers().isEmpty()
}

fun logOut(room: AppDatabase){
    room.userDao().deleteAll()
}

fun logIn(room: AppDatabase, user: User){
    val userDao = room.userDao()

    userDao.insert(LocalUser(user = user, loggedIn = true))
}

fun isOnline(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val capabilities =
        connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)

    if (capabilities != null) {
        if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
            Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
            return true
        } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
            Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
            return true
        } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
            Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
            return true
        }
    }

    return false
}

