package com.heqichang.course.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.heqichang.course.db.CourseDatabase
import com.heqichang.course.model.Course

class CourseRepo(context: Context) {

    private var db = CourseDatabase.getInstance(context)
    private var courseDao = db.courseDao()

    val allCourses: LiveData<List<Course>>
        get() {
            return courseDao.loadAll()
        }

    fun addCourse(course: Course): Long? {
        val newId = courseDao.insertCourse(course)
        return newId
    }

    fun getCourse(courseId: Long): Course {
        return courseDao.loadCourse(courseId)
    }

    fun updateCourse(course: Course) {
        courseDao.updateCourse(course)
    }

    fun createCourse(): Course {
        return Course()
    }


}