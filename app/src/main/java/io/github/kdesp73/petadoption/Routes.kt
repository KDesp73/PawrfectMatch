package io.github.kdesp73.petadoption

import androidx.annotation.StringRes

sealed class Route (val route: String, @StringRes val resId: Int){
    object SearchResults : Route(route = "search_results", R.string.route_search_results)
    object EditPet: Route(route = "edit_pet", R.string.route_edit_pet)
    object EditToy : Route(route = "edit_toy", R.string.route_edit_toy)
    object UserPage : Route(route = "user_page", R.string.route_user_page)
    object PetPage : Route(route = "pet_page", R.string.route_pet_showcase)
    object ToyPage : Route(route = "toy_page", R.string.route_toy_showcase)
    object MyPets : Route(route = "my_pets", resId = R.string.route_my_pets)
    object MyToys : Route(route = "my_toys", resId = R.string.route_my_toys)
    object MyAdditions : Route(route = "my_additions", resId = R.string.route_my_additions)
    object Home : Route(route = "home", resId = R.string.route_home)
    object About : Route(route = "about", resId = R.string.route_about)
    object Account : Route(route = "account", resId = R.string.route_account)
    object AddPet : Route(route = "add_pet", resId = R.string.route_add_pet)
    object AddToy : Route(route = "add_toy", resId = R.string.route_add_toy)
    object AccountSettings: Route(route = "account_settings", resId = R.string.route_account_settings)
    object Favourites : Route("favourites", resId = R.string.route_favourites)
    object Search : Route(route = "search", resId = R.string.route_search)
    object SearchPets : Route(route = "search_pets", resId = R.string.route_search)
    object SearchToys : Route(route = "search_toys", resId = R.string.route_search)
    object Settings : Route(route = "settings", resId = R.string.route_settings)
    object SignIn : Route(route = "sign_in", resId = R.string.route_sign_in)
    object CreateAccount : Route(route = "create_account", resId = R.string.route_create_account)
    object Login : Route(route = "login", resId = R.string.route_login)
    object ChangePassword : Route(route = "change_password", R.string.route_change_password)
}
