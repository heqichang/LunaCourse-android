package com.heqichang.course.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import com.haibin.calendarview.Calendar
import com.haibin.calendarview.MonthView

class MonthView(context: Context): MonthView(context) {

    private val mDayBackgroundPaint = Paint()

    init {
        mDayBackgroundPaint.color = 0xff91989F.toInt()
        mDayBackgroundPaint.style = Paint.Style.FILL

        mCurMonthTextPaint.color = 0xff91989F.toInt()
        mCurDayTextPaint.color = 0xff787878.toInt()
    }

    override fun onDrawSelected(canvas: Canvas?, calendar: Calendar?, x: Int, y: Int, hasScheme: Boolean): Boolean {

        return true
    }

    override fun onDrawScheme(canvas: Canvas?, calendar: Calendar?, x: Int, y: Int) {

        calendar?.let {

            var cx = x + 20
            var cy = y + 20

            canvas?.drawRect(x.toFloat() + 2, y.toFloat() + 2, (x + mItemWidth).toFloat() - 2, (y + mItemHeight).toFloat() - 2, mDayBackgroundPaint)

            for (scheme in it.schemes) {

                mSchemePaint.color = scheme.shcemeColor

                canvas?.drawRect(cx.toFloat(), cy.toFloat(), (cx + 20).toFloat(), (cy + 20).toFloat(), mSchemePaint)

                cx += 40
                if (cx - x > mItemWidth - 40) {
                    cx = x + 20
                    cy += 40
                }
            }
        }


    }

    override fun onDrawText(canvas: Canvas?, calendar: Calendar?, x: Int, y: Int, hasScheme: Boolean, isSelected: Boolean) {
        // back #FFB11B
        // text #91989F
        // red #91989F
        // green #20604F
        // yellow #FFB11B

        calendar?.let {
            val cx = x + mItemWidth - 40
            val cy = y + mItemHeight - 20

            if (!it.hasScheme()) {
                canvas?.drawRect(x.toFloat() + 2, y.toFloat() + 2, (x + mItemWidth).toFloat() - 2, (y + mItemHeight).toFloat() - 2, mDayBackgroundPaint)
            }

            if (it.isCurrentDay) {
                canvas?.drawRect(x.toFloat(), y.toFloat(), (x + mItemWidth).toFloat(), (y + mItemHeight).toFloat(), mCurDayTextPaint)
            }

            canvas?.drawText(it.day.toString(), cx.toFloat(), cy.toFloat(), mCurMonthTextPaint)
        }


    }


    private fun dip2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5).toInt()
    }

}