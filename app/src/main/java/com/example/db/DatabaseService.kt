package com.example.db

import com.example.room.Entity.Course
import com.example.room.Entity.Groups
import com.example.room.Entity.Mentor
import com.example.room.Entity.Student

interface DatabaseService {

    fun insertCourse(course: Course)
    fun insertMentor(mentor: Mentor)
    fun insertGroup(group: Groups)
    fun insertStudent(student: Student)

    fun getCourseById(id: Int): Course
    fun getMentorById(id: Int): Mentor
    fun getGroupById(id: Int): Groups
    fun getStudentById(id: Int): Student

    fun getAllCourse(): ArrayList<Course>
    fun getAllMentor(courseId: Int): ArrayList<Mentor>
    fun getAllGroup(query: String): ArrayList<Groups>
    fun getAllStudent(query: String): ArrayList<Student>

    //  fun updateCourse(course: Course)
    fun updateMentor(mentor: Mentor)
    fun updateGroup(group: Groups)
    fun updateStudent(student: Student)

    //  fun deleteCourse(course:Course)
    fun deleteMentor(mentor: Mentor)
    fun deleteStudent(student: Student)
    fun deleteGroup(group: Groups)


}