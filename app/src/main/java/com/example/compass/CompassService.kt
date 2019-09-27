package com.example.compass

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import io.reactivex.subjects.PublishSubject
import java.util.*

class CompassService(sensorManager: SensorManager, accelerometerSensor: Sensor, magnetometerSensor: Sensor) :
    SensorEventListener {
    init {
        sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL)
        sensorManager.registerListener(this, magnetometerSensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    var dataBus: PublishSubject<SensorData> = PublishSubject.create()

    fun getSensorDataBus(): PublishSubject<SensorData> {
        return dataBus
    }

    override fun onSensorChanged(p0: SensorEvent?) {
        p0?.let { sensorEvent ->
            when (sensorEvent.sensor.type) {
                Sensor.TYPE_ACCELEROMETER -> dataBus.onNext(SensorData.AccelerometerData(sensorEvent))
                Sensor.TYPE_MAGNETIC_FIELD -> dataBus.onNext(SensorData.MagnetometerData(sensorEvent))
            }
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {}

}