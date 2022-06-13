package com.example.wowcart.data

import com.example.wowcart.data.dto.ProductsResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ProductApiService {
    @GET("/products")
    suspend fun getProducts(): ProductsResponse
}

