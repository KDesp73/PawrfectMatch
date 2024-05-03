package io.github.kdesp73.petadoption.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import io.github.kdesp73.petadoption.Route
import io.github.kdesp73.petadoption.navigateTo

@Composable
fun BottomBar(navController: NavController) {
    val iconModifier: Modifier = Modifier.size(30.dp)

    BottomAppBar(
        actions = {
            Row (
                modifier = Modifier.fillMaxWidth().height(40.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ){
                IconButton(onClick = {
                    navigateTo(
                        route = Route.Home.route,
                        navController = navController,
                        popUpToStartDestination = true,
                        saveStateOnPopUpTo = false,
                        restore = false
                    )
                }) {
                    Icon(
                        modifier = iconModifier,
                        imageVector = Icons.Filled.Home,
                        contentDescription = "Home"
                    )
                }
                IconButton(onClick = {
                    navigateTo(
                        route = Route.Search.route,
                        navController = navController,
                        popUpToStartDestination = true,
                        saveStateOnPopUpTo = false,
                        restore = false
                    )
                }) {
                    Icon(
                        modifier = iconModifier,
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search",
                    )
                }
                IconButton(onClick = {
                    navigateTo(
                        route = Route.Favourites.route,
                        navController = navController,
                        popUpToStartDestination = true,
                        saveStateOnPopUpTo = false,
                        restore = false
                    )
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