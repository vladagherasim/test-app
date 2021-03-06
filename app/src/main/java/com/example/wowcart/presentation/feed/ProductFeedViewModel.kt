package com.example.wowcart.presentation.feed

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.wowcart.domain.useCases.ChangeFavoriteStatusUseCase
import com.example.wowcart.domain.useCases.GetProductFeedUseCase
import com.example.wowcart.utils.toLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProductFeedViewModel @Inject constructor(
    private val getProductFeedUseCase: GetProductFeedUseCase,
    private val changeFavoriteStatusUseCase: ChangeFavoriteStatusUseCase
) : ViewModel() {
    private val exceptionListener = MutableLiveData<Exception>()
    val exceptions = exceptionListener.toLiveData()

    init {
        getProducts()
    }

    fun getProducts(): Flow<PagingData<ItemProduct>> {
        return getProductFeedUseCase(viewModelScope)
    }

    fun insert(productID: Int) {
        viewModelScope.launch { changeFavoriteStatusUseCase.addFavorite(productID) }
    }

    fun delete(productID: Int) {
        viewModelScope.launch { changeFavoriteStatusUseCase.deleteFavorite(productID) }
    }
}