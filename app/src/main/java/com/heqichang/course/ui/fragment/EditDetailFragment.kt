package com.heqichang.course.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.heqichang.course.R
import com.heqichang.course.model.CourseDetailWithItems

private const val ARG_DETAIL = "detail"

/**
 * A simple [Fragment] subclass.
 * Use the [EditDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditDetailFragment : DialogFragment() {
//    private var param1: String? = null
//    private var param2: String? = null

    private var detail: CourseDetailWithItems? = null

    private lateinit var dateTextView: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            detail = it.getParcelable(ARG_DETAIL)
        }
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
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EditDetailFragment.
         */
        @JvmStatic
        fun newInstance() =
            EditDetailFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
            }
    }
}