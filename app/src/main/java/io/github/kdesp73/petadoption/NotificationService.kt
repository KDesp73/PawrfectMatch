package io.github.kdesp73.petadoption

import android.R.attr.height
import android.R.attr.width
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import androidx.annotation.DrawableRes
import androidx.core.app.NotificationCompat
import java.io.FileNotFoundException
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

    fun showExpandableImageNotification(
        channel: String,
        title: String,
        content: String,
        imageUri: Uri?,
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
                    .bigPicture(imageUri?.let { getBitmapFromUri(it) })
            )
            .build()
        notificationManager.notify(Random.nextInt(), notification)
    }

    private fun getBitmapFromUri(uri: Uri): Bitmap? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(inputStream)

//            rotateBitmap(bitmap, 90f)
            bitmap
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            null
        }
    }

    private fun rotateBitmap(source: Bitmap, angle: Float): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(angle)
        return Bitmap.createBitmap(
            source,
            0,
            0,
            source.getWidth(),
            source.getHeight(),
            matrix,
            true
        )
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

