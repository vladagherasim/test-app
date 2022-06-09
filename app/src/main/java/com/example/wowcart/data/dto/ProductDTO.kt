package com.example.wowcart.data.dto

import com.google.gson.annotations.SerializedName

data class ProductDTO(
    @SerializedName("category")
    val category: Category,
    @SerializedName("colour")
    val colour: String,
    @SerializedName("details")
    val details: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("main_image")
    val mainImage: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price: Int,
    @SerializedName("size")
    val size: String
)