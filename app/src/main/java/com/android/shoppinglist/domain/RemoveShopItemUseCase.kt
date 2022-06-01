package com.android.shoppinglist.domain

import javax.inject.Inject

class RemoveShopItemUseCase @Inject constructor(private val listRepository: ShopListRepository) {
    suspend fun removeShopItem(shopItem: ShopItem) {
        listRepository.removeShopItem(shopItem)
    }
}