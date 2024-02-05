package com.example.taskmoengage.widgets

import android.view.View
import androidx.viewpager.widget.ViewPager
import kotlin.math.abs

class ViewPagerPageTransformer : ViewPager.PageTransformer {

    private val MIN_SCALE = 0.75f

    override fun transformPage(page: View, position: Float) {
        /*val pageWidth: Int = view.width
        if (position < -1) {
            view.alpha = 0F
        } else if (position <= 0) {
            view.alpha = 1F
            view.translationY = 0F
            view.scaleX = 1F
            view.scaleY = 1F
        } else if (position <= 1) {
            view.alpha = 1 - position

            view.translationY = pageWidth * -position
            val scaleFactor = (MIN_SCALE
                    + (1 - MIN_SCALE) * (1 - abs(position)))
            view.scaleX = scaleFactor
            view.scaleY = scaleFactor
        } else {
            view.alpha = 0F
        }*/

        page.translationX = -position * page.width
        page.cameraDistance = 999999999F

        if (position < 0.5 && position > -0.5) {
            page.visibility = View.VISIBLE
        } else {
            page.visibility = View.INVISIBLE
        }
        if (position < -1) {
            page.alpha = 0F
        } else if (position <= 0) {
            page.alpha = 1F
            page.rotationX = 180 * (1 - abs(position) + 1)
        } else if (position <= 1) {
            page.alpha = 1F
            page.rotationX = -180 * (1 - abs(position) + 1)
        } else {
            page.alpha = 0F
        }
    }
}