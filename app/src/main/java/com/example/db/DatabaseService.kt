package com.example.db

import com.example.room.entity.Course
import com.example.room.entity.Group
import com.example.room.entity.Mentor
import com.example.room.entity.Student

interface DatabaseService {

    fun insertCourse(course: Course)
    fun insertMentor(mentor: Mentor)
    fun insertGroup(group: Group)
    fun insertStudent(student: Student)

    fun getCourseById(id: Int): Course
    fun getMentorById(id: Int): Mentor
    fun getGroupById(id: Int): Group
    fun getStudentById(id: Int): Student

    fun getAllCourse(): ArrayList<Course>
    fun getAllMentor(courseId: Int): ArrayList<Mentor>
    fun getAllGroup(query: String): ArrayList<Group>
    fun getAllStudent(query: String): ArrayList<Student>

    //  fun updateCourse(course: Course)
    fun updateMentor(mentor: Mentor)
    fun updateGroup(group: Group)
    fun updateStudent(student: Student)

    //  fun deleteCourse(course:Course)
    fun deleteMentor(mentor: Mentor)
    fun deleteStudent(student: Student)
    fun deleteGroup(group: Group)


}