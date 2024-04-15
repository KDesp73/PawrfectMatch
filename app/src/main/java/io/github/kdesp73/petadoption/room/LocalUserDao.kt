package io.github.kdesp73.petadoption.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.google.firebase.firestore.auth.User

@Dao
interface LocalUserDao {
    @Query("SELECT email FROM LocalUser WHERE id = 0")
    fun getEmail(): String

    @Query("SELECT gender FROM LocalUser WHERE id = 0")
    fun getGender(): String

    @Query("SELECT image_url FROM LocalUser WHERE id = 0")
    fun getImageUrl(): String

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg user: LocalUser)

    @Delete
    fun delete(user: LocalUser)

    @Query("DELETE FROM LocalUser")
    fun deleteAll()

    @Query("SELECT * FROM LocalUser")
    fun getUsers(): List<LocalUser>

    @Query("SELECT * FROM LocalUser WHERE id = 0")
    fun getUser(): LocalUser

    @Update
    fun update(user: LocalUser)
}