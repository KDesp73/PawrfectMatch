package io.github.kdesp73.petadoption

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.firebase.FirebaseApp
import io.github.kdesp73.petadoption.enums.Routes
import io.github.kdesp73.petadoption.routes.About
import io.github.kdesp73.petadoption.routes.Account
import io.github.kdesp73.petadoption.routes.AddPet
import io.github.kdesp73.petadoption.routes.AddToy
import io.github.kdesp73.petadoption.routes.CreateAccount
import io.github.kdesp73.petadoption.routes.EditAccount
import io.github.kdesp73.petadoption.routes.Favourites
import io.github.kdesp73.petadoption.routes.Home
import io.github.kdesp73.petadoption.routes.Login
import io.github.kdesp73.petadoption.routes.Search
import io.github.kdesp73.petadoption.routes.Settings
import io.github.kdesp73.petadoption.routes.SignIn
import io.github.kdesp73.petadoption.ui.components.Layout


class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n", "RestrictedApi", "StateFlowValueCalledInComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this);

        val debugChannel= NotificationChannel(
            R.string.DEBUG.toString(),
            "Debug",
            NotificationManager.IMPORTANCE_HIGH
        )

        val mainChannel= NotificationChannel(
            R.string.MAIN.toString(),
            "Main",
            NotificationManager.IMPORTANCE_HIGH
        )

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(debugChannel)
        notificationManager.createNotificationChannel(mainChannel)

        setContent {
            val navController = rememberNavController()
            var currentRoute by remember { mutableStateOf(Routes.HOME.label) }
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination?.route

            currentRoute = currentDestination ?: ""

            Layout(topAppBarText = currentRoute, navController = navController) {
                NavHost(navController, startDestination = Routes.HOME.label) {
                    composable(Routes.HOME.label) { Home() }
                    composable(Routes.SEARCH.label) { Search() }
                    composable(Routes.FAVOURITES.label) { Favourites() }
                    composable(Routes.ABOUT.label) { About() }
                    composable(Routes.SETTINGS.label) { Settings() }
                    composable(Routes.ACCOUNT.label) { Account(navController) }
                    composable(Routes.EDIT_ACCOUNT.label) { EditAccount() }
                    composable(Routes.SIGN_IN.label) { SignIn(navController) }
                    composable(Routes.ADD_PET.label) { AddPet() }
                    composable(Routes.ADD_TOY.label) { AddToy() }
                    composable(Routes.CREATE_ACCOUNT.label) { CreateAccount()}
                    composable(Routes.LOGIN.label) { Login() }
                }
            }
        }
    }
}
