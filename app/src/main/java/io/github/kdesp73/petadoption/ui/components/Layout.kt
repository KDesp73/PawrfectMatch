package io.github.kdesp73.petadoption.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.navigation.NavHostController
import io.github.kdesp73.petadoption.Route
import io.github.kdesp73.petadoption.enums.ThemeName
import io.github.kdesp73.petadoption.isLandscape
import io.github.kdesp73.petadoption.navigateTo
import io.github.kdesp73.petadoption.room.AppDatabase
import io.github.kdesp73.petadoption.ui.theme.AppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

private const val TAG = "Layout"

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Layout(topAppBarText: String, navController: NavHostController, room: AppDatabase, content: @Composable () -> Unit) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    var themeName by remember {
        mutableStateOf(ThemeName.AUTO.value)
    }

    LaunchedEffect(key1 = themeName) {
        themeName = room.settingsDao().getTheme()
    }

    val darkMode: Boolean = when(themeName){
        ThemeName.DARK.value -> true
        ThemeName.LIGHT.value -> false
        else -> isSystemInDarkTheme()
    }


    AppTheme (
        darkColorScheme = darkMode,
        dynamicColor = themeName == ThemeName.DYNAMIC.value
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
                        accountAction = { navigateTo(Route.Account.route, navController) }
                    )
                },
                content =  { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        content()
                    }
                },
                bottomBar = {
                    if(!isLandscape(LocalConfiguration.current)) {
                        BottomBar(navController)
                    }
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
