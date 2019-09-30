package com.example.compass.data.source

import com.example.compass.entity.Compass
import com.example.compass.model.CompassPhysics
import com.example.compass.model.SensorData
import com.example.compass.data.service.CompassService
import com.example.compass.utills.LowPassFilterStrategy
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class CompassServiceSource(
    private var dataBus: PublishSubject<HashMap<SensorData.SensorDataType, SensorData>>,
    compassService: CompassService
) : CompassService.OnCompassServiceDataListener {
    init {
        compassService.registerDataListener(this)
    }

    override fun onCompassServiceData(data: HashMap<SensorData.SensorDataType, SensorData>) {
        dataBus.onNext(data)
    }

    fun getCompass(): Observable<Compass> = dataBus.map { getCompass(it) }

    private fun getCompass(data: HashMap<SensorData.SensorDataType, SensorData>): Compass {
        val compassPhysics = with(data) {
            get(SensorData.SensorDataType.ACCELEROMETER)?.sensorEvent?.values?.let { accelerateMeter ->
                get(SensorData.SensorDataType.MAGNETOMETER)?.sensorEvent?.values?.let { magnetoMeter ->
                    CompassPhysics(accelerateMeter, magnetoMeter)
                }
            }
        }

        val azimuth = compassPhysics?.calculateAzimuthInDegrees(LowPassFilterStrategy())?.toInt()

        return Compass(azimuth = azimuth)
    }

}