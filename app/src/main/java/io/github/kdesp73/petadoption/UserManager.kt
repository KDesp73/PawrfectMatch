package io.github.kdesp73.petadoption

import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class UserManager {
    private val TAG = "UserManagement"
    private val db = FirebaseFirestore.getInstance()

    public enum class StatusMessage(val message: String) {
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
            if (!exists) {
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

    fun getUserByEmail(email: String, onComplete: (List<User>) -> Unit)
    {
        db.collection("Users")
            .whereEqualTo("email", email)
            .get()
            .addOnSuccessListener { documents ->
                val users = mutableListOf<User>()
                for (document in documents) {
                    users.add(User.documentToObject(document))
                }
                onComplete(users)
            }
            .addOnFailureListener { exception ->
                onComplete(emptyList())
            }
    }
}