package com.example.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Course {
    @PrimaryKey(autoGenerate = true)
    var courseId: Int? = null

    @ColumnInfo(name = "courseName")
    var name: String? = null

    @ColumnInfo(name = "courseAbout")
    var about: String? = null

    constructor()
    constructor(name: String?, about: String?) {
        this.name = name
        this.about = about
    }


}