package io.github.kdesp73.petadoption.firebase

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import io.github.kdesp73.petadoption.room.AppDatabase
import io.github.kdesp73.petadoption.room.LocalPet
import io.github.kdesp73.petadoption.room.LocalToy
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.tasks.await

class ToyManager {
    private val TAG = "ToyManager"
    private val db = FirebaseFirestore.getInstance()
    private val collection = "Toys"


    private fun checkToyExists(email: String, id: String, onComplete: (Boolean) -> Unit){
        db.collection(collection)
            .whereEqualTo("ownerEmail", email)
            .whereEqualTo("id", id)
            .get()
            .addOnSuccessListener { snapshot ->
                onComplete(snapshot.documents.isNotEmpty())
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, exception.message.toString())
                onComplete(false)
            }
    }
    private fun checkToyExists(id: String, onComplete: (Boolean) -> Unit){
        db.collection(collection)
            .whereEqualTo("id", id)
            .get()
            .addOnSuccessListener { snapshot ->
                onComplete(snapshot.documents.isNotEmpty())
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, exception.message.toString())
                onComplete(false)
            }
    }
    private fun checkToyExists(toy: FirestoreToy, onComplete: (Boolean) -> Unit){
        db.collection(collection)
            .whereEqualTo("ownerEmail", toy.ownerEmail)
            .whereEqualTo("id", toy.id)
            .get()
            .addOnSuccessListener { snapshot ->
                onComplete(snapshot.documents.isNotEmpty())
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, exception.message.toString())
                onComplete(false)
            }
    }

    fun addToy(toy: FirestoreToy, onComplete: (Boolean) -> Unit){
        checkToyExists(toy){ exists ->
            if(exists){
                Log.d(TAG, "${toy.toString()} already exists")
                onComplete(false)
            } else {
                db.collection(collection)
                    .add(toy)
                    .addOnSuccessListener { snapshot ->
                        onComplete(true)
                    }
                    .addOnFailureListener { exception ->
                        onComplete(false)
                    }
            }
        }
    }

    suspend fun getToys(): List<FirestoreToy>{
        val list = mutableListOf<FirestoreToy>()

        try {
            val querySnapshot = db.collection(collection)
                .get()
                .await()

            for(doc in querySnapshot.documents) {
                list.add(FirestoreToy(doc))
            }
        } catch (_: Exception) {
        }

        return list

    }

    fun getToysByEmail(email: String, onComplete: (List<FirestoreToy>) -> Unit) {
        val list = mutableListOf<FirestoreToy>()

        try {
            val querySnapshot = db.collection(collection)
                .whereEqualTo("ownerEmail", email)
                .get()
                .addOnSuccessListener { querySnapshot ->
                    for(doc in querySnapshot.documents) {
                        list.add(FirestoreToy(doc))
                    }
                    onComplete(list)
                }
                .addOnFailureListener{
                    onComplete(list)
                }

        } catch (_: Exception) {
        }
    }
    suspend fun getToyByEmail(email: String) : List<FirestoreToy> {
        val list = mutableListOf<FirestoreToy>()

        try {
            val querySnapshot = db.collection(collection)
                .whereEqualTo("ownerEmail", email)
                .get()
                .await()

            for(doc in querySnapshot.documents) {
                list.add(FirestoreToy(doc))
            }
        } catch (_: Exception) {
        }

        return list
    }

    suspend fun getToyByEmailAndId(email: String, id: String) : FirestoreToy? {
        return try {
            val querySnapshot = db.collection(collection)
                .whereEqualTo("ownerEmail", email)
                .whereEqualTo("id", id)
                .get()
                .await()

            if(querySnapshot.documents.isNotEmpty()) {
                FirestoreToy(querySnapshot.documents[0])
            } else null
        } catch (_: Exception) {
            null
        }
    }

    suspend fun getToyById(id: String) : FirestoreToy? {
        return try {
            val querySnapshot = db.collection(collection)
                .whereEqualTo("id", id)
                .get()
                .await()

            if(querySnapshot.documents.isNotEmpty()) {
                FirestoreToy(querySnapshot.documents[0])
            } else null
        } catch (_: Exception) {
            null
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    suspend fun getToysByIds(ids: List<String?>): List<FirestoreToy> = coroutineScope {
        if(ids.isEmpty()) return@coroutineScope emptyList()
        val deferredDocs = ids.map { id ->
            async(Dispatchers.IO) {
                if (id != null) {
                    db.collection(collection).whereEqualTo("id", id).get().await()
                } else {
                    null
                }
            }
        }

        val documents = deferredDocs.awaitAll().filterNotNull()
        val list = documents.map { if(it.documents.isNotEmpty()) FirestoreToy(it.documents[0]) else null }

        return@coroutineScope list.filterNotNull()
    }


    private fun getToyDocumentId(id: String, onComplete: (String?) -> Unit){
        db.collection(collection).whereEqualTo("id", id).get()
            .addOnSuccessListener { snapshot ->
                if(snapshot.documents.isEmpty()) onComplete(null)
                else {
                    onComplete(snapshot.documents[0].id)
                }
            }
    }

    fun deleteToyById(id: String, onComplete: (Boolean) -> Unit) {
        getToyDocumentId(id) { documentId ->
            if (documentId != null) {
                db.collection(collection)
                    .document(documentId)
                    .delete()
                    .addOnSuccessListener { onComplete(true) }
                    .addOnFailureListener { onComplete(false) }
            }
        }
    }

    fun syncToys(room: AppDatabase){
        val email = room.userDao().getEmail() ?: return
        getToysByEmail(email) { list ->
            val localToys = room.toyDao().selectToys(email)
            for (toy in list){
                if (!localToys.any { it.generateId() == toy.generateId() }) {
                    val newToy = LocalToy(toy)
                    room.toyDao().insert(newToy)
                }
            }

            for(localToy in localToys){
                checkToyExists(localToy.ownerEmail, localToy.generateId()) { exists ->
                    if (!exists){
                        room.toyDao().delete(localToy)
                    }
                }
            }

        }
    }

}