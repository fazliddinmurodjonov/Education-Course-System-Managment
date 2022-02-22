package com.example.room.Dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.room.entity.Course

@Dao
interface CourseDao {
    @Insert
    fun insertCourse(course: Course)
}