package com.heqichang.course.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.heqichang.course.model.Course
import com.heqichang.course.repository.CourseRepo

class MainViewModel(application: Application): AndroidViewModel(application) {

    private var courseRepo = CourseRepo(getApplication())
    private var courses: LiveData<List<CourseViewModel>>? = null

    fun addCourse(name: String, total: Int = 1) {
        val course = courseRepo.createCourse()
        course.name = name
        course.total = total
        courseRepo.addCourse(course)
    }

    fun getCourseViewModel() : LiveData<List<CourseViewModel>>? {
        if (courses == null) {
            mapCoursesToCourseVM()
        }
        return courses
    }

    fun getCourseId(index: Int): Long? {
        return courses?.value?.get(index)?.courseId
    }

    private fun courseToCourseVM(course: Course): MainViewModel.CourseViewModel {

        var intro: String = "总课时：${course.total}，已上：${course.normalUsed}，请假：${course.absence}，补课：${course.additionalUsed}"
        return CourseViewModel(courseId = course.id, name = course.name, intro = intro)
    }

    private fun mapCoursesToCourseVM() {
        courses = Transformations.map(courseRepo.allCourses) { repoCourses ->
            repoCourses.map { item ->
                courseToCourseVM(item)
            }
        }
    }

    data class CourseViewModel(
        var courseId: Long? = null,
        var name: String,
        var intro: String
    )

}