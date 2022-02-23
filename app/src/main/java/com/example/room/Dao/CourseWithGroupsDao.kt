package com.example.room.Dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.room.Entity.CourseWithGroups

@Dao
interface CourseWithGroupsDao {
    @Transaction
    @Query("select * from Course where courseId =:courseId")
    fun getGroupsByCourse(courseId: Int): CourseWithGroups
}