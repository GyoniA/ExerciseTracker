package com.gyonia.exercisetracker.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    version = 2,
    exportSchema = false,
    entities = [RoomExercise::class]
)
@TypeConverters(
    ExerciseTypeConverter.ExerciseTypeTypeConverter::class,
    ExerciseTypeConverter.MapTypeConverter::class
)
abstract class ExerciseDatabase : RoomDatabase() {

    abstract fun exerciseDao(): ExerciseDao

}