package com.example.compass.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.compass.R
import com.example.compass.databinding.ActivityMainBinding
import com.example.compass.model.LatLng
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainActivityViewModel by viewModel()

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.model = viewModel

        viewModel.startCompass()

        viewModel.azimuthDegree.observe(this, Observer {
            it?.let { azimuth ->
                compass.rotateCompass(azimuth)
            }
        })

        navigate_button.setOnClickListener {
            viewModel.getIndicatorAzimuth()
        }

        viewModel.indicatorAzimuth.observe(this, Observer {
           // compass.drawIndicator(it)
            compass.rotateNeedle(it.toFloat())
        })

        viewModel.requestPermission.observe(this, Observer {
            if (it) {
                Log.d("TEST", "request permission")
            }
        })

    }


}
