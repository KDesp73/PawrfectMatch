package io.github.kdesp73.petadoption.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface LocalUserDao {
    @Query("SELECT email FROM LocalUser")
    fun getEmail(): String

    @Query("SELECT COUNT(*) FROM LocalUser")
    fun count(): Int

    @Update
    fun update(user: LocalUser)

    @Insert
    fun insert(vararg user: LocalUser)

    @Delete
    fun delete(user: LocalUser)

    @Query("DELETE FROM LocalUser")
    fun deleteAll()
}