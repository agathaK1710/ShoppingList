package com.android.shoppinglist.domain

class AddShopItemUseCase(private val listRepository: ShopListRepository) {
    suspend fun addShopItem(shopItem: ShopItem) {
        listRepository.addShopItem(shopItem)
    }
}