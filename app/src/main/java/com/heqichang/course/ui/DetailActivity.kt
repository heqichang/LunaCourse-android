package com.heqichang.course.ui

import androidx.appcompat.app.AppCompatActivity
import com.heqichang.course.R
import android.os.Bundle
import android.text.Editable
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.haibin.calendarview.Calendar
import com.haibin.calendarview.CalendarView
import com.heqichang.course.adapter.DetailRecyclerViewAdapter
import com.heqichang.course.ui.fragment.EditDetailFragment
import com.heqichang.course.ui.view.DialogUtil
import com.heqichang.course.ui.view.OnEditRecordSubmitListener
import com.heqichang.course.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch


class DetailActivity : AppCompatActivity(), CalendarView.OnCalendarSelectListener, CalendarView.OnMonthChangeListener, DetailRecyclerViewAdapter.DetailRecyclerViewClickListener {

    private lateinit var calendarView: CalendarView
    private lateinit var listRecyclerView: RecyclerView
    private lateinit var adapter: DetailRecyclerViewAdapter

    private val viewModel by viewModels<DetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)


        val courseId = intent.getLongExtra(MainActivity.INTENT_COURSE_ID_KEY, 0)
        viewModel.courseId = courseId

        calendarView = findViewById(R.id.calendarView)
        calendarView.setOnCalendarSelectListener(this)
        calendarView.setOnMonthChangeListener(this)
        
        viewModel.getCalendars()?.observe(this, {
            calendarView.clearSchemeDate()
            val calendarMap: MutableMap<String, Calendar> = mutableMapOf()
            for ((index, calendar) in it.withIndex()) {
                calendarMap[calendar.toString()] = calendar
            }
            calendarView.setSchemeDate(calendarMap)
        })

        adapter = DetailRecyclerViewAdapter()
        adapter.setOnItemClickListener(this)

        listRecyclerView = findViewById(R.id.detailRecyclerView)
        listRecyclerView.layoutManager = LinearLayoutManager(this)
        listRecyclerView.adapter = adapter

        viewModel.getList()?.observe(this, {
            adapter.reload(it)
        })

        supportActionBar?.subtitle = "${calendarView.curYear}-${calendarView.curMonth}"
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val result = super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.detail_menu, menu)
        return result
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.menuDeleteCourse) {
            AlertDialog.Builder(this).setMessage("确定删除该课程信息吗？").setPositiveButton("确定") { _, _ ->

                GlobalScope.launch {
                    viewModel.deleteCourse()
                }

                finish()

            }.setNegativeButton("取消", null).show()

        } else if (item.itemId == R.id.menuEditCourse) {

            val weakThis = this

            GlobalScope.launch {
                val course = viewModel.getCourse()

                MainScope().launch {
                    val addView = layoutInflater.inflate(R.layout.edit_course_item, null, false)
                    val nameEditText: EditText = addView.findViewById(R.id.edit_text_course_name)
                    nameEditText.text = Editable.Factory.getInstance().newEditable(course.name)
                    val totalEditText: EditText = addView.findViewById(R.id.edit_text_course_total)
                    totalEditText.text = Editable.Factory.getInstance().newEditable(course.total.toString())

                    val builder = AlertDialog.Builder(weakThis)
                    builder.setView(addView)
                    builder.setPositiveButton("确定") { _, _ ->

                        course.name = nameEditText.text.toString()
                        course.total = totalEditText.text.toString().toInt()

                        GlobalScope.launch {
                            viewModel.updateCourse(course)
                        }
                    }

                    builder.create().show()
                }
            }
        }

        return true
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

    override fun onMonthChange(year: Int, month: Int) {
        supportActionBar?.subtitle = "$year-$month"
    }

    override fun listItemClicked(index: Int) {

        val dayList = viewModel.selectItem(index)
        if (dayList.count() > 0) {
            calendarView.scrollToCalendar(dayList[0], dayList[1], dayList[2])
        }
    }

}