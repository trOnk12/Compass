package com.example.compass.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.compass.data.usecase.GetCompassAzimuthUseCase
import com.example.compass.data.usecase.GetLocationAzimuthUseCase
import com.example.compass.model.Compass
import com.nhaarman.mockitokotlin2.mock
import org.junit.Before
import org.junit.Rule
import org.mockito.MockitoAnnotations

class MainActivityViewModelTest {

    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var mainActivityViewModel: MainActivityViewModel

    private val getCompassAzimuthUseCase: GetCompassAzimuthUseCase = mock()
    private val getLocationAzimuthUseCase: GetLocationAzimuthUseCase = mock()

    private val azimuthDegreeObserver: Observer<Compass> = mock()
    private val indicatorAzimuthObserver: Observer<Double> = mock()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        mainActivityViewModel = MainActivityViewModel(getCompassAzimuthUseCase, getLocationAzimuthUseCase)
        mainActivityViewModel.azimuthDegree.observeForever(azimuthDegreeObserver)
        mainActivityViewModel.indicatorAzimuth.observeForever(indicatorAzimuthObserver)
    }

//    @Test
//    fun testAzimuthDegreeGet() {
//
//        //Arrange
//        val result = Mockito.`when`(getCompassAzimuthUseCase.getAzimuth())
//            .thenReturn(Observable.just(Compass(32)))
//        //Act
//        mainActivityViewModel.startCompass()
//        //Assert
//        val captor = ArgumentCaptor.forClass(Compass::class.java)
//        captor.run {
//            verify(azimuthDegreeObserver, times(1)).onChanged(capture())
//            assertEquals(expectedCompass, value)
//        }
//    }
//
//    @Test
//    fun testIndicatorAzimuthGet() {
//        //Arrange
//        mainActivityViewModel.longitude.value = "32.00"
//        mainActivityViewModel.latitude.value = "45.00"
//
//        val latLng =
//            LatLng(mainActivityViewModel.longitude.value.toDouble(), mainActivityViewModel.latitude.value.toDouble())
//
//        Mockito.`when`(getLocationAzimuthUseCase.getAzimuth(mainActivityViewModel.longitude.value,))
//            .thenReturn(expectedAzimuth)
//        //Act
//        mainActivityViewModel.onNavigateButtonClicked()
//        //Assert
//        val captor = ArgumentCaptor.forClass(Double::class.java)
//        captor.run {
//            verify(indicatorAzimuthObserver, times(1)).onChanged(capture())
//            assertEquals(expectedAzimuth, value)
//        }
//    }
//

}

