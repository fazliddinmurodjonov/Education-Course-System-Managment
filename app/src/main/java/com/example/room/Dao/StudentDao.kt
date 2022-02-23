package com.example.room.Dao

import androidx.room.*
import com.example.room.Entity.Student

@Dao
interface StudentDao {
    @Insert
    fun insertStudent(student: Student)

    @Update
    fun updateStudent(student: Student)

    @Delete
    fun deleteStudent(student: Student)

    @Query("select *from Student where id = :studentId")
    fun getStudentById(studentId: Int): Student
}