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
import javax.inject.Inject

class ProductsRepository @Inject constructor(
    private val productService: ProductApiService,
    private val productDao: ProductDao
) {

    suspend fun getProducts(): List<ProductDTO> {
        val response = productService.getProducts()
        return response.results
    }

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

    suspend fun getFavorites(): Flow<List<ProductEntity>> {
        return productDao.getFavoriteProducts()
    }

    suspend fun deleteFavorite(id: Int) {
        productDao.deleteFavorite(id)
    }
}