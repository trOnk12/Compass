package com.example.compass.model

sealed class AzimuthResult {
    data class Azimuth(val value: Double) : AzimuthResult()
    data class Failure(val message: LocationResult.PermissionStatusError) : AzimuthResult()
}