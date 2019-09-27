package com.example.compass

import android.content.Context
import android.content.Context.SENSOR_SERVICE
import android.hardware.Sensor
import android.hardware.SensorManager
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { MainActivityViewModel(get()) }

}

val sensorServiceModule = module {

    fun provideSensorManager(context: Context): SensorManager {
        return context.getSystemService(SENSOR_SERVICE) as SensorManager
    }

    fun provideAccelerometerSensor(sensorManager: SensorManager): Sensor {
        return sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    }

    fun provideMagnetometerSensor(sensorManager: SensorManager): Sensor {
        return sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
    }

    single { provideSensorManager(get()) }
    single(named("accelerometer")) { provideAccelerometerSensor(get()) }
    single(named("magnetometer")) { provideMagnetometerSensor(get()) }
    single { CompassService(get(), get(named("accelerometer")), get(named("magnetometer"))) }


}


