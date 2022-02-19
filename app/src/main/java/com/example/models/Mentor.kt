package com.example.models

class Mentor {
    var id: Int? = null
    var firstname: String? = null
    var lastname: String? = null
    var fatherSName: String? = null
    var courseId: Int? = null
    var groupIdList: String? = null

    constructor()
    constructor(
        firstname: String?,
        lastname: String?,
        fatherSName: String?,
        courseId: Int?,
        groupIdList: String?,
    ) {
        this.firstname = firstname
        this.lastname = lastname
        this.fatherSName = fatherSName
        this.courseId = courseId
        this.groupIdList = groupIdList
    }

    constructor(
        id: Int?,
        firstname: String?,
        lastname: String?,
        fatherSName: String?,
        courseId: Int?,
        groupIdList: String?,
    ) {
        this.id = id
        this.firstname = firstname
        this.lastname = lastname
        this.fatherSName = fatherSName
        this.courseId = courseId
        this.groupIdList = groupIdList
    }


}