package com.example.compass.model

import android.location.Location
import com.example.compass.utills.LocationDataExtractor

sealed class LocationResult {
    data class Success(val value: Location) : LocationResult()
    data class Failure(val message: PermissionStatusError) : LocationResult()
    data class Azimuth(val value: Double) : LocationResult()

    enum class PermissionStatusError { NOT_GRANTED }
}

fun toAzimuth(destinationLatLng: LatLng, currentLocation: Location): LocationResult.Azimuth {
    return LocationResult.Azimuth(
        LocationDataExtractor.calculateDestinationAzimuth(
            destinationLatLng,
            LatLng(currentLocation.latitude, currentLocation.longitude)
        )
    )
}
