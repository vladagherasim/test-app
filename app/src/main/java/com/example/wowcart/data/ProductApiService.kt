package com.example.wowcart.data

import com.example.wowcart.data.dto.ProductDTO
import com.example.wowcart.data.dto.ProductsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductApiService {
    @GET("/products")
    suspend fun getProducts(@Query("page") page: Int): ProductsResponse

    @GET("/products/{id}")
    suspend fun getProductById(@Path("id") id: Int): ProductDTO
}

