package com.example.wowcart.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(product: Product)

    @Query("SELECT * FROM products_table")
    fun getFavoriteProducts(): Flow<List<Product>>

    @Query("DELETE FROM products_table WHERE id =:id")
    suspend fun deleteFavorite(id: Int)

    @Query("SELECT COUNT(*) FROM products_table")
    fun getFavoritesCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM products_table WHERE id =:id")
    fun getItemInFavorites(id:Int): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(extra: Extra)

    @Query("SELECT * FROM extra_table WHERE id=1")
    fun getExtra() : Flow<Extra>


}