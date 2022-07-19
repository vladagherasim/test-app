package com.example.wowcart.data.repos

import androidx.paging.*
import com.example.wowcart.R
import com.example.wowcart.data.Product
import com.example.wowcart.data.ProductApiService
import com.example.wowcart.data.ProductDao
import com.example.wowcart.domain.pagingSources.ProductPagingSource
import com.example.wowcart.ui.Item
import com.example.wowcart.ui.details.ItemImage
import com.example.wowcart.ui.details.ItemMiddleText
import com.example.wowcart.ui.details.ItemText
import com.example.wowcart.ui.feed.ItemProduct
import com.example.wowcart.utils.Dimen
import com.example.wowcart.utils.DimenS
import com.example.wowcart.utils.Margin
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProductsRepository @Inject constructor(
    private val productService: ProductApiService,
    private val productDao: ProductDao
) {

    private fun productPagingSource(coroutineScope: CoroutineScope) = Pager(
        config = PagingConfig(pageSize = 10),
        pagingSourceFactory = { ProductPagingSource(productService) }
    ).flow.cachedIn(coroutineScope)

    fun getProductsForFeed(coroutineScope: CoroutineScope): Flow<PagingData<ItemProduct>> {
        return combine(
            productPagingSource(coroutineScope),
            productDao.getFavoriteProducts()
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

    suspend fun getItemById(id: Int): List<Item> {
        val product = productService.getProductById(id)
        return mutableListOf<Item>().apply {
            add(
                ItemImage(tag = "101", product.mainImage)
            )
            add(
                ItemMiddleText(
                    tag = "102",
                    product.name,
                    product.details,
                    product.price.toDouble()
                )
            )
            add(
                ItemText(
                    tag = "103",
                    DimenS._14ssp,
                    R.font.open_sans_extra_bold,
                    R.color.title_text_color_07195c,
                    Margin.Only(top = Dimen._20sdp),
                    "INFORMATION"
                )
            )
            add(
                ItemText(
                    tag = "104",
                    DimenS._10ssp,
                    R.font.open_sans_regular,
                    R.color.common_text_424a56,
                    Margin.Only(top = Dimen._10sdp),
                    product.details
                )
            )
        }
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