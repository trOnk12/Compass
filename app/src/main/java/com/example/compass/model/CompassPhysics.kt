package com.example.compass.model

import android.hardware.SensorManager
import com.example.compass.utills.SignalSmoothingStrategy

class CompassPhysics(var acceleratorMeterValues: FloatArray, var magnetoMeterValues: FloatArray) {

    fun calculateAzimuthInDegrees(signalSmoothingStrategy: SignalSmoothingStrategy): Double {

        val rotationMatrix = FloatArray(9).apply {
            SensorManager.getRotationMatrix(
                this,
                null,
                acceleratorMeterValues,
                magnetoMeterValues
            )
        }

        val orientationValues = FloatArray(3).apply {
            SensorManager.getOrientation(rotationMatrix, this)
        }

        return signalSmoothingStrategy.compute(orientationValues[0])
    }

}