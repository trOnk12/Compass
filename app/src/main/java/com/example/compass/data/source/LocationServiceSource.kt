package com.example.compass.data.source

import android.location.Location
import com.example.compass.data.service.LocationService
import com.example.compass.model.LatLng
import com.example.compass.model.LocationAzimuthResult
import com.example.compass.utills.LocationDataExtractor
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class LocationServiceSource(
    private var dataBus: PublishSubject<LocationAzimuthResult>,
    locationService: LocationService
) :
    LocationService.OnPermissionStatusListener, LocationService.OnLocationUpdateListener {
    init {
        locationService.onPermissionStatusListener = this
        locationService.onLocationUpdateListener = this

        locationService.requestLocation()
    }

    private lateinit var destinationLatLng: LatLng

    override fun onLocationUpdate(location: Location?) {
        location?.let {
            val locationAzimuth =
                LocationDataExtractor.calculateDestinationAzimuth(destinationLatLng, LatLng(it.latitude, it.longitude))
            dataBus.onNext(LocationAzimuthResult.Success(it))
        }
    }

    override fun onPermissionStatus() {
        dataBus.onNext(LocationAzimuthResult.Failure("TEST"))
    }

    fun getLocationAzimuthBus(): PublishSubject<LocationAzimuthResult> {
        return dataBus
    }

}