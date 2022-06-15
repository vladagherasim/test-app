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
class ProductFavoritesViewModel @Inject constructor(private val repository: ProductsRepository) :
    ViewModel() {
    private val favoritesListener = createListedLiveData<Product>()
    val favorites = favoritesListener.toLiveData()
    private val exceptionListener = MutableLiveData<Exception>()
    val exceptions = exceptionListener.toLiveData()

    init {
        getFavorites()
    }

    private fun getFavorites() {
        viewModelScope.launch(Dispatchers.IO) {
            launchOn {
                repository.getFavorites()
            }.subscribeOver(exceptionListener) {
                collectLatest {
                    favoritesListener.assignValue(it)
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
