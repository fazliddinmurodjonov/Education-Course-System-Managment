package com.example.room.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Groups {
    @PrimaryKey(autoGenerate = true)
    var groupId: Int? = null

    @ColumnInfo(name = "name")
    var name: String? = null

    @ColumnInfo(name = "time")
    var time: String? = null

    @ColumnInfo(name = "days")
    var days: String? = null

    @ColumnInfo(name = "groupMentorId")
    var groupMentorId: Int? = null

    @ColumnInfo(name = "groupCourseId")
    var groupCourseId: Int? = null

    @ColumnInfo(name = "lessonStart")
    var lessonStart: Int? = null

    @ColumnInfo(name = "studentCount")
    var studentCount: Int? = null

    constructor()
    constructor(
        name: String?,
        time: String?,
        days: String?,
        groupMentorId: Int?,
        groupCourseId: Int?,
        lessonStart: Int?,
        studentCount: Int?,
    ) {
        this.name = name
        this.time = time
        this.days = days
        this.groupMentorId = groupMentorId
        this.groupCourseId = groupCourseId
        this.lessonStart = lessonStart
        this.studentCount = studentCount
    }

}