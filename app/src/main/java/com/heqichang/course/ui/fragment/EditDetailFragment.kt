package com.heqichang.course.ui.fragment

import android.app.AlertDialog
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.heqichang.course.R
import com.heqichang.course.databinding.FragmentEditDetailBinding
import com.heqichang.course.model.CourseDetailWithItems
import com.heqichang.course.ui.view.DialogUtil
import com.heqichang.course.ui.view.OnEditRecordSubmitListener
import com.heqichang.course.viewmodel.EditDetailViewModel
import kotlinx.android.synthetic.main.fragment_edit_detail.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

private const val ARG_DETAIL_ID = "detail_id"

/**
 * A simple [Fragment] subclass.
 * Use the [EditDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditDetailFragment : DialogFragment() {

    private var detailId: Long? = null


    private val viewModel by viewModels<EditDetailViewModel>()

    private lateinit var binding: FragmentEditDetailBinding
    private var signButtons = mutableListOf<Button>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

            detailId = it.getLong(ARG_DETAIL_ID)
            detailId?.let { dId ->
                viewModel.detailId = dId
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_detail, container, false)

        // 确认按钮点击
        binding.updateConfirmButton.setOnClickListener {

            GlobalScope.launch {
                viewModel.update()
            }

            this.dismiss()
        }

        // 删除按钮点击
        binding.deleteSignButton.setOnClickListener {
            AlertDialog.Builder(context).setPositiveButton("确认") { _, _ ->
                GlobalScope.launch {
                    viewModel.deleteItem()
                }
            }
                .setNegativeButton("取消", null)
                .setMessage("确认删除本次签到？")
                .show()

        }

        // checkbox 点击
        binding.normalCheckBox.setOnClickListener {
            viewModel.checkType(1)
        }

        binding.absenceCheckBox.setOnClickListener {
            viewModel.checkType(2)
        }

        binding.additionalCheckBox.setOnClickListener {
            viewModel.checkType(3)
        }

        viewModel.getDetail()?.observe(this, { detailViewModel ->

            if (detailViewModel == null) {
                dismiss()
            }

            detailViewModel?.let {
                binding.dateTextView.text = it.dateString

                val context = binding.root.context

                binding.scrollViewLayout.removeAllViews()
                signButtons.clear()

                for ((index, item) in it.items.withIndex()) {
                    val btn = Button(context)
                    btn.text = "第${index + 1}次签到"
                    btn.textSize = 16F
                    btn.setOnClickListener {clickButton ->
                        for ((clickIndex, clickItem) in signButtons.withIndex()) {
                            if (clickButton === clickItem) {
                                viewModel.selectIndex(clickIndex)
                                activeButton()
                                break
                            }
                        }
                    }
                    binding.scrollViewLayout.addView(btn)
                    signButtons.add(btn)
                }

                val btn = Button(context)
                btn.text = "+"
                btn.textSize = 16F
                btn.setOnClickListener {
                    viewModel.addItem()
                    addItemButton()
                    viewModel.selectIndex(signButtons.lastIndex)
                }
                binding.scrollViewLayout.addView(btn)
                activeButton()
            }

        })


        viewModel.currentItemType.observe(this, { currentItemType ->
            normalCheckBox.isChecked = false
            absenceCheckBox.isChecked = false
            additionalCheckBox.isChecked = false
            when (currentItemType) {
                1 -> normalCheckBox.isChecked = true
                2 -> absenceCheckBox.isChecked = true
                3 -> additionalCheckBox.isChecked = true
            }
        })

        viewModel.currentItemNote.observe(this, { note ->
            remarkEditText.text = Editable.Factory.getInstance().newEditable(note ?: "")
        })

        return binding.root
    }

    private fun activeButton() {
        if (viewModel.currentItemIndex < signButtons.count()) {
            signButtons.forEach {
                it.setTextColor(Color.BLACK)
            }
            signButtons[viewModel.currentItemIndex].setTextColor(Color.WHITE)
        }
    }

    private fun addItemButton() {
        signButtons.forEach {
            it.setTextColor(Color.BLACK)
        }
        val btn = Button(context)
        btn.text = "第${signButtons.count() + 1}次签到"
        btn.textSize = 16F
        btn.setOnClickListener {clickButton ->
            for ((clickIndex, clickItem) in signButtons.withIndex()) {
                if (clickButton === clickItem) {
                    viewModel.selectIndex(clickIndex)
                    activeButton()
                    break
                }
            }
        }
        btn.setTextColor(Color.WHITE)
        binding.scrollViewLayout.addView(btn, scrollViewLayout.childCount - 1)
        signButtons.add(btn)
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