package com.heqichang.course.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.heqichang.course.model.CourseDetailWithItems
import com.heqichang.course.repository.CourseDetailRepo
import java.util.*

class EditDetailViewModel(application: Application): AndroidViewModel(application) {

    var detailId: Long = 0

    private var courseDetailRepo = CourseDetailRepo(getApplication())
    private var detail: LiveData<DetailViewModel>? = null


    fun update() {

    }

    private fun mapDetailModelToVM(model: CourseDetailWithItems): DetailViewModel {

        val date = Date(model.detail.recordTime)
        val cal = Calendar.getInstance()
        cal.time = date
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        var day = cal.get(Calendar.DATE)
        var dateString = "$year-$month-$day"

        var items = mutableListOf<DetailItemViewModel>()

        for (item in items) {
            items.add(DetailItemViewModel(item.itemId, item.type, item.note))
        }

        return DetailViewModel(dateString, items)

    }

    fun getDetail(): LiveData<DetailViewModel>? {
        if (detail == null) {
            detail = Transformations.map(courseDetailRepo.getDetailWithItems(detailId)) {
                it?.let { model ->
                    mapDetailModelToVM(model)
                }
            }
        }
        return detail
    }

    data class DetailViewModel(
        var dateString: String,
        var items: List<DetailItemViewModel>
    )

    data class DetailItemViewModel(
        var itemId: Long?,
        var type: Int,
        var note: String?
    )

}