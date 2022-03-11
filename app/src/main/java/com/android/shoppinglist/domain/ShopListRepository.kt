package com.android.shoppinglist.domain

import androidx.lifecycle.LiveData

interface ShopListRepository {
    fun getShopList(): LiveData<List<ShopItem>>
    fun removeShopItem(item: ShopItem)
    fun getShopItemForId(id: Int): ShopItem
    fun editShopItem(item: ShopItem)
    fun addShopItem(item: ShopItem)
}