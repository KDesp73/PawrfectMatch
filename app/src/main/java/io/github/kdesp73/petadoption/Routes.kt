package io.github.kdesp73.petadoption

import androidx.annotation.StringRes

sealed class Route (val label: String, val route: String, @StringRes val resId: Int){
    object Home : Route("Home", route = "home", resId = R.string.HOME)
    object About : Route("About", route = "about", resId = R.string.ABOUT)
    object Account : Route("Account", route = "account", resId = R.string.ACCOUNT)
    object AddPet : Route("Add Pet", route = "add_pet", resId = R.string.ADD_PET)
    object AddToy : Route("Add Toy", route = "add_toy", resId = R.string.ADD_TOY)
    object AccountSettings: Route("Account Settings", route = "account_settings", resId = R.string.ACCOUNT_SETTINGS)
    object Favourites : Route("Favourites", "favourites", resId = R.string.FAVOURITES)
    object Search : Route("Search", route = "search", resId = R.string.SEARCH)
    object Settings : Route("Settings", route = "settings", resId = R.string.SETTINGS)
    object SignIn : Route("Sign In", route = "sign_in", resId = R.string.SIGN_IN)
    object CreateAccount : Route("Create Account", route = "create_account", resId = R.string.CREATE_ACCOUNT)
    object Login : Route("Login", route = "login", resId = R.string.LOGIN)
    object ChangePassword : Route("Change Password", route = "change_password", R.string.CHANGE_PASSWORD)
}
