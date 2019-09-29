package com.example.compass.ui

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

open class BaseDisposableViewModel : ViewModel() {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()

        compositeDisposable.clear()
    }

}