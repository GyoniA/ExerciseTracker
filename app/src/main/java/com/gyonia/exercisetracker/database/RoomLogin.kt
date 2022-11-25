package com.gyonia.exercisetracker.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "login")
data class RoomLogin (
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var username: String = "",
    var password: String = ""
)