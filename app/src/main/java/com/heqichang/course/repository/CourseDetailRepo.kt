package com.heqichang.course.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.heqichang.course.db.CourseDatabase
import com.heqichang.course.model.CourseDetail
import com.heqichang.course.model.CourseDetailItem
import com.heqichang.course.model.CourseDetailWithItems

class CourseDetailRepo(context: Context) {

    private var db = CourseDatabase.getInstance(context)
    private var courseDetailDao = db.courseDetailDao()
    private var courseDao = db.courseDao()
    private var courseDetailItemDao = db.courseDetailItemDao()

    fun getCourseRecord(courseId: Long): LiveData<List<CourseDetailWithItems>> {
        return courseDetailDao.loadCourseAllRecord(courseId)
    }

    fun addCourseRecord(courseId: Long, type: Int, recordTime: Long, note: String?) {

        if (courseId.equals(0)) {
            return
        }

        val courseDetail = CourseDetail()
        courseDetail.courseId = courseId
        courseDetail.recordTime = recordTime

        val courseDetailItem = CourseDetailItem()
        courseDetailItem.courseId = courseId
        courseDetailItem.note = note
        courseDetailItem.recordType = type

        val course = courseDao.loadCourse(courseId)
        when(type) {
            1 -> course.normalUsed++
            2 -> course.absence++
            3 -> course.additionalUsed++
        }

        db.runInTransaction {
            courseDao.updateCourse(course)
            val detailId = courseDetailDao.insertRecord(courseDetail) ?: 0
            courseDetailItem.detailId = detailId
            courseDetailItemDao.insertItem(courseDetailItem)
        }
    }

    fun createRecord(): CourseDetail {
        return CourseDetail()
    }


}