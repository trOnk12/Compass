package com.example.compass.usecase

import com.example.compass.model.Compass
import com.example.compass.source.CompassServiceSource
import io.reactivex.Observable

class GetAzimuthUseCase(var compassServiceSource: CompassServiceSource) {

    fun getAzimuth(): Observable<Compass> {
       return compassServiceSource.getCompass()
    }

}