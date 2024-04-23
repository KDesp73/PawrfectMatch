package io.github.kdesp73.petadoption.firebase

import android.util.Log
import com.google.android.gms.tasks.Tasks.await
import com.google.android.play.integrity.internal.i
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import io.github.kdesp73.petadoption.room.AppDatabase
import io.github.kdesp73.petadoption.room.LocalPet
import io.github.kdesp73.petadoption.viewmodels.SearchOptions
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.tasks.await

class PetManager {
    private val TAG = "PetManager"
    private val db = FirebaseFirestore.getInstance()

    suspend fun filterPets(options: SearchOptions): MutableList<FirestorePet> {
        fun typeIncluded(pet: FirestorePet): Boolean{
            return options.type[pet.type] == true
        }

        fun ageIncluded(pet: FirestorePet): Boolean{
            return options.age[pet.age] == true
        }

        fun sizeIncluded(pet: FirestorePet): Boolean{
            return options.size[pet.size] == true
        }

        fun genderIncluded(pet: FirestorePet): Boolean{
            return options.gender[pet.gender] == true
        }

        fun includePet(pet: FirestorePet): Boolean{
            return typeIncluded(pet) && ageIncluded(pet) && sizeIncluded(pet) && genderIncluded(pet)
        }

        val list = mutableListOf<FirestorePet>()
        val querySnapshot = db.collection("Pets").get().await()


        for(doc in querySnapshot.documents){
            val pet = FirestorePet(doc)
            if(includePet(pet)){
                list.add(pet)
            }
        }
        return list
    }

    private fun checkPetExists(pet: FirestorePet, onComplete: (Boolean) -> Unit){
        db.collection("Pets")
            .whereEqualTo("ownerEmail", pet.ownerEmail)
            .get()
            .addOnSuccessListener { snapshot ->
                var found = false
                for(p in snapshot.documents){
                    if(FirestorePet(p).id == pet.id)
                        found = true
                }

                onComplete(found)
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, exception.message.toString())
                onComplete(false)
            }
    }

    fun addPet(pet: FirestorePet, onComplete: (Boolean) -> Unit){
        checkPetExists(pet){ exists ->
            if(exists){
                Log.d(TAG, "${pet.toString()} already exists")
                onComplete(false)
            } else {
                db.collection("Pets")
                    .add(pet)
                    .addOnSuccessListener { snapshot ->
                        onComplete(true)
                    }
                    .addOnFailureListener { exception ->
                        onComplete(false)
                    }
            }
        }
    }

    fun getPetsByEmail(email: String, onComplete: (List<FirestorePet>) -> Unit) {
        val list = mutableListOf<FirestorePet>()

        try {
            val querySnapshot = db.collection("Pets")
                .whereEqualTo("ownerEmail", email)
                .get()
                .addOnSuccessListener { querySnapshot ->
                    for(doc in querySnapshot.documents) {
                        list.add(FirestorePet(doc))
                    }
                    onComplete(list)
                }
                .addOnFailureListener{
                    onComplete(list)
                }

        } catch (_: Exception) {
        }
    }
    suspend fun getPetsByEmail(email: String) : List<FirestorePet> {
        val list = mutableListOf<FirestorePet>()

        try {
            val querySnapshot = db.collection("Pets")
                .whereEqualTo("ownerEmail", email)
                .get()
                .await()

                for(doc in querySnapshot.documents) {
                    list.add(FirestorePet(doc))
                }
        } catch (_: Exception) {
        }

        return list
    }

    suspend fun getPetByEmailAndId(email: String, id: String) : FirestorePet? {
        return try {
            val querySnapshot = db.collection("Pets")
                .whereEqualTo("ownerEmail", email)
                .whereEqualTo("id", id)
                .get()
                .await()

            if(querySnapshot.documents.isNotEmpty()) {
                FirestorePet(querySnapshot.documents[0])
            } else null
        } catch (_: Exception) {
            null
        }
    }

    suspend fun getPetById(id: String) : FirestorePet? {
        return try {
            val querySnapshot = db.collection("Pets")
                .whereEqualTo("id", id)
                .get()
                .await()

            if(querySnapshot.documents.isNotEmpty()) {
                FirestorePet(querySnapshot.documents[0])
            } else null
        } catch (_: Exception) {
            null
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    suspend fun getPetsByIds(ids: List<String?>): List<FirestorePet> = coroutineScope {
        val deferredDocs = ids.map { id ->
            async(Dispatchers.IO) {
                if (id != null) {
                    db.collection("Pets").whereEqualTo("id", id).get().await()
                } else {
                    null
                }
            }
        }

        val documents = deferredDocs.awaitAll().filterNotNull()
        documents.map { FirestorePet(it.documents[0]) }
    }

    @OptIn(DelicateCoroutinesApi::class)
    suspend fun getFavouritePets(email: String) : List<FirestorePet?> {
        val likedManager = LikedManager()

        return emptyList()
    }

    private fun getPetDocumentId(id: String, onComplete: (String?) -> Unit){
        db.collection("Pets").whereEqualTo("id", id).get()
            .addOnSuccessListener { snapshot ->
                if(snapshot.documents.isEmpty()) onComplete(null)
                else {
                    onComplete(snapshot.documents[0].id)
                }
            }
    }

    fun deletePetById(id: String, onComplete: (Boolean) -> Unit) {
        getPetDocumentId(id) { documentId ->
            if (documentId != null) {
                db.collection("Pets")
                    .document(documentId)
                    .delete()
                    .addOnSuccessListener { onComplete(true) }
                    .addOnFailureListener { onComplete(false) }
            }
        }
    }

    fun syncPets(room: AppDatabase){
        val email = room.userDao().getEmail() ?: return
        getPetsByEmail(email) { list ->
            val localPets = room.petDao().selectPets(email)
            if (localPets.size < list.size){
                for (pet in list){
                    if (!localPets.any { it.generateId() == pet.generateId() }) {
                        val newPet = LocalPet(pet)
                        room.petDao().insert(newPet)
                    }
                }
            }
        }

    }
 }
