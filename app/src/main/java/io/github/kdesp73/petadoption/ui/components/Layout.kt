package io.github.kdesp73.petadoption.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import io.github.kdesp73.petadoption.ThemeName
import io.github.kdesp73.petadoption.enums.Routes
import io.github.kdesp73.petadoption.ui.theme.PetAdoptionTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Layout(topAppBarText: String, navController: NavHostController, content: @Composable () -> Unit) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    PetAdoptionTheme (
        theme = ThemeName.DARK
    ){
        Drawer(
            drawerState = drawerState,
            navController = navController
        ){
            Scaffold (
                topBar = {
                    AppBar(
                        topAppBarText = topAppBarText,
                        menuAction = { menuAction(scope, drawerState) },
                        accountAction = {
                            navController.navigate(Routes.ACCOUNT.label){
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true

                            }
                        }
                    )
                },
                content =  { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        content()
                    }
                },
                bottomBar = {
                    BottomBar(navController)
                }
            )
        }
    }
}

fun menuAction(scope: CoroutineScope, drawerState: DrawerState){
    scope.launch {
        drawerState.apply {
            if (isClosed) open() else close()
        }
    }
}
