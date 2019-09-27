package com.example.compass

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApp)
            androidLogger(Level.DEBUG)
            modules(listOf(sensorServiceModule))
        }
    }


}