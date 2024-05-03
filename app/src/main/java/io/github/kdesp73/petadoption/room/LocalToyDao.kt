package io.github.kdesp73.petadoption.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface LocalToyDao {
    @Query("SELECT * FROM LocalToy WHERE owner_email = :ownerEmail")
    fun selectToys(ownerEmail: String) : List<LocalToy>

    @Query("SELECT * FROM LocalToy WHERE id = :id")
    fun selectToyFromId(id: Int) : LocalToy?

    @Query("SELECT * FROM LocalToy WHERE owner_email = :ownerEmail ORDER BY name ASC")
    fun selectToysAlphabetically(ownerEmail: String) : List<LocalToy>

    @Insert
    fun insert(vararg toy: LocalToy)

    @Delete
    fun delete(toy: LocalToy)

    @Update
    fun update(vararg toy: LocalToy)
}