package com.android.shoppinglist.domain

import javax.inject.Inject

class GetShopItemForId @Inject constructor(private val listRepository: ShopListRepository) {
    suspend fun getShopItemForId(id: Int) : ShopItem {
        return listRepository.getShopItemForId(id)
    }
}