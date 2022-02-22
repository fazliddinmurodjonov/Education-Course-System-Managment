package com.example.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Student {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
    @ColumnInfo(name = "firstname")
    var firstname: String? = null
    @ColumnInfo(name = "lastname")
    var lastname: String? = null
    @ColumnInfo(name = "fatherSName")
    var fatherSName: String? = null
    @ColumnInfo(name = "timeBeginEnd")
    var time: String? = null
    @ColumnInfo(name = "studentGroupId")
    var studentGroupId: Int? = null

    constructor()
    constructor(
        firstname: String?,
        lastname: String?,
        fatherSName: String?,
        time: String?,
        studentGroupId: Int?,
    ) {
        this.firstname = firstname
        this.lastname = lastname
        this.fatherSName = fatherSName
        this.time = time
        this.studentGroupId = studentGroupId
    }


}