package com.example.compass.data.service

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import com.example.compass.model.SensorData
import kotlin.collections.HashMap

class CompassService(sensorManager: SensorManager, accelerometerSensor: Sensor, magnetometerSensor: Sensor) :
    SensorEventListener {
    init {
        sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL)
        sensorManager.registerListener(this, magnetometerSensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    lateinit var onCompassServiceDataListener: OnCompassServiceDataListener

    interface OnCompassServiceDataListener {
        fun onCompassServiceData(data: HashMap<SensorData.SensorDataType, SensorData>)
    }

    fun registerDataListener(listener: OnCompassServiceDataListener) {
        onCompassServiceDataListener = listener
    }

    var dataMap: HashMap<SensorData.SensorDataType, SensorData> = HashMap()

    override fun onSensorChanged(p0: SensorEvent?) {
        p0?.let { sensorEvent ->
            when (sensorEvent.sensor.type) {
                Sensor.TYPE_ACCELEROMETER -> prepareData(
                    SensorData.SensorDataType.ACCELEROMETER,
                    SensorData(sensorEvent)
                )
                Sensor.TYPE_MAGNETIC_FIELD -> prepareData(
                    SensorData.SensorDataType.MAGNETOMETER,
                    SensorData(sensorEvent)
                )
            }
        }
    }

    private fun prepareData(key: SensorData.SensorDataType, data: SensorData) {
        dataMap[key] = data

        if (dataMap[SensorData.SensorDataType.ACCELEROMETER] != null && dataMap[SensorData.SensorDataType.MAGNETOMETER] != null) {
            onCompassServiceDataListener.onCompassServiceData(dataMap)
        }

    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {}

}