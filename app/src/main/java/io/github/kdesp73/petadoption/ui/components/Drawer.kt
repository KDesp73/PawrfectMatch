package io.github.kdesp73.petadoption.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import io.github.kdesp73.petadoption.Route
import io.github.kdesp73.petadoption.enums.CustomAlignment
import io.github.kdesp73.petadoption.isLandscape
import io.github.kdesp73.petadoption.navigateTo
import io.github.kdesp73.petadoption.routes.About
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
    val iconColor = MaterialTheme.colorScheme.tertiaryContainer
    val iconSize = 45.dp

    ModalDrawerSheet (
        modifier = Modifier
            .width(width)
    ){
        VerticalScaffold(
            bottomAlignment = CustomAlignment.END,
            top = {
                  if(isLandscape(LocalConfiguration.current)) {
                      Column (
                          modifier = Modifier.padding(2.dp),
                          verticalArrangement = Arrangement.spacedBy(5.dp),
                          horizontalAlignment = Alignment.CenterHorizontally
                      ){
                          @Composable
                          fun NavButton(route: Route){
                              ElevatedButton(
                                  modifier = Modifier
                                      .fillMaxWidth()
                                      .padding(vertical = 2.dp, horizontal = 6.dp),
                                  onClick = {
                                      navController?.let { navigateTo(route.route, it) }
                                      scope.launch {
                                          drawerState.close()
                                      }
                                  }
                              ) {
                                  Text(text = stringResource(id = route.resId))
                              }

                          }
                          NavButton(route = Route.Home)
                          NavButton(route = Route.Search)
                          NavButton(route = Route.Favourites)
                      }
                  } else {
                      About()
                  }
            },
            bottom = {
                Row (
                    horizontalArrangement = Arrangement.spacedBy(15.dp),
                    modifier = Modifier.padding(10.dp)
                ){
                    if(isLandscape(LocalConfiguration.current)){
                        CircularIconButton(icon = DrawerIcons.About.icon, description = DrawerIcons.About.description, bg = iconColor, size = iconSize) {
                            navController?.navigate(Route.About.route) {
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

                    CircularIconButton(icon = DrawerIcons.Settings.icon, description = DrawerIcons.Settings.description, bg = iconColor, size = iconSize) {
                        navController?.navigate(Route.Settings.route) {
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

    val landscape = isLandscape(configuration)

    ModalNavigationDrawer(
        modifier = modifier,
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                width = if(landscape) screenWidth * 0.4 else screenWidth * 0.8,
                navController = navController,
                drawerState = drawerState
            )
        }
    ) {
        content()
    }
}

private operator fun Dp.times(d: Double): Dp {
    return (this.value * d).dp
}

@Preview
@Composable
fun DrawerContentPreview(){
    DrawerContent(null, drawerState = DrawerState(initialValue = DrawerValue.Open))
}
