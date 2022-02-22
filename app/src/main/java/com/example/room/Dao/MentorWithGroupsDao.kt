package com.example.room.Dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.room.entity.MentorWithGroups

@Dao
interface MentorWithGroupsDao {
    @Transaction
    @Query("select * from Mentor")
    fun getGroupsByMentor():List<MentorWithGroups>

}