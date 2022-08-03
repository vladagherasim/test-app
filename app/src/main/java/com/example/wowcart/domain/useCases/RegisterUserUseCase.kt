package com.example.wowcart.domain.useCases

import com.example.wowcart.domain.repositories.ProductsRepository
import com.example.wowcart.models.RegisterResponse
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(
    private val repository: ProductsRepository
) {
    suspend operator fun invoke(response: RegisterResponse): RegisterResponse {
        return repository.registerUser(response)
    }
}