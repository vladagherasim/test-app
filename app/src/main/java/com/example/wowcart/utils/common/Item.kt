package com.example.wowcart.utils.common

interface Item {
    fun areItemsTheSame(other: Any): Boolean

    fun areContentsTheSame(other: Any): Boolean

    fun getChangePayload(other: Any): Any?
}