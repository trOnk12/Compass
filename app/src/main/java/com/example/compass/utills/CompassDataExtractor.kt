package com.example.compass.utills

import android.hardware.SensorManager
import com.example.compass.model.CompassPhysics
import com.example.compass.model.LatLng
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

class CompassDataExtractor {

    fun calculateAzimuthInDegrees(
        signalSmoothingStrategy: SignalSmoothingStrategy,
        compassPhysics: CompassPhysics
    ): Double {

        val rotationMatrix = FloatArray(9).apply {
            SensorManager.getRotationMatrix(
                this,
                null,
                compassPhysics.acceleratorMeterValues,
                compassPhysics.magnetoMeterValues
            )
        }

        val orientationValues = FloatArray(3).apply {
            SensorManager.getOrientation(rotationMatrix, this)
        }

        return signalSmoothingStrategy.compute(orientationValues[0])
    }

    // θ = atan2 [(sin Δλ ⋅ cos φ₂), (cos φ₁ ⋅ sin φ₂ − sin φ₁ ⋅ cos φ₂ ⋅ cos Δλ)]
    fun calculateDestinationAzimuth(currentLatLng: LatLng, destinationLatLng: LatLng): Double {
        val deltaLongitude = destinationLatLng.longitude - currentLatLng.longitude // Δλ

        val azimuthInRadians = atan2(
            sin(Math.toRadians(deltaLongitude)) * cos(Math.toRadians(destinationLatLng.latitude)),
            (cos(Math.toRadians(currentLatLng.latitude)) * sin(Math.toRadians(destinationLatLng.latitude)) - (sin(
                Math.toRadians(
                    currentLatLng.latitude
                )
            ) * cos(Math.toRadians(destinationLatLng.latitude)) * cos(
                Math.toRadians(deltaLongitude)
            )))
        )

        val azimuthInDegrees = Math.toDegrees(azimuthInRadians)

        return if (azimuthInDegrees < 0) {
            (360.00 + azimuthInDegrees)
        } else azimuthInDegrees
    }

}