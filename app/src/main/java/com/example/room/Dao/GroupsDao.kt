package com.example.room.Dao

import androidx.room.*
import com.example.room.Entity.Groups
import java.security.acl.Group

@Dao
interface GroupsDao {
    @Insert
    fun insertGroup(groups: Groups)

    @Update
    fun updateGroup(groups: Groups)

    @Delete
    fun deleteGroup(groups: Groups)

    @Query("select * from Groups where groupId = :groupId")
    fun getGroupById(groupId: Int): Groups

    @Query("select * from Groups where groupMentorId=:mentorId and groupCourseId = :courseId and lessonStart =:lessonStart")
    fun getGroupsByMentor(mentorId: Int, courseId: Int, lessonStart: Int): List<Groups>

    @Query("select *from Groups where lessonStart = :lessonStart")
    fun getGroupsByLessonStart(lessonStart: Int): List<Groups>

    @Query("select *from Groups where lessonStart = :lessonStart and groupCourseId = :courseId")
    fun getGroupsByLessonStartCourse(lessonStart: Int, courseId: Int): List<Groups>

}