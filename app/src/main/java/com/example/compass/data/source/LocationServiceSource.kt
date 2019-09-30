package com.example.compass.data.source

import android.location.Location
import com.example.compass.data.service.LocationService
import com.example.compass.model.LatLng
import com.example.compass.model.LocationResult
import com.example.compass.model.toAzimuth
import com.example.compass.utills.LocationDataExtractor
import io.reactivex.subjects.PublishSubject

class LocationServiceSource(
     locationService: LocationService
) :
    LocationService.OnLocationDataListener {
    init {
        locationService.onLocationDataListener = this
    }

    lateinit var locationResult: LocationResult

    override fun onLocationData(locationResult: LocationResult) {
        this.locationResult = locationResult
    }

    fun getLocationAzimuth(destinationLatLng: LatLng): LocationResult {
        return when (locationResult) {
            is LocationResult.Success -> locationResult.toAzimuth(
                destinationLatLng,
                (locationResult as LocationResult.Success).value
            )

            else -> locationResult
        }
    }


}