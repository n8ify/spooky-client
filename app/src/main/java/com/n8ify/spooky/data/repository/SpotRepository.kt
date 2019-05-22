package com.n8ify.spooky.data.repository

import com.n8ify.spooky.data.api.SpotAPI
import com.n8ify.spooky.data.entity.UseCaseResult
import com.n8ify.spooky.model.spot.Spot
import java.lang.Exception

interface SpotRepository {
    suspend fun getAllSpots(): UseCaseResult<List<Spot>>
    suspend fun deleteSpot(spot: Spot): UseCaseResult<List<Spot>>
    suspend fun insertSpot(spot: Spot): UseCaseResult<List<Spot>>
    suspend fun updateSpot(spot: Spot): UseCaseResult<List<Spot>>
}

class SpotRepositoryImpl(private val spotAPI: SpotAPI) : SpotRepository {
    override suspend fun getAllSpots(): UseCaseResult<List<Spot>> {
        return try {
            val data = spotAPI.findAllAsync().await()
            UseCaseResult.Success(data.data)
        } catch (e: Exception) {
            UseCaseResult.Failure(e)
        }
    }

    override suspend fun deleteSpot(spot: Spot): UseCaseResult<List<Spot>> {
        return try {
            val data = spotAPI.deleteAsync(spot).await()
            UseCaseResult.Success(data.data)
        } catch (e : Exception) {
            UseCaseResult.Failure(e)
        }
    }

    override suspend fun insertSpot(spot: Spot): UseCaseResult<List<Spot>> {
        return try {
            val data = spotAPI.insertAsync(spot).await()
            UseCaseResult.Success(data.data)
        } catch (e : Exception) {
            UseCaseResult.Failure(e)
        }
    }

    override suspend fun updateSpot(spot: Spot): UseCaseResult<List<Spot>> {
        return try {
            val data = spotAPI.updateAsync(spot).await()
            UseCaseResult.Success(data.data)
        } catch (e : Exception) {
            UseCaseResult.Failure(e)
        }
    }
}