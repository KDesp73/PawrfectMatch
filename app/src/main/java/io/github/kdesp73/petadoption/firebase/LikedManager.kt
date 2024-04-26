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

    suspend fun getLikedPetIds(email: String) : List<String>{
        return try {
            val querySnapshot = db.collection("Liked")
                .whereEqualTo("userEmail", email)
                .get()
                .await()

            val list = querySnapshot.documents.map { it["petId"].toString() }
            return list
        } catch (_: Exception){
            emptyList<String>()
        }
    }

    suspend fun countLikes(petId: String) : Int {
        return try {
            val task = db.collection("Liked")
                .whereEqualTo("petId", petId)
                .count()
                .get(AggregateSource.SERVER)
                .await()

            task.count.toInt()
        } catch (_: Exception){
            -1
        }
    }

    fun removeAllLikes(petId: String, onComplete: (Boolean) -> Unit) {
        getLikedDocumentIds(petId){ list ->
            for (liked in list){
                if (liked != null) {
                    db.collection("Liked")
                        .document(liked)
                        .delete()
                        .addOnSuccessListener { onComplete(true) }
                        .addOnFailureListener { onComplete(false) }
                }

            }

        }
    }

    suspend fun isLiked(email: String, petId: String) : Boolean? {
        return try {
            val querySnapshot = db.collection("Liked")
                .whereEqualTo("userEmail", email)
                .whereEqualTo("petId", petId)
                .get()
                .await()

            return querySnapshot.documents.isNotEmpty()
        } catch (_: Exception){
            null
        }
    }

    private fun isLiked(email: String, petId: String, onComplete: (Boolean?) -> Unit){
        try {
            db.collection("Liked")
                .whereEqualTo("userEmail", email)
                .whereEqualTo("petId", petId)
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

    fun likePet(email: String, petId: String, onComplete : (Boolean) -> Unit){
        isLiked(email, petId){ isLiked ->
            Log.d(TAG, "isLiked: $isLiked")
            if(isLiked != null && !isLiked){
                db.collection("Liked")
                    .add(hashMapOf(
                        "userEmail" to email,
                        "petId" to petId
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

    fun unlikePet(email: String, petId: String, onComplete: (Boolean) -> Unit) {
        getLikedDocumentId(email, petId){ ids ->
            for (id in ids){
                Log.d(TAG, "DocID: $id")
                if(id != null){
                    db.collection("Liked")
                        .document(id)
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

    private fun getLikedDocumentIds(petId: String, onComplete: (List<String?>) -> Unit) {
        db.collection("Liked")
            .whereEqualTo("petId", petId)
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
    private fun getLikedDocumentId(email: String, petId: String, onComplete: (List<String?>) -> Unit) {
        db.collection("Liked")
            .whereEqualTo("petId", petId)
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