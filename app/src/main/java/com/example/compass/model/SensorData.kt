package com.example.compass.model

import android.hardware.SensorEvent

data class SensorData(val sensorEvent: SensorEvent) {
    enum class SensorDataType { ACCELEROMETER, MAGNETOMETER }

    fun Pair<SensorData?,SensorData?>.let(action : (pair:Pair<SensorData?,SensorData?>) -> Unit){
        if(this.first != null && this.second != null){
            action
        }
    }
}
