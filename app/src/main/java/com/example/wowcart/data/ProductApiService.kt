package com.example.wowcart.data

import com.example.wowcart.data.dto.ProductDTO
import com.example.wowcart.data.dto.ProductsResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.http.GET
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "http://mobile-shop-api.hiring.devebs.net/products"

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .client(OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }).build())
    .build()

interface ProductApiService {
    @GET("/products")
    suspend fun getData() : ProductsResponse
}

object ProductApi {
    val retrofitService: ProductApiService by lazy { retrofit.create(ProductApiService::class.java) }
}