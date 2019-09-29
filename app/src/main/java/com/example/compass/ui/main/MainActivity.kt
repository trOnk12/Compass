package com.example.compass.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.example.compass.R
import com.example.compass.model.LatLng
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainActivityViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.startCompass()
        viewModel.azimuthDegree.observe(this, Observer { it ->
            it?.let {
                compass.rotateCompass(it.toFloat())
            }
        })

        viewModel.drawDirectionIndicator(LatLng(-22.97, -43.18))
        viewModel.indicatorAzimuth.observe(this, Observer { it ->
            it?.let {
                compass.drawIndicator(it)
                compass.rotateNeedle(it.toFloat())
            }
        })

    }


}
