package io.github.kdesp73.petadoption

import android.app.NotificationManager
import android.content.Context
import android.graphics.BitmapFactory
import androidx.annotation.DrawableRes
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.Builder
import kotlin.random.Random


class NotificationService (
    private val context: Context
){
    private val notificationManager=context.getSystemService(NotificationManager::class.java)

    fun showBasicNotification(channel: String, title: String, content: String, importance: Int){
        val notification= Builder(context, channel)
            .setContentTitle(title)
            .setContentText(content)
            .setPriority(importance)
            .setSmallIcon(R.drawable.bell)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(
            Random.nextInt(),
            notification
        )
    }

    fun showExpandableNotification(channel: String, title: String, content: String, @DrawableRes resId: Int, importance: Int){
        val notification= Builder(context, channel)
            .setContentTitle(title)
            .setContentText(content)
            .setPriority(importance)
            .setSmallIcon(R.drawable.bell)
            .setAutoCancel(true)
            .setStyle(
                NotificationCompat
                    .BigPictureStyle()
                    .bigPicture(
                        context.bitmapFromResource(resId)
                    )
            )
            .build()
        notificationManager.notify(Random.nextInt(),notification)
    }

    fun showExpandableNotification(channel: String, title: String, content: String, expandedContent: String, importance: Int){
        val notification= Builder(context, channel)
            .setContentTitle(title)
            .setContentText(content)
            .setPriority(importance)
            .setSmallIcon(R.drawable.bell)
            .setAutoCancel(true)
            .build()
        notificationManager.notify(Random.nextInt(),notification)
    }

    private fun Context.bitmapFromResource(
        @DrawableRes resId:Int
    )= BitmapFactory.decodeResource(
        resources,
        resId
    )
}