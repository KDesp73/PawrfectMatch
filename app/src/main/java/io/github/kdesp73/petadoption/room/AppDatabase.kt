package io.github.kdesp73.petadoption.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(LocalUser::class, Settings::class), version = 4, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): LocalUserDao
    abstract fun settingsDao(): SettingsDao
}
