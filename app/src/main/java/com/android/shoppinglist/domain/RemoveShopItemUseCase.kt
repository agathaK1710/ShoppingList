package com.android.shoppinglist.domain

class RemoveShopItemUseCase(private val listRepository: ShopListRepository) {
    fun removeShopItem(shopItem: ShopItem) {
        listRepository.removeShopItem(shopItem)
    }
}