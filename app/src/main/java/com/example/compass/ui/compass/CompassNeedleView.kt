package com.example.compass.ui.compass

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.FrameLayout
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.example.compass.R
import kotlinx.android.synthetic.main.compass_needle.view.*

class CompassNeedleView(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {
    init {
        View.inflate(context, R.layout.compass_needle, this)
    }

    private var currentDegree = 0f

    fun rotateNeedle(degree: Float) {

        val rotateAnimation =
            RotateAnimation(
                currentDegree,
                degree,
                Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF,
                0.5f
            ).apply {
                interpolator = FastOutSlowInInterpolator()
                duration = 2000
                fillAfter = true
            }

        currentDegree = degree
        needle.startAnimation(rotateAnimation)

    }

}