package com.example.wowcart.data.repos

import androidx.paging.*
import com.example.wowcart.data.ProductApiService
import com.example.wowcart.data.database.Product
import com.example.wowcart.data.database.ProductDao
import com.example.wowcart.data.dto.ProductDTO
import com.example.wowcart.domain.pagingSources.ProductPagingSource
import com.example.wowcart.domain.repositories.ProductsRepository
import com.example.wowcart.presentation.feed.ItemProduct
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProductsRepositoryImpl @Inject constructor(
    private val productService: ProductApiService,
    private val productDao: ProductDao
) : ProductsRepository {
    fun productPagingSource(coroutineScope: CoroutineScope) = Pager(
        config = PagingConfig(pageSize = 10),
        pagingSourceFactory = { ProductPagingSource(productService) }
    ).flow.cachedIn(coroutineScope)

    override fun getProductsForFeed(coroutineScope: CoroutineScope): Flow<PagingData<ProductDTO>> {
        return productPagingSource(coroutineScope)
            /*productDao.getFavoriteProducts()
        ) { remote, db ->
            val favIds = db.map { it.id }
            remote.map {
                ItemProduct(
                    it.id,
                    it.mainImage,
                    it.name,
                    it.details,
                    it.price.toDouble(),
                    it.id in favIds
                )
            }
        }*/
    }

    override suspend fun insert(productID: Int) {
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

    override fun getFavorites(): Flow<List<Product>> {
        return productDao.getFavoriteProducts()
    }

    override suspend fun getItemById(id: Int): ProductDTO {
       return productService.getProductById(id)
    }

    override suspend fun deleteFavorite(id: Int) {
        productDao.deleteFavorite(id)
    }

    override fun getFavoritesCount(): Flow<Int> {
        return productDao.getFavoritesCount()
    }

    override fun getItemInFavorites(id: Int): Flow<Boolean> {
        return productDao.getItemInFavorites(id).map {
            it == 1
        }
    }

}