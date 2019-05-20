package com.n8ify.spooky.data.entity

sealed class UseCaseResult<out T : Any> {
    class Success<out T : Any>(val data : T) : UseCaseResult<T>()
    class Failure(val e : Throwable) : UseCaseResult<Nothing>()
}