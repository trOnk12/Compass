package com.example.compass.ui.compass

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import com.example.compass.R
import com.example.compass.ui.calculateCircumreference
import com.example.compass.ui.fromDpToPx


class CompassPlateView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private var drawIndicator = false
    private var indicatorDegree: Double = 0.00

    private var displayWidthCenter: Int = 0
    private var displayHeightCenter: Int = 0

    private val indicatorRadius = 32f
    private val outerCompassRadius = 128
    private val innerCompassRadius = 96
    private val insideCompassRadius = 64
    private val plateDegreeCircleRadius = 32
    private val fontSize = 32f

    private val indicatorPaint = Paint().apply {
        color = Color.WHITE
        strokeWidth = 0.5f
    }

    private val textPaint = Paint().apply {
        color = Color.WHITE
        strokeWidth = 0.5f
        textSize = fontSize
    }

    private val outerCompassCircle: Drawable = resources.getDrawable(R.drawable.outer_compass_circle, null)
    private val innerCompassCircle: Drawable = resources.getDrawable(R.drawable.inner_compass_circle, null)
    private val insideCompassCircle: Drawable = resources.getDrawable(R.drawable.inside_compass_circle, null)

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        displayWidthCenter = width / 2
        displayHeightCenter = height / 2

        outerCompassCircle.setBoundsInCenter(outerCompassRadius)
        innerCompassCircle.setBoundsInCenter(innerCompassRadius)
        insideCompassCircle.setBoundsInCenter(insideCompassRadius)

        canvas?.let {
            outerCompassCircle.draw(canvas)
            drawDegreesText(canvas)
            drawPlateCircles(canvas)
            innerCompassCircle.draw(canvas)
            drawDirectionText(canvas)
            insideCompassCircle.draw(canvas)
            if (drawIndicator)
                drawIndicator(canvas)
        }

    }

    private fun drawTextAroundCircle(canvas: Canvas, textArray: Array<String>, circleRadius: Int) {
        val degreePerElement = 360.00 / 4

        for (i in 0 until textArray.size) {

            val pointCoordinates =
                calculateCircumReferenceCenter(
                    (circleRadius-fontSize/2).toInt(),
                    degreePerElement * i - 90
                )

            canvas.drawText(
                textArray[i],
                pointCoordinates[0].toFloat(),
                pointCoordinates[1].toFloat(),
                textPaint
            )
        }

    }

    private fun drawDegreesText(canvas: Canvas) {
        val degreeTextArray = arrayOf("0", "90", "180", "270")
        drawTextAroundCircle(canvas, degreeTextArray, outerCompassRadius)
    }

    private fun drawDirectionText(canvas: Canvas) {
        val directionTextArray = arrayOf("N", "E", "S", "W")
        drawTextAroundCircle(canvas, directionTextArray, innerCompassRadius)
    }

    private fun drawPlateCircles(canvas: Canvas) {
        val degreePerScale = 360.00 / 28

        for (i in 1..28) {
            if (i % 7 != 0) {
                val scaleComponents =
                    calculateCircumReferenceCenter(
                        outerCompassRadius - (plateDegreeCircleRadius / 2),
                        degreePerScale * i
                    )

                canvas.drawCircle(
                    scaleComponents[0].toFloat(),
                    scaleComponents[1].toFloat(),
                    12f,
                    indicatorPaint
                )
            }
        }

    }

    private fun calculateCircumReferenceCenter(
        radiusInDp: Int,
        degree: Double
    ): Array<Double> {
        return calculateCircumreference(
            displayWidthCenter, displayHeightCenter,
            fromDpToPx(radiusInDp),
            degree
        )
    }

    private fun drawIndicator(canvas: Canvas) {
        val indicatorComponents = calculateCircumReferenceCenter(
            (outerCompassRadius - indicatorRadius / 2).toInt(),
            indicatorDegree
        )

        canvas.drawCircle(
            indicatorComponents[0].toFloat(),
            indicatorComponents[1].toFloat(),
            32f,
            indicatorPaint
        )
    }

    fun drawLatLngIndicator(degree: Double) {
        drawIndicator = true
        indicatorDegree = degree - 90
    }

    private fun Drawable.setBoundsInCenter(radiusInDp: Int) {
        val radiusInPx = fromDpToPx(radiusInDp)

        val left = displayWidthCenter - radiusInPx
        val top = displayHeightCenter - radiusInPx
        val right = displayWidthCenter + radiusInPx
        val bottom = displayHeightCenter + radiusInPx

        setBounds(left, top, right, bottom)
    }

}