package com.example.compass.model

import android.location.Location

sealed class LocationResult {
    data class Success(val value: Location) : LocationResult()
    data class Failure(val message: String) : LocationResult()
}

