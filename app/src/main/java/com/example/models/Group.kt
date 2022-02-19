package com.example.models

class Group {
    var id: Int? = null
    var name: String? = null
    var time: String? = null
    var days: String? = null
    var studentSCount: Int = 0
    var courseId: Int? = null
    var mentorId: Int? = null
    var lessonStart: Int? = null

    constructor()
    constructor(
        name: String?,
        time: String?,
        days: String?,
        studentSCount: Int,
        courseId: Int?,
        mentorId: Int?,
        lessonStart: Int?,
    ) {
        this.name = name
        this.time = time
        this.days = days
        this.studentSCount = studentSCount
        this.courseId = courseId
        this.mentorId = mentorId
        this.lessonStart = lessonStart
    }

        constructor(
        id: Int?,
        name: String?,
        time: String?,
        days: String?,
        studentSCount: Int,
        courseId: Int?,
        mentorId: Int?,
        lessonStart: Int?,
    ) {
        this.id = id
        this.name = name
        this.time = time
        this.days = days
        this.studentSCount = studentSCount
        this.courseId = courseId
        this.mentorId = mentorId
        this.lessonStart = lessonStart
    }


}