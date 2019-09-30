package com.example.compass.data.source

import com.example.compass.data.service.LocationService
import com.example.compass.model.LatLng
import com.example.compass.model.LocationResult
import com.example.compass.model.toAzimuth

class LocationServiceSource(
    locationService: LocationService
) :
    LocationService.OnLocationDataListener {
    init {
        locationService.onLocationDataListener = this
        locationService.requestLocation()
    }

    lateinit var locationResult: LocationResult

    override fun onLocationData(locationResult: LocationResult) {
        this.locationResult = locationResult
    }

    fun getLocationAzimuth(destinationLatLng: LatLng): LocationResult {
        return when (locationResult) {
            is LocationResult.Success -> toAzimuth(
                destinationLatLng,
                (locationResult as LocationResult.Success).value
            )

            else -> locationResult
        }
    }


}