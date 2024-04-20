package io.github.kdesp73.petadoption.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface SettingsDao {
    @Query("SELECT * FROM Settings WHERE id = 0")
    fun getSettings(): Settings

    @Query("SELECT theme FROM Settings WHERE id = 0")
    fun getTheme(): String

    @Query("SELECT language FROM Settings WHERE id = 0")
    fun getLanguage(): String

    @Query("SELECT first_run FROM Settings WHERE id = 0")
    fun isFirstRun(): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg settings: Settings)

    @Delete
    fun delete(settings: Settings)

    @Update
    fun updateTheme(vararg settings: Settings)

}