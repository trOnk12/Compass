package com.example.compass

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import io.reactivex.subjects.PublishSubject
import java.util.*

class SensorService(sensorManager: SensorManager, sensor: Sensor) : SensorEventListener {
    init {
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    var subject: PublishSubject<FloatArray> = PublishSubject.create()

    fun getSensorData() : PublishSubject<FloatArray> {
        return subject
    }

    override fun onSensorChanged(p0: SensorEvent?) {
        p0?.let { sensorEvent ->
            subject.onNext(sensorEvent.values)
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {}

}