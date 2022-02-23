package com.example.room.Entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation

@Entity
data class CourseWithGroups(
    @Embedded
    val course: Course,
    @Relation(
        parentColumn = "courseId",
        entityColumn = "groupCourseId"
    )
    val groups: List<Groups>,
)