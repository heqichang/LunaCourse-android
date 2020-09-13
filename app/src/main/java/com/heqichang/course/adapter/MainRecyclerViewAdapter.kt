package com.heqichang.course.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.heqichang.course.R
import com.heqichang.course.ui.view.MainCourseViewHolder
import com.heqichang.course.viewmodel.MainViewModel


class MainRecyclerViewAdapter(private val clickListener: MainRecyclerViewClickListener): RecyclerView.Adapter<MainCourseViewHolder>() {

    interface MainRecyclerViewClickListener {
        fun listItemClicked(index: Int)
    }

    private var courseList: MutableList<MainViewModel.CourseViewModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainCourseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.main_course_view_holder, parent, false)
        return MainCourseViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainCourseViewHolder, position: Int) {
        holder.refresh(courseList[position])
        holder.itemView.setOnClickListener {
            clickListener.listItemClicked(position)
        }
    }

    override fun getItemCount(): Int {
        return courseList.count()
    }

    fun reload(list: List<MainViewModel.CourseViewModel>) {
        courseList.clear()
        courseList.addAll(list)
        notifyDataSetChanged()
    }
}