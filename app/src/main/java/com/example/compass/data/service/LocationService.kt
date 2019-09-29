package com.example.compass.data.service

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.core.content.ContextCompat

class LocationService(var context: Context, var locationManager: LocationManager) : LocationListener {

    interface OnPermissionStatusListener {
        fun onPermissionStatus()
    }

    interface OnLocationUpdateListener {
        fun onLocationUpdate(location: Location?)
    }

    lateinit var onPermissionStatusListener: OnPermissionStatusListener
    lateinit var onLocationUpdateListener: OnLocationUpdateListener



    fun requestLocation(){
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            onPermissionStatusListener.onPermissionStatus()
        }
        else{
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10f, this)
        }
    }


    override fun onLocationChanged(p0: Location?) {
        onLocationUpdateListener.onLocationUpdate(p0)
    }

    override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {

    }

    override fun onProviderEnabled(p0: String?) {

    }

    override fun onProviderDisabled(p0: String?) {

    }

    fun getLastKnownLatLng(): Array<Double> {

        val longitude = 45.00
        val latitude = 23.00

        return arrayOf(longitude, latitude)
    }

}