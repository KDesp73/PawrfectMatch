package io.github.kdesp73.petadoption.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [LocalUser::class, Settings::class, LocalPet::class], version = 6, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): LocalUserDao
    abstract fun settingsDao(): SettingsDao
    abstract fun petDao() : LocalPetDao
}
