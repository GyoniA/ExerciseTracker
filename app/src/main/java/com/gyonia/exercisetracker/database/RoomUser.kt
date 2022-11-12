package com.gyonia.exercisetracker.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.gyonia.exercisetracker.model.Exercise


@Entity(tableName = "user")
data class RoomUser(
    @PrimaryKey(autoGenerate = true)
    val userId: Int = 0,
    val username: String,
    val password: String,
    @Relation(
        parentColumn = "userId",
        entityColumn = "ownerUserId"
    )
    val exercises: List<Exercise>
)