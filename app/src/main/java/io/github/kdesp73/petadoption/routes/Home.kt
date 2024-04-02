package io.github.kdesp73.petadoption.routes

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
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
            notificationService.showExpandableTextNotification(
                channel = R.string.MAIN.toString(),
                title = "Expandable Text Notification Test",
                content = "Hello",
                expandedText = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA",
                NotificationManager.IMPORTANCE_HIGH
            )
        }) {
            Text(text = "Click me")

        }
        /*
        Button(onClick = {
            notificationService.showActionNotification(
                channel = R.string.MAIN.toString(),
                title = "Expandable Text Notification Test",
                content = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA",
                actionText = "Press",
                actionIntent = ,
                importance = NotificationManager.IMPORTANCE_HIGH
            )
        }) {
            Text(text = "Click me")

        }
         */
    }
}

@Preview
@Composable
fun HomePreview(){
    Home()
}
