package com.heqichang.course.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.heqichang.course.R
import com.heqichang.course.ui.view.DetailRecyclerViewHolder
import com.heqichang.course.viewmodel.DetailViewModel


class DetailRecyclerViewAdapter: RecyclerView.Adapter<DetailRecyclerViewHolder>() {

    interface DetailRecyclerViewClickListener {
        fun listItemClicked(index: Int)
    }

    private var clickListener: DetailRecyclerViewClickListener? = null

    private var itemList: MutableList<DetailViewModel.RecordViewModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailRecyclerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.detail_view_holder, parent, false)
        return DetailRecyclerViewHolder(view)
    }

    override fun onBindViewHolder(holder: DetailRecyclerViewHolder, position: Int) {
        val item = itemList[position]
        val dateStr = "${position + 1}、${item.dateString}"
        holder.loadData(dateStr, item.type)
        holder.itemView.setOnClickListener {
            clickListener?.listItemClicked(position)
        }
    }

    override fun getItemCount(): Int {
        return itemList.count()
    }

    fun setOnItemClickListener(listener: DetailRecyclerViewClickListener) {
        clickListener = listener
    }

    fun reload(list: List<DetailViewModel.RecordViewModel>) {
        itemList.clear()
        itemList.addAll(list)
        notifyDataSetChanged()
    }

}