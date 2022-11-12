package com.gyonia.exercisetracker

import android.app.Application
import androidx.room.Room
import com.gyonia.exercisetracker.database.ExerciseDatabase

class ExerciseApplication : Application() {

    companion object {
        lateinit var exerciseDatabase: ExerciseDatabase
            private set
    }

    override fun onCreate() {
        super.onCreate()

        exerciseDatabase = Room.databaseBuilder(
            applicationContext,
            ExerciseDatabase::class.java,
            "exercise_database"
        ).fallbackToDestructiveMigration().build()
    }

}