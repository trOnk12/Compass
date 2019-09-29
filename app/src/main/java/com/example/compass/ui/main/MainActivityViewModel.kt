package com.example.compass.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.compass.BaseDisposableViewModel
import com.example.compass.utills.CompassDataExtractor
import com.example.compass.model.LatLng
import com.example.compass.model.SensorData
import com.example.compass.service.CompassService
import com.example.compass.service.LocationService
import com.example.compass.usecase.GetAzimuthUseCase
import com.example.compass.usecase.GetLocationUseCase
import com.example.compass.utills.LowPassFilterStrategy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainActivityViewModel(var getAzimuthUseCase: GetAzimuthUseCase, var getLocationUseCase: GetLocationUseCase, var compassService: CompassService, var locationService: LocationService) :
    BaseDisposableViewModel() {

    var compassDataExtractorModel: CompassDataExtractor = CompassDataExtractor(LowPassFilterStrategy())

    private val _azimuthDegree: MutableLiveData<Double> = MutableLiveData()
    val azimuthDegree: LiveData<Double> = _azimuthDegree

    private val _indicatorAzimuth: MutableLiveData<Double> = MutableLiveData()
    val indicatorAzimuth: LiveData<Double> = _indicatorAzimuth

    fun startCompass() {
        compositeDisposable.add(
            compassService.getSensorDataBus()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe { data ->
                    handleData(data)
                }
        )
    }

    fun drawDirectionIndicator(destinationLatLng: LatLng) {
        val currentLocation = LatLng(51.50, 0.00)
        val azimuthInDegrees = compassDataExtractorModel.calculateDestinationAzimuth(currentLocation, destinationLatLng)

        _indicatorAzimuth.value = azimuthInDegrees
    }

    private fun handleData(data: HashMap<SensorData.SensorDataType, SensorData>?) {
        data?.let {
            val accelerometerValues = it[SensorData.SensorDataType.ACCELEROMETER]
            val magnetometerValues = it[SensorData.SensorDataType.MAGNETOMETER]

            accelerometerValues?.let {
                magnetometerValues?.let {
                    _azimuthDegree.value = compassDataExtractorModel.calculateAzimuthInDegrees(
                        accelerometerValues.sensorEvent.values,
                        magnetometerValues.sensorEvent.values
                    )
                }
            }

        }
    }


}