package com.gyonia.exercisetracker.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.gyonia.exercisetracker.model.Exercise

class ExerciseTypeConverter {
    object MapTypeConverter {

        @TypeConverter
        @JvmStatic
        fun stringToMap(value: String): HashMap<String, Int> {
            return Gson().fromJson(value,  object : TypeToken<HashMap<String, Int>>() {}.type)
        }

        @TypeConverter
        @JvmStatic
        fun mapToString(value: HashMap<String, Int>?): String {
            return if(value == null) "" else Gson().toJson(value)
        }
    }

    object ExerciseTypeTypeConverter {
        const val Reps = "Reps"
        const val Time = "Time"

        @TypeConverter
        @JvmStatic
        fun stringToExerciseType(value: String): Exercise.ExerciseType {
            return when (value) {
                Reps -> Exercise.ExerciseType.Reps
                Time -> Exercise.ExerciseType.Time
                else -> Exercise.ExerciseType.Reps
            }
        }

        @TypeConverter
        @JvmStatic
        fun exerciseTypeToString(value: Exercise.ExerciseType): String {
            return value.name
        }
    }
}