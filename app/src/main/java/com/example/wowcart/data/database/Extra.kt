package com.example.wowcart.data.database

import androidx.room.Entity
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

@Entity(tableName = "extra_table")
class Extra(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "value") val value: Int
)