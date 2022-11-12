package com.gyonia.exercisetracker.model

data class Exercise(
    val id: Int,
    val name: String,
    val description: String,
    val type: ExerciseType,

    val amountDoneOnDate: HashMap<String, Int>,
    val ownerUserId: String
) {
    enum class ExerciseType {
        Reps, Time
    }
}