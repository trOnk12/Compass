package com.example.compass.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.compass.BaseDisposableViewModel
import com.example.compass.model.Compass
import com.example.compass.usecase.GetAzimuthUseCase
import com.example.compass.usecase.GetLocationUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainActivityViewModel(
    var getAzimuthUseCase: GetAzimuthUseCase,
    var getLocationUseCase: GetLocationUseCase
) :
    BaseDisposableViewModel() {

    private val _azimuthDegree: MutableLiveData<Int> = MutableLiveData()
    val azimuthDegree: LiveData<Int> = _azimuthDegree

    private val _indicatorAzimuth: MutableLiveData<Double> = MutableLiveData()
    val indicatorAzimuth: LiveData<Double> = _indicatorAzimuth

    fun startCompass() {
        compositeDisposable.add(
            getAzimuthUseCase.getAzimuth()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe { data ->
                    handleData(data)
                }
        )
    }

    private fun handleData(data: Compass) {
        data.let {
            _azimuthDegree.value = data.azimuth
        }
    }

}