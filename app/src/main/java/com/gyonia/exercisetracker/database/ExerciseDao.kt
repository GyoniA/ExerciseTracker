package com.gyonia.exercisetracker.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ExerciseDao {
    @Insert
    fun insertExercise(exercise: RoomExercise)

    @Query("SELECT * FROM exercise")
    fun getAllExercises(): LiveData<List<RoomExercise>>

    @Update
    fun updateExercise(exercise: RoomExercise): Int

    @Delete
    fun deleteExercise(exercise: RoomExercise)

    @Query("SELECT * FROM exercise WHERE id == :id")
    fun getExerciseById(id: Int?): RoomExercise?
}