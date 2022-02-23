package com.example.room.Entity

import androidx.room.Embedded
import androidx.room.Relation

data class StudentWithGroup(
    @Embedded val group: Groups,
    @Relation(
        parentColumn = "groupId",
        entityColumn = "studentGroupId"
    )
    val students: List<Student>,
)