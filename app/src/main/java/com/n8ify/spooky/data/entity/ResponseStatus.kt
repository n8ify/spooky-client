package com.n8ify.spooky.model

import java.util.*

const val RESPONSE_SUCCESS = 1
const val RESPONSE_FAILED = -1
const val RESPONSE_FORCED_SUCCESS = 2

data class ResponseStatus(val status : Int, val message : String, val timestamp: Date = Date(System.currentTimeMillis()))