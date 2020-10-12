package com.heqichang.course.viewmodel

import android.app.Application
import android.graphics.Color
import android.os.Parcel
import android.os.Parcelable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.haibin.calendarview.CalendarView
import com.heqichang.course.model.Course
import com.heqichang.course.model.CourseDetail
import com.heqichang.course.model.CourseDetailWithItems
import com.heqichang.course.repository.CourseDetailRepo
import com.heqichang.course.repository.CourseRepo
import java.util.*

class DetailViewModel(application: Application): AndroidViewModel(application) {

    var courseId: Long = 0
    private var courseDetailRepo = CourseDetailRepo(getApplication())
    private var courseRepo = CourseRepo(getApplication())

    private var itemList: LiveData<List<RecordViewModel>>? = null
    private var calendars: LiveData<List<com.haibin.calendarview.Calendar>>? = null

    fun addRecord(type: Int, recordTime: Long, note: String?) {
        courseDetailRepo.addCourseRecord(courseId, type, recordTime, note)
    }

    fun getList(): LiveData<List<RecordViewModel>>? {
        if (itemList == null) {
            mapDetailToVM()
        }
        return itemList
    }


    fun getCalendars(): LiveData<List<com.haibin.calendarview.Calendar>>? {
        if (calendars == null) {
            mapDetailToCalendar()
        }
        return calendars
    }

    fun deleteCourse() {
        courseDetailRepo.deleteCourse(courseId)
    }

    fun getCourse(): Course {
        return courseRepo.getCourse(courseId)
    }

    fun updateCourse(course: Course) {
        courseRepo.updateCourse(course)
    }

    fun selectItem(index: Int): List<Int> {

        itemList?.value?.let {
            if (index >= it.count()) {
                return listOf()
            }

            val item = it[index]
            return listOf(item.year, item.month, item.day)
        }

        return listOf()
    }

    private fun detailToCalendar(courseDetail: CourseDetailWithItems): com.haibin.calendarview.Calendar {

        val date = Date(courseDetail.detail.recordTime)
        val cal = Calendar.getInstance()
        cal.time = date
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        var day = cal.get(Calendar.DATE)

        val result = com.haibin.calendarview.Calendar()
        result.scheme = courseDetail.detail.id.toString() // trick：用来保存 id
        result.year = year
        result.month = month + 1
        result.day = day

        val schemeList = mutableListOf<com.haibin.calendarview.Calendar.Scheme>()
        for (item in courseDetail.items) {
            val scheme = com.haibin.calendarview.Calendar.Scheme()
            when (item.recordType) {
                1 -> {
                    scheme.shcemeColor = 0xff20604F.toInt()
                    scheme.scheme = "课"
                }
                2 -> {
                    scheme.shcemeColor = 0xffCB1B45.toInt()
                    scheme.scheme = "假"
                }
                3 -> {
                    scheme.shcemeColor = 0xffFFB11B.toInt()
                    scheme.scheme = "补"
                }
            }
            schemeList.add(scheme)
        }

        result.schemes = schemeList

        return result
    }

    private fun detailToVM(courseDetailWithItems: CourseDetailWithItems): List<RecordViewModel> {
        val date = Date(courseDetailWithItems.detail.recordTime)
        val cal = Calendar.getInstance()
        cal.time = date
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH) + 1
        var day = cal.get(Calendar.DATE)

        var result = mutableListOf<RecordViewModel>()

        for (item in courseDetailWithItems.items) {
            val vm = RecordViewModel(item.recordType, year, month, day, "$year-$month-$day")
            result.add(vm)
        }

        return result
    }

    private fun mapDetailToCalendar() {
        calendars = Transformations.map(courseDetailRepo.getCourseAllRecord(courseId)) { details ->
            details.map { item ->
                detailToCalendar(item)
            }
        }
    }

    private fun mapDetailToVM() {
        itemList = Transformations.map(courseDetailRepo.getCourseAllRecord(courseId)) { details ->
            details.flatMap { item ->
                detailToVM(item)
            }
        }
    }

    data class RecordViewModel (
        var type: Int,
        var year: Int,
        var month: Int,
        var day: Int,
        var dateString: String,
    )


}