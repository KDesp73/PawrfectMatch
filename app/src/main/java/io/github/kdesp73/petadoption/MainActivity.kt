package io.github.kdesp73.petadoption

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.room.Room
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.firebase.FirebaseApp
import io.github.kdesp73.petadoption.enums.ThemeName
import io.github.kdesp73.petadoption.room.AppDatabase
import io.github.kdesp73.petadoption.routes.About
import io.github.kdesp73.petadoption.routes.Account
import io.github.kdesp73.petadoption.routes.AccountSettings
import io.github.kdesp73.petadoption.routes.AddPet
import io.github.kdesp73.petadoption.routes.AddToy
import io.github.kdesp73.petadoption.routes.ChangePassword
import io.github.kdesp73.petadoption.routes.CreateAccount
import io.github.kdesp73.petadoption.routes.EditPet
import io.github.kdesp73.petadoption.routes.EditToy
import io.github.kdesp73.petadoption.routes.Favourites
import io.github.kdesp73.petadoption.routes.Home
import io.github.kdesp73.petadoption.routes.Login
import io.github.kdesp73.petadoption.routes.MyAdditions
import io.github.kdesp73.petadoption.routes.Search
import io.github.kdesp73.petadoption.routes.SearchResults
import io.github.kdesp73.petadoption.routes.Settings
import io.github.kdesp73.petadoption.routes.ShowPet
import io.github.kdesp73.petadoption.routes.ShowToy
import io.github.kdesp73.petadoption.routes.SignIn
import io.github.kdesp73.petadoption.routes.UserPage
import io.github.kdesp73.petadoption.ui.components.Layout
import kotlinx.coroutines.launch

private const val TAG = "MainActivity"

class MainActivity : ComponentActivity() {
    companion object {
        lateinit var appContext: Context
        lateinit var instance: MainActivity
    }

    private fun roomInit(roomDatabase: AppDatabase){
        lifecycleScope.launch {
            if(roomDatabase.settingsDao().getSettings() == null){
                roomDatabase.settingsDao().insert(io.github.kdesp73.petadoption.room.Settings(theme = ThemeName.LIGHT.name, language = "en", firstRun = false))
            }
        }
    }

    @OptIn(ExperimentalPermissionsApi::class)
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n", "RestrictedApi", "StateFlowValueCalledInComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        appContext = applicationContext
        instance = this

        Log.wtf(TAG, "Application Started")
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
//        Firebase.appCheck.installAppCheckProviderFactory(
//            PlayIntegrityAppCheckProviderFactory.getInstance(),
//        )
        val room = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "local-db"
        ).allowMainThreadQueries().fallbackToDestructiveMigration().build()

        roomInit(room)

        val debugChannel= NotificationChannel(
            applicationContext.getString(R.string.notif_channel_debug),
            "Debug",
            NotificationManager.IMPORTANCE_HIGH
        )

        val mainChannel= NotificationChannel(
            applicationContext.getString(R.string.notif_channel_main),
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

            Layout(topAppBarText = stringResource(id = R.string.app_name), navController = navController, room) {
                NavHost(navController, startDestination = Route.Home.route) {
                    composable(Route.Home.route) { Home(room, navController) }
                    composable(Route.Search.route) { Search(room, navController) }
                    composable(Route.Favourites.route) { Favourites(room, navController) }
                    composable(Route.About.route) { About() }
                    composable(Route.Settings.route) { Settings(room) }
                    composable(Route.Account.route) { Account(navController = navController, roomDatabase = room) }
                    composable(Route.AccountSettings.route) { AccountSettings(navController, room) }
                    composable(Route.SignIn.route) { SignIn(navController) }
                    composable(Route.AddPet.route) { AddPet(navController, room) }
                    composable(Route.AddToy.route) { AddToy(navController, room) }
                    composable(Route.ChangePassword.route) { ChangePassword(room, navController) }
                    composable(Route.CreateAccount.route) { CreateAccount(navController)}
                    composable(
                        route = Route.EditPet.route + "?id={id}",
                        arguments = listOf(
                            navArgument(
                                name = "id"
                            ) { defaultValue = "" },
                        )
                    ) { backStackEntry ->
                        backStackEntry.arguments?.getString("id")
                            ?.let { EditPet(id = it, room, navController) }
                    }
                    composable(
                        route = Route.EditToy.route + "?id={id}",
                        arguments = listOf(
                            navArgument(
                                name = "id"
                            ) { defaultValue = "" },
                        )
                    ) { backStackEntry ->
                        backStackEntry.arguments?.getString("id")
                            ?.let { EditToy(id = it, room, navController) }
                    }
                    composable(
                        route = Route.MyAdditions.route + "?index={index}",
                        arguments = listOf(
                            navArgument(
                                name = "index"
                            ) { defaultValue = 0 },
                        )
                    ) { backStackEntry ->
                        backStackEntry.arguments?.getInt("index")
                            ?.let { MyAdditions(tabIndex = it, room = room, navController = navController) }
                    }

                    composable(
                        route = Route.SearchResults.route + "?search_options={search_options}",
                        arguments = listOf(
                            navArgument(
                               name = "search_options"
                            ) { defaultValue = "" },
                        )
                    ) { backStackEntry ->
                        backStackEntry.arguments?.getString("search_options")
                            ?.let { SearchResults(json = it, navController = navController) }
                    }
                    composable(
                        route = Route.Login.route + "?email={email}",
                        arguments = listOf(navArgument(
                            name = "email",
                        ) { defaultValue = "" })
                    ) { backStackEntry ->
                        backStackEntry.arguments?.getString("email")
                            ?.let { Login(navController, it, room) }
                    }
                    composable(
                        route = Route.ToyPage.route + "?id={id}",
                        arguments = listOf(navArgument(
                            name = "id",
                        ) { defaultValue = "" })
                    ) { backStackEntry ->
                        backStackEntry.arguments?.getString("id")
                            ?.let { ShowToy(it, room, navController) }
                    }
                    composable(
                        route = Route.PetPage.route + "?id={id}",
                        arguments = listOf(navArgument(
                            name = "id",
                        ) { defaultValue = "" })
                    ) { backStackEntry ->
                        backStackEntry.arguments?.getString("id")
                            ?.let { ShowPet(it, room, navController) }
                    }
                    composable(
                        route = Route.UserPage.route + "?email={email}",
                        arguments = listOf(navArgument(
                            name = "email",
                        ) { defaultValue = "" })
                    ) { backStackEntry ->
                        backStackEntry.arguments?.getString("email")
                            ?.let { UserPage(it, navController) }
                    }
                }
            }
        }
    }
}
