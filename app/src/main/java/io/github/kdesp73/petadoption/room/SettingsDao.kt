package io.github.kdesp73.petadoption.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface SettingsDao {
    @Query("SELECT theme FROM Settings")
    fun getTheme(): String

    @Insert
    fun insert(vararg settings: Settings)

    @Delete
    fun delete(settings: Settings)

}