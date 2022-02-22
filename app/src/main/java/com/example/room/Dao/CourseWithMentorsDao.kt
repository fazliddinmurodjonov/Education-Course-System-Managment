package com.example.room.Dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.room.entity.CourseWithMentors

@Dao
interface CourseWithMentorsDao {
    @Transaction
    @Query("select * from Course where courseId= :courseId")
    fun getMentorsByCourse(courseId: Int): List<CourseWithMentors>
}