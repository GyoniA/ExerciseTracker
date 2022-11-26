package com.gyonia.exercisetracker

import android.app.Application
import androidx.room.Room
import com.gyonia.exercisetracker.database.ExerciseDatabase
import com.gyonia.exercisetracker.viewmodel.ExerciseViewModel

class ExerciseApplication : Application() {

    companion object {
        lateinit var exerciseDatabase: ExerciseDatabase
            private set

        var exerciseViewModel: ExerciseViewModel? = null
        var userId: String = ""
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