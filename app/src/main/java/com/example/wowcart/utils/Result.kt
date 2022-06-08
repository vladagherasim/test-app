package com.example.wowcart.utils

sealed class Result <T> {
    data class Success <T> (val data: T)
    data class Failed (val exception: Exception)
}
