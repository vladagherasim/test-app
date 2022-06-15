package com.example.wowcart.ui.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wowcart.data.repos.ProductsRepository
import com.example.wowcart.ui.Product
import com.example.wowcart.utils.assignValue
import com.example.wowcart.utils.createListedLiveData
import com.example.wowcart.utils.launchOn
import com.example.wowcart.utils.toLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductDetailsViewModel @Inject constructor(private val repository: ProductsRepository) :
    ViewModel() {
    private val itemListener = createListedLiveData<Product>()
    val item = itemListener.toLiveData()
    private val exceptionListener = MutableLiveData<Exception>()
    val exceptions = exceptionListener.toLiveData()

    init {
        getFavorites()
    }

    private fun getFavorites(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            launchOn {
                repository.getItemById(id)
            }.subscribeOver(exceptionListener) {
                    itemListener.assignValue(it)
            }
        }
    }
}