package com.example.compass

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), SensorService.OnSensorValueChangedListener {

    override fun onSensorValueChanged(sensorValues: FloatArray) {
        for (value in sensorValues) {
            Log.d("TEST", "value$value")
        }
    }

    lateinit var sensorService: SensorService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        sensorService =
            SensorService(onSensorValueChangedListener = this, sensorManager = sensorManager, sensor = sensor)

    }

}
