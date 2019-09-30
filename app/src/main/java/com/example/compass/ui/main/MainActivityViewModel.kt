package com.example.compass.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.compass.ui.BaseDisposableViewModel
import com.example.compass.data.usecase.GetCompassAzimuthUseCase
import com.example.compass.data.usecase.GetLocationAzimuthUseCase
import com.example.compass.model.AzimuthResult
import com.example.compass.entity.Compass
import com.example.compass.model.LocationResult
import com.example.compass.model.PermissionStatusError
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainActivityViewModel(
    var getCompassAzimuthUseCase: GetCompassAzimuthUseCase,
    var getLocationAzimuthUseCase: GetLocationAzimuthUseCase
) :
    BaseDisposableViewModel() {

    private val _azimuthDegree: MutableLiveData<Double> = MutableLiveData()
    val azimuthDegree: LiveData<Double> = _azimuthDegree

    private val _indicatorAzimuth: MutableLiveData<Double> = MutableLiveData()
    val indicatorAzimuth: LiveData<Double> = _indicatorAzimuth

    private val _requestPermission: MutableLiveData<PermissionStatusError> = MutableLiveData()
    val requestPermission: LiveData<PermissionStatusError> = _requestPermission

    val latitude: MutableLiveData<String> = MutableLiveData()
    val longitude: MutableLiveData<String> = MutableLiveData()

    fun startCompass() {
        compositeDisposable.add(
            getCompassAzimuthUseCase.getAzimuth()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe { data ->
                    _azimuthDegree.value = data.azimuth
                })
    }

    fun onNavigateButtonClicked() {
        latitude.value?.let { latitude ->
            longitude.value?.let { longitude ->
                getLocationAzimuth(latitude.toDouble(), longitude.toDouble())
            }
        }
    }

    fun permissionGranted() {
        _requestPermission.value = PermissionStatusError.GRANTED
    }

     fun getLocationAzimuth(latitude: Double, longitude: Double) {
        handleLocationAzimuthData(getLocationAzimuthUseCase.getAzimuth(latitude, longitude))
    }

    private fun handleLocationAzimuthData(azimuthResult: AzimuthResult) {
        when (azimuthResult) {
            is AzimuthResult.Azimuth -> _indicatorAzimuth.value = azimuthResult.value
            is AzimuthResult.Failure -> _requestPermission.value = azimuthResult.message
        }
    }



}