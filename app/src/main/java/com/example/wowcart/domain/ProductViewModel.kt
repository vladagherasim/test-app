package com.example.wowcart.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wowcart.data.ProductApi
import com.example.wowcart.ui.Product
import kotlinx.coroutines.launch

fun <T> createListedLiveData(): MutableLiveData<Result<List<T>>> {
    return MutableLiveData()
}

fun <T> MutableLiveData<T>.toLiveData(): LiveData<T> {
    return this
}

class ProductViewModel : ViewModel() {
    private val _status = createListedLiveData<Product>()
    val status  = _status.toLiveData()

    init {
        getData()
    }

    fun getData() {
        viewModelScope.launch {
            _status.value = try {
                val listResult = ProductApi.retrofitService.getData().results.map {
                    Product(
                     it.id, it.mainImage, it.name, it.details, it.price.toDouble(), false
                    )
                }
                Result.success(listResult)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}