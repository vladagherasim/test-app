package com.example.wowcart.data.repos

import com.example.wowcart.data.ProductApiService
import com.example.wowcart.data.dto.ProductDTO

class ProductsRepository (private val productService: ProductApiService) {

    suspend fun getProducts() : List<ProductDTO>{
        val response = productService.getProducts()
        return response.results
    }
}