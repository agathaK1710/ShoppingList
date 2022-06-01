package com.android.shoppinglist.domain

import javax.inject.Inject

class AddShopItemUseCase @Inject constructor(private val listRepository: ShopListRepository) {
    suspend fun addShopItem(shopItem: ShopItem) {
        listRepository.addShopItem(shopItem)
    }
}