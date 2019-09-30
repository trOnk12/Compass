package com.example.compass.data.service

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.compass.model.LocationResult

class LocationService(var context: Context, var locationManager: LocationManager) : LocationListener {

    interface OnLocationDataListener {
        fun onLocationData(locationResult: LocationResult)
    }

    lateinit var onLocationDataListener: OnLocationDataListener

    fun requestLocation() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            onLocationDataListener.onLocationData(LocationResult.Failure(LocationResult.PermissionStatusError.NOT_GRANTED))
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 10f, this)
        }
    }

    override fun onLocationChanged(p0: Location?) {
        p0?.let {
            onLocationDataListener.onLocationData(LocationResult.Success(it))
        }

    }

    override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {

    }

    override fun onProviderEnabled(p0: String?) {

    }

    override fun onProviderDisabled(p0: String?) {

    }

}