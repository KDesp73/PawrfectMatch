package io.github.kdesp73.petadoption.routes

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import io.github.kdesp73.petadoption.NotificationService
import io.github.kdesp73.petadoption.R
import io.github.kdesp73.petadoption.Route
import io.github.kdesp73.petadoption.navigateTo
import io.github.kdesp73.petadoption.ui.components.Center
import io.github.kdesp73.petadoption.ui.components.MyCard
import io.github.kdesp73.petadoption.ui.components.VerticalScaffold

@Composable
private fun BigIconButton(
    modifier: Modifier = Modifier,
    @DrawableRes resId: Int,
    contentDescription: String,
    action: () -> Unit = {}
){
    val halfWidth = LocalConfiguration.current.screenWidthDp.dp / 2 - 20.dp
    MyCard (
        modifier = modifier
            .clickable {
                action()
            }
            .padding(horizontal = 5.dp)
            .width(halfWidth)
            .height(halfWidth)
    ){
        Center(modifier = Modifier.fillMaxSize()) {
            Icon(
                modifier = Modifier.size(halfWidth - 50.dp),
                painter = painterResource(id = resId),
                contentDescription = contentDescription
            )
        }
    }
}

@SuppressLint("PermissionLaunchedDuringComposition")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun Home(navController: NavController) {
    val context = LocalContext.current
    val notificationService = NotificationService(context = context)


    val scrollState = rememberScrollState()
    VerticalScaffold(
        top = {
            Column (
                modifier = Modifier.height(300.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(text = "Welcome to ${stringResource(id = R.string.app_name)}, ", fontSize = 6.em)
                Text(text = "where every pet finds a loving home. ")
                Text(text = "Start your adoption journey today!")
            }
        },
        bottom = {
            Column (
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .fillMaxSize()
                    .padding(8.dp)
            ){
                Row {
                    BigIconButton(resId = R.drawable.paw_solid, contentDescription = "My Animals") {
                        navigateTo(Route.MyPets.route, navController = navController)
                    }
                    BigIconButton(resId = R.drawable.paw_solid, contentDescription = "My Animals")
                }
                Row {
                    BigIconButton(resId = R.drawable.paw_solid, contentDescription = "My Animals")
                    BigIconButton(resId = R.drawable.paw_solid, contentDescription = "My Animals")
                }
            }
        }
    )

    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
        val notificationPermission = rememberPermissionState(
            permission = Manifest.permission.POST_NOTIFICATIONS
        )
        if (!notificationPermission.status.isGranted) {
            notificationPermission.launchPermissionRequest()
        }
    }
}