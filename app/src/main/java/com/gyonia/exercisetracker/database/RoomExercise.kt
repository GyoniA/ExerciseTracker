package com.gyonia.exercisetracker.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.gyonia.exercisetracker.model.Exercise

@Entity(tableName = "exercise")
data class RoomExercise(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val description: String,
    @TypeConverters(ExerciseTypeConverter.ExerciseTypeTypeConverter::class)
    val type: Exercise.ExerciseType,

    @TypeConverters(ExerciseTypeConverter.MapTypeConverter::class)
    val amountDoneOnDate: HashMap<String, Int>,

    val ownerUserId: Int
)