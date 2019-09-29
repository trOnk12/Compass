package com.example.compass.ui

import android.content.res.Resources

fun fromDpToPx(dp: Int): Int {
    val scale = Resources.getSystem().displayMetrics.density
    return (dp * scale + 0.5f).toInt()
}

fun formPxToDp(pixels: Int): Float {
    val scale = Resources.getSystem().displayMetrics.density
    return pixels / scale + 0.5f.toInt()
}

fun calculateCircumreference(width: Int, height: Int, radius: Int, degree: Double): Array<Double> {
    val radians = Math.toRadians(degree)

    val x = width + radius * Math.cos(radians)
    val y = height + radius * Math.sin(radians)

    return arrayOf(x, y)
}

