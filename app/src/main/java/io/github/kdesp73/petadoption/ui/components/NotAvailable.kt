package io.github.kdesp73.petadoption.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import io.github.kdesp73.petadoption.R
import io.github.kdesp73.petadoption.Route
import io.github.kdesp73.petadoption.navigateTo

@Composable
fun PleaseLogin(msg: String? = stringResource(R.string.please_login_first_to_use_this_feature), email: String?, navController: NavController){
    Center(modifier = Modifier.fillMaxSize()) {
        if(msg != null){
            Text(text = msg)
        }
        IconButton(text = "Login", imageVector = Icons.Filled.AccountCircle) {
            navigateTo(Route.Login.route, navController)
        }
    }
}

@Composable
fun NoWifiConnection(msg: String = stringResource(R.string.connect_to_a_network_to_use_this_feature)){
    Center(modifier = Modifier.fillMaxSize()) {
        Row (
            modifier = Modifier.padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(15.dp)
        ){
            Icon(imageVector = Icons.Filled.Warning, contentDescription = msg)
            Text(text = msg)
        }
    }
}