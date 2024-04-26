package io.github.kdesp73.petadoption.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.NotificationManagerCompat

@Composable
fun RequestNotificationPermissionDialog(
    onPermissionGranted: () -> Unit,
    onPermissionDenied: () -> Unit
) {
    val context = LocalContext.current
    val notificationManager = NotificationManagerCompat.from(context)
    val notificationPermissionState = remember { mutableStateOf(notificationManager.areNotificationsEnabled()) }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Enable Notifications")
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "This app requires notification permission to function properly.",
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            if (!notificationPermissionState.value) {
                notificationManager.cancelAll() // Cancel any previously shown notifications
                notificationPermissionState.value = true
                onPermissionGranted()
            }
        }) {
            Text(text = "Enable Notifications")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            onPermissionDenied()
        }) {
            Text(text = "Deny")
        }
    }
}
