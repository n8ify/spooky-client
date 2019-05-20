package com.n8ify.spooky.data.repository

import com.n8ify.spooky.data.api.SpotAPI
import com.n8ify.spooky.data.entity.UseCaseResult
import com.n8ify.spooky.model.spot.Spot
import java.lang.Exception

interface SpotRepository {
    suspend fun getAllSpots(): UseCaseResult<List<Spot>>
}

class SpotRepositoryImpl(private val spotAPI: SpotAPI) : SpotRepository {
    override suspend fun getAllSpots(): UseCaseResult<List<Spot>> {
        return try {
            val data = spotAPI.getAllSpots().await().data
            UseCaseResult.Success(data)
        } catch (e: Exception) {
            UseCaseResult.Failure(e)
        }
    }
}