package io.github.kdesp73.petadoption.firestore

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import io.github.kdesp73.petadoption.hash

class PetManager {
    private val TAG = "PetManager"
    private val db = FirebaseFirestore.getInstance()


    private fun checkPetExists(pet: Pet, onComplete: (Boolean) -> Unit){
        db.collection("Pets")
            .whereEqualTo("ownerEmail", pet.ownerEmail)
            .get()
            .addOnSuccessListener { snapshot ->
                var found = false
                for(p in snapshot.documents){
                    if(Pet(p).id == pet.id)
                        found = true
                }

                onComplete(found)
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, exception.message.toString())
                onComplete(false)
            }
    }

    fun addPet(pet: Pet, onComplete: (Boolean) -> Unit){
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
}