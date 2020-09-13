package com.heqichang.course.ui.view

import android.app.Activity
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.heqichang.course.R
import com.heqichang.course.db.CourseDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

interface OnEditRecordSubmitListener {
    fun recordSubmit(type: Int, note: String?)
}


class DialogUtil {


    companion object {
        private var instance: DialogUtil? = null

        fun getInstance(): DialogUtil {
            if (instance == null) {
                instance = DialogUtil()
            }

            return instance as DialogUtil
        }
    }


    fun showEditRecordDialog(activity: Activity, title: String, listener: OnEditRecordSubmitListener) {

        val addView = activity.layoutInflater.inflate(R.layout.edit_record, null, false)
        val dateTextView: TextView = addView.findViewById(R.id.dateTextView)

        val checkBoxes: List<CheckBox> = listOf(
            addView.findViewById(R.id.checkBox),
            addView.findViewById(R.id.checkBox2),
            addView.findViewById(R.id.checkBox3))

        val noteEditText: EditText = addView.findViewById(R.id.noteEditText)

        dateTextView.text = title

        checkBoxes[0].isChecked = true

        for (box in checkBoxes) {
            box.setOnCheckedChangeListener { buttonView, _ ->
                if (buttonView.isPressed) {
                    checkBoxes.forEach { it.isChecked = false }
                    (buttonView as CheckBox).isChecked = true
                }

            }
        }

        val builder = AlertDialog.Builder(activity)
        builder.setView(addView)
        builder.setPositiveButton("签到") { _, _ ->

            var checkSelected = 0
            for ((index, box) in checkBoxes.withIndex()) {
                if (box.isChecked) {
                    checkSelected = index + 1
                }
            }

            listener.recordSubmit(checkSelected, noteEditText.text.toString())
        }

        builder.create().show()
    }


}