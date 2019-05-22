package com.n8ify.spooky.data.api

import com.n8ify.spooky.model.spot.Spot
import com.n8ify.spooky.model.spot.SpotResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.Body
import retrofit2.http.POST

interface SpotAPI {

    @POST("/spot/")
    fun findAllAsync() : Deferred<SpotResponse>

    @POST("/spot/delete")
    fun deleteAsync(@Body spot: Spot) : Deferred<SpotResponse>

    @POST("/spot/update")
    fun updateAsync(@Body spot: Spot) : Deferred<SpotResponse>

    @POST("/spot/insert")
    fun insertAsync(@Body spot: Spot) : Deferred<SpotResponse>
}