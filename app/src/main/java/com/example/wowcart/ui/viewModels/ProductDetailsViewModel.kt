package com.example.wowcart.ui.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wowcart.data.dto.ProductDTO
import com.example.wowcart.data.repos.ProductsRepository
import com.example.wowcart.utils.assignValue
import com.example.wowcart.utils.createLiveData
import com.example.wowcart.utils.launchOn
import com.example.wowcart.utils.toLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(private val repository: ProductsRepository) :
    ViewModel() {
    private val itemListener = createLiveData<ProductDTO>()
    val item = itemListener.toLiveData()
    private val exceptionListener = MutableLiveData<Exception>()
    val exceptions = exceptionListener.toLiveData()

    fun getItemById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            launchOn {
                repository.getItemById(id)
            }.subscribeOver(exceptionListener) {
                itemListener.assignValue(this)
            }
        }
    }
}