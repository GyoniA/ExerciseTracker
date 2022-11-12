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

    @Query("SELECT CASE WHEN EXISTS (SELECT * FROM user WHERE username == :username AND password == :password)" +
            "THEN CAST(1 AS BIT)" +
            "ELSE CAST(0 AS BIT) END")
    fun login(username: String, password: String): Boolean
}