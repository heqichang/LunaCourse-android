package com.heqichang.course.viewmodel

import android.app.Application
import android.graphics.Color
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
    private var records: LiveData<List<RecordViewModel>>? = null
    private var calendars: LiveData<List<com.haibin.calendarview.Calendar>>? = null

    fun addRecord(type: Int, recordTime: Long, note: String?) {
        courseDetailRepo.addCourseRecord(courseId, type, recordTime, note)
    }

    fun getRecords(): LiveData<List<RecordViewModel>>? {
        if (records == null) {
            mapDetailToCourseVM()
        }
        return records
    }

    fun getCalendars(): LiveData<List<com.haibin.calendarview.Calendar>>? {
        if (calendars == null) {
            mapDetailToCalendar()
        }
        return calendars
    }

    private fun mapDetailToCourseVM() {
        records = Transformations.map(courseDetailRepo.getCourseRecord(courseId)) { details ->

            val result: MutableList<RecordViewModel> = mutableListOf()
            for (detail in details) {
                val date = Date(detail.detail.recordTime)
                val cal = Calendar.getInstance()
                cal.time = date
                val year = cal.get(Calendar.YEAR)
                val month = cal.get(Calendar.MONTH)
                var day = cal.get(Calendar.DATE)

                for (item in detail.items) {
                    val recordViewModel = RecordViewModel(detail.detail.id, item.id, item.recordType, year, month, day, item.note)
                    result.add(recordViewModel)
                }
            }

            result.toList()
        }
    }

    private fun detailToCalendar(courseDetail: CourseDetailWithItems): com.haibin.calendarview.Calendar {

        val date = Date(courseDetail.detail.recordTime)
        val cal = Calendar.getInstance()
        cal.time = date
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        var day = cal.get(Calendar.DATE)

        val result = com.haibin.calendarview.Calendar()
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

    private fun mapDetailToCalendar() {
        calendars = Transformations.map(courseDetailRepo.getCourseRecord(courseId)) { details ->
            details.map { item ->
                detailToCalendar(item)
            }
        }
    }

    data class RecordViewModel(
        var detailId: Long?,
        var itemId: Long?,
        var type: Int,
        var year: Int,
        var month: Int,
        var day: Int,
        var note: String?
    )


}