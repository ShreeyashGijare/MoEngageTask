package com.example.taskmoengage.widgets

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.taskmoengage.R


class CustomVerticalViewPager @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null
) :
    ViewPager(context!!, attrs) {

    private var itemsVisible = 3
    private var divisor: Float = 0.toFloat()

    init {
        init()
        initAttributes(context!!, attrs)
    }


    private fun initAttributes(context: Context, attrs: AttributeSet?) {
        if (attrs != null) {
            val array = context.obtainStyledAttributes(attrs, R.styleable.PickerViewPager)
            itemsVisible = array.getInteger(R.styleable.PickerViewPager_items_visible, itemsVisible)
            divisor = when (itemsVisible) {
                3 -> {
                    val threeValue = TypedValue()
                    resources.getValue(R.dimen.three_items, threeValue, true)
                    threeValue.float
                }
                else -> 3f
            }
            array.recycle()
        }
    }

    override fun canScrollHorizontally(direction: Int): Boolean {
        return false
    }

    override fun canScrollVertically(direction: Int): Boolean {
        return super.canScrollHorizontally(direction)
    }

    private fun init() {
        setPageTransformer(true, VerticalPageTransformer())
        overScrollMode = OVER_SCROLL_NEVER
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        val toIntercept = super.onInterceptTouchEvent(flipXY(ev))
        flipXY(ev)
        return toIntercept
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        val toHandle = super.onTouchEvent(flipXY(ev))
        flipXY(ev)
        return toHandle
    }

    private fun flipXY(ev: MotionEvent): MotionEvent {
        val width = width.toFloat()
        val height = height.toFloat()
        val x = ev.y / height * width
        val y = ev.x / width * height
        ev.setLocation(x, y)
        return ev
    }

    private class VerticalPageTransformer : PageTransformer {
        override fun transformPage(view: View, position: Float) {
            val pageWidth = view.width
            val pageHeight = view.height
            if (position < -1) {
                view.alpha = 0f
            } else if (position <= 1) {
                view.alpha = 1f
                view.translationX = pageWidth * -position
                val yPosition = position * pageHeight
                view.translationY = yPosition
            } else {
                view.alpha = 0f
            }
        }
    }

    override fun setAdapter(adapter: PagerAdapter?) {
        super.setAdapter(adapter)
        this.offscreenPageLimit = adapter!!.count
    }
}