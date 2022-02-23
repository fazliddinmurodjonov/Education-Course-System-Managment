package com.example.room.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.room.Entity.Course

@Dao
interface CourseDao {
    @Insert
    fun insertCourse(course: Course)

    @Query("select * from Course")
    fun getAllCourse(): List<Course>

    @Query("select * from Course where courseId = :courseId")
    fun getCourseById(courseId: Int): Course
}