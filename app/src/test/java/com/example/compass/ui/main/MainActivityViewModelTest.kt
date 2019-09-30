package com.example.compass.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.compass.RxImmediateSchedulerRule
import com.example.compass.data.usecase.GetCompassAzimuthUseCase
import com.example.compass.data.usecase.GetLocationAzimuthUseCase
import com.example.compass.entity.Compass
import com.example.compass.model.AzimuthResult
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.Observable
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.Scheduler.Worker
import io.reactivex.disposables.Disposable
import io.reactivex.Scheduler
import org.junit.BeforeClass
import java.util.concurrent.Executor


class MainActivityViewModelTest {

    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    var scheduler: RxImmediateSchedulerRule = RxImmediateSchedulerRule()

    private lateinit var mainActivityViewModel: MainActivityViewModel

    private val getCompassAzimuthUseCase: GetCompassAzimuthUseCase = mock()
    private val getLocationAzimuthUseCase: GetLocationAzimuthUseCase = mock()

    private val azimuthDegreeObserver: Observer<Double> = mock()
    private val indicatorAzimuthObserver: Observer<Double> = mock()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        mainActivityViewModel = MainActivityViewModel(getCompassAzimuthUseCase, getLocationAzimuthUseCase)
        mainActivityViewModel.azimuthDegree.observeForever(azimuthDegreeObserver)
        mainActivityViewModel.indicatorAzimuth.observeForever(indicatorAzimuthObserver)
    }

    @Test
    fun testAzimuthDegreeGet() {
        //Arrange
        val result = Mockito.`when`(getCompassAzimuthUseCase.getAzimuth())
            .thenReturn(Observable.just(Compass(32.00)))
        //Act
        mainActivityViewModel.startCompass()
        //Assert
        val captor = ArgumentCaptor.forClass(Double::class.java)
        captor.run {
            verify(azimuthDegreeObserver, times(1)).onChanged(capture())
            assertEquals(32.00, value)
        }
    }

    @Test
    fun testIndicatorAzimuthGet() {
        //Arrange
        val azimuthResult = AzimuthResult.Azimuth(45.00)

        Mockito.`when`(getLocationAzimuthUseCase.getAzimuth(ArgumentMatchers.anyDouble(), ArgumentMatchers.anyDouble()))
            .thenReturn(azimuthResult)
        //Act
        mainActivityViewModel.getLocationAzimuth(ArgumentMatchers.anyDouble(), ArgumentMatchers.anyDouble())
        //Assert
        val captor = ArgumentCaptor.forClass(Double::class.java)
        captor.run {
            verify(indicatorAzimuthObserver, times(1)).onChanged(capture())
            assertEquals(azimuthResult.value, value)
        }
    }


}

