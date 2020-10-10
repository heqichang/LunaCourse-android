package com.heqichang.course.ui.view

import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.heqichang.course.R
import kotlinx.android.synthetic.main.detail_view_holder.view.*

class DetailRecyclerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    private val dateTextView = itemView.findViewById<TextView>(R.id.dateTextView)
    private val recordTypeTextView = itemView.findViewById<TextView>(R.id.recordTypeTextView)

    fun loadData(date: String, type: Int) {

        dateTextView.text = date

        when (type) {
            1 -> {
                recordTypeTextView.text = "正常"
                recordTypeTextView.setTextColor(Color.parseColor("#20604F"))
            }
            2 -> {
                recordTypeTextView.text = "请假"
                recordTypeTextView.setTextColor(Color.parseColor("#CB1B45"))
            }
            3 -> {
                recordTypeTextView.text = "补课"
                recordTypeTextView.setTextColor(Color.parseColor("#FFB11B"))
            }
        }

    }


}