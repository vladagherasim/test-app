package com.example.wowcart.data.repos

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.wowcart.data.ProductApiService
import com.example.wowcart.data.database.Product
import com.example.wowcart.data.database.ProductDao
import com.example.wowcart.data.dto.ProductDTO
import com.example.wowcart.domain.pagingSources.ProductPagingSource
import com.example.wowcart.domain.repositories.ProductsRepository
import com.example.wowcart.models.LoginResponse
import com.example.wowcart.models.RegisterResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProductsRepositoryImpl @Inject constructor(
    private val productService: ProductApiService,
    private val productDao: ProductDao
) : ProductsRepository {
    private fun productPagingSource(coroutineScope: CoroutineScope) = Pager(
        config = PagingConfig(pageSize = 10),
        pagingSourceFactory = { ProductPagingSource(productService) }
    ).flow.cachedIn(coroutineScope)

    override fun getProductsForFeed(coroutineScope: CoroutineScope): Flow<PagingData<ProductDTO>> {
        return productPagingSource(coroutineScope)
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

    override suspend fun registerUser(register: RegisterResponse): RegisterResponse {
        val params = HashMap<String, Any>()
        params["email"] = register.email
        params["full_name"] = register.full_name
        params["password"] = register.password
        params["phone_number"] = register.phone_number
        params["profile_picture"] = register.profile_picture
        return productService.registerUser(params)
    }

    override suspend fun postLogin(email: String, password: String): LoginResponse {
        return productService.postLogin(email, password)
    }

}