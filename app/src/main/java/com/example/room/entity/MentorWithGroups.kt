package com.example.room.entity

import androidx.room.Embedded
import androidx.room.Relation

data class MentorWithGroups(
    @Embedded val mentor: Mentor,
    @Relation(
        parentColumn = "mentorId",
        entityColumn = "groupMentorId"
    )
    val groups: List<Group>,
)