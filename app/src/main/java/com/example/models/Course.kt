package com.example.models

class Course {
    var id: Int? = null
    var name: String? = null
    var about: String? = null

    constructor()

    constructor(name: String?, about: String?) {
        this.name = name
        this.about = about
    }

    constructor(id: Int?, name: String?, about: String?) {
        this.id = id
        this.name = name
        this.about = about
    }


}