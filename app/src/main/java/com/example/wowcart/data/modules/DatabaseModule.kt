package com.example.wowcart.data.modules

import android.content.Context
import androidx.room.Room
import com.example.wowcart.data.ProductDao
import com.example.wowcart.data.ProductDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context.applicationContext,
            ProductDatabase::class.java,
            ProductDatabase.DATABASE_NAME
        ).build()

    @Provides
    @Singleton
     fun provideDao(database: ProductDatabase): ProductDao = database.productDao()
}
