package com.example.wowcart.presentation.favorites

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wowcart.domain.useCases.ChangeFavoriteStatusUseCase
import com.example.wowcart.domain.useCases.GetFavoritesNumberUseCase
import com.example.wowcart.domain.useCases.GetFavoritesUseCase
import com.example.wowcart.presentation.feed.ItemProduct
import com.example.wowcart.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductFavoritesViewModel @Inject constructor(
    private val numberUseCase: GetFavoritesNumberUseCase,
    private val favoritesUseCase: GetFavoritesUseCase,
    private val changeUseCase: ChangeFavoriteStatusUseCase
) :
    ViewModel() {
    private val favoritesListener = createListedLiveData<ItemProduct>()
    val favorites = favoritesListener.toLiveData()

    private val favoritesCountListener = createLiveData<Int>()
    val favoritesCount = favoritesCountListener.toLiveData()

    private val exceptionListener = MutableLiveData<Exception>()
    val exceptions = exceptionListener.toLiveData()

    init {
        getFavorites()
        getFavoritesCount()
    }

    private fun getFavorites() {
        viewModelScope.launch(Dispatchers.IO) {
            launchOn {
                favoritesUseCase()
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
                numberUseCase()
            }.subscribeOver(exceptionListener) {
                collectLatest { favoritesCountListener.assignValue(it) }
            }
        }
    }

    fun insert(productID: Int) {
        viewModelScope.launch { changeUseCase.addFavorite(productID) }
    }

    fun delete(productID: Int) {
        viewModelScope.launch { changeUseCase.deleteFavorite(productID) }
    }
}
