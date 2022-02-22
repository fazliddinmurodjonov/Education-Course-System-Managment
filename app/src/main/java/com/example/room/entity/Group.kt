package com.example.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Group {
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
    var groupCourseId:Int?=null
    @ColumnInfo(name = "lessonStart")
    var lessonStart: Int? = null

    constructor()
    constructor(
        name: String?,
        time: String?,
        days: String?,
        mentorIdCreated: Int?,
        courseIdCreated: Int?,
        lessonStart: Int?,
    ) {
        this.name = name
        this.time = time
        this.days = days
        this.groupMentorId = mentorIdCreated
        this.groupCourseId = courseIdCreated
        this.lessonStart = lessonStart
    }


}