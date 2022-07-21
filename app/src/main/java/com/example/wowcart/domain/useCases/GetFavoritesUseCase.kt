package com.example.wowcart.domain.useCases

import com.example.wowcart.domain.repositories.ProductsRepository
import com.example.wowcart.presentation.feed.ItemProduct
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetFavoritesUseCase @Inject constructor(
    private val repository: ProductsRepository,
) {
    operator fun invoke(): Flow<List<ItemProduct>> {
        return repository.getFavorites().map {
            it.map { product ->
                ItemProduct(
                    product.id,
                    product.image,
                    product.name,
                    product.details,
                    product.price,
                    true
                )
            }
        }
    }
}