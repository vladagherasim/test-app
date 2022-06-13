package com.example.wowcart.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products_table")
class ProductEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "details") val details: String,
    @ColumnInfo(name = "price") val price: Double,
    @ColumnInfo(name = "image") val image: String,
    @ColumnInfo(name = "cart_count") val cartCount: Int
)
