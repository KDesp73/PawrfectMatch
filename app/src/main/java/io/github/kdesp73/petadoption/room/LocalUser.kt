package io.github.kdesp73.petadoption.room

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class LocalUser (
    @PrimaryKey val email: String,
    @ColumnInfo(name = "logged_in") val loggedIn: Boolean
)