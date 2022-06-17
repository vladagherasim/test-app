package com.example.wowcart.ui.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wowcart.data.repos.ProductsRepository
import com.example.wowcart.ui.Product
import com.example.wowcart.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductFavoritesViewModel @Inject constructor(private val repository: ProductsRepository) :
    ViewModel() {
    //TODO: make new line after each liveData block
    private val favoritesListener = createListedLiveData<Product>()
    val favorites = favoritesListener.toLiveData()
    //TODO: new line
    private val favoritesCountListener = createLiveData<Int>()
    val favoritesCount = favoritesCountListener.toLiveData()
    //TODO: new line
    private val exceptionListener = MutableLiveData<Exception>()
    val exceptions = exceptionListener.toLiveData()

    init {
        getFavorites()
        getFavoritesCount()
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

    private fun getFavoritesCount() {
        viewModelScope.launch(Dispatchers.IO) {
            launchOn {
                repository.getFavoritesCount()
            }.subscribeOver(exceptionListener) {
                collectLatest { favoritesCountListener.assignValue(it) }
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
