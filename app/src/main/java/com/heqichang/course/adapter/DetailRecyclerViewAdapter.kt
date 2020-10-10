package com.heqichang.course.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.heqichang.course.R
import com.heqichang.course.ui.view.DetailRecyclerViewHolder
import com.heqichang.course.viewmodel.DetailViewModel


class DetailRecyclerViewAdapter: RecyclerView.Adapter<DetailRecyclerViewHolder>() {

    private var itemList: MutableList<DetailViewModel.RecordViewModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailRecyclerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.detail_view_holder, parent, false)
        return DetailRecyclerViewHolder(view)
    }

    override fun onBindViewHolder(holder: DetailRecyclerViewHolder, position: Int) {
        val item = itemList[position]
        holder.loadData(item.dateString, item.type)
    }

    override fun getItemCount(): Int {
        return itemList.count()
    }

    fun reload(list: List<DetailViewModel.RecordViewModel>) {
        itemList.clear()
        itemList.addAll(list)
        notifyDataSetChanged()
    }

}