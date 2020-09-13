package com.heqichang.course.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "course")
data class Course(
    @PrimaryKey(autoGenerate = true) var id: Long? = null,
    var name: String = "",
    var total: Int = 0, // 总课时
    @ColumnInfo(name = "normal_used") var normalUsed: Int = 0, // 正常上课
    @ColumnInfo(name = "additional_used") var additionalUsed: Int = 0, // 补课
    var absence: Int = 0, // 缺席
) {



}