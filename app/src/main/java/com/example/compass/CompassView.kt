package com.example.compass

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.compass_view.view.*

class CompassView(context: Context,attrs:AttributeSet) : LinearLayout(context,attrs) {
    init {
        View.inflate(context, R.layout.compass_view, this)
    }

    var accelerometerValues : FloatArray? = null
    var magnetoMeterValues : FloatArray? = null

    var currentDegree = 0f

    fun rotateCompass(degree: Float) {

        val rotateAnimation = RotateAnimation(
            currentDegree,
            -degree,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        ).apply {
            duration = 2000
            fillAfter = true
        }

        compass_image.startAnimation(rotateAnimation)

        currentDegree = -degree
    }

}