package com.example.wowcart.data.dto


import com.google.gson.annotations.SerializedName

data class ProductsResponse(
    @SerializedName("count")
    val count: Int,
    @SerializedName("current_page")
    val currentPage: Int,
    @SerializedName("per_page")
    val perPage: Int,
    @SerializedName("results")
    val results: List<ProductDTO>,
    @SerializedName("total_pages")
    val totalPages: Int
)