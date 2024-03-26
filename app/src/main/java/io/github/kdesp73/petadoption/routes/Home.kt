package io.github.kdesp73.petadoption.routes

import android.app.NotificationManager
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import io.github.kdesp73.petadoption.NotificationService
import io.github.kdesp73.petadoption.R

@Composable
fun Home() {
    val notificationService = NotificationService(context = LocalContext.current)

    Column (
        modifier = Modifier
            .fillMaxSize()
    ){
        Text(text = "Welcome Home")
        Button(onClick = {
            notificationService.showExpandableNotification(
                R.string.DEBUG.toString(),
                "Test",
                "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA",
                resId = R.drawable.profile_pic_placeholder,
                NotificationManager.IMPORTANCE_HIGH
            )
        }) {
            Text(text = "Click me")

        }
    }
}

@Preview
@Composable
fun HomePreview(){
    Home()
}
