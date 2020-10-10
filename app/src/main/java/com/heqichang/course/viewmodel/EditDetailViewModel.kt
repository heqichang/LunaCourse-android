package com.heqichang.course.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.heqichang.course.model.CourseDetailWithItems
import com.heqichang.course.repository.CourseDetailRepo
import java.util.*

class EditDetailViewModel(application: Application): AndroidViewModel(application) {

    var detailId: Long = 0

    private var courseDetailRepo = CourseDetailRepo(getApplication())
    private var detail: LiveData<DetailViewModel>? = null

    var currentItemIndex = 0
    var currentItemType = MutableLiveData<Int>(0)
    var currentItemNote = MutableLiveData<String>("")


    fun update() {

        detail?.let {
            it.value?.let { detail ->
                for (item in detail.items) {

                    if (item.itemId == null) {
                        // 添加
                        courseDetailRepo.addCourseItem(detail.courseId ?: 0, detailId, item.type, item.note)
                    } else {
                        // 更新
                        courseDetailRepo.updateItem(detail.courseId ?: 0, item.itemId!!, item.type, item.note)
                    }
                }

            }
        }

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

        for (item in model.items) {
            items.add(DetailItemViewModel(item.id, item.recordType, item.note))
        }

        val firstItem = items.first()
        currentItemIndex = 0
        currentItemType.value = firstItem.type
        currentItemNote.value = firstItem.note

        return DetailViewModel(dateString, model.detail.courseId, items)

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

    fun selectIndex(index: Int) {
        currentItemIndex = index
        detail?.let { detail ->
            detail.value?.let {
                if (currentItemIndex < it.items.count()) {
                    val item = it.items[currentItemIndex]
                    currentItemType.value = item.type
                    currentItemNote.value = item.note
                }
            }
        }
    }

    fun addItem() {

        val newItem = DetailItemViewModel(null, 1, null)
        detail?.let {
            it.value?.let { model ->
                model.items.add(newItem)
            }
        }
    }

    fun checkType(type: Int) {

        detail?.let {
            it.value?.let { detailModel ->
                if (currentItemIndex >= detailModel.items.count()) {
                    return
                }
                val item = detailModel.items[currentItemIndex]
                if (item.type == type) {
                    return
                }

                item.type = type
                currentItemType.value = type
            }
        }
    }

    fun deleteItem() {

        detail?.let {
            it.value?.let { detailModel ->
                if (currentItemIndex >= detailModel.items.count()) {
                    return
                }
                val item = detailModel.items[currentItemIndex]
                item.itemId?.let { itemId ->
                    courseDetailRepo.deleteItem(itemId)
                }

            }
        }

    }

    data class DetailViewModel(
        var dateString: String,
        var courseId: Long?,
        var items: MutableList<DetailItemViewModel>
    )

    data class DetailItemViewModel(
        var itemId: Long?,
        var type: Int,
        var note: String?
    )

}