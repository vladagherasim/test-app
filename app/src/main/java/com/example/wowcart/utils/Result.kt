package com.example.wowcart.utils

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Failed(val exception: Exception) : Result<Nothing>()
}
