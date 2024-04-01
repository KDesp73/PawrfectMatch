package io.github.kdesp73.petadoption.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [LocalUser::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): LocalUserDao
    abstract fun settingsDao(): SettingsDao
}
