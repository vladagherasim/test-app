package com.example.wowcart.domain.useCases

import androidx.paging.PagingData
import androidx.paging.map
import com.example.wowcart.data.database.ProductDao
import com.example.wowcart.domain.repositories.ProductsRepository
import com.example.wowcart.presentation.feed.ItemProduct
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetProductFeedUseCase @Inject constructor(
    private val productsRepository: ProductsRepository,
    private val productDao: ProductDao
) {
    operator fun invoke(coroutineScope: CoroutineScope): Flow<PagingData<ItemProduct>> {
        return combine(
            productsRepository.getProductsForFeed(coroutineScope),
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

}