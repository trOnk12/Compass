package com.example.compass.data.usecase

import com.example.compass.data.source.LocationServiceSource
import com.example.compass.model.AzimuthResult
import com.example.compass.model.LatLng
import com.example.compass.model.LocationResult

class GetLocationAzimuthUseCase(var locationServiceSource: LocationServiceSource) {

    fun getAzimuth(latitude: Double,longitude:Double): AzimuthResult {
        return locationServiceSource.getLocationAzimuth(LatLng(latitude,longitude))
    }

}
