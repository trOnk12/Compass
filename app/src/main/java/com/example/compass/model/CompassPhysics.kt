package com.example.compass.model

import android.hardware.SensorManager
import com.example.compass.utills.SignalSmoothingStrategy

class CompassPhysics(var acceleratorMeterValues: FloatArray, var magnetoMeterValues: FloatArray) {

    fun calculateAzimuthInDegrees(signalSmoothingStrategy: SignalSmoothingStrategy): Double {

        var smoothedAcceleratorMeterValues = FloatArray(acceleratorMeterValues.size)
        smoothedAcceleratorMeterValues =
            signalSmoothingStrategy.compute(acceleratorMeterValues, smoothedAcceleratorMeterValues)


        var smoothedMagnetoMeterValues = FloatArray(magnetoMeterValues.size)
        smoothedMagnetoMeterValues =
            signalSmoothingStrategy.compute(magnetoMeterValues, smoothedMagnetoMeterValues)

        val rotationMatrix = FloatArray(9).apply {
            SensorManager.getRotationMatrix(
                this,
                null,
                smoothedAcceleratorMeterValues,
                smoothedMagnetoMeterValues
            )
        }

        val orientationValues = FloatArray(3).apply {
            SensorManager.getOrientation(rotationMatrix, this)
        }

        return (((orientationValues[0]*180)/Math.PI))
    }


}