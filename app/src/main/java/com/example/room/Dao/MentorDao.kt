package com.example.room.Dao

import androidx.room.*
import com.example.room.Entity.Mentor

@Dao
interface MentorDao {
    @Insert
    fun insertMentor(mentor: Mentor)

    @Update
    fun updateMentor(mentor: Mentor)

    @Delete
    fun deleteMentor(mentor: Mentor)

    @Query("select * from Mentor where mentorCourseId = :courseId")
    fun getAllMentors(courseId: Int): List<Mentor>
}