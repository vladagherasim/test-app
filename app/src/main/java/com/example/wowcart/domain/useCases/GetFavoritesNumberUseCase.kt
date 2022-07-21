package com.example.wowcart.domain.useCases

import com.example.wowcart.domain.repositories.ProductsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoritesNumberUseCase @Inject constructor(
    private val productsRepository: ProductsRepository
) {
    operator fun invoke(): Flow<Int> {
        return productsRepository.getFavoritesCount()
    }
}