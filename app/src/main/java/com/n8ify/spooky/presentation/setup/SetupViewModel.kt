package com.n8ify.spooky.presentation.setup

import android.app.Application
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.*
import com.karumi.dexter.Dexter
import com.n8ify.spooky.Manifest
import com.n8ify.spooky.data.entity.UseCaseResult
import com.n8ify.spooky.data.repository.SpotRepository
import com.n8ify.spooky.model.spot.LatLng
import com.n8ify.spooky.model.spot.Spot
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

/**
 * Tag : LocationViewModel */
class SetupViewModel(val spotRepository: SpotRepository, application: Application) : AndroidViewModel(application),
    CoroutineScope {

    val TAG = SetupViewModel::class.java.simpleName

    // Coroutine's background job
    private val job = Job()
    // Define default thread for Coroutine as Main and add job
    override val coroutineContext: CoroutineContext = Dispatchers.Main.plus(job)

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
    val exception = MutableLiveData<Throwable>()
    val isLoading = MutableLiveData<Boolean>()
    val spots = MutableLiveData<List<Spot>>()
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

    fun loadSpots() {
        isLoading.value = true
        launch {
            when (val result = withContext(Dispatchers.IO) { spotRepository.getAllSpots() }) {
                is UseCaseResult.Success -> spots.value = result.data
                is UseCaseResult.Failure -> {
                    Log.e(TAG, "(On Load) Unexpected Error!", result.e)
                    exception.value = result.e
                }
            }
            isLoading.value = false
        }
    }

    fun deleteSpot(spot: Spot) {
        isLoading.value = true
        launch {
            when (val result = spotRepository.deleteSpot(spot)) {
                is UseCaseResult.Success -> spots.value = result.data
                is UseCaseResult.Failure -> {
                    Log.e(TAG, "(On Delete) Unexpected Error!", result.e)
                    exception.value = result.e
                }
            }
            isLoading.value = false
        }
    }

    fun insertSpot(spot: Spot) {
        isLoading.value = true
        launch {
            when (val result = spotRepository.insertSpot(spot)) {
                is UseCaseResult.Success -> spots.value = result.data
                is UseCaseResult.Failure -> {
                    Log.e(TAG, "(On Insert) Unexpected Error!", result.e)
                    exception.value = result.e
                }
            }
            isLoading.value = false
        }
    }

    fun updateSpot(spot: Spot) {
        isLoading.value = true
        launch {
            when (val result = spotRepository.updateSpot(spot)) {
                is UseCaseResult.Success -> spots.value = result.data
                is UseCaseResult.Failure -> {
                    Log.e(TAG, "(On Update) Unexpected Error!", result.e)
                    exception.value = result.e
                }
            }
            isLoading.value = false
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}