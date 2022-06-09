package com.example.wowcart.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wowcart.data.repos.ProductsRepository
import com.example.wowcart.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

fun <T> createListedLiveData(): MutableLiveData<Result<List<T>>> {
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

@HiltViewModel
class ProductViewModel @Inject constructor(private val repository: ProductsRepository) :
    ViewModel() {
    private val _status = createListedLiveData<Product>()
    val status = _status.toLiveData()

    init {
        getProducts()
    }

    private fun getProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = try {
                val listResult = repository.getProducts().map {
                    Product(
                        it.id, it.mainImage, it.name, it.details, it.price.toDouble(), false
                    )
                }
                Result.Success(listResult)
            } catch (e: Exception) {
                Result.Failed(e)
            }
            _status.assignValue(result)
        }
    }
}