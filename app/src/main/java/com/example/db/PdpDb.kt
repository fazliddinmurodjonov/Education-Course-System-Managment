package com.example.db

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.room.entity.Course
import com.example.room.entity.Group
import com.example.room.entity.Mentor
import com.example.room.entity.Student
import com.example.utils.Constant

class PdpDb(context: Context) :
    SQLiteOpenHelper(context, Constant.DB_NAME, null, Constant.VERSION), DatabaseService {
    override fun onCreate(db: SQLiteDatabase?) {
        val courseQuery =
            "CREATE TABLE ${Constant.COURSE_TABLE} (${Constant.COURSE_ID} INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,${Constant.COURSE_NAME} TEXT NOT NULL, ${Constant.COURSE_ABOUT} TEXT NOT NULL)"
        val mentorQuery =
            "CREATE TABLE ${Constant.MENTOR_TABLE} (${Constant.MENTOR_ID} INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,${Constant.MENTOR_FIRSTNAME} TEXT NOT NULL,${Constant.MENTOR_LASTNAME} TEXT NOT NULL,${Constant.MENTOR_FATHERS_NAME} TEXT NOT NULL,${Constant.MENTOR_COURSE_ID} INTEGER NOT NULL,${Constant.MENTOR_GROUP_ID_LIST}  TEXT NOT NULL,FOREIGN KEY(${Constant.MENTOR_COURSE_ID}) REFERENCES ${Constant.COURSE_TABLE}(${Constant.COURSE_ID}))"
        val groupQuery =
            "CREATE TABLE ${Constant.GROUP_TABLE} (${Constant.GROUP_ID} INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,${Constant.GROUP_NAME} TEXT NOT NULL,${Constant.GROUP_TIME} TEXT NOT NULL,${Constant.GROUP_DAYS} TEXT NOT NULL,${Constant.GROUP_STUDENTS_COUNT} INTEGER NOT NULL,${Constant.GROUP_COURSE_ID} INTEGER NOT NULL,${Constant.GROUP_MENTOR_ID} INTEGER NOT NULL,${Constant.GROUP_LESSON} INTEGER NOT NULL,FOREIGN KEY(${Constant.GROUP_COURSE_ID}) REFERENCES ${Constant.COURSE_TABLE}(${Constant.COURSE_ID}), FOREIGN KEY (${Constant.GROUP_MENTOR_ID}) REFERENCES ${Constant.MENTOR_TABLE}(${Constant.MENTOR_ID}))"
        val studentQuery =
            "CREATE TABLE ${Constant.STUDENT_TABLE} (${Constant.STUDENT_ID} INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, ${Constant.STUDENT_FIRSTNAME} TEXT NOT NULL, ${Constant.STUDENT_LASTNAME} TEXT NOT NULL, ${Constant.STUDENT_FATHERS_NAME} TEXT NOT NULL,${Constant.STUDENT_JOIN_TIME} TEXT NOT NULL,${Constant.STUDENT_GROUP_ID} INTEGER NOT NULL,FOREIGN KEY(${Constant.STUDENT_GROUP_ID}) REFERENCES ${Constant.GROUP_TABLE}(${Constant.GROUP_ID}))"
        db?.execSQL(courseQuery)
        db?.execSQL(mentorQuery)
        db?.execSQL(groupQuery)
        db?.execSQL(studentQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    /// INSERT
    override fun insertCourse(course: Course) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(Constant.COURSE_NAME, course.name)
        contentValues.put(Constant.COURSE_ABOUT, course.about)
        database.insert(Constant.COURSE_TABLE, null, contentValues)
        database.close()
    }

    override fun insertMentor(mentor: Mentor) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(Constant.MENTOR_FIRSTNAME, mentor.firstname)
        contentValues.put(Constant.MENTOR_LASTNAME, mentor.lastname)
        contentValues.put(Constant.MENTOR_FATHERS_NAME, mentor.fatherSName)
        contentValues.put(Constant.MENTOR_COURSE_ID, mentor.courseId)
        contentValues.put(Constant.MENTOR_GROUP_ID_LIST, mentor.groupIdList)
        database.insert(Constant.MENTOR_TABLE, null, contentValues)
        database.close()
    }

    override fun insertGroup(group: Group) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(Constant.GROUP_NAME, group.name)
        contentValues.put(Constant.GROUP_TIME, group.time)
        contentValues.put(Constant.GROUP_DAYS, group.days)
        contentValues.put(Constant.GROUP_STUDENTS_COUNT, group.studentSCount)
        contentValues.put(Constant.GROUP_COURSE_ID, group.courseId)
        contentValues.put(Constant.GROUP_MENTOR_ID, group.mentorId)
        contentValues.put(Constant.GROUP_LESSON, group.lessonStart)
        database.insert(Constant.GROUP_TABLE, null, contentValues)
        database.close()
    }

    override fun insertStudent(student: Student) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(Constant.STUDENT_LASTNAME, student.lastname)
        contentValues.put(Constant.STUDENT_FIRSTNAME, student.firstname)
        contentValues.put(Constant.STUDENT_FATHERS_NAME, student.fatherSName)
        contentValues.put(Constant.STUDENT_JOIN_TIME, student.time)
        contentValues.put(Constant.STUDENT_GROUP_ID, student.groupId)
        database.insert(Constant.STUDENT_TABLE, null, contentValues)
        database.close()
    }

    /// GET BY ID
    @SuppressLint("Recycle")
    override fun getCourseById(id: Int): Course {
        val database = this.readableDatabase
        val cursor = database.query(Constant.COURSE_TABLE,
            arrayOf(Constant.COURSE_ID, Constant.COURSE_NAME, Constant.COURSE_ABOUT),
            "${Constant.COURSE_ID} = ?",
            arrayOf("$id"),
            null,
            null,
            null)
        cursor.moveToFirst()
        return Course(cursor.getInt(0), cursor.getString(1), cursor.getString(2))
    }

    override fun getMentorById(id: Int): Mentor {
        val database = this.readableDatabase
        var cursor = database.query(Constant.MENTOR_TABLE,
            arrayOf(Constant.MENTOR_ID,
                Constant.MENTOR_FIRSTNAME,
                Constant.MENTOR_LASTNAME,
                Constant.MENTOR_FATHERS_NAME,
                Constant.MENTOR_COURSE_ID,
                Constant.MENTOR_GROUP_ID_LIST),
            "${Constant.MENTOR_ID} = ?",
            arrayOf("$id"),
            null,
            null,
            null)
        cursor.moveToFirst()
        return Mentor(cursor.getInt(0),
            cursor.getString(1),
            cursor.getString(2),
            cursor.getString(3),
            cursor.getInt(4),
            cursor.getString(5))
    }

    override fun getGroupById(id: Int): Group {
        val database = this.readableDatabase
        val cursor =
            database.query(Constant.GROUP_TABLE, arrayOf(Constant.GROUP_ID,
                Constant.GROUP_NAME,
                Constant.GROUP_TIME,
                Constant.GROUP_DAYS,
                Constant.GROUP_STUDENTS_COUNT,
                Constant.GROUP_COURSE_ID,
                Constant.GROUP_MENTOR_ID,
                Constant.GROUP_LESSON
            ), "${Constant.GROUP_ID} = ?", arrayOf("$id"), null, null, null)
        cursor.moveToFirst()
        return Group(cursor.getInt(0),
            cursor.getString(1),
            cursor.getString(2),
            cursor.getString(3),
            cursor.getInt(4),
            cursor.getInt(5),
            cursor.getInt(6),
            cursor.getInt(7)
        )
    }

    override fun getStudentById(id: Int): Student {
        val database = this.readableDatabase
        var cursor = database.query(Constant.STUDENT_TABLE,
            arrayOf(Constant.STUDENT_ID,
                Constant.STUDENT_FIRSTNAME,
                Constant.STUDENT_LASTNAME,
                Constant.STUDENT_FATHERS_NAME,
                Constant.STUDENT_JOIN_TIME,
                Constant.STUDENT_GROUP_ID),
            "${Constant.STUDENT_ID} = ?",
            arrayOf("$id"),
            null,
            null,
            null)
        cursor.moveToFirst()
        return Student(cursor.getInt(0),
            cursor.getString(1),
            cursor.getString(2),
            cursor.getString(3),
            cursor.getString(4),
            cursor.getInt(5)
        )

    }


    /// GET ALL
    @SuppressLint("Recycle")
    override fun getAllCourse(): ArrayList<Course> {
        val courseList = ArrayList<Course>()
        val database = this.readableDatabase
        val query = "select * from ${Constant.COURSE_TABLE}"
        var cursor = database.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                val course = Course(cursor.getInt(0), cursor.getString(1), cursor.getString(2))
                courseList.add(course)

            } while (cursor.moveToNext())
        }
        return courseList
    }

    @SuppressLint("Recycle")
    override fun getAllMentor(courseId: Int): ArrayList<Mentor> {
        val mentorList = ArrayList<Mentor>()
        val query =
            "select *from ${Constant.MENTOR_TABLE} WHERE ${Constant.MENTOR_COURSE_ID} = $courseId"
        val database = this.readableDatabase
        val cursor = database.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                val mentor = Mentor(cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getInt(4),
                    cursor.getString(5))
                mentorList.add(mentor)
            } while (cursor.moveToNext())
        }
        return mentorList
    }

    @SuppressLint("Recycle")
    override fun getAllGroup(query: String): ArrayList<Group> {
        val groupList = ArrayList<Group>()
        val database = this.readableDatabase
        val cursor = database.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                val group = Group(cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getInt(4),
                    cursor.getInt(5),
                    cursor.getInt(6),
                    cursor.getInt(7)
                )
                groupList.add(group)
            } while (cursor.moveToNext())
        }
        return groupList
    }

    @SuppressLint("Recycle")
    override fun getAllStudent(query: String): ArrayList<Student> {
        val studentList = ArrayList<Student>()
        val database = this.readableDatabase
        val cursor = database.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                val student = Student(cursor.getInt(0), cursor.getString(1),
                    cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getInt(5))
                studentList.add(student)
            } while (cursor.moveToNext())
        }
        return studentList
    }

    /// UPDATE
    override fun updateMentor(mentor: Mentor) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(Constant.MENTOR_ID, mentor.id)
        contentValues.put(Constant.MENTOR_FIRSTNAME, mentor.firstname)
        contentValues.put(Constant.MENTOR_LASTNAME, mentor.lastname)
        contentValues.put(Constant.MENTOR_FATHERS_NAME, mentor.fatherSName)
        contentValues.put(Constant.MENTOR_COURSE_ID, mentor.courseId)
        contentValues.put(Constant.MENTOR_GROUP_ID_LIST, mentor.groupIdList)
        database.update(Constant.MENTOR_TABLE,
            contentValues,
            "${Constant.MENTOR_ID} = ?",
            arrayOf("${mentor.id}"))

    }

    override fun updateGroup(group: Group) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(Constant.GROUP_ID, group.id)
        contentValues.put(Constant.GROUP_NAME, group.name)
        contentValues.put(Constant.GROUP_TIME, group.time)
        contentValues.put(Constant.GROUP_DAYS, group.days)
        contentValues.put(Constant.GROUP_STUDENTS_COUNT, group.studentSCount)
        contentValues.put(Constant.GROUP_COURSE_ID, group.courseId)
        contentValues.put(Constant.GROUP_MENTOR_ID, group.mentorId)
        contentValues.put(Constant.GROUP_LESSON, group.lessonStart)
        database.update(Constant.GROUP_TABLE,
            contentValues,
            "${Constant.GROUP_ID} = ?",
            arrayOf("${group.id}"))
    }

    override fun updateStudent(student: Student) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        with(contentValues) {
            put(Constant.STUDENT_LASTNAME, student.lastname)
            put(Constant.STUDENT_FIRSTNAME, student.firstname)
            put(Constant.STUDENT_FATHERS_NAME, student.fatherSName)
            put(Constant.STUDENT_JOIN_TIME, student.time)
            put(Constant.STUDENT_GROUP_ID, student.groupId)
        }
        database.update(Constant.STUDENT_TABLE,
            contentValues,
            "${Constant.STUDENT_ID} = ?",
            arrayOf("${student.id}"))
    }

    /// DELETE
    override fun deleteMentor(mentor: Mentor) {
        val database = this.writableDatabase
        database.delete(Constant.MENTOR_TABLE,
            "${Constant.MENTOR_ID} = ? ",
            arrayOf("${mentor.id}"))
        database.close()
    }

    override fun deleteStudent(student: Student) {
        val database = this.writableDatabase
        database.delete(Constant.STUDENT_TABLE,
            "${Constant.STUDENT_ID} = ? ",
            arrayOf("${student.id}"))
        database.close()
    }

    override fun deleteGroup(group: Group) {
        val database = this.writableDatabase
        database.delete(Constant.GROUP_TABLE,
            "${Constant.GROUP_ID} = ? ",
            arrayOf("${group.id}"))
        database.close()
    }


}