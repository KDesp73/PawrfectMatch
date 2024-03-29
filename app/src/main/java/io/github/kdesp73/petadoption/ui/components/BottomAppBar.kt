package io.github.kdesp73.petadoption.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import io.github.kdesp73.petadoption.enums.Routes

@Composable
fun BottomBar(navController: NavController) {
    val iconModifier: Modifier = Modifier.size(30.dp)

    BottomAppBar(
        actions = {
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ){
                IconButton(onClick = {
                    navController.navigate(Routes.HOME.tag){
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }) {
                    Icon(
                        modifier = iconModifier,
                        imageVector = Icons.Filled.Home,
                        contentDescription = "Home"
                    )
                }
                IconButton(onClick = {
                    navController.navigate(Routes.SEARCH.tag){
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }) {
                    Icon(
                        modifier = iconModifier,
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search",
                    )
                }
                IconButton(onClick = {
                    navController.navigate(Routes.FAVOURITES.tag){
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }) {
                    Icon(
                        modifier = iconModifier,
                        imageVector = Icons.Filled.Favorite,
                        contentDescription = "Favourites",
                    )
                }
            }
        }
    )
}