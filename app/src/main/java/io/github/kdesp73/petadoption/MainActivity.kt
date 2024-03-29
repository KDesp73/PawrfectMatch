package io.github.kdesp73.petadoption

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
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

private const val TAG = "MainActivity"
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n", "RestrictedApi", "StateFlowValueCalledInComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.wtf(TAG, "Application Started")
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
            /*
            var currentRoute by remember { mutableStateOf(Routes.HOME.tag) }
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination?.route

            currentRoute = currentDestination ?: ""
             */

            Layout(topAppBarText = "PetAdoption", navController = navController) {
                NavHost(navController, startDestination = Routes.HOME.tag) {
                    composable(Routes.HOME.tag) { Home() }
                    composable(Routes.SEARCH.tag) { Search() }
                    composable(Routes.FAVOURITES.tag) { Favourites() }
                    composable(Routes.ABOUT.tag) { About() }
                    composable(Routes.SETTINGS.tag) { Settings() }
                    composable(Routes.ACCOUNT.tag) { Account(navController) }
                    composable(Routes.EDIT_ACCOUNT.tag) { EditAccount() }
                    composable(Routes.SIGN_IN.tag) { SignIn(navController) }
                    composable(Routes.ADD_PET.tag) { AddPet() }
                    composable(Routes.ADD_TOY.tag) { AddToy() }
                    composable(Routes.CREATE_ACCOUNT.tag) { CreateAccount()}
                    composable(Routes.LOGIN.tag) { Login() }
                }
            }
        }
    }
}
