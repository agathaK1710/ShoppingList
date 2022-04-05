package com.android.shoppinglist.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shopItem")
data class ShopItemDBModel(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    val count: Int,
    val name: String,
    val enabled: Boolean
)