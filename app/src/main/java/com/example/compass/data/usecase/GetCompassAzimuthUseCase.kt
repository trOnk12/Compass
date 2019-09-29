package com.example.compass.data.usecase

import com.example.compass.model.Compass
import com.example.compass.data.source.CompassServiceSource
import io.reactivex.Observable

class GetCompassAzimuthUseCase(var compassServiceSource: CompassServiceSource) {

    fun getAzimuth(): Observable<Compass> {
       return compassServiceSource.getCompass()
    }

}