package com.heqichang.course.db

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.OnConflictStrategy.REPLACE
import com.heqichang.course.model.Course

@Dao
interface CourseDao {
    @Query("SELECT * FROM course")
    fun loadAll(): LiveData<List<Course>>

    @Query("SELECT * FROM course WHERE id = :courseId")
    fun loadCourse(courseId: Long): Course

    @Insert(onConflict = IGNORE)
    fun insertCourse(course: Course): Long

    @Update(onConflict = REPLACE)
    fun updateCourse(course: Course)

    @Delete
    fun deleteCourse(course: Course)
}
