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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.room.Room
import com.google.firebase.FirebaseApp
import com.google.firebase.database.core.view.Change
import io.github.kdesp73.petadoption.enums.ThemeName
import io.github.kdesp73.petadoption.room.AppDatabase
import io.github.kdesp73.petadoption.room.LocalUser
import io.github.kdesp73.petadoption.routes.About
import io.github.kdesp73.petadoption.routes.Account
import io.github.kdesp73.petadoption.routes.AccountSettings
import io.github.kdesp73.petadoption.routes.AddPet
import io.github.kdesp73.petadoption.routes.AddToy
import io.github.kdesp73.petadoption.routes.ChangePassword
import io.github.kdesp73.petadoption.routes.CreateAccount
import io.github.kdesp73.petadoption.routes.Favourites
import io.github.kdesp73.petadoption.routes.Home
import io.github.kdesp73.petadoption.routes.Login
import io.github.kdesp73.petadoption.routes.Search
import io.github.kdesp73.petadoption.routes.Settings
import io.github.kdesp73.petadoption.routes.SignIn
import io.github.kdesp73.petadoption.ui.components.Layout
import kotlinx.coroutines.launch

private const val TAG = "MainActivity"

class MainActivity : ComponentActivity() {
    private fun roomInit(roomDatabase: AppDatabase){
        lifecycleScope.launch {
            if(roomDatabase.settingsDao().isFirstRun()){
                roomDatabase.settingsDao().insert(io.github.kdesp73.petadoption.room.Settings(theme = ThemeName.LIGHT.name, language = "en", firstRun = false))
                roomDatabase.userDao().insert(LocalUser())
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n", "RestrictedApi", "StateFlowValueCalledInComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.wtf(TAG, "Application Started")
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this);
        val room = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "local-db"
        ).allowMainThreadQueries().fallbackToDestructiveMigration().build()

        roomInit(room)

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

            Layout(topAppBarText = "PetAdoption", navController = navController, room) {
                NavHost(navController, startDestination = Route.Home.route) {
                    composable(Route.Home.route) { Home() }
                    composable(Route.Search.route) { Search() }
                    composable(Route.Favourites.route) { Favourites() }
                    composable(Route.About.route) { About() }
                    composable(Route.Settings.route) { Settings(room) }
                    composable(route = Route.Account.route) {
                        Account(
                            navController = navController,
                            roomDatabase = room
                        )
                    }
                    composable(Route.AccountSettings.route) { AccountSettings(navController, room) }
                    composable(Route.SignIn.route) { SignIn(navController) }
                    composable(Route.AddPet.route) { AddPet(room) }
                    composable(Route.AddToy.route) { AddToy() }
                    composable(Route.ChangePassword.route) { ChangePassword(room, navController) }
                    composable(Route.CreateAccount.route) { CreateAccount(navController)}
                    composable(
                        route = Route.Login.route + "?email={email}",
                        arguments = listOf(navArgument(
                            name = "email",
                        ) { defaultValue = "" })
                    ) { backStackEntry ->
                        backStackEntry.arguments?.getString("email")
                            ?.let { Login(navController, it, room) }
                    }
                }
            }
        }
    }
}
