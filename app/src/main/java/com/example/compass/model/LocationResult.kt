package com.example.compass.model

import android.location.Location
import com.example.compass.utills.LocationDataExtractor

sealed class LocationResult {
    data class Location(val value: android.location.Location) : LocationResult()
    data class Failure(val message: PermissionStatusError) : LocationResult()

    enum class PermissionStatusError {
        NOT_GRANTED,
        GRANTED
    }
}

fun Location.locationAzimuth(destinationLatLng: LatLng): AzimuthResult {
    return AzimuthResult.Azimuth(
        LocationDataExtractor.calculateDestinationAzimuth(
            destinationLatLng,
            LatLng(latitude, longitude)
        )
    )
}
