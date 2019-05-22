package com.n8ify.spooky.presentation._base

import android.app.Application
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.*
import com.n8ify.spooky.Manifest
import com.n8ify.spooky.model.spot.LatLng

abstract class AbstractLocationViewModel(application: Application) : AbstractBaseViewModel(application) {

    private val locationRequest: LocationRequest = LocationRequest().apply {
        interval = 2500L
        fastestInterval = 5000L
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }
    private val fusedLocationProviderClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(
            application
        )
    }
    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult?) {
            result?.lastLocation?.let {
                val resultLatLng = LatLng(it.latitude.toFloat(), it.longitude.toFloat())
                println(resultLatLng)
                latLng.value = resultLatLng
            }
        }
    }

    val latLng = MutableLiveData<LatLng>()

    init {
        if (ContextCompat.checkSelfPermission(
                application,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null)
        }
    }

}