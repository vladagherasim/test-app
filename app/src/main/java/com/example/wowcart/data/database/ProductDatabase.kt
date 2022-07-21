package com.example.wowcart.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Product::class, Extra::class], version = 1)
abstract class ProductDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao

    companion object {
        const val DATABASE_NAME = "products_database"
    }
}