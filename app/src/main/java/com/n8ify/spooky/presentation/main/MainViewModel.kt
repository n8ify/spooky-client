package com.n8ify.spooky.presentation.main

import android.app.Application
import android.location.Location
import android.location.LocationManager
import com.n8ify.spooky.data.model.DetailedSpot
import com.n8ify.spooky.data.repository.SpotRepository
import com.n8ify.spooky.model.spot.Spot
import com.n8ify.spooky.presentation._base.AbstractSpotViewModel
import kotlinx.coroutines.CoroutineScope

class MainViewModel(spotRepository: SpotRepository, application: Application) :
    AbstractSpotViewModel(spotRepository, application), CoroutineScope {

    init {
        TAG = MainViewModel::class.java.simpleName
    }

    fun getNearestSpot(): DetailedSpot? {
        var nearestSpot: Spot? = null
        var nearestDistance = -1F
        spots.value?.forEach { spot ->
            latLng.value?.let {
                val spotLocation = Location(LocationManager.NETWORK_PROVIDER).apply {
                    latitude = spot.latitude.toDouble()
                    longitude = spot.longitude.toDouble()
                }

                val currentLocation = Location(LocationManager.NETWORK_PROVIDER).apply {
                    latitude = it.latitude.toDouble()
                    longitude = it.longitude.toDouble()
                }
                if (nearestSpot != null) {
                    val measuredDistance = currentLocation.distanceTo(spotLocation)
                    if(measuredDistance < nearestDistance || nearestDistance == -1F){
                        nearestSpot = spot
                        nearestDistance = measuredDistance
                    }
                } else {
                    nearestSpot = spot
                }
            }
        }
        nearestSpot?.createTimestamp = null
        return DetailedSpot(nearestSpot, nearestDistance)
    }

}