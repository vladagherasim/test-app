package com.example.wowcart.domain.repositories

import androidx.paging.PagingData
import com.example.wowcart.data.database.Product
import com.example.wowcart.data.dto.ProductDTO
import com.example.wowcart.utils.common.Item
import com.example.wowcart.presentation.feed.ItemProduct
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface ProductsRepository {

    fun getProductsForFeed(coroutineScope: CoroutineScope): Flow<PagingData<ProductDTO>>

    suspend fun insert(productID: Int)

    fun getFavorites(): Flow<List<Product>>

    suspend fun getItemById(id: Int): ProductDTO

    suspend fun deleteFavorite(id: Int)

    fun getFavoritesCount(): Flow<Int>

    fun getItemInFavorites(id: Int): Flow<Boolean>

}