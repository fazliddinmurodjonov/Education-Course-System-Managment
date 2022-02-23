package com.example.room.Entity

import androidx.room.Embedded
import androidx.room.Relation

data class MentorWithGroups(
    @Embedded val mentor: Mentor,
    @Relation(
        parentColumn = "mentorId",
        entityColumn = "groupMentorId"
    )
    val groups: List<Groups>,
)