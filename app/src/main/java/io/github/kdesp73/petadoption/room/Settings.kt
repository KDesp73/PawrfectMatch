package io.github.kdesp73.petadoption.room

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class Settings (
    @PrimaryKey val id: Int = 1,
    @ColumnInfo(name = "theme") val theme: String,
    @ColumnInfo(name = "language") val language: String,
    @ColumnInfo(name = "first_run") val firstRun: Boolean = true
)