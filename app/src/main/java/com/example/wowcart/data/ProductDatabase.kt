package com.example.wowcart.data

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.wowcart.data.modules.DatabaseModule

@Database(entities = [ProductEntity::class], version = 1, exportSchema = false)
abstract class ProductDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao

    companion object {
        const val DATABASE_NAME = "products_database"
    }
}