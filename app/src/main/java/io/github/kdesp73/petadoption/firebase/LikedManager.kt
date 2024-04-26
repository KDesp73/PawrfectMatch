package io.github.kdesp73.petadoption.firebase

import android.util.Log
import androidx.room.util.query
import com.google.firebase.firestore.AggregateSource
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.asDeferred
import kotlinx.coroutines.tasks.await

class LikedManager {
    private val TAG = "LikedManager"
    private val db = FirebaseFirestore.getInstance()

    companion object {
        val pets = "LikedPets"
        val toys = "LikedToys"
    }

    suspend fun getLikedIds(collection: String, email: String) : List<String>{
        return try {
            val querySnapshot = db.collection(collection)
                .whereEqualTo("userEmail", email)
                .get()
                .await()

            val list = querySnapshot.documents.map { it["id"].toString() }
            return list
        } catch (_: Exception){
            emptyList<String>()
        }
    }

    suspend fun countLikes(collection: String, id: String) : Int {
        return try {
            val task = db.collection(collection)
                .whereEqualTo("id", id)
                .count()
                .get(AggregateSource.SERVER)
                .await()

            task.count.toInt()
        } catch (_: Exception){
            -1
        }
    }

    fun removeAllLikes(collection: String, id: String, onComplete: (Boolean) -> Unit) {
        getLikedDocumentIds(collection, id){ list ->
            for (liked in list){
                if (liked != null) {
                    db.collection(collection)
                        .document(liked)
                        .delete()
                        .addOnSuccessListener { onComplete(true) }
                        .addOnFailureListener { onComplete(false) }
                }
            }
        }
    }

    suspend fun isLiked(collection: String, email: String, id: String) : Boolean? {
        return try {
            val querySnapshot = db.collection(collection)
                .whereEqualTo("userEmail", email)
                .whereEqualTo("id", id)
                .get()
                .await()

            return querySnapshot.documents.isNotEmpty()
        } catch (_: Exception){
            null
        }
    }

    private fun isLiked(collection: String, email: String, id: String, onComplete: (Boolean?) -> Unit){
        try {
            db.collection(collection)
                .whereEqualTo("userEmail", email)
                .whereEqualTo("id", id)
                .get()
                .addOnSuccessListener { query ->
                    onComplete(query.documents.isNotEmpty())
                }
                .addOnFailureListener{
                    onComplete(null)
                }


        } catch (_: Exception){
            onComplete(null)
        }
    }

    fun like(collection: String, email: String, id: String, onComplete : (Boolean) -> Unit){
        isLiked(collection, email, id){ isLiked ->
            Log.d(TAG, "isLiked: $isLiked")
            if(isLiked != null && !isLiked){
                db.collection(collection)
                    .add(hashMapOf(
                        "userEmail" to email,
                        "id" to id
                    ))
                    .addOnCompleteListener { snapshot ->
                        onComplete(snapshot.isComplete)
                    }
                    .addOnFailureListener { exception ->
                        onComplete(false)
                    }
            }
        }
    }

    fun unlike(collection: String, email: String, id: String, onComplete: (Boolean) -> Unit) {
        getLikedDocumentId(collection, email, id){ ids ->
            for (_id in ids){
                Log.d(TAG, "DocID: $_id")
                if(_id != null){
                    db.collection(collection)
                        .document(_id)
                        .delete()
                        .addOnSuccessListener { snapshot ->
                            onComplete(true)
                        }
                        .addOnFailureListener {
                            onComplete(false)
                        }
                }
            }
        }
    }

    private fun getLikedDocumentIds(collection: String, id: String, onComplete: (List<String?>) -> Unit) {
        db.collection(collection)
            .whereEqualTo("id", id)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val documentIds = task.result?.documents?.map { it.id } ?: emptyList()
                    onComplete(documentIds)
                } else {
                    val exception = task.exception
                    println("Error getting liked document IDs: $exception")
                    onComplete(emptyList())
                }
            }
    }
    private fun getLikedDocumentId(collection: String, email: String, id: String, onComplete: (List<String?>) -> Unit) {
        db.collection(collection)
            .whereEqualTo("id", id)
            .whereEqualTo("userEmail", email)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val documentIds = task.result?.documents?.map { it.id } ?: emptyList()
                    onComplete(documentIds)
                } else {
                    val exception = task.exception
                    println("Error getting liked document IDs: $exception")
                    onComplete(emptyList())
                }
            }
    }
}