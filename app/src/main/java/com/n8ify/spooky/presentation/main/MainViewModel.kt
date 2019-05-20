package com.n8ify.spooky.presentation.main

import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.n8ify.spooky.data.entity.UseCaseResult
import com.n8ify.spooky.data.repository.SpotRepository
import com.n8ify.spooky.model.spot.Spot
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainViewModel(val spotRepository: SpotRepository) : ViewModel(), CoroutineScope {

    val TAG = MainViewModel::class.java.simpleName

    // Coroutine's background job
    private val job = Job()
    // Define default thread for Coroutine as Main and add job
    override val coroutineContext: CoroutineContext =  Dispatchers.Main + job

    val spots = MutableLiveData<List<Spot>>().apply { this.value = mutableListOf() }

    fun loadSpots(){
        launch {
            val result = withContext(Dispatchers.IO){ spotRepository.getAllSpots() }
            val any = when (result) {
                is UseCaseResult.Success -> result.data.forEach { Log.d(TAG, it.toString()) }
                is UseCaseResult.Failure -> Log.e(TAG, "Unexpected Error!", result.e)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}