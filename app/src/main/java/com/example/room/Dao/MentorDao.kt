package com.example.room.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import com.example.room.entity.Mentor

@Dao
interface MentorDao {
    @Insert
    fun insertMentor(mentor: Mentor)
    @Update
    fun updateMentor(mentor: Mentor)
    @Delete
    fun deleteMentor(mentor: Mentor)

}