package io.github.kdesp73.petadoption.firebase

import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import io.github.kdesp73.petadoption.MainActivity
import io.github.kdesp73.petadoption.R
import kotlinx.coroutines.tasks.await

class UserManager {
    private val TAG = "UserManagement"
    private val db = FirebaseFirestore.getInstance()

    public enum class StatusMessage(val message: String) {
        ERRO_USER_EXISTS(MainActivity.appContext.getString(R.string.err_user_already_exists)),
        ERRO_FAILED_DOC_CREATION(MainActivity.appContext.getString(R.string.err_error_adding_document)),
        SUCC_CREATED_USER(MainActivity.appContext.getString(R.string.err_user_created_successfully))
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
                val u = hashMapOf(
                    "email" to user.email,
                    "password" to user.password
                )
                db.collection("Users")
                    .add(u)
                    .addOnSuccessListener { userDocumentReference ->
                        Log.d(TAG, "DocumentSnapshot added with ID: ${userDocumentReference.id}")
                        val i = hashMapOf(
                            "email" to user.email,
                            "firstName" to user.info?.firstName!!,
                            "lastName" to user.info.lastName,
                            "gender" to user.info.gender,
                            "location" to user.info.location,
                            "phone" to user.info.phone,
                            "profileType" to user.info.profileType
                        )

                        db.collection("UserInfo")
                            .add(i)
                            .addOnSuccessListener { infoDocumentReference ->
                                Log.d(TAG, "DocumentSnapshot added with ID: ${infoDocumentReference.id}")
                                onComplete(true, StatusMessage.SUCC_CREATED_USER.message)
                            }
                            .addOnFailureListener { e ->
                                Log.w(TAG, StatusMessage.ERRO_FAILED_DOC_CREATION.message, e)
                                onComplete(false, StatusMessage.ERRO_FAILED_DOC_CREATION.message)
                            }
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

    suspend fun getUserByEmail(email: String) : User? {
        val userDocument: DocumentSnapshot

        val querySnapshot = db.collection("Users")
            .whereEqualTo("email", email)
            .get()
            .await()

        val infoQuerySnapshot = db.collection("UserInfo")
                .whereEqualTo("email", email)
                .get()
                .await()

        if(querySnapshot.documents.isEmpty()) return null

        return User.documentToObject(querySnapshot.documents[0], infoQuerySnapshot.documents[0])
    }


    fun getUserByEmail(email: String, onComplete: (List<User>) -> Unit) {
        val userDocument: DocumentSnapshot

        val userTask = db.collection("Users")
            .whereEqualTo("email", email)
            .get()
            .addOnSuccessListener { userDocs ->
                val user = userDocs.documents[0] ?: null;

                if(user != null){
                    db.collection("UserInfo")
                        .whereEqualTo("email", email)
                        .get()
                        .addOnSuccessListener { documents ->
                            val users = mutableListOf<User>()
                            for (document in documents) {
                                users.add(user.let { User.documentToObject(it, document) })
                            }
                            onComplete(users)
                        }
                        .addOnFailureListener { exception ->
                            onComplete(emptyList())
                        }
                } else {
                    onComplete(emptyList())
                }

            }
    }

    fun updateInfo(info: UserInfo, onComplete: (Boolean) -> Unit){
        getUserInfoDocumentId(info.email){ id ->
            if(id != null)
                db.collection("UserInfo")
                    .document(id)
                    .update(info.toMap())
                    .addOnSuccessListener {
                        onComplete(true)
                    }
                    .addOnFailureListener {
                        onComplete(false)
                    }
        }
    }

    fun updateUser(user: User, onComplete: (Boolean) -> Unit) {
        getUserDocumentId(user.email){ id ->
            if (id != null) {
                db.collection("Users")
                    .document(id)
                    .update(user.toMap())
                    .addOnSuccessListener {
                        onComplete(true)
                    }
                    .addOnFailureListener {
                        onComplete(false)
                    }
            }
        }
    }

    fun getUserDocumentId(email: String, onComplete: (String?) -> Unit){
        db.collection("Users").whereEqualTo("email", email).get()
            .addOnSuccessListener { snapshot ->
                if(snapshot.documents.isEmpty()) onComplete(null)
                else {
                    onComplete(snapshot.documents[0].id)
                }
            }
    }

    fun getUserInfoDocumentId(email: String, onComplete: (String?) -> Unit){
        db.collection("UserInfo").whereEqualTo("email", email).get()
            .addOnSuccessListener { snapshot ->
                if(snapshot.documents.isEmpty()) onComplete(null)
                else {
                    onComplete(snapshot.documents[0].id)
                }
            }
    }
}