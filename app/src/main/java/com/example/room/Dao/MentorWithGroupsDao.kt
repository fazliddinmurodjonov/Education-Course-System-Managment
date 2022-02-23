package com.example.room.Dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.room.Entity.MentorWithGroups

@Dao
interface MentorWithGroupsDao {
    @Transaction
    @Query("select * from Mentor where mentorId = :mentorId")
    fun getGroupsByMentor(mentorId:Int): MentorWithGroups

}