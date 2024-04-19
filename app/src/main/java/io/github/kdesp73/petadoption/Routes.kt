package io.github.kdesp73.petadoption

import androidx.annotation.StringRes

sealed class Route (val label: String, val route: String, @StringRes val resId: Int){
    object MyPets : Route("My Pets", route = "my_pets", resId = R.string.route_my_pets)
    object Home : Route("Home", route = "home", resId = R.string.route_home)
    object About : Route("About", route = "about", resId = R.string.route_about)
    object Account : Route("Account", route = "account", resId = R.string.route_account)
    object AddPet : Route("Add Pet", route = "add_pet", resId = R.string.route_add_pet)
    object AddToy : Route("Add Toy", route = "add_toy", resId = R.string.route_add_toy)
    object AccountSettings: Route("Account Settings", route = "account_settings", resId = R.string.route_account_settings)
    object Favourites : Route("Favourites", "favourites", resId = R.string.route_favourites)
    object Search : Route("Search", route = "search", resId = R.string.route_search)
    object Settings : Route("Settings", route = "settings", resId = R.string.route_settings)
    object SignIn : Route("Sign In", route = "sign_in", resId = R.string.route_sign_in)
    object CreateAccount : Route("Create Account", route = "create_account", resId = R.string.route_create_account)
    object Login : Route("Login", route = "login", resId = R.string.route_login)
    object ChangePassword : Route("Change Password", route = "change_password", R.string.route_change_password)
}
