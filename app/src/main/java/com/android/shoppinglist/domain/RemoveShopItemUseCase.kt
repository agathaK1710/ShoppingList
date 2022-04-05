package com.android.shoppinglist.domain

class RemoveShopItemUseCase(private val listRepository: ShopListRepository) {
    suspend fun removeShopItem(shopItem: ShopItem) {
        listRepository.removeShopItem(shopItem)
    }
}