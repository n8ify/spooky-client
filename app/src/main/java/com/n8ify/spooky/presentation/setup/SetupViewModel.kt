package com.n8ify.spooky.presentation.setup

import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.n8ify.spooky.data.entity.UseCaseResult
import com.n8ify.spooky.data.repository.SpotRepository
import com.n8ify.spooky.model.spot.Spot
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class SetupViewModel(val spotRepository: SpotRepository) : ViewModel(), CoroutineScope {

    val TAG = SetupViewModel::class.java.simpleName

    // Coroutine's background job
    private val job = Job()
    // Define default thread for Coroutine as Main and add job
    override val coroutineContext: CoroutineContext =  Dispatchers.Main.plus(job)

    val spots = MutableLiveData<List<Spot>>()

    fun loadSpots(){
        launch {
            val result = withContext(Dispatchers.IO){ spotRepository.getAllSpots() }
            when (result) {
                is UseCaseResult.Success -> spots.value = result.data
                is UseCaseResult.Failure -> Log.e(TAG, "Unexpected Error!", result.e)
            }
        }
    }

    fun deleteSpot(index : Int){
        val deleteSpot = spots.value?.let { it[index] }

    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}