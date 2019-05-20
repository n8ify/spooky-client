package com.n8ify.spooky.data.api

import com.n8ify.spooky.model.spot.Spot
import com.n8ify.spooky.model.spot.SpotResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.POST

interface SpotAPI {

    @POST("/spot/")
    fun getAllSpots() : Deferred<SpotResponse>

    @POST("/spot/delete")
    fun delete() : Deferred<SpotResponse>

    @POST("/spot/update")
    fun update() : Deferred<SpotResponse>

    @POST("/spot/insert")
    fun insert() : Deferred<SpotResponse>
}