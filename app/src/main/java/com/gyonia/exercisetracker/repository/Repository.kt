package com.gyonia.exercisetracker.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.gyonia.exercisetracker.ExerciseApplication
import com.gyonia.exercisetracker.database.ExerciseDao
import com.gyonia.exercisetracker.database.RoomExercise
import com.gyonia.exercisetracker.model.Exercise
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository(private val exerciseDao: ExerciseDao) {

    fun getAllExercises(): LiveData<List<Exercise>> {
        return exerciseDao.getExercisesByOwnerUserId(ExerciseApplication.userId)
            .map {roomExercises ->
                roomExercises.map {roomExercise ->
                    roomExercise.toDomainModel() }
            }
    }

    suspend fun insert(exercise: Exercise) = withContext(Dispatchers.IO) {
        exerciseDao.insertExercise(exercise.toRoomModel())
    }

    suspend fun update(exercise: Exercise) = withContext(Dispatchers.IO) {
        exerciseDao.updateExercise(exercise.toRoomModel())
    }

    private fun RoomExercise.toDomainModel(): Exercise {
        return Exercise(
            id = id,
            name = name,
            description = description,
            type = type,
            amountDoneOnDate = amountDoneOnDate,
            ownerUserId = ownerUserId
        )
    }

    private fun Exercise.toRoomModel(): RoomExercise {
        return RoomExercise(
            id = id,
            name = name,
            description = description,
            type = type,
            amountDoneOnDate = amountDoneOnDate,
            ownerUserId = ownerUserId
        )
    }

    suspend fun delete(exercise: Exercise) = withContext(Dispatchers.IO) {
        val roomExercise = exerciseDao.getExerciseById(exercise.id) ?: return@withContext
        exerciseDao.deleteExercise(roomExercise)
    }
}