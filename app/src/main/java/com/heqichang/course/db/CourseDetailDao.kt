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

    @Query("select * from course_detail where id = :id")
    fun loadCourseRecord(id: Long): LiveData<CourseDetailWithItems>

    @Query("select * from course_detail where id = :id")
    fun loadCourseDetail(id: Long): CourseDetail

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertRecord(courseDetail: CourseDetail): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateRecord(courseDetail: CourseDetail)

    @Delete
    fun deleteRecord(courseDetail: CourseDetail)

    @Query("DELETE FROM course_detail WHERE course_id = :courseId")
    fun deleteRecordsByCourseId(courseId: Long)

}