package com.example.wowcart.ui.feed

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.wowcart.data.repos.ProductsRepository
import com.example.wowcart.utils.toLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProductFeedViewModel @Inject constructor(private val repository: ProductsRepository) :
    ViewModel() {

    private val exceptionListener = MutableLiveData<Exception>()
    val exceptions = exceptionListener.toLiveData()

    init {
        getProducts()
    }

    fun getProducts(): Flow<PagingData<ItemProduct>> {
        return repository.getProductsForFeed(viewModelScope)
    }

    fun insert(productID: Int) {
        viewModelScope.launch { repository.insert(productID) }
    }

    fun delete(productID: Int) {
        viewModelScope.launch { repository.deleteFavorite(productID) }
    }
}