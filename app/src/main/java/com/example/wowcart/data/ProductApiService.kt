package com.example.wowcart.data

import com.example.wowcart.data.dto.ProductDTO
import com.example.wowcart.data.dto.ProductsResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductApiService {
    @GET("/products")
    suspend fun getProducts(): ProductsResponse

    @GET("/products/{id}")
    suspend fun getProductById(@Path("id") id: Int): ProductDTO
}

