package com.example.wowcart.domain

import androidx.lifecycle.*
import com.example.wowcart.data.ProductApi
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {
    private val _status = MutableLiveData<String>()
    val status : LiveData<String> = _status

    init {
        getData()
    }

    fun getData() {
        viewModelScope.launch {
            try {
                val listResult = ProductApi.retrofitService.getData()
                _status.value = "Success"
            } catch (e: Exception) {
                _status.value = "Failure: ${e.message}"
            }
        }
    }
}