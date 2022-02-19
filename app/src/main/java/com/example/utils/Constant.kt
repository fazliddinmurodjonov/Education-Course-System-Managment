package com.example.utils

object Constant {
    const val DB_NAME = "PDP_UZ"
    const val VERSION = 1

    const val COURSE_TABLE = "course"
    const val COURSE_ID = "id"
    const val COURSE_NAME = "name"
    const val COURSE_ABOUT = "about"

    const val MENTOR_TABLE = "mentor"
    const val MENTOR_ID = "id"
    const val MENTOR_FIRSTNAME = "firstname"
    const val MENTOR_LASTNAME = "lastname"
    const val MENTOR_FATHERS_NAME = "fathers_name"
    const val MENTOR_COURSE_ID = "course_id"
    const val MENTOR_GROUP_ID_LIST = "group_id"

    const val GROUP_TABLE = "groups"
    const val GROUP_ID = "id"
    const val GROUP_NAME = "name"
    const val GROUP_TIME = "time"
    const val GROUP_DAYS = "days"
    const val GROUP_LESSON = "lesson"
    const val GROUP_STUDENTS_COUNT = "students_count"
    const val GROUP_COURSE_ID = "course_id"
    const val GROUP_MENTOR_ID = "mentor_id"

    const val STUDENT_TABLE = "student"
    const val STUDENT_ID = "id"
    const val STUDENT_FIRSTNAME = "firstname"
    const val STUDENT_LASTNAME = "lastname"
    const val STUDENT_FATHERS_NAME = "fathers_name"
    const val STUDENT_JOIN_TIME = "time"
    const val STUDENT_GROUP_ID = "group_id"
}