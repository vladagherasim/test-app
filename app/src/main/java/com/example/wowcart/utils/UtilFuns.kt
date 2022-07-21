package com.example.wowcart.utils

import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavOptions
import com.example.wowcart.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun <T> launchOn(call: suspend () -> T): DataResult<T> {
    return try {
        val result = call()
        DataResult.Success(result)
    } catch (ex: Exception) {
        DataResult.Failed(ex)
    }
}

fun <T> createListedLiveData(): MutableLiveData<List<T>> {
    return MutableLiveData()
}

fun <T> createLiveData(): MutableLiveData<T> {
    return MutableLiveData()
}

fun <T> MutableLiveData<T>.toLiveData(): LiveData<T> {
    return this
}

suspend fun <T> MutableLiveData<T>.assignValue(value: T) {
    withContext(Dispatchers.Main) {
        this@assignValue.value = value
    }
}

inline fun LifecycleCoroutineScope.safeLaunch(
    onError: (Exception) -> Unit,
    crossinline action: suspend () -> Unit
) {
    try {
        launchWhenStarted {
            action()
        }
    } catch (e: Exception) {
        onError(e)
    }
}

fun getNavOptions(): NavOptions {
    return NavOptions.Builder()
        .setEnterAnim(R.anim.slide_left)
        .setExitAnim(R.anim.wait)
        .setPopEnterAnim(R.anim.wait)
        .setPopExitAnim(R.anim.slide_right)
        .build()
}

