package io.github.kdesp73.petadoption

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import io.github.kdesp73.petadoption.ui.components.AppBar
import io.github.kdesp73.petadoption.ui.components.Drawer
import io.github.kdesp73.petadoption.ui.theme.PetAdoptionTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun Layout(topAppBarText: String, navController: NavHostController, content: @Composable () -> Unit) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    PetAdoptionTheme {
        Drawer(
            drawerState = drawerState,
            navController = navController
        ){
            Column {
                AppBar(
                    topAppBarText = topAppBarText,
                    menuAction = { menuAction(scope, drawerState) },
                    accountAction = {
                        navController.navigate("Account"){
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true

                        }
                    }
                )
                content()
            }
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
