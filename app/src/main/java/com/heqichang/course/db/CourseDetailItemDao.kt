package com.heqichang.course.db

import androidx.room.*
import com.heqichang.course.model.CourseDetail
import com.heqichang.course.model.CourseDetailItem

@Dao
interface CourseDetailItemDao {

    @Query("SELECT * FROM course_detail_item WHERE id = :id")
    fun getItem(id: Long): CourseDetailItem

    @Query("SELECT COUNT(id) FROM course_detail_item WHERE detail_id = :detailId")
    fun getCount(detailId: Long): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertItem(courseDetailItem: CourseDetailItem): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateItem(courseDetailItem: CourseDetailItem)

    @Delete
    fun deleteItem(courseDetailItem: CourseDetailItem)

    @Query("DELETE FROM course_detail_item WHERE course_id = :courseId")
    fun deleteItemsByCourseId(courseId: Long)
}