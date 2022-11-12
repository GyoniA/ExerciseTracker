package com.gyonia.exercisetracker.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gyonia.exercisetracker.ExerciseApplication
import com.gyonia.exercisetracker.model.Exercise
import com.gyonia.exercisetracker.repository.Repository
import kotlinx.coroutines.launch

class ExerciseViewModel : ViewModel() {

    private val repository: Repository

    val allExercises: LiveData<List<Exercise>>

    init {
        val exerciseDao = ExerciseApplication.exerciseDatabase.exerciseDao()
        repository = Repository(exerciseDao)
        allExercises = repository.getAllExercises()
    }

    fun insert(exercise: Exercise) = viewModelScope.launch {
        repository.insert(exercise)
    }

    fun delete(exercise: Exercise) = viewModelScope.launch {
        repository.delete(exercise)
    }
}