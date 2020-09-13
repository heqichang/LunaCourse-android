package com.heqichang.course.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.heqichang.course.model.Course
import com.heqichang.course.model.CourseDetail
import com.heqichang.course.model.CourseDetailItem

@Database(entities = [Course::class, CourseDetail::class, CourseDetailItem::class], version = 1)
abstract class CourseDatabase : RoomDatabase() {

    abstract fun courseDao(): CourseDao
    abstract fun courseDetailDao(): CourseDetailDao
    abstract fun courseDetailItemDao(): CourseDetailItemDao

    companion object {

        private var instance: CourseDatabase? = null

        fun getInstance(context: Context): CourseDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(context.applicationContext, CourseDatabase::class.java, "Course").build()
            }

            return instance as CourseDatabase
        }

    }

}