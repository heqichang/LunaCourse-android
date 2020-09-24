package com.heqichang.course.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.heqichang.course.R
import com.heqichang.course.model.CourseDetailWithItems
import com.heqichang.course.viewmodel.EditDetailViewModel

private const val ARG_DETAIL_ID = "detail_id"

/**
 * A simple [Fragment] subclass.
 * Use the [EditDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditDetailFragment : DialogFragment() {

    private var detailId: Long? = null


    private val viewModel by viewModels<EditDetailViewModel>()

    private lateinit var dateTextView: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

            detailId = it.getLong(ARG_DETAIL_ID)
            detailId?.let { dId ->
                viewModel.detailId = dId
            }
        }

        viewModel.getDetail()?.observe(this, {
            dateTextView.text = it?.dateString
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val editView = inflater.inflate(R.layout.fragment_edit_detail, container, false)
        dateTextView = editView.findViewById(R.id.textView2)



        return editView
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param detailId 详情id
         * @return A new instance of fragment EditDetailFragment.
         */
        @JvmStatic
        fun newInstance(detailId: Long) =
            EditDetailFragment().apply {
                arguments = Bundle().apply {
                    putLong(ARG_DETAIL_ID, detailId)
                }
            }
    }
}