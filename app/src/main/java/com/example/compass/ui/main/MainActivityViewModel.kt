package com.example.compass.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.compass.ui.BaseDisposableViewModel
import com.example.compass.data.usecase.GetCompassAzimuthUseCase
import com.example.compass.data.usecase.GetLocationAzimuthUseCase
import com.example.compass.model.AzimuthResult
import com.example.compass.model.Compass
import com.example.compass.model.LocationResult
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainActivityViewModel(
    var getCompassAzimuthUseCase: GetCompassAzimuthUseCase,
    var getLocationAzimuthUseCase: GetLocationAzimuthUseCase
) :
    BaseDisposableViewModel() {

    private val _azimuthDegree: MutableLiveData<Compass> = MutableLiveData()
    val azimuthDegree: LiveData<Compass> = _azimuthDegree

    private val _indicatorAzimuth: MutableLiveData<Double> = MutableLiveData()
    val indicatorAzimuth: LiveData<Double> = _indicatorAzimuth

    private val _requestPermission: MutableLiveData<LocationResult.PermissionStatusError> = MutableLiveData()
    val requestPermission: LiveData<LocationResult.PermissionStatusError> = _requestPermission

    val latitude: MutableLiveData<String> = MutableLiveData()
    val longitude: MutableLiveData<String> = MutableLiveData()

    fun startCompass() {
        compositeDisposable.add(
            getCompassAzimuthUseCase.getAzimuth()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe { data ->
                    _azimuthDegree.value = data
                })
    }

    fun onNavigateButtonClicked() {
        requestLocationAzimuth()
    }

    fun permissionGranted() {
        _requestPermission.value = LocationResult.PermissionStatusError.GRANTED
        requestLocationAzimuth()
    }

    private fun requestLocationAzimuth() {
        latitude.value?.let { latitude ->
            longitude.value?.let { longitude ->
                getLocationAzimuth(latitude.toDouble(), longitude.toDouble())
            }
        }
    }

    private fun getLocationAzimuth(latitude: Double, longitude: Double) {
        handleLocationAzimuthData(getLocationAzimuthUseCase.getAzimuth(latitude, longitude))
    }

    private fun handleLocationAzimuthData(azimuthResult: AzimuthResult) {
        when (azimuthResult) {
            is AzimuthResult.Azimuth -> _indicatorAzimuth.value = azimuthResult.value
            is AzimuthResult.Failure -> _requestPermission.value = azimuthResult.message
        }
    }



}