package com.example.compass.source

import android.annotation.SuppressLint
import com.example.compass.model.CompassPhysics
import com.example.compass.model.SensorData
import com.example.compass.service.CompassService
import io.reactivex.android.schedulers.AndroidSchedulers
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

    @SuppressLint("CheckResult")
    fun getCompass() {
//        val test = dataBus.map { dataMap ->
//            val acceleroMeterValues = dataMap[SensorData.SensorDataType.ACCELEROMETER]
//                ?.sensorEvent
//                ?.values
//
//            val magnetoMeterValues = dataMap[SensorData.SensorDataType.ACCELEROMETER]?.sensorEvent?.values
//
//            val compassPhysics = acceleroMeterValues?.let { magnetoMeterValues?.let { it1 -> CompassPhysics(it, it1) } }
//        }
//
    }

}