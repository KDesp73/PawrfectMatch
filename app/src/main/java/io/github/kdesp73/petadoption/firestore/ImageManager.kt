package io.github.kdesp73.petadoption.firestore

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Paint.Style
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.DrawScope.Companion.DefaultBlendMode
import androidx.compose.ui.graphics.drawscope.DrawScope.Companion.DefaultFilterQuality
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import coil.compose.rememberAsyncImagePainter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.File

class ImageManager {
    private val TAG = "ImageManager"
    private val storage = Firebase.storage("gs://petadoption-f6e9a.appspot.com").reference

    companion object {
        val users = "users/"
        val pets = "pets/"
    }


    private fun uploadImage(uri: Uri, fileName: String, path: String, onComplete: (String?) -> Unit) {
        val storageRef = storage.child("$path$fileName.jpg")
        storageRef.putFile(uri)
            .addOnSuccessListener {
                storageRef.downloadUrl.addOnSuccessListener { downloadUri ->
                    val downloadUrl = downloadUri.toString()
                    onComplete(downloadUrl)
                }
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, "Error uploading image: ${exception.message}")
                onComplete(null)
            }
    }

    fun uploadProfileImage(uri: Uri, filename: String, onComplete: (String?) -> Unit){
        uploadImage(uri, filename, "users/"){ url ->
            onComplete(url)
        }
    }

    fun uploadPetImage(uri: Uri, filename: String, onComplete: (String?) -> Unit){
        uploadImage(uri, filename, "pets/"){ url ->
            onComplete(url)
        }
    }

    fun deleteImage(path: String, onComplete: (Boolean) -> Unit){
        val desertRef = storage.child(path)

        desertRef.delete().addOnSuccessListener {
            onComplete(true)
        }.addOnFailureListener {
            onComplete(false)
        }
    }

    suspend fun getImageUrl(path: String): Uri? {
        return try {
            storage.child(path).downloadUrl.await()
        } catch (_: Exception){
            null
        }
    }
}
