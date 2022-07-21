package com.example.wowcart.di.modules

import com.example.wowcart.data.repos.ProductsRepositoryImpl
import com.example.wowcart.domain.repositories.ProductsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoriesModule {

    @Binds
    abstract fun bindProductRepo(repo: ProductsRepositoryImpl): ProductsRepository

}