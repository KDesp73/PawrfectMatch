package io.github.kdesp73.petadoption.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import io.github.kdesp73.petadoption.enums.CustomAlignment
import io.github.kdesp73.petadoption.enums.Routes
import io.github.kdesp73.petadoption.ui.utils.VerticalScaffold
import kotlinx.coroutines.launch

class DrawerIcons{
    object Settings{
        val icon: ImageVector = Icons.Filled.Settings
        const val description: String = "Settings Drawer Button"
    }

    object About{
        val icon: ImageVector = Icons.Filled.Info
        const val description: String = "About Drawer Button"
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
private fun DrawerContent(
    navController: NavHostController?,
    width: Dp = 250.dp,
    drawerState: DrawerState
){
    val scope = rememberCoroutineScope()
    val iconColor = MaterialTheme.colorScheme.primary
    val iconSize = 45.dp

    ModalDrawerSheet (
        modifier = Modifier
            .width(width)
    ){
        VerticalScaffold(
            bottomAlignment = CustomAlignment.END,
            top = { Text(text = "TODO")},
            center = { Text(text = "TODO")},
            bottom = {
                Row (
                    horizontalArrangement = Arrangement.spacedBy(15.dp),
                    modifier = Modifier.padding(10.dp)
                ){
                    CircularIconButton(icon = DrawerIcons.About.icon, description = DrawerIcons.About.description, bg = iconColor, size = iconSize) {
                        navController?.navigate(Routes.ABOUT.label) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                        scope.launch {
                            drawerState.close()
                        }
                    }
                    CircularIconButton(icon = DrawerIcons.Settings.icon, description = DrawerIcons.Settings.description, bg = iconColor, size = iconSize) {
                        navController?.navigate(Routes.SETTINGS.label) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                        scope.launch {
                            drawerState.close()
                        }
                    }
                }
            }
        )
    }

}

@Composable
fun Drawer(
    modifier: Modifier = Modifier,
    drawerState: DrawerState,
    navController: NavHostController,
    content: @Composable () -> Unit
) {
    val configuration = LocalConfiguration.current

    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

    ModalNavigationDrawer(
        modifier = modifier,
        drawerState = drawerState,
        drawerContent = { DrawerContent(width = screenWidth - 40.dp, navController = navController, drawerState = drawerState) }
    ) {
        content()
    }
}

@Preview
@Composable
fun DrawerContentPreview(){
    DrawerContent(null, drawerState = DrawerState(initialValue = DrawerValue.Open))
}