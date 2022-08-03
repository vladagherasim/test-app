package com.example.wowcart.data

import com.example.wowcart.data.dto.ProductDTO
import com.example.wowcart.data.dto.ProductsResponse
import com.example.wowcart.models.LoginResponse
import com.example.wowcart.models.RegisterResponse
import retrofit2.http.*

interface ProductApiService {
    @GET("/products")
    suspend fun getProducts(@Query("page") page: Int): ProductsResponse

    @GET("/products/{id}")
    suspend fun getProductById(@Path("id") id: Int): ProductDTO

    @POST("/users/login")
    suspend fun postLogin(@Query("email") email: String, @Query("password") password: String): LoginResponse

    @POST("/users/register")
    suspend fun registerUser(@Body params: HashMap<String, Any>): RegisterResponse


}

