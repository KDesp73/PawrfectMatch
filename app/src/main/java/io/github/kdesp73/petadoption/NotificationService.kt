package io.github.kdesp73.petadoption
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.graphics.BitmapFactory
import androidx.annotation.DrawableRes
import androidx.core.app.NotificationCompat
import kotlin.random.Random

class NotificationService(private val context: Context) {

    private val notificationManager =
        context.getSystemService(NotificationManager::class.java)

    fun showBasicNotification(channel: String, title: String, content: String, importance: Int) {
        val notification = NotificationCompat.Builder(context, channel)
            .setContentTitle(title)
            .setContentText(content)
            .setPriority(importance)
            .setSmallIcon(R.drawable.bell)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(Random.nextInt(), notification)
    }

    fun showExpandableImageNotification(
        channel: String,
        title: String,
        content: String,
        @DrawableRes resId: Int,
        importance: Int
    ) {
        val notification = NotificationCompat.Builder(context, channel)
            .setContentTitle(title)
            .setContentText(content)
            .setPriority(importance)
            .setSmallIcon(R.drawable.bell)
            .setAutoCancel(true)
            .setStyle(
                NotificationCompat.BigPictureStyle()
                    .bigPicture(context.bitmapFromResource(resId))
            )
            .build()
        notificationManager.notify(Random.nextInt(), notification)
    }

    fun showExpandableTextNotification(
        channel: String,
        title: String,
        content: String,
        expandedText: String,
        importance: Int
    ) {
        val notification = NotificationCompat.Builder(context, channel)
            .setContentTitle(title)
            .setContentText(content)
            .setPriority(importance)
            .setSmallIcon(R.drawable.bell)
            .setAutoCancel(true)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(expandedText)
            )
            .build()
        notificationManager.notify(Random.nextInt(), notification)
    }

    fun showActionNotification(
        channel: String,
        title: String,
        content: String,
        actionText: String,
        actionIntent: PendingIntent,
        importance: Int
    ) {
        val notification = NotificationCompat.Builder(context, channel)
            .setContentTitle(title)
            .setContentText(content)
            .setPriority(importance)
            .setSmallIcon(R.drawable.bell)
            .setAutoCancel(true)
            .addAction(0, actionText, actionIntent)
            .build()
        notificationManager.notify(Random.nextInt(), notification)
    }

    private fun Context.bitmapFromResource(@DrawableRes resId: Int) =
        BitmapFactory.decodeResource(resources, resId)
}

