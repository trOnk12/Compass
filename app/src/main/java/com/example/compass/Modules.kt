package com.example.compass

import android.content.Context
import android.content.Context.SENSOR_SERVICE
import android.hardware.Sensor
import android.hardware.SensorManager
import org.koin.dsl.module

val sensorServiceModule = module {

    fun provideSensorManager(context: Context): SensorManager {
        return context.getSystemService(SENSOR_SERVICE) as SensorManager
    }

    fun provideAccelerometerSensor(sensorManager: SensorManager): Sensor {
        return sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    }

    single { provideSensorManager(get()) }
    single { provideAccelerometerSensor(get()) }
    single { SensorService(get(), get()) }

}
