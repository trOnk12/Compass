package com.example.compass.utills

import com.example.compass.entity.LatLng
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

class LocationDataExtractor {
    companion object {
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
}