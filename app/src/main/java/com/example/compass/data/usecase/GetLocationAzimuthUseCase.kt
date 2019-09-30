package com.example.compass.data.usecase

import com.example.compass.data.source.LocationServiceSource
import com.example.compass.model.AzimuthResult
import com.example.compass.entity.LatLng

class GetLocationAzimuthUseCase(var locationServiceSource: LocationServiceSource) {

    fun getAzimuth(latitude: Double,longitude:Double): AzimuthResult {
        return locationServiceSource.getLocationAzimuth(LatLng(latitude, longitude))
    }

}
