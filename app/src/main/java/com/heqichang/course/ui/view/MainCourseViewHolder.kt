package com.heqichang.course.ui.view

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.heqichang.course.R
import com.heqichang.course.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.main_course_view_holder.view.*

class MainCourseViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    private val nameTextView = itemView.findViewById<TextView>(R.id.course_name_text_view)
    private val introTextView = itemView.findViewById<TextView>(R.id.course_intro_text_view)

    fun refresh(model: MainViewModel.CourseViewModel) {
        nameTextView.text = model.name
        introTextView.text = model.intro
    }

}