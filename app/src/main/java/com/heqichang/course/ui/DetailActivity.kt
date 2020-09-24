package com.heqichang.course.ui

import androidx.appcompat.app.AppCompatActivity
import com.heqichang.course.R
import android.os.Bundle
import androidx.activity.viewModels
import com.haibin.calendarview.Calendar
import com.haibin.calendarview.CalendarView
import com.heqichang.course.ui.fragment.EditDetailFragment
import com.heqichang.course.ui.view.DialogUtil
import com.heqichang.course.ui.view.OnEditRecordSubmitListener
import com.heqichang.course.viewmodel.DetailViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*


class DetailActivity : AppCompatActivity(), CalendarView.OnCalendarSelectListener {

    private lateinit var calendarView: CalendarView
    private val viewModel by viewModels<DetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val courseId = intent.getLongExtra(MainActivity.INTENT_COURSE_ID_KEY, 0)
        viewModel.courseId = courseId

        calendarView = findViewById(R.id.calendarView)
        calendarView.setOnCalendarSelectListener(this)

        
        viewModel.getCalendars()?.observe(this, {
            calendarView.clearSchemeDate()
            val calendarMap: MutableMap<String, Calendar> = mutableMapOf()
            for ((index, calendar) in it.withIndex()) {
                calendarMap[calendar.toString()] = calendar
            }
            calendarView.setSchemeDate(calendarMap)
        })

    }

    override fun onCalendarOutOfRange(calendar: Calendar?) {
        print("out")
    }

    override fun onCalendarSelect(calendar: Calendar?, isClick: Boolean) {

        if (isClick) {
            calendar?.let {

                val title = "${it.year}-${it.month}-${it.day}"
                if (it.hasScheme()) {

                    it.scheme.toLong().let { detailId ->

                        val fragment = EditDetailFragment.newInstance(detailId)
                        fragment.show(supportFragmentManager, "dialog")
                    }
                } else {
                    DialogUtil.getInstance().showEditRecordDialog(this, title, object: OnEditRecordSubmitListener {
                        override fun recordSubmit(type: Int, note: String?) {
                            GlobalScope.launch {
                                viewModel.addRecord(type, it.timeInMillis, note)
                            }
                        }
                    })
                }

            }
        }

    }
}