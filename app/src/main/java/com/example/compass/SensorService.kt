package com.example.compass

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager

class SensorService(
    var onSensorValueChangedListener: OnSensorValueChangedListener,
    sensorManager: SensorManager,
    sensor: Sensor
) : SensorEventListener {
    init {
        sensorManager.registerListener(this, sensor, 100)
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {}

    override fun onSensorChanged(p0: SensorEvent?) {
        p0?.let {
            onSensorValueChangedListener.onSensorValueChanged(p0.values)
        }
    }

    interface OnSensorValueChangedListener {
        fun onSensorValueChanged(sensorValues: FloatArray)
    }


}