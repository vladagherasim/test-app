package com.example.wowcart.domain.useCases

import com.example.wowcart.domain.repositories.ProductsRepository
import javax.inject.Inject

class ChangeFavoriteStatusUseCase @Inject constructor(
    private val repository: ProductsRepository
) {
    suspend fun deleteFavorite(id: Int) {
        repository.deleteFavorite(id)
    }

    suspend fun addFavorite(id: Int) {
        repository.insert(id)
    }
}