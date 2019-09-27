package com.example.compass

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainActivityViewModel(var sensorService: CompassService) : BaseDisposableViewModel() {

    private val _accelerometerData: MutableLiveData<FloatArray> = MutableLiveData()
    val accelerometerData: LiveData<FloatArray> = _accelerometerData

    private val _magnetometerData: MutableLiveData<FloatArray> = MutableLiveData()
    val magnetometerData: LiveData<FloatArray> = _magnetometerData

    fun observeData() {
        compositeDisposable.add(
            sensorService.getSensorDataBus()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe { sensorData ->
                    when (sensorData) {
                        is SensorData.AccelerometerData -> _accelerometerData.value = sensorData.sensorEvent.values
                        is SensorData.MagnetometerData -> _magnetometerData.value = sensorData.sensorEvent.values
                    }
                }
        )
    }

}