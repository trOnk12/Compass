package com.example.compass.data.usecase

import com.example.compass.data.source.LocationServiceSource
import com.example.compass.model.LatLng
import com.example.compass.model.LocationAzimuthResult
import io.reactivex.subjects.PublishSubject

class GetLocationAzimuthUseCase(var locationServiceSource: LocationServiceSource) {

    fun getAzimuth(latLng: LatLng): PublishSubject<LocationAzimuthResult> {
        return locationServiceSource.getLocationAzimuthBus()
    }

}
