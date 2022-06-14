package com.example.wowcart.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.wowcart.ui.assignValue

sealed class DataResult<out T> {
    data class Success<out T>(val data: T) : DataResult<T>()
    data class Failed(val exception: Exception) : DataResult<Nothing>()

    suspend fun onSuccess(action: suspend T.() -> Unit): DataResult<T> {
        if (this is Success) action(this.data)
        return this
    }

    suspend fun onFailure(action: suspend Exception.() -> Unit): DataResult<T> {
        if (this is Failed) action(exception)
        return this
    }

    suspend fun subscribeOver(
        exceptionsSubscriber: MutableLiveData<Exception>,
        onSuccess: suspend T.() -> Unit
    ) {
        when (this) {
            is Failed -> exceptionsSubscriber.assignValue(exception)
            is Success -> onSuccess(data)
        }
    }
}
