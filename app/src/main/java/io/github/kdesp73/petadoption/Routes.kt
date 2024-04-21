package io.github.kdesp73.petadoption

import androidx.annotation.StringRes

sealed class Route (val route: String, @StringRes val resId: Int){
    object UserPage : Route(route = "user_page", R.string.route_user_page)
    object PetPage : Route(route = "pet_page", R.string.route_pet_showcase)
    object MyPets : Route(route = "my_pets", resId = R.string.route_my_pets)
    object Home : Route(route = "home", resId = R.string.route_home)
    object About : Route(route = "about", resId = R.string.route_about)
    object Account : Route(route = "account", resId = R.string.route_account)
    object AddPet : Route(route = "add_pet", resId = R.string.route_add_pet)
    object AddToy : Route(route = "add_toy", resId = R.string.route_add_toy)
    object AccountSettings: Route(route = "account_settings", resId = R.string.route_account_settings)
    object Favourites : Route("favourites", resId = R.string.route_favourites)
    object Search : Route(route = "search", resId = R.string.route_search)
    object Settings : Route(route = "settings", resId = R.string.route_settings)
    object SignIn : Route(route = "sign_in", resId = R.string.route_sign_in)
    object CreateAccount : Route(route = "create_account", resId = R.string.route_create_account)
    object Login : Route(route = "login", resId = R.string.route_login)
    object ChangePassword : Route(route = "change_password", R.string.route_change_password)
}
