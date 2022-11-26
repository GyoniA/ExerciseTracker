package com.gyonia.exercisetracker.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    version = 1,
    exportSchema = false,
    entities = [RoomLogin::class]
)
abstract class LoginDatabase : RoomDatabase() {

    abstract fun loginDao(): LoginDao
}