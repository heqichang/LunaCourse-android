package com.heqichang.course.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.heqichang.course.R
import com.heqichang.course.adapter.MainRecyclerViewAdapter
import com.heqichang.course.ui.fragment.EditDetailFragment
import com.heqichang.course.viewmodel.MainViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), MainRecyclerViewAdapter.MainRecyclerViewClickListener {

    private val viewModel by viewModels<MainViewModel>()
    private lateinit var listRecyclerView: RecyclerView
    private lateinit var adapter: MainRecyclerViewAdapter

    companion object {
        const val INTENT_COURSE_ID_KEY = "course_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        title = "课程"

        adapter = MainRecyclerViewAdapter(this)

        listRecyclerView = findViewById(R.id.recyclerView)
        listRecyclerView.layoutManager = LinearLayoutManager(this)
        listRecyclerView.adapter = adapter

        viewModel.getCourseViewModel()?.observe(this, Observer {
            adapter.reload(it)
        })

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val result = super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.main_menu, menu)
        return result
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.add_item) {

            val addView = layoutInflater.inflate(R.layout.edit_course_item, null, false)
            val nameEditText: EditText = addView.findViewById(R.id.edit_text_course_name)
            val totalEditText: EditText = addView.findViewById(R.id.edit_text_course_total)


            val builder = AlertDialog.Builder(this)
            builder.setView(addView)
            builder.setPositiveButton("确定") { dialog, _ ->

                val name = nameEditText.text
                val total = totalEditText.text

                GlobalScope.launch {
                    viewModel.addCourse(name.toString(), total.toString().toInt())
                }

            }

            builder.create().show()
        }

        return true
    }

    override fun listItemClicked(index: Int) {

        viewModel.getCourseId(index)?.let {
            var detailIntent = Intent(this, DetailActivity::class.java)
            detailIntent.putExtra(INTENT_COURSE_ID_KEY, it)
            startActivity(detailIntent)
        }

    }
}