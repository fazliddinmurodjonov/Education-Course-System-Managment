package com.example.room.Dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.room.Entity.StudentWithGroup

@Dao
interface StudentWithGroupDao {
    @Transaction
    @Query("select * from Groups where groupId = :groupId")
    fun getStudentsByGroup(groupId: Int): StudentWithGroup
}