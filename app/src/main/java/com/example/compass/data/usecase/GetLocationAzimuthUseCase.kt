package com.example.compass.data.usecase

import com.example.compass.data.source.LocationServiceSource
import com.example.compass.model.LatLng
import com.example.compass.model.LocationResult
import io.reactivex.subjects.PublishSubject

class GetLocationAzimuthUseCase(var locationServiceSource: LocationServiceSource) {

    fun getAzimuth(destinationLatLng: LatLng): LocationResult {
        return locationServiceSource.getLocationAzimuth(destinationLatLng)
    }

}
