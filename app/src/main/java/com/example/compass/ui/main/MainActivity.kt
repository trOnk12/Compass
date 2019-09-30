package com.example.compass.ui.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.compass.BaseActivity
import com.example.compass.utills.PERMISSIONS_REQUEST_FINE_COARSE
import com.example.compass.R
import com.example.compass.databinding.ActivityMainBinding
import com.example.compass.model.PermissionStatusError
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity() {

    private val viewModel: MainActivityViewModel by viewModel()
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViewModel()

        viewModel.startCompass()
    }

    private fun initViewModel() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.model = viewModel

        viewModel.azimuthDegree.observe(this, Observer { azimuth ->
            azimuth?.let { compass_view.rotateCompass(it) }
        })

        viewModel.indicatorAzimuth.observe(
            this,
            Observer { azimuth -> azimuth?.let { compass_view.showAzimuthDirection(it) } })

        viewModel.requestPermission.observe(this, Observer { permissionStatusError ->
            permissionStatusError?.let {
                when (it) {
                    PermissionStatusError.NOT_GRANTED -> requestLocationPermission()
                    PermissionStatusError.GRANTED -> {
                        showToast(getString(R.string.permission_granted_information))
                    }
                }
            }
        })
    }

    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSIONS_REQUEST_FINE_COARSE
        )
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSIONS_REQUEST_FINE_COARSE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    viewModel.permissionGranted()
                } else {
                    showToast(getString(R.string.permision_not_granted_information))
                }
                return
            }
        }
    }
}


