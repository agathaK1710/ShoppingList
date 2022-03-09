package com.android.shoppinglist.domain

class GetShopItemForId(private val listRepository: ShopListRepository) {
    fun getShopItemForId(id: Int) : ShopItem {
        return listRepository.getShopItemForId(id)
    }
}