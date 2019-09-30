package com.example.compass.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.compass.ui.BaseDisposableViewModel
import com.example.compass.data.usecase.GetCompassAzimuthUseCase
import com.example.compass.data.usecase.GetLocationAzimuthUseCase
import com.example.compass.model.LatLng
import com.example.compass.model.LocationResult
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

    val _latitude: MutableLiveData<String> = MutableLiveData()
    val latitude: LiveData<String> = _latitude
    val _longitude: MutableLiveData<String> = MutableLiveData()
    val longitude: LiveData<String> = _longitude

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
        latitude.value?.let { latitude ->
            longitude.value?.let { longitude ->
                when (val data =
                    getLocationAzimuthUseCase.getAzimuth(LatLng(latitude.toDouble(), longitude.toDouble()))) {
                    is LocationResult.Azimuth -> _indicatorAzimuth.value = data.value
                    is LocationResult.Failure -> _requestPermission.value = true
                }
            }
        }
    }

}