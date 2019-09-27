package com.example.compass

import android.hardware.SensorEvent

sealed class SensorData {
    data class AccelerometerData(val sensorEvent: SensorEvent):SensorData()
    data class MagnetometerData(val sensorEvent: SensorEvent):SensorData()
}