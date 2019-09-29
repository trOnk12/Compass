package com.example.compass.service

import android.location.LocationManager

class LocationService(var locationManager: LocationManager) {

    fun getLastKnownLatLng(): Array<Double> {

        val longitude = 45.00
        val latitude = 23.00

        return arrayOf(longitude,latitude)
    }

}