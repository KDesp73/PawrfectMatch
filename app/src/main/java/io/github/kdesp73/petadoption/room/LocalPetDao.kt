package io.github.kdesp73.petadoption.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface LocalPetDao {
    @Query("SELECT * FROM LocalPet WHERE owner_email = :ownerEmail")
    fun selectPets(ownerEmail: String) : List<LocalPet>

    @Query("SELECT * FROM LocalPet WHERE id = :id")
    fun selectPetFromId(id: Int) : LocalPet?

    @Insert
    fun insert(vararg pet: LocalPet)

    @Delete
    fun delete(pet: LocalPet)

    @Update
    fun update(vararg pet: LocalPet)
}