package com.android.shoppinglist.domain

interface ShopListRepository {
    fun getShopList(): List<ShopItem>
    fun removeShopItem(item: ShopItem)
    fun getShopItemForId(id: Int): ShopItem
    fun editShopItem(item: ShopItem)
    fun addShopItem(item: ShopItem)
}