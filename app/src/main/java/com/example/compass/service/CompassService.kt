package com.example.compass.service

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import com.example.compass.model.SensorData
import io.reactivex.subjects.PublishSubject
import kotlin.collections.HashMap

class CompassService(sensorManager: SensorManager, accelerometerSensor: Sensor, magnetometerSensor: Sensor) :
    SensorEventListener {
    init {
        sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_UI)
        sensorManager.registerListener(this, magnetometerSensor, SensorManager.SENSOR_DELAY_UI)
    }

    var dataBus: PublishSubject<HashMap<SensorData.SensorDataType, SensorData>> = PublishSubject.create()
    var dataMap: HashMap<SensorData.SensorDataType, SensorData> = HashMap()

    fun getSensorDataBus(): PublishSubject<HashMap<SensorData.SensorDataType, SensorData>> {
        return dataBus
    }

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
            dataBus.onNext(dataMap)
        }

    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {}

}