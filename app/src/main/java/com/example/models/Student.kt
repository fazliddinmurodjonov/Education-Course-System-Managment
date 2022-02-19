package com.example.models

class Student {
    var id: Int? = null
    var firstname: String? = null
    var lastname: String? = null
    var fatherSName: String? = null
    var time: String? = null
    var groupId: Int? = null

    constructor()
    constructor(
        firstname: String?,
        lastname: String?,
        fatherSName: String?,
        time: String?,
        groupId: Int?,
    ) {
        this.firstname = firstname
        this.lastname = lastname
        this.fatherSName = fatherSName
        this.time = time
        this.groupId = groupId
    }

    constructor(
        id: Int?,
        firstname: String?,
        lastname: String?,
        fatherSName: String?,
        time: String?,
        groupId: Int?,
    ) {
        this.id = id
        this.firstname = firstname
        this.lastname = lastname
        this.fatherSName = fatherSName
        this.time = time
        this.groupId = groupId
    }


}