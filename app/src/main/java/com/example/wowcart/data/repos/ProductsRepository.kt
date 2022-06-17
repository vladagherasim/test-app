package com.example.wowcart.data.repos

import android.util.Log
import com.example.wowcart.data.ProductApiService
import com.example.wowcart.data.ProductDao
import com.example.wowcart.data.ProductEntity
import com.example.wowcart.data.dto.ProductDTO
import com.example.wowcart.ui.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProductsRepository @Inject constructor(
    private val productService: ProductApiService,
    private val productDao: ProductDao
) {

    suspend fun getProductsForFeed(): Flow<List<Product>> {
        return combine(
            flowOf(productService.getProducts()),
            productDao.getFavoriteProducts()
        ) { remote, db ->
            val favIds = db.map { it.id }
            remote.results.map {
                Product(
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


    //TODO: after successful debugging remove unnecessary Logs
    suspend fun insert(productID: Int) {
        Log.d("Product", "product = $productID")
        val product = productService.getProductById(productID)
        val productEntity = ProductEntity(
            product.id,
            product.name,
            product.details,
            product.price.toDouble(),
            product.mainImage,
            1
        )
        productDao.insert(productEntity)
    }

    fun getFavorites(): Flow<List<Product>> {
        return productDao.getFavoriteProducts().map {
            it.map { product ->
                Product(
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
}