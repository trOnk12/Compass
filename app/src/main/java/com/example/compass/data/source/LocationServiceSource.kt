package com.example.compass.data.source

import android.location.Location
import com.example.compass.data.service.LocationService
import com.example.compass.model.AzimuthResult
import com.example.compass.entity.LatLng
import com.example.compass.model.LocationResult
import com.example.compass.model.locationAzimuth

class LocationServiceSource(
    var locationService: LocationService
) {
    init {
        locationService.requestLocation()
    }

    fun getLocationAzimuth(latLng: LatLng): AzimuthResult {
        return when (val locationResult = locationService.getLocationResult()) {
            is LocationResult.Location -> calculateAzimuth(latLng,locationResult.value)
            is LocationResult.Failure -> AzimuthResult.Failure(locationResult.message)
        }
    }

    private fun calculateAzimuth(destinationLatLng: LatLng, locationValue: Location): AzimuthResult {
        return locationValue.locationAzimuth(destinationLatLng)
    }

}