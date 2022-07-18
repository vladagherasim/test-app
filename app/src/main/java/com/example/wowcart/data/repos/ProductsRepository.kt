package com.example.wowcart.data.repos

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.wowcart.data.ProductApiService
import com.example.wowcart.data.ProductDao
import com.example.wowcart.data.Product
import com.example.wowcart.data.dto.ProductDTO
import com.example.wowcart.ui.ItemProduct
import com.example.wowcart.ui.pagingSources.ProductPagingSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProductsRepository @Inject constructor(
    private val productService: ProductApiService,
    private val productDao: ProductDao
) {

    private fun productPagingSource(coroutineScope: CoroutineScope) = Pager(
        config = PagingConfig(pageSize = 10),
        pagingSourceFactory = { ProductPagingSource(productService)}
    ).flow.cachedIn(coroutineScope)

    suspend fun getProductsForFeed(): Flow<List<ItemProduct>> {
        return combine(
            flowOf(productService.getProducts()),
            productDao.getFavoriteProducts()
        ) { remote, db ->
            val favIds = db.map { it.id }
            remote.results.map {
                ItemProduct(
                    it.id,
                    it.mainImage,
                    it.name,
                    it.details,
                    it.price.toDouble(),
                    it.id in favIds
                )
            }
        }
    }

    suspend fun insert(productID: Int) {
        val product = productService.getProductById(productID)
        val productEntity = Product(
            product.id,
            product.name,
            product.details,
            product.price.toDouble(),
            product.mainImage,
            1
        )
        productDao.insert(productEntity)
    }

    fun getFavorites(): Flow<List<ItemProduct>> {
        return productDao.getFavoriteProducts().map {
            it.map { product ->
                ItemProduct(
                    product.id, product.image, product.name, product.details, product.price, true
                )
            }
        }
    }

    suspend fun getItemById(id: Int): ProductDTO {
        return productService.getProductById(id)
    }

    suspend fun deleteFavorite(id: Int) {
        productDao.deleteFavorite(id)
    }

    fun getFavoritesCount(): Flow<Int> {
        return productDao.getFavoritesCount()
    }

    fun getItemInFavorites(id: Int): Flow<Boolean> {
        return productDao.getItemInFavorites(id).map {
            it == 1
        }
    }
}