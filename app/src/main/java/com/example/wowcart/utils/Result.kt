package com.example.wowcart.utils

import kotlin.Exception

sealed class Result <T> {
    data class Success <T> (val data: T)
    data class Failed (val exception: Exception)
}
