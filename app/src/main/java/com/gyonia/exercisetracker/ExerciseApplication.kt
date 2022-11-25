package com.gyonia.exercisetracker

import android.app.Application
import androidx.room.Room
import com.gyonia.exercisetracker.database.ExerciseDatabase
import com.gyonia.exercisetracker.database.LoginDatabase

class ExerciseApplication : Application() {

    companion object {
        lateinit var exerciseDatabase: ExerciseDatabase
            private set

        lateinit var loginDatabase: LoginDatabase
            private set

        var userId: String = ""
    }

    override fun onCreate() {
        super.onCreate()

        exerciseDatabase = Room.databaseBuilder(
            applicationContext,
            ExerciseDatabase::class.java,
            "exercise_database"
        ).fallbackToDestructiveMigration().build()

        loginDatabase = Room.databaseBuilder(
            applicationContext,
            LoginDatabase::class.java,
            "login_database"
        ).fallbackToDestructiveMigration().build()
    }
}