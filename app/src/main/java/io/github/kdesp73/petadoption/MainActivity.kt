package io.github.kdesp73.petadoption

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import io.github.kdesp73.petadoption.routes.About
import io.github.kdesp73.petadoption.routes.Account
import io.github.kdesp73.petadoption.routes.EditAccount
import io.github.kdesp73.petadoption.routes.Home
import io.github.kdesp73.petadoption.routes.Search

class MainActivity : ComponentActivity() {
    @SuppressLint("SetTextI18n", "RestrictedApi", "StateFlowValueCalledInComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            var currentRoute by remember { mutableStateOf("Home") }
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination?.route

            currentRoute = currentDestination ?: ""

            Layout(topAppBarText = currentRoute, navController = navController) {
                NavHost(navController, startDestination = "Home") {
                    composable("Home") { Home() }
                    composable("Search") { Search() }
                    composable("About") { About() }
                    composable("Account") { Account(navController) }
                    composable("Edit Account") { EditAccount() }
                }
            }
        }
    }
}
