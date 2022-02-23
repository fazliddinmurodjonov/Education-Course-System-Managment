package com.example.room.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.room.Dao.*
import com.example.room.Entity.*

@Database(entities = [Groups::class, Mentor::class, Student::class, Course::class], version = 1)
abstract class PdpDatabase : RoomDatabase() {
    abstract fun CourseDao(): CourseDao
    abstract fun GroupsDao(): GroupsDao
    abstract fun StudentDao(): StudentDao
    abstract fun MentorDao(): MentorDao
    abstract fun CourseWithGroupsDao(): CourseWithGroupsDao
    abstract fun CourseWithMentorsDao(): CourseWithMentorsDao
    abstract fun MentorWithGroupsDao(): MentorWithGroupsDao
    abstract fun StudentWithGroupDao(): StudentWithGroupDao

    companion object {
        private var instance: PdpDatabase? = null

        @Synchronized
        fun getInstance(context: Context): PdpDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(context, PdpDatabase::class.java, "Pdp_database")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }
            return instance!!
        }
    }
}