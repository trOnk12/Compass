package com.example.compass.model

import android.hardware.SensorEvent

data class SensorData(val sensorEvent: SensorEvent) {
    enum class SensorDataType { ACCELEROMETER, MAGNETOMETER }
}
