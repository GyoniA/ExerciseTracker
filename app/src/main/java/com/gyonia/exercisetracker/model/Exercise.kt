package com.gyonia.exercisetracker.model

data class Exercise(
    val id: Int,
    val name: String,
    val description: String,
    val type: ExerciseType,
    //TODO store hashmap in database as a JSON string
    val amountDoneOnDate: HashMap<String, Int>
) {
    enum class ExerciseType {
        Reps, Time
    }
}