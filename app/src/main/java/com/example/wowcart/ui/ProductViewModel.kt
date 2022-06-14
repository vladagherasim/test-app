package com.example.wowcart.ui

import android.util.Log
import androidx.lifecycle.*
import com.example.wowcart.data.repos.ProductsRepository
import com.example.wowcart.utils.DataResult
import com.example.wowcart.utils.launchOn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

fun <T> createListedLiveData(): MutableLiveData<List<T>> {
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
    private val exceptionListener = MutableLiveData<Exception>()
    val exceptions = exceptionListener.toLiveData()

    init {
        getProducts()
        getFavorites()
    }

    private fun getProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            launchOn {
                repository.getProductsForFeed()
            }.subscribeOver(exceptionListener) {
                collectLatest {
                    _status.assignValue(it)
                }
            }
        }
    }

    fun insert(productID: Int) {
        viewModelScope.launch { repository.insert(productID) }
    }

    fun delete(productID: Int) {
        viewModelScope.launch { repository.deleteFavorite(productID) }
    }

    private fun getFavorites() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.getFavorites().collectLatest { list ->
                    val favoritesList = list.map {
                        Product(
                            it.id, it.image, it.name, it.details, it.price, true
                        )
                    }
                    DataResult.Success(favoritesList)
                }
            } catch (e: Exception) {
                DataResult.Failed(e)
            }
        }
    }
}