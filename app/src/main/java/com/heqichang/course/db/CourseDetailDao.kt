package com.heqichang.course.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.heqichang.course.model.CourseDetail
import com.heqichang.course.model.CourseDetailWithItems

@Dao
interface CourseDetailDao {
    @Transaction
    @Query("SELECT * FROM course_detail where course_id = :courseId ORDER BY record_time")
    fun loadCourseAllRecord(courseId: Long): LiveData<List<CourseDetailWithItems>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertRecord(courseDetail: CourseDetail): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateRecord(courseDetail: CourseDetail)

    @Delete
    fun deleteRecord(courseDetail: CourseDetail)


}