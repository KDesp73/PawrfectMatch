package io.github.kdesp73.petadoption.firebase

import android.net.Uri
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import kotlinx.coroutines.tasks.await

class ImageManager {
    private val TAG = "ImageManager"
    private val storage = Firebase.storage("gs://petadoption-f6e9a.appspot.com").reference

    companion object {
        val users = "users/"
        val pets = "pets/"
        val toys = "toys/"
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
    fun uploadToyImage(uri: Uri, filename: String, onComplete: (String?) -> Unit){
        uploadImage(uri, filename, ImageManager.toys){ url ->
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
