package com.n8ify.spooky.presentation._base

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.n8ify.spooky.data.entity.UseCaseResult
import com.n8ify.spooky.data.repository.SpotRepository
import com.n8ify.spooky.model.spot.Spot
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

abstract class AbstractSpotViewModel(val spotRepository: SpotRepository, application: Application) : AbstractLocationViewModel(application),
    CoroutineScope {

    init {
        TAG = AbstractSpotViewModel::class.java.simpleName
    }

    // Coroutine's background job
    private val job = Job()
    // Define default thread for Coroutine as Main and add job
    override val coroutineContext: CoroutineContext = Dispatchers.Main.plus(job)

    val spots = MutableLiveData<List<Spot>>()

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