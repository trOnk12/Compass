package com.example.compass

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainActivityViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.observeData()
        viewModel.accelerometerData.observe(this, Observer {
            Log.d("TEST", "x$it[")

            compass.accelerometerValues = it
        })

        viewModel.magnetometerData.observe(this, Observer {
            Log.d("TEST", "x$it[")

            compass.magnetoMeterValues = it
        })

    }

}
