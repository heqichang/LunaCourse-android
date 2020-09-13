package com.heqichang.course.model

import androidx.room.Embedded
import androidx.room.Relation


data class CourseDetailWithItems(
    @Embedded val detail: CourseDetail,
    @Relation(
        parentColumn = "id",
        entityColumn = "detail_id"
    )
    var items: List<CourseDetailItem>
) {
}