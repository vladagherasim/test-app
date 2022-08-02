package com.example.wowcart.presentation.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wowcart.domain.useCases.ChangeFavoriteStatusUseCase
import com.example.wowcart.domain.useCases.GetProductUseCase
import com.example.wowcart.utils.*
import com.example.wowcart.utils.common.Item
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val getProductUseCase: GetProductUseCase,
    private val changeFavoriteStatusUseCase: ChangeFavoriteStatusUseCase
) : ViewModel() {
    private val itemListener = createListedLiveData<Item>()
    val item = itemListener.toLiveData()

    private val exceptionListener = MutableLiveData<Exception>()
    val exceptions = exceptionListener.toLiveData()

    private val isFavoriteListener = createLiveData<Boolean>()
    val isFavorite = isFavoriteListener.toLiveData()


    fun getItemById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            launchOn {
                getProductUseCase.getProductByID(id)
            }.subscribeOver(exceptionListener) {
                this.collectLatest {
                    itemListener.assignValue(it)
                }
            }
        }
    }

    fun getItemInFavorites(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            launchOn {
                getProductUseCase.getItemInFavorites(id)
            }.subscribeOver(exceptionListener) {
                collectLatest {
                    isFavoriteListener.assignValue(it)
                }
            }
        }
    }

    fun insert(productID: Int) {
        viewModelScope.launch { changeFavoriteStatusUseCase.addFavorite(productID) }
    }

    fun delete(productID: Int) {
        viewModelScope.launch { changeFavoriteStatusUseCase.deleteFavorite(productID) }
    }

}

