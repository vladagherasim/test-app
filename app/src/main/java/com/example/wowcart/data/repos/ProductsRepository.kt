package com.example.wowcart.data.repos

import com.example.wowcart.data.ProductApiService
import com.example.wowcart.data.ProductDao
import com.example.wowcart.data.ProductEntity
import com.example.wowcart.data.dto.ProductDTO
import javax.inject.Inject

class ProductsRepository @Inject constructor(
    private val productService: ProductApiService,
    private val productDao: ProductDao
) {

    suspend fun getProducts(): List<ProductDTO> {
        val response = productService.getProducts()
        return response.results
    }

    suspend fun insert(product: ProductEntity) {
        productDao.insert(product)
    }

}