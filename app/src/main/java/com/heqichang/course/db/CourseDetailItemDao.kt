package com.heqichang.course.db

import androidx.room.*
import com.heqichang.course.model.CourseDetail
import com.heqichang.course.model.CourseDetailItem

@Dao
interface CourseDetailItemDao {

    @Query("select * from course_detail_item where id = :id")
    fun getItem(id: Long): CourseDetailItem

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertItem(courseDetailItem: CourseDetailItem): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateItem(courseDetailItem: CourseDetailItem)

    @Delete
    fun deleteItem(courseDetailItem: CourseDetailItem)

}