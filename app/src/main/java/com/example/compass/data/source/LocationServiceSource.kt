package com.example.compass.data.source

import android.location.Location
import com.example.compass.data.service.LocationService
import com.example.compass.model.LatLng
import com.example.compass.model.LocationResult
import com.example.compass.utills.LocationDataExtractor
import io.reactivex.subjects.PublishSubject

class LocationServiceSource(
    private var dataBus: PublishSubject<LocationResult>,
    var locationService: LocationService
) :
    LocationService.OnLocationDataListener {
    init {
        locationService.onLocationDataListener = this
    }

    lateinit var locationResult: LocationResult

    override fun onLocationData(locationResult: LocationResult) {

    }

    override fun onLocationUpdate(location: Location?) {
        location?.let {

        }
        location?.let {
            val locationAzimuth =
                LocationDataExtractor.calculateDestinationAzimuth(destinationLatLng, LatLng(it.latitude, it.longitude))
            dataBus.onNext(LocationResult.Success(locationAzimuth))
        }
    }

    override fun onPermissionStatus() {
        dataBus.onNext(LocationResult.Failure("TEST"))
    }

    fun getLocationAzimuth(destinationLatLng: LatLng): PublishSubject<LocationResult> {
        this.destinationLatLng = destinationLatLng


        return dataBus
    }

}