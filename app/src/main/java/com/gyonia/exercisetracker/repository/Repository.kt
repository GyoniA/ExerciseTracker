package com.gyonia.exercisetracker.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.gyonia.exercisetracker.database.ExerciseDao
import com.gyonia.exercisetracker.database.RoomExercise
import com.gyonia.exercisetracker.model.Exercise
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository(private val exerciseDao: ExerciseDao) {

    fun getAllExercises(): LiveData<List<Exercise>> {
        return exerciseDao.getAllExercises()
            .map {roomExercises ->
                roomExercises.map {roomExercise ->
                    roomExercise.toDomainModel() }
            }
    }

    suspend fun insert(exercise: Exercise) = withContext(Dispatchers.IO) {
        exerciseDao.insertExercise(exercise.toRoomModel())
    }

    private fun RoomExercise.toDomainModel(): Exercise {
        return Exercise(
            id = id,
            name = name,
            description = description,
            type = type,
            amountDoneOnDate = amountDoneOnDate
        )
    }

    private fun Exercise.toRoomModel(): RoomExercise {
        return RoomExercise(
            id = id,
            name = name,
            description = description,
            type = type,
            amountDoneOnDate = amountDoneOnDate
        )
    }
}