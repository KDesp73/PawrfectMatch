package io.github.kdesp73.petadoption.firestore

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.firebase.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.storage

class ImageManager {
    private val TAG = "ImageManager"
    private val storage = Firebase.storage("gs://petadoption-f6e9a.appspot.com").reference

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

    fun loadImageFromUrl(context: Context, url: String, imageView: ImageView) {
        Glide.with(context)
            .load(url)
            .into(imageView)
    }
}