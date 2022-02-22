package com.example.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Mentor {
    @PrimaryKey(autoGenerate = true)
    var mentorId: Int? = null

    @ColumnInfo(name = "firstname")
    var firstname: String? = null

    @ColumnInfo(name = "lastname")
    var lastname: String? = null

    @ColumnInfo(name = "fathersName")
    var fatherSName: String? = null

    @ColumnInfo(name = "mentorCourseId")
    var mentorCourseId: Int? = null

    constructor()
    constructor(firstname: String?, lastname: String?, fatherSName: String?, courseId: Int?) {
        this.firstname = firstname
        this.lastname = lastname
        this.fatherSName = fatherSName
        this.mentorCourseId = courseId
    }


}