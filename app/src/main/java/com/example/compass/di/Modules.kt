package com.example.compass.di

import android.content.Context
import android.content.Context.SENSOR_SERVICE
import android.hardware.Sensor
import android.hardware.SensorManager
import android.location.LocationManager
import com.example.compass.model.SensorData
import com.example.compass.data.service.CompassService
import com.example.compass.data.service.LocationService
import com.example.compass.data.source.CompassServiceSource
import com.example.compass.data.source.LocationServiceSource
import com.example.compass.ui.main.MainActivityViewModel
import com.example.compass.data.usecase.GetCompassAzimuthUseCase
import com.example.compass.data.usecase.GetLocationAzimuthUseCase
import io.reactivex.subjects.PublishSubject
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainActivityViewModel(get(), get()) }
}

val useCaseModule = module {
    single { GetLocationAzimuthUseCase(get()) }
    single { GetCompassAzimuthUseCase(get()) }
}

val sourcesModule = module {

    fun providePublishSubject(): PublishSubject<HashMap<SensorData.SensorDataType, SensorData>> {
        return PublishSubject.create()
    }

    single { providePublishSubject() }
    single { CompassServiceSource(get(), get()) }
    single { LocationServiceSource(get(), get()) }

}

val locationServiceModule = module {
    fun provideLocationManager(context: Context): LocationManager {
        return context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    single { provideLocationManager(get()) }
    single { LocationService(get(),get()) }
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
    single {
        CompassService(
            get(),
            get(named("accelerometer")),
            get(named("magnetometer"))
        )
    }


}


