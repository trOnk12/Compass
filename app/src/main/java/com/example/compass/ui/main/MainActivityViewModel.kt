package com.example.compass.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.compass.ui.BaseDisposableViewModel
import com.example.compass.model.Compass
import com.example.compass.data.usecase.GetCompassAzimuthUseCase
import com.example.compass.data.usecase.GetLocationAzimuthUseCase
import com.example.compass.model.LatLng
import com.example.compass.model.LocationAzimuthResult
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainActivityViewModel(
    var getCompassAzimuthUseCase: GetCompassAzimuthUseCase,
    var getLocationAzimuthUseCase: GetLocationAzimuthUseCase
) :
    BaseDisposableViewModel() {

    private val _azimuthDegree: MutableLiveData<Int> = MutableLiveData()
    val azimuthDegree: LiveData<Int> = _azimuthDegree

    private val _indicatorAzimuth: MutableLiveData<Double> = MutableLiveData()
    val indicatorAzimuth: LiveData<Double> = _indicatorAzimuth

    private val _requestPermission: MutableLiveData<Boolean> = MutableLiveData()
    val requestPermission: LiveData<Boolean> = _requestPermission

    val latitude: MutableLiveData<String> = MutableLiveData()
    val longitude: MutableLiveData<String> = MutableLiveData()

    fun startCompass() {
        compositeDisposable.add(
            getCompassAzimuthUseCase.getAzimuth()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe { data ->
                    _azimuthDegree.value = data.azimuth
                }
        )
    }

    fun getIndicatorAzimuth() {
        val _latitude = latitude.value
        val _longitude = longitude.value

        _latitude?.let { latitude ->
            _longitude?.let { longitude ->
                getLocationAzimuthUseCase.getAzimuth(
                    LatLng(
                        latitude = latitude.toDouble(),
                        longitude = longitude.toDouble()
                    )
                )
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe { data ->
                        when (data) {
                            is LocationAzimuthResult.Success -> _indicatorAzimuth.value = 90.00
                            is LocationAzimuthResult.Failure -> _requestPermission.value = true
                        }
                    }
            }
        }

    }

}