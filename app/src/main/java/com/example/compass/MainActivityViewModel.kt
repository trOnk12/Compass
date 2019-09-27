package com.example.compass

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainActivityViewModel(var sensorService: SensorService) : BaseDisposableViewModel() {

    private val _sensorData: MutableLiveData<FloatArray> = MutableLiveData()
    val sensorData: LiveData<FloatArray> = _sensorData

    fun observeData() {
        compositeDisposable.add(
            sensorService.getSensorDataBus()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe { data ->
                    _sensorData.value = data
                }
        )
    }

}