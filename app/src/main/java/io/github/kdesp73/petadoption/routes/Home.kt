package io.github.kdesp73.petadoption.routes

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import io.github.kdesp73.petadoption.NotificationService
import io.github.kdesp73.petadoption.R
import io.github.kdesp73.petadoption.Route
import io.github.kdesp73.petadoption.isLandscape
import io.github.kdesp73.petadoption.isNotLoggedIn
import io.github.kdesp73.petadoption.navigateTo
import io.github.kdesp73.petadoption.room.AppDatabase
import io.github.kdesp73.petadoption.ui.components.Center
import io.github.kdesp73.petadoption.ui.components.MyCard
import io.github.kdesp73.petadoption.ui.components.PleaseLogin

@Composable
private fun BigIconButton(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    contentDescription: String,
    action: () -> Unit = {}
){
    val width = if(isLandscape(LocalConfiguration.current)){
        180.dp
    } else {
        LocalConfiguration.current.screenWidthDp.dp / 2 - 30.dp
    }
    MyCard (
        modifier = modifier
            .clickable {
                action()
            }
            .padding(horizontal = 5.dp)
            .width(width)
            .height(width)
    ){
        Center(modifier = Modifier.fillMaxSize()) {
            Text(text = contentDescription, fontSize = 4.em, softWrap = true)
            Spacer(modifier = Modifier.height(15.dp))
            Icon(
                modifier = Modifier.size(50.dp),
                imageVector = imageVector,
                contentDescription = contentDescription
            )
        }
    }
}
@Composable
private fun BigIconButton(
    modifier: Modifier = Modifier,
    @DrawableRes resId: Int,
    contentDescription: String,
    action: () -> Unit = {}
){
    BigIconButton(
        modifier = modifier,
        imageVector = ImageVector.vectorResource(resId),
        contentDescription = contentDescription
    ) {
        action()
    }
}

@SuppressLint("PermissionLaunchedDuringComposition")
@Composable
fun Home(room: AppDatabase, navController: NavController) {
    val context = LocalContext.current
    val notificationService = NotificationService(context = context)

    val scrollState = rememberScrollState()

    Column (
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Column (
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.welcome_to), fontSize = 5.em)
            Text(text = stringResource(id = R.string.app_name), fontSize = 10.em, color = MaterialTheme.colorScheme.primary)
        }
        Spacer(modifier = Modifier.height(10.dp))
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
        ){
            val email: String? = room.userDao().getEmail()
            if(isNotLoggedIn(room)){
                PleaseLogin(msg = null, email = email, navController = navController)
            } else{
                if (!isLandscape(LocalConfiguration.current)){
                    Row (
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        BigIconButton(
                            resId = R.drawable.paw_solid,
                            contentDescription = stringResource(
                                id = R.string.route_my_pets
                            )
                        ) {
                            navigateTo(Route.MyAdditions.route + "?index=0", navController = navController)
                        }
                        BigIconButton(
                            imageVector = ImageVector.vectorResource(R.drawable.robot_solid),
                            contentDescription = stringResource(
                                R.string.route_my_toys
                            )
                        ){
                            navigateTo(Route.MyAdditions.route + "?index=1", navController = navController)
                        }
                    }
                    Row (
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        BigIconButton(
                            imageVector = Icons.Filled.AccountCircle,
                            contentDescription = stringResource(
                                R.string.my_account
                            )
                        ){
                            navigateTo(
                                Route.UserPage.route + "?email=${room.userDao().getEmail()}",
                                navController = navController,
                                restore = false
                            )
                        }
//                    BigIconButton(resId = R.drawable.paw_solid, contentDescription = "My Animals")
                    }
                } else {
                    Row (
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        BigIconButton(
                            resId = R.drawable.paw_solid,
                            contentDescription = stringResource(
                                id = R.string.route_my_pets
                            )
                        ) {
                            navigateTo(Route.MyAdditions.route + "?index=0", navController = navController)
                        }
                        BigIconButton(
                            imageVector = ImageVector.vectorResource(R.drawable.robot_solid),
                            contentDescription = stringResource(
                                R.string.route_my_toys
                            )
                        ){
                            navigateTo(Route.MyAdditions.route + "?index=1", navController = navController)
                        }
                        BigIconButton(
                            imageVector = Icons.Filled.AccountCircle,
                            contentDescription = stringResource(
                                R.string.my_account
                            )
                        ){
                            navigateTo(
                                Route.UserPage.route + "?email=${room.userDao().getEmail()}",
                                navController = navController,
                                restore = false
                            )
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
    }
}