package com.heqichang.course.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "course_detail")
data class CourseDetail(
    @PrimaryKey(autoGenerate = true) var id: Long? = null,
    @ColumnInfo(name = "course_id") var courseId: Long? = null,
    @ColumnInfo(name = "record_time") var recordTime: Long = 0 // 记录的时间戳

) {

}