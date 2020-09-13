package com.heqichang.course.db

import androidx.room.*
import com.heqichang.course.model.CourseDetail
import com.heqichang.course.model.CourseDetailItem

@Dao
interface CourseDetailItemDao {


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertItem(courseDetailItem: CourseDetailItem): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateItem(courseDetailItem: CourseDetailItem)

    @Delete
    fun deleteItem(courseDetailItem: CourseDetailItem)

}