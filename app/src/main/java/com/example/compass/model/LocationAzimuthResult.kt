package com.example.compass.model

import android.location.Location

sealed class LocationAzimuthResult {
    data class Success(val value: Location) : LocationAzimuthResult()
    data class Failure(val message: String) : LocationAzimuthResult()
}

