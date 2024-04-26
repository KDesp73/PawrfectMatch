package io.github.kdesp73.petadoption.routes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import io.github.kdesp73.petadoption.Route
import io.github.kdesp73.petadoption.room.AppDatabase

@Composable
fun MyAdditions(tabIndex: Int = 0, room: AppDatabase, navController: NavController) {
    var index by remember { mutableIntStateOf(tabIndex) }

    val tabs = listOf(stringResource(id = Route.MyPets.resId), stringResource(id = Route.MyToys.resId))

    Column(modifier = Modifier.fillMaxWidth()) {
        TabRow(selectedTabIndex = index) {
            tabs.forEachIndexed { i, title ->
                Tab(text = { Text(title) },
                    selected = index == i,
                    onClick = { index = i }
                )
            }
        }
        when (index) {
            0 -> MyPets(room, navController)
            1 -> MyToys(room, navController)
            else -> Text("No index")
        }
    }
}