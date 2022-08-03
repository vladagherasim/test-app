package com.example.wowcart.domain.useCases

import com.example.wowcart.domain.repositories.ProductsRepository
import com.example.wowcart.models.LoginResponse
import javax.inject.Inject

class PostLoginUseCase @Inject constructor(private val repository: ProductsRepository) {
    suspend operator fun invoke(email: String, password: String): LoginResponse{
        return repository.postLogin(email, password)
    }
}