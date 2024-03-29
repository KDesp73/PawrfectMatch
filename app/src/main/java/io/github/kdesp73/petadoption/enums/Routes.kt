package io.github.kdesp73.petadoption.enums

enum class Routes (val label: String, val tag: String = ""){
    HOME("Home", tag = "home"),
    ABOUT("About", tag = "about"),
    ACCOUNT("Account", tag = "account"),
    ADD_PET("Add Pet", tag = "add_pet"),
    ADD_TOY("Add Toy", tag = "add_toy"),
    EDIT_ACCOUNT("Edit Account", tag = "edit_account"),
    FAVOURITES("Favourites", "favourites"),
    SEARCH("Search", tag = "search"),
    SETTINGS("Settings", tag = "settings"),
    SIGN_IN("Sign In", tag = "sign_in"),
    CREATE_ACCOUNT("Create Account", tag = "create_account"),
    LOGIN("Login", tag = "login")
}