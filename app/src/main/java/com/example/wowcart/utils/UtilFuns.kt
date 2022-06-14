package com.example.wowcart.utils


suspend fun <T> launchOn(call: suspend () -> T): DataResult<T> {
    return try {
        val result = call()
        DataResult.Success(result)
    } catch (ex: Exception) {
        DataResult.Failed(ex)
    }
}