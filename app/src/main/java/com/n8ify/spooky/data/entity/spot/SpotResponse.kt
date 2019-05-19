package com.n8ify.spooky.model.spot

import com.n8ify.spooky.model.ResponseStatus

class SpotResponse {
    lateinit var status : ResponseStatus
    lateinit var data : List<Spot>
}