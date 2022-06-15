package com.example.wowcart.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wowcart.data.repos.ProductsRepository
import com.example.wowcart.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProductFeedViewModel @Inject constructor(private val repository: ProductsRepository) :
    ViewModel() {
    private val _status = createListedLiveData<Product>()
    val status = _status.toLiveData()
    private val exceptionListener = MutableLiveData<Exception>()
    val exceptions = exceptionListener.toLiveData()

    init {
        getProducts()
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
}