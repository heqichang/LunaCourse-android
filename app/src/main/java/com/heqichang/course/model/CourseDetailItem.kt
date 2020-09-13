package com.heqichang.course.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "course_detail_item")
data class CourseDetailItem(
    @PrimaryKey(autoGenerate = true) var id: Long? = null,
    @ColumnInfo(name = "detail_id") var detailId: Long? = null,
    @ColumnInfo(name = "course_id") var courseId: Long? = null,
    @ColumnInfo(name = "record_type") var recordType: Int = 1, // 1 为 正常上课 2 为 请假 3 为 补课
    var note: String? = null // 说明
) {
}