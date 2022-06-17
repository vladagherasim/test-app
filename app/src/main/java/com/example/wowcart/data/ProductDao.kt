package com.example.wowcart.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    //TODO: keep one new line after class declaration
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(product: ProductEntity)

    @Query("SELECT * FROM products_table")
    fun getFavoriteProducts(): Flow<List<ProductEntity>>

    @Query("DELETE FROM products_table WHERE id =:id")
    suspend fun deleteFavorite(id: Int)

    @Query("SELECT COUNT(*) FROM products_table")
    fun getFavoritesCount(): Flow<Int>
    //TODO: keep one new line after class body end
}