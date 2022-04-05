package com.android.shoppinglist.domain

import androidx.lifecycle.LiveData

interface ShopListRepository {
    fun getShopList(): LiveData<List<ShopItem>>
    suspend fun removeShopItem(item: ShopItem)
    suspend fun getShopItemForId(id: Int): ShopItem
    suspend fun editShopItem(item: ShopItem)
    suspend fun addShopItem(item: ShopItem)
}