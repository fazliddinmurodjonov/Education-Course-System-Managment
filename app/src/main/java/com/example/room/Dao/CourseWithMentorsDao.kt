package com.example.room.Dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.room.Entity.CourseWithMentors

@Dao
interface CourseWithMentorsDao {
    @Transaction
    @Query("select * from Course where courseId = :id")
    fun getMentorsByCourse(id: Int): CourseWithMentors
}