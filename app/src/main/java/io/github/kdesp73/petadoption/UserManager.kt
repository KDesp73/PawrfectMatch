package io.github.kdesp73.petadoption

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

class UserManager {
    private val TAG = "UserManagement"
    private val db = FirebaseFirestore.getInstance()

    public enum class StatusMessage(val message: String){
        ERRO_USER_EXISTS("User already exists"),
        ERRO_FAILED_DOC_CREATION("Error adding document"),
        SUCC_CREATED_USER("User created successfully")
    }

    fun checkUserExists(email: String, onComplete: (Boolean) -> Unit) {
        db.collection("Users")
            .whereEqualTo("email", email)
            .get()
            .addOnSuccessListener { documents ->
                onComplete(!documents.isEmpty)
            }
            .addOnFailureListener { exception ->
                onComplete(false)
            }
    }

    fun addUser(user: User, onComplete: (Boolean, String) -> Unit) {
        checkUserExists(user.email) { exists ->
            if(!exists){
                db.collection("Users")
                    .add(user)
                    .addOnSuccessListener { documentReference ->
                        Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                        onComplete(true, StatusMessage.SUCC_CREATED_USER.message)
                    }
                    .addOnFailureListener { e ->
                        Log.w(TAG, StatusMessage.ERRO_FAILED_DOC_CREATION.message, e)
                        onComplete(false, StatusMessage.ERRO_FAILED_DOC_CREATION.message)
                    }
            } else {
                Log.d(TAG, StatusMessage.ERRO_USER_EXISTS.message)
                onComplete(false, StatusMessage.ERRO_USER_EXISTS.message)
            }
        }
    }

    fun getUserByEmail(email: String, onComplete: (List<String>) -> Unit) {
        db.collection("Users")
            .whereEqualTo("email", email)
            .get()
            .addOnSuccessListener { documents ->
                val userIds = mutableListOf<String>()
                for (document in documents) {
                    userIds.add(document.id)
                }
                onComplete(userIds)
            }
            .addOnFailureListener { exception ->
                onComplete(emptyList())
            }
    }

    fun getPasswordHash(email: String, onComplete: (String?) -> Unit){
        getUserByEmail(email){ list ->
            if(list.size == 1){
                db.collection("Users")
                    .document(list[0])
                    .get()
                    .addOnSuccessListener { document ->
                        if(document.exists()){
                            val passHass = document.getString("password")
                            onComplete(passHass)
                        } else {
                            onComplete(null)
                        }
                    }
                    .addOnFailureListener { exception ->
                        onComplete(null)
                    }
            }
        }

    }
}
